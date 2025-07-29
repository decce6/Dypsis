package me.decce.dypsis.gl;

import me.decce.dypsis.gl.util.GlHelper;
import me.decce.dypsis.util.HashCollisionException;
import me.decce.dypsis.util.HashHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

public class Vao extends GlObject {
    protected final int hash;
    protected final int drawMode; // The *original* draw mode, with GL_QUADS as a possible value
    protected final int vertexCount;
    protected final VertexFormat format;
    protected final GlBufferSubData vbo;
    protected final GlBufferSubData ebo;

    public Vao(BufferBuilder builder, GlBufferSubData vbo, GlBufferSubData ebo) {
        this.format = builder.getVertexFormat();
        this.drawMode = builder.getDrawMode();
        this.vertexCount = builder.getVertexCount();
        this.vbo = vbo;
        this.ebo = ebo;
        this.hash = HashHelper.hash(builder);
    }

    public void verify(BufferBuilder builder) {
        if (this.format != builder.getVertexFormat() ||
            this.drawMode != builder.getDrawMode() ||
            this.vertexCount != builder.getVertexCount()) {
            throw new HashCollisionException();
        }
    }

    public void configure() {
        this.id = GL45.glCreateVertexArrays();
        GL45.glVertexArrayVertexBuffer(id, 0, vbo.id(), vbo.offset(), format.getSize());
        if (this.ebo != null) {
            GL45.glVertexArrayElementBuffer(id, ebo.id());
        }
        int offset = 0;
        for (int i = 0; i < format.getElementCount(); i++) {
            VertexFormatElement element = format.getElement(i);
            VertexFormatElement.EnumType type = element.getType();
            GL45.glEnableVertexArrayAttrib(id, i);
            GL45.glVertexArrayAttribFormat(id, i, element.getElementCount(), type.getGlConstant(), false, offset);
            GL45.glVertexArrayAttribBinding(id, i, 0);
            offset += element.getSize();
        }
    }

    public static Vao of(BufferBuilder builder) {
        return VaoManager.vaoOf(builder);
    }

    public static Vao create(BufferBuilder builder, GlBufferSubData vbo, GlBufferSubData ebo) {
        return VaoManager.createVao(builder, vbo, ebo);
    }

    public int getDrawMode() {
        return drawMode == GL11.GL_QUADS ? GL11.GL_TRIANGLES : drawMode;
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void draw() {
        if (this.ebo == null) {
            GL11.glDrawArrays(getDrawMode(), vbo.offset(), vbo.size());
        }
        else {
            // TODO: GL12.glDrawRangeElements
            GL11.glDrawElements(getDrawMode(), ebo.size() / Integer.BYTES, GlHelper.Ebo.TYPE, ebo.offset());
        }
    }

    @Override
    public void delete() {
        GL30.glDeleteVertexArrays(id);
    }
}
