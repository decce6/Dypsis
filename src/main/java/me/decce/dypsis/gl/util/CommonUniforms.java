package me.decce.dypsis.gl.util;

import me.decce.dypsis.gl.GlShader;
import me.decce.dypsis.gl.GlUniform;

public class CommonUniforms {
    public static final int COUNT = 4;
    public static final String MODEL_VIEW = "modelView";
    public static final String PROJECTION = "projection";
    public static final String TEXTURE = "fTexture";
    public static final String COLOR = "globalColor";

    //TODO: optimize alloc
    public static GlUniform<?>[] get(GlShader shader) {
        GlUniform<?>[] uniforms = new GlUniform<?>[COUNT];
        uniforms[0] = new GlUniform<>(MODEL_VIEW, MatrixHelper.getModelViewMatrix(), shader::setMatrix4f);
        uniforms[1] = new GlUniform<>(PROJECTION, MatrixHelper.getProjectionMatrix(), shader::setMatrix4f);
        uniforms[2] = new GlUniform<>(TEXTURE, 0, shader::setInt);
        uniforms[3] = new GlUniform<>(COLOR, GlStateTracker.color, shader::setVec4f);
        return uniforms;
    }
}
