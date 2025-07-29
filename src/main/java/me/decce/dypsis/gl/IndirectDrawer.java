package me.decce.dypsis.gl;

import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.util.GlHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL40;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IndirectDrawer {
    private static final Int2ObjectOpenHashMap<IndirectCommand> map = new Int2ObjectOpenHashMap<>();

    public static void draw(Vao vao, IndirectCommand command) {
        GlBufferSubData data = uploadIndirectCommand(command);
        GlBuffer buffer = data.buffer();
        buffer.bind();
        if (vao.ebo == null) {
            GL40.glDrawArraysIndirect(vao.getDrawMode(), data.offset());
        }
        else {
            GL40.glDrawElementsIndirect(vao.getDrawMode(), GlHelper.Ebo.TYPE, data.offset());
        }
        buffer.unbind();
    }

    public static IndirectCommand commandOf(Vao vao) {
        if (vao == null) {
            return null;
        }
        return map.get(vao.id());
    }

    public static IndirectCommand generateIndirectCommand(Vao vao, GlBufferSubData vbo, GlBufferSubData ebo) {
        IndirectCommand command;
        if (vao.ebo == null) {
            command = new DrawArraysIndirectCommand(
                    vao.vertexCount,//vbo.size() / vao.format.getSize(),
                    1,
                    vbo.offset() / Integer.BYTES,
                    0);
        }
        else {
            command = new DrawElementsIndirectCommand(
                    ebo.size() / Integer.BYTES,
                    1,
                    ebo.offset() / Integer.BYTES,
                    0,
                    0);
        }
        map.put(vao.id(), command);
        return command;
    }

    public static void invalidateVao(Vao vao) {
        map.remove(vao.id());
    }

    public static GlBufferSubData uploadIndirectCommand(IndirectDrawer.IndirectCommand command) {
        return uploadIndirectCommand(command, 0);
    }

    public static GlBufferSubData uploadIndirectCommand(IndirectDrawer.IndirectCommand command, int index) {
        try {
            GlBuffer buffer = GlBufferManager.get(GlHelper.IndirectBufferObject.TARGET, index);
            return buffer.upload(command.toByteBuffer());
        }
        catch (GlBufferUsedUpException exception) {
            if (index + 1 >= DypsisConfig.ADVANCED.maxBufferCount) throw new GlBufferUsedUpException("No buffer is available for index " + index + "!");
            return uploadIndirectCommand(command, index + 1);
        }
    }

    public static abstract class IndirectCommand {
        public int count;
        public int instanceCount;
        public int baseInstance;

        public abstract ByteBuffer toByteBuffer();
    }

    public static class DrawArraysIndirectCommand extends IndirectCommand {
        private static final ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(5 * Integer.BYTES);
        private static final IntBuffer intBuffer = byteBuffer.asIntBuffer();

        public int firstVertex;

        public DrawArraysIndirectCommand(int count, int instanceCount, int firstVertex, int baseInstance) {
            this.count = count;
            this.instanceCount = instanceCount;
            this.firstVertex = firstVertex;
            this.baseInstance = baseInstance;
        }

        @Override
        public ByteBuffer toByteBuffer() {
            byteBuffer.clear();
            intBuffer.put(count)
                    .put(instanceCount)
                    .put(firstVertex)
                    .put(baseInstance)
                    .flip();
            return byteBuffer;
        }
    }

    public static class DrawElementsIndirectCommand extends IndirectCommand {
        private static final ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(5 * Integer.BYTES);
        private static final IntBuffer intBuffer = byteBuffer.asIntBuffer();

        public int firstIndex;
        public int baseVertex;

        public DrawElementsIndirectCommand(int count, int instanceCount, int firstIndex, int baseVertex, int baseInstance) {
            this.count = count;
            this.instanceCount = instanceCount;
            this.firstIndex = firstIndex;
            this.baseVertex = baseVertex;
            this.baseInstance = baseInstance;
        }

        @Override
        public ByteBuffer toByteBuffer() {
            byteBuffer.clear();
            intBuffer.put(count)
                    .put(instanceCount)
                    .put(firstIndex)
                    .put(baseVertex)
                    .put(baseInstance)
                    .flip();
            return byteBuffer;
        }
    }
}
