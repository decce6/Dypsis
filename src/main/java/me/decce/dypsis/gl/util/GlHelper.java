package me.decce.dypsis.gl.util;

import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.GlBufferSubData;
import me.decce.dypsis.gl.GlBufferUsedUpException;
import me.decce.dypsis.gl.GlBuffer;
import me.decce.dypsis.gl.GlShader;
import me.decce.dypsis.gl.IndirectDrawer;
import me.decce.dypsis.gl.GlBufferManager;
import me.decce.dypsis.gl.Vao;
import me.decce.dypsis.util.IndicesGenerator;
import net.minecraft.client.renderer.BufferBuilder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL40;

import java.nio.ByteBuffer;

public class GlHelper {
    public static void draw(GlShader shader, BufferBuilder builder) {
        draw(shader, builder, 0);
    }

    private static void draw(GlShader shader, BufferBuilder builder, int index) {
        try {
            Vao vao = Vao.of(builder);
            IndirectDrawer.IndirectCommand command = IndirectDrawer.commandOf(vao);
            if (vao == null || command == null) {
                GlBuffer ebo = GlBufferManager.get(Ebo.TARGET, index);
                GlBufferSubData eboData = uploadEboData(builder, ebo);

                GlBuffer vbo = GlBufferManager.get(Vbo.TARGET, index);
                GlBufferSubData vboData = vbo.upload(builder.getByteBuffer());

                vao = Vao.create(builder, vboData, eboData);

                command = IndirectDrawer.generateIndirectCommand(vao, vboData, eboData);
            }

            if (DypsisConfig.shaderBatching) {
                shader.drawLater(vao);
                return;
            }
            shader.use();
            ShaderHelper.setCommonUniforms(shader);
            vao.bind();
            if (DypsisConfig.indirectDrawing) {
                IndirectDrawer.draw(vao, command);
            }
            else {
                vao.draw();
            }
            vao.unbind();
            shader.unuse();
        }
        catch (GlBufferUsedUpException exception) {
            if (index + 1 >= DypsisConfig.ADVANCED.maxBufferCount) throw new GlBufferUsedUpException("No buffer is available for index " + index + "!");
            draw(shader, builder, index + 1);
        }
    }

    private static GlBufferSubData uploadEboData(BufferBuilder builder, GlBuffer ebo) {
        GlBufferSubData eboData = null;
        if (builder.getDrawMode() == GL11.GL_QUADS) {
            ByteBuffer indices = IndicesGenerator.generate(builder.getVertexCount());
            eboData = ebo.upload(indices);
        }
        return eboData;
    }

    public static class Ebo {
        public static final int TARGET = GL15.GL_ELEMENT_ARRAY_BUFFER;
        public static final int TYPE = GL11.GL_UNSIGNED_INT;
    }

    public static class Vbo {
        public static final int TARGET = GL15.GL_ARRAY_BUFFER;
    }

    public static class IndirectBufferObject {
        public static final int TARGET = GL40.GL_DRAW_INDIRECT_BUFFER;
    }
}
