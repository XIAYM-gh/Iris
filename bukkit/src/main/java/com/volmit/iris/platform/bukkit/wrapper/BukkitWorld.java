package com.volmit.iris.platform.bukkit.wrapper;

import com.volmit.iris.platform.PlatformBiome;
import com.volmit.iris.platform.PlatformBlock;
import com.volmit.iris.platform.PlatformChunk;
import com.volmit.iris.platform.PlatformPlayer;
import com.volmit.iris.platform.PlatformWorld;
import com.volmit.iris.util.WorldHeight;
import lombok.Data;
import org.bukkit.World;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class BukkitWorld implements PlatformWorld {
    private final World delegate;
    private final WorldHeight worldHeight;

    private BukkitWorld(World delegate) {
        this.delegate = delegate;
        this.worldHeight = new WorldHeight(delegate.getMinHeight(), delegate.getMaxHeight());
    }

    @Override
    public WorldHeight getHeight() {
        return worldHeight;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public File getFolder() {
        return delegate.getWorldFolder();
    }

    @Override
    public Iterable<PlatformPlayer> getPlayers() {
        //noinspection Convert2MethodRef
        return delegate.getPlayers().stream().map(i -> BukkitPlayer.of(i)).collect(Collectors.toList());
    }

    @Override
    public Iterable<PlatformChunk> getLoadedChunks() {
        //noinspection Convert2MethodRef
        return Arrays.stream(delegate.getLoadedChunks()).map(i -> BukkitChunk.of(i)).collect(Collectors.toList());
    }

    @Override
    public PlatformChunk getOrLoadChunk(int x, int z) {
        return BukkitChunk.of(delegate.getChunkAt(x, z));
    }

    @Override
    public PlatformBlock getBlock(int x, int y, int z) {
        return BukkitBlock.of(delegate.getBlockAt(x, y, z).getBlockData());
    }

    @Override
    public PlatformBiome getBiome(int x, int y, int z) {
        return BukkitBiome.of(delegate.getBiome(x, y, z));
    }

    @Override
    public long getSeed() {
        return delegate.getSeed();
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return delegate.isChunkLoaded(x, z);
    }

    @Override
    public void setBlock(int x, int y, int z, PlatformBlock block) {
        delegate.setBlockData(x, y, z, ((BukkitBlock) block).getDelegate());
    }

    @Override
    public void setBiome(int x, int y, int z, PlatformBiome biome) {
        delegate.setBiome(x, y, z, ((BukkitBiome) biome).getDelegate());
    }

    public static BukkitWorld of(World world) {
        return new BukkitWorld(world);
    }
}