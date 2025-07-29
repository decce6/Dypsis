package me.decce.dypsis.batching;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;

public class BatchingManager {
    private static final ObjectArrayList<BatchingTessellator> tessellators = new ObjectArrayList<>();
    private static boolean batching;
    private static BatchingTessellator tessellator;

    public static boolean isBatching() {
        return batching;
    }

    public static void register(BatchingTessellator tessellator) {
        tessellators.add(tessellator);
    }

    public static void begin(BatchingTessellator tessellator, int drawMode, VertexFormat format) {
        batching = true;
        BatchingManager.tessellator = tessellator;
        tessellator.getBuffer().begin(drawMode, format);
    }

    public static void end() {
        if (tessellator == null) {
            throw new IllegalStateException("BatchingManager end without begin!");
        }
        tessellator = null;
        batching = false;
    }

    public static void flushAll() {
        for (int i = 0; i < tessellators.size(); i++) {
            tessellators.get(i).flush();
        }
    }

    public static BatchingTessellator getTessellator() {
        return tessellator;
    }

    public static boolean isBatched(TileEntity tileEntity) {
        for (int i = 0; i < tessellators.size(); i++) {
            if (tessellators.get(i).canBatch(tileEntity)) {
                return true;
            }
        }
        return false;
    }
}
