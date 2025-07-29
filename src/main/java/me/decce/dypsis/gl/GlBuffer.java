package me.decce.dypsis.gl;

import me.decce.dypsis.util.Dispatcher;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL45;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.concurrent.TimeUnit;

public class GlBuffer extends GlObject {
    private final Cache<Integer, GlBufferSubData> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(20, TimeUnit.SECONDS)
            .removalListener(this::onCacheRemoval)
            .build();
    private final int size;
    private final int target;
    private final BitSet memory;

    public GlBuffer(int target, int size) {
        this.id = GL45.glCreateBuffers();
        GL45.glNamedBufferData(id, size, GL15.GL_DYNAMIC_DRAW);
        this.size = size;
        this.target = target;
        this.memory = new BitSet(size);
    }

    private void onCacheRemoval(RemovalNotification<Integer, GlBufferSubData> notification) {
        GlBufferSubData data = notification.getValue();
        Dispatcher.invokeLater(() -> {
            this.memory.clear(data.offset(), data.offset() + data.size());
            GL43.glInvalidateBufferSubData(id, data.offset(), data.size());
            VaoManager.invalidateVao(data);
        });
    }

    private int findOffset(int size) {
        return _findOffset(0, size);
    }

    private int _findOffset(int offset, int size) {
        int firstUnused = this.memory.nextClearBit(offset);
        if (firstUnused == -1 || firstUnused + size >= this.size) {
            throw new GlBufferUsedUpException();
        }
        int nextUsed = -1;// = this.used.nextSetBit(firstUnused);
        for (int i = firstUnused; i < firstUnused + size; i++) {
            if (this.memory.get(i)) {
                nextUsed = i;
                break;
            }
        }
        if (nextUsed == -1) {
            return firstUnused;
        }
        return _findOffset(nextUsed, size);
    }

    public void bind() {
        GL15.glBindBuffer(this.target, this.id);
    }

    public void unbind() {
        GL15.glBindBuffer(this.target, 0);
    }

    public GlBufferSubData upload(ByteBuffer byteBuffer) {
        int key = byteBuffer.hashCode();
        GlBufferSubData data = cache.getIfPresent(key);
        if (data == null) {
            data = _upload(byteBuffer, findOffset(byteBuffer.remaining()), byteBuffer.remaining());
            cache.put(key, data);
        }
        else {
            data.verify(byteBuffer);
        }
        return data;
    }

    private GlBufferSubData _upload(ByteBuffer byteBuffer, int offset, int size) {
        GL45.glNamedBufferSubData(id, offset, byteBuffer);
        this.memory.set(offset, offset + size);
        return new GlBufferSubData(this, offset, size, byteBuffer);
    }

    @Override
    public void delete() {
        GL15.glDeleteBuffers(id);
    }
}
