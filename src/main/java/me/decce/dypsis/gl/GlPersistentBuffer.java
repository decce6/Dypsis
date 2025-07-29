package me.decce.dypsis.gl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import java.nio.ByteBuffer;

// TODO
public class GlPersistentBuffer extends GlBuffer {
    private static final int BASE_BITS = GL30.GL_MAP_WRITE_BIT | GL44.GL_MAP_PERSISTENT_BIT;
    private static final int MAP_BITS = BASE_BITS | GL30.GL_MAP_FLUSH_EXPLICIT_BIT;
    private static final int STORAGE_BITS = BASE_BITS | GL44.GL_CLIENT_STORAGE_BIT; //| GL44.GL_DYNAMIC_STORAGE_BIT;

    private final ByteBuffer buffer;

    public GlPersistentBuffer(int target, int size) {
        super(target, size);
        GL45.glNamedBufferStorage(id, size, STORAGE_BITS);
        this.buffer = GL45.glMapNamedBufferRange(id, 0, size, MAP_BITS, null);
    }

    @Override
    public void delete() {
        GL45.glUnmapNamedBuffer(id);
        GL15.glDeleteBuffers(id);
    }
}
