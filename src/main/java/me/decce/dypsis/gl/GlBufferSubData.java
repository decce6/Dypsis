package me.decce.dypsis.gl;

import me.decce.dypsis.util.HashCollisionException;
import me.decce.dypsis.util.HashHelper;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.nio.ByteBuffer;

public class GlBufferSubData {
    private final GlBuffer buffer;
    private final int offset;
    private final int size;
    private final int _bufferLimit;
    private IntArrayList vaos;

    public GlBufferSubData(GlBuffer parent, int offset, int size, ByteBuffer byteBuffer) {
        this.buffer = parent;
        this.offset = offset;
        this.size = size;
        this._bufferLimit = byteBuffer.limit();
    }

    public int id() {
        return buffer.id();
    }

    public int offset() {
        return offset;
    }

    public int size() {
        return size;
    }

    public GlBuffer buffer() {
        return buffer;
    }

    public void verify(ByteBuffer buffer) {
        if (this._bufferLimit != buffer.limit()) {
            throw new HashCollisionException();
        }
    }

    public IntArrayList vaos() {
        if (vaos == null) {
            vaos = new IntArrayList(1);
        }
        return vaos;
    }

    @Override
    public int hashCode() {
        return HashHelper.hash(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof GlBufferSubData) {
            GlBufferSubData that = (GlBufferSubData) obj;
            return this.id() == that.id() &&
                    this.offset() == that.offset() &&
                    this.size() == that.size();
        }
        return false;
    }
}