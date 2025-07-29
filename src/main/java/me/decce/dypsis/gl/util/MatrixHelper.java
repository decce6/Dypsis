package me.decce.dypsis.gl.util;

import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.MatrixStack;
import net.minecraft.client.renderer.GLAllocation;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class MatrixHelper {
    private static final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);

    public static Matrix4f getModelViewMatrix() {
        modelView.clear();
        if (DypsisConfig.trackMatrices) {
            return MatrixStack.modelView().last();
        }
        else {
            GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
            return new Matrix4f(modelView); //TODO
        }
    }

    public static Matrix4f getProjectionMatrix() {
        projection.clear();
        if (DypsisConfig.trackMatrices) {
            return MatrixStack.projection().last();
        }
        else {
            GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
            return new Matrix4f(projection);
        }
    }
}
