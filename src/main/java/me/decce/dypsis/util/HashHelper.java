package me.decce.dypsis.util;

import me.decce.dypsis.gl.GlBufferSubData;
import net.minecraft.client.renderer.BufferBuilder;

public class HashHelper {
    public static int hash(BufferBuilder builder) {
        int result = 17;
        result = result * 31 + builder.getDrawMode();
        result = result * 31 + builder.getVertexFormat().hashCode();
        result = result * 31 + builder.getByteBuffer().hashCode();
        return result;
    }

    public static int hash(GlBufferSubData data) {
        int result = 17;
        result = result * 31 + data.id();
        result = result * 31 + data.offset();
        result = result * 31 + data.size();
        return result;
    }
}
