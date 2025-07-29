package me.decce.dypsis.gl.util;

import me.decce.dypsis.gl.GlShader;

public class ShaderHelper {
    public static void setCommonUniforms(GlShader shader) {
        shader.setMatrix4f(CommonUniforms.MODEL_VIEW, MatrixHelper.getModelViewMatrix());
        shader.setMatrix4f(CommonUniforms.PROJECTION, MatrixHelper.getProjectionMatrix());
        shader.setInt(CommonUniforms.TEXTURE, 0);
        shader.setVec4f(CommonUniforms.COLOR, GlStateTracker.color);

    }
}
