package me.decce.dypsis.gl;

import me.decce.dypsis.util.ResourceHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

import java.util.function.Consumer;

// TODO: ITEM format needs lighting to be implemented
public class CommonShaders {
    public static final GlShader POSITION = GlShader.builder()
            .withVertexShader("position.vsh", ResourceHelper::readShader)
            .withFragmentShader("position.fsh", ResourceHelper::readShader)
            .build();
    public static final GlShader POSITION_TEX = GlShader.builder()
            .withVertexShader("position_tex.vsh", ResourceHelper::readShader)
            .withFragmentShader("position_tex.fsh", ResourceHelper::readShader)
            .build();
    public static final GlShader POSITION_COLOR = GlShader.builder()
            .withVertexShader("position_color.vsh", ResourceHelper::readShader)
            .withFragmentShader("position_color.fsh", ResourceHelper::readShader)
            .build();
    public static final GlShader POSITION_TEX_COLOR = GlShader.builder()
            .withVertexShader("position_tex_color.vsh", ResourceHelper::readShader)
            .withFragmentShader("position_tex_color.fsh", ResourceHelper::readShader)
            .build();

    public static GlShader findAppropriateShader(VertexFormat format) {
        if (format == DefaultVertexFormats.POSITION) return POSITION;
        if (format == DefaultVertexFormats.POSITION_TEX) return POSITION_TEX;
        if (format == DefaultVertexFormats.POSITION_COLOR) return POSITION_COLOR;
        if (format == DefaultVertexFormats.POSITION_TEX_COLOR) return POSITION_TEX_COLOR;
        return null;
    }

    public static void forEach(Consumer<GlShader> consumer) {
        consumer.accept(POSITION);
        consumer.accept(POSITION_TEX);
        consumer.accept(POSITION_COLOR);
        consumer.accept(POSITION_TEX_COLOR);
    }
}
