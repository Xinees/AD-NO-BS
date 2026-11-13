#!/usr/bin/env python3
import argparse
import hashlib
import json
import os
from pathlib import Path
import tempfile
import zipfile

LIBRARY_GROUP = "local"
LIBRARY_ARTIFACT = "astemirlib_re-1.20.1-forge"
LIBRARY_VERSION = "1.2.98-SNAPSHOT"
LIBRARY_PATH = (
    "META-INF/jarjar/"
    "astemirlib_re-1.20.1-forge-1.2.98-SNAPSHOT.jar"
)
EXPECTED_LIBRARY_SHA256 = (
    "906198b220958f352c308ec9a2178342b7af90198ade206d1596eb336a89a0a3"
)


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def read_mod_id(jar: Path, expected_mod_id: str) -> None:
    with zipfile.ZipFile(jar, "r") as archive:
        try:
            mods_toml = archive.read("META-INF/mods.toml").decode("utf-8")
        except KeyError as exc:
            raise RuntimeError("Astemir RE input has no META-INF/mods.toml") from exc

    expected = f'modId = "{expected_mod_id}"'
    if expected not in mods_toml:
        raise RuntimeError(
            f"Astemir RE input does not declare {expected_mod_id!r}"
        )

    if f'version = "{LIBRARY_VERSION}"' not in mods_toml:
        raise RuntimeError(
            f"Astemir RE input is not version {LIBRARY_VERSION}"
        )


def make_metadata(existing: bytes | None) -> bytes:
    if existing:
        metadata = json.loads(existing.decode("utf-8"))
    else:
        metadata = {"jars": []}

    jars = metadata.setdefault("jars", [])
    if not isinstance(jars, list):
        raise RuntimeError("Jar-in-Jar metadata 'jars' must be an array")

    # Remove only an older entry for this exact Astemir coordinate/path.
    jars[:] = [
        item
        for item in jars
        if not (
            item.get("identifier", {}).get("group") == LIBRARY_GROUP
            and item.get("identifier", {}).get("artifact") == LIBRARY_ARTIFACT
        )
        and item.get("path") != LIBRARY_PATH
    ]

    jars.append(
        {
            "identifier": {
                "group": LIBRARY_GROUP,
                "artifact": LIBRARY_ARTIFACT,
            },
            "version": {
                "range": f"[{LIBRARY_VERSION},)",
                "artifactVersion": LIBRARY_VERSION,
            },
            "path": LIBRARY_PATH,
            "isObfuscated": False,
        }
    )
    return (json.dumps(metadata, indent=2) + "\n").encode("utf-8")


def embed(base_jar: Path, library_jar: Path, output_jar: Path) -> None:
    if not base_jar.is_file():
        raise FileNotFoundError(base_jar)
    if not library_jar.is_file():
        raise FileNotFoundError(library_jar)

    library_hash = sha256_file(library_jar)
    if library_hash != EXPECTED_LIBRARY_SHA256:
        raise RuntimeError(
            "Astemir RE input SHA-256 mismatch: "
            f"expected {EXPECTED_LIBRARY_SHA256}, got {library_hash}"
        )

    read_mod_id(library_jar, "astemirlib_re")

    output_jar.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.NamedTemporaryFile(
        prefix=output_jar.stem + "-",
        suffix=".tmp.jar",
        dir=output_jar.parent,
        delete=False,
    ) as temporary:
        temporary_path = Path(temporary.name)

    try:
        with zipfile.ZipFile(base_jar, "r") as source:
            bad_entry = source.testzip()
            if bad_entry:
                raise RuntimeError(f"Base JAR is corrupt at {bad_entry}")

            names = set(source.namelist())
            if LIBRARY_PATH in names:
                raise RuntimeError(f"Base JAR already contains {LIBRARY_PATH}")

            metadata_name = "META-INF/jarjar/metadata.json"
            existing_metadata = (
                source.read(metadata_name) if metadata_name in names else None
            )
            new_metadata = make_metadata(existing_metadata)

            with zipfile.ZipFile(
                temporary_path,
                "w",
                compression=zipfile.ZIP_DEFLATED,
                compresslevel=9,
            ) as target:
                for info in source.infolist():
                    if info.filename == metadata_name:
                        target.writestr(info, new_metadata)
                    else:
                        target.writestr(info, source.read(info.filename))

                target.writestr(
                    LIBRARY_PATH,
                    library_jar.read_bytes(),
                    compress_type=zipfile.ZIP_STORED,
                )

        with zipfile.ZipFile(temporary_path, "r") as result:
            bad_entry = result.testzip()
            if bad_entry:
                raise RuntimeError(f"Embedded output is corrupt at {bad_entry}")

            result_names = set(result.namelist())
            if LIBRARY_PATH not in result_names:
                raise RuntimeError("Astemir RE nested JAR is missing from output")

            metadata = json.loads(result.read("META-INF/jarjar/metadata.json"))
            matches = [
                item
                for item in metadata.get("jars", [])
                if item.get("path") == LIBRARY_PATH
            ]
            if len(matches) != 1:
                raise RuntimeError("Astemir RE Jar-in-Jar metadata is not unique")

            embedded_hash = hashlib.sha256(result.read(LIBRARY_PATH)).hexdigest()
            if embedded_hash != EXPECTED_LIBRARY_SHA256:
                raise RuntimeError("Embedded Astemir RE bytes do not match input")

        os.replace(temporary_path, output_jar)
    except Exception:
        temporary_path.unlink(missing_ok=True)
        raise

    print(f"Embedded library: {LIBRARY_PATH}")
    print(f"Library SHA-256: {library_hash}")
    print(f"Output JAR: {output_jar}")
    print(f"Output size: {output_jar.stat().st_size} bytes")


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Embed the untouched Astemir RE Forge mod as a Jar-in-Jar dependency."
    )
    parser.add_argument("base_jar", type=Path)
    parser.add_argument("library_jar", type=Path)
    parser.add_argument("output_jar", type=Path)
    args = parser.parse_args()
    embed(args.base_jar, args.library_jar, args.output_jar)


if __name__ == "__main__":
    main()
