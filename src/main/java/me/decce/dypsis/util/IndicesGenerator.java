package me.decce.dypsis.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.renderer.GLAllocation;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IndicesGenerator {
    private static final int INITIAL_CAPACITY = 24;
    private static final IntArrayList indices = new IntArrayList();
    private static ByteBuffer byteBuffer;
    private static IntBuffer intBuffer;

    private static void validate() {
        if (byteBuffer == null || intBuffer.remaining() < indices.size()) {
            byteBuffer = GLAllocation.createDirectByteBuffer(4 * Math.max(INITIAL_CAPACITY, indices.size()));
            intBuffer = byteBuffer.asIntBuffer();
        }
        byteBuffer.clear();
        intBuffer.clear();
    }

    public static ByteBuffer generate(int len) {
        indices.clear();
        int j = 0;
        for (int i = 0; i < len / 4; i++) {
            indices.add(j);
            indices.add(j + 1);
            indices.add(j + 3);
            indices.add(j + 1);
            indices.add(j + 2);
            indices.add(j + 3);
            j += 4;
        }
        validate();
        intBuffer.put(indices.toIntArray()).flip();
        byteBuffer.limit(intBuffer.limit() * 4);
        return byteBuffer;
    }
}
