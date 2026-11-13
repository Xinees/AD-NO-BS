/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.PackOutput
 */
package com.eternal_creations.archies_dino_mod.data.util;

import com.eternal_creations.archies_dino_mod.data.util.DataProviderFactory;
import com.eternal_creations.archies_dino_mod.data.util.PlatformSpecific;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiPlatformProvider<T extends DataProvider & PlatformSpecific>
implements DataProvider {
    private final T fabricProvider;
    private final T forgeProvider;
    private final PackOutput forgeOutput;
    private final PackOutput fabricOutput;

    MultiPlatformProvider(DataProviderFactory<T> factory, PackOutput forgeOutput, PackOutput fabricOutput) {
        this.forgeOutput = forgeOutput;
        this.fabricOutput = fabricOutput;
        this.forgeProvider = factory.create(forgeOutput);
        this.fabricProvider = factory.create(fabricOutput);
        ((PlatformSpecific)this.forgeProvider).setEnvironment(PlatformSpecific.Environment.FORGE);
        ((PlatformSpecific)this.fabricProvider).setEnvironment(PlatformSpecific.Environment.FABRIC);
    }

    public T getFabricProvider() {
        return this.fabricProvider;
    }

    public T getForgeProvider() {
        return this.forgeProvider;
    }

    public CompletableFuture<?> m_213708_(CachedOutput cachedOutput) {
        ConcurrentHashMap.KeySetView written = ConcurrentHashMap.newKeySet();
        CachedOutput tracking = (path, data, hash) -> {
            written.add(path.toAbsolutePath().normalize());
            cachedOutput.m_213871_(path, data, hash);
        };
        return CompletableFuture.allOf(this.forgeProvider.m_213708_(tracking), this.fabricProvider.m_213708_(tracking)).thenRun(() -> {
            this.purgeStale(this.forgeOutput.m_245114_(), "forge", written);
            this.purgeStale(this.fabricOutput.m_245114_(), "fabric", written);
        });
    }

    private void purgeStale(Path outputFolder, String platform, Set<Path> written) {
        Path root = outputFolder.toAbsolutePath().normalize();
        String safeName = this.m_6055_().replaceAll("[^A-Za-z0-9]+", "_");
        Path manifest = root.resolve(".cache").resolve("multiplatform_" + platform + "_" + safeName + ".txt");
        Set current = written.stream().filter(p -> p.startsWith(root)).map(p -> root.relativize((Path)p).toString().replace('\\', '/')).collect(Collectors.toCollection(TreeSet::new));
        try {
            if (Files.exists(manifest, new LinkOption[0])) {
                for (String rel : Files.readAllLines(manifest)) {
                    if (rel.isBlank() || current.contains(rel)) continue;
                    Files.deleteIfExists(root.resolve(rel));
                }
            }
            Files.createDirectories(manifest.getParent(), new FileAttribute[0]);
            Files.write(manifest, (Iterable<? extends CharSequence>)current, new OpenOption[0]);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String m_6055_() {
        String providerName = this.forgeProvider.m_6055_();
        if (providerName.equals(this.fabricProvider.m_6055_())) {
            return "MultiPlatformProvider [" + providerName + "]";
        }
        return String.format("MultiPlatformProvider [%s / %s]", providerName, this.fabricProvider.m_6055_());
    }

    public static <T extends DataProvider & PlatformSpecific> MultiPlatformProvider<T> of(DataProviderFactory<T> factory, PackOutput forgeOutput, PackOutput fabricOutput) {
        return new MultiPlatformProvider<T>(factory, forgeOutput, fabricOutput);
    }
}

