package me.decce.dypsis.gl;

import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.util.GlHelper;

public class GlBufferManager {
    public static final int KB = 1024;
    public static final int MAX_BUFFER_COUNT = DypsisConfig.ADVANCED.maxBufferCount;
    private static final GlBuffer[] vbos = new GlBuffer[MAX_BUFFER_COUNT];
    private static final GlBuffer[] ebos = new GlBuffer[MAX_BUFFER_COUNT];
    private static final GlBuffer[] indirects = new GlBuffer[MAX_BUFFER_COUNT];

    public static GlBuffer get(int target, int index) {
        switch (target) {
            case GlHelper.Vbo.TARGET:
                if (vbos[index] == null) vbos[index] = new GlBuffer(GlHelper.Vbo.TARGET, DypsisConfig.ADVANCED.vboSize * KB);
                return vbos[index];
            case GlHelper.Ebo.TARGET:
                if (ebos[index] == null) ebos[index] = new GlBuffer(GlHelper.Ebo.TARGET, DypsisConfig.ADVANCED.eboSize * KB);
                return ebos[index];
            case GlHelper.IndirectBufferObject.TARGET:
                if (indirects[index] == null) indirects[index] = new GlBuffer(GlHelper.IndirectBufferObject.TARGET, DypsisConfig.ADVANCED.indirectSize * KB);
                return indirects[index];
            default:
                throw new GlRuntimeException("Unknown target for GlPersistentBuffer! target=" + target + ", index=" + index);
        }
    }
}
