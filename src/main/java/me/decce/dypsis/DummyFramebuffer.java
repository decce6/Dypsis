package me.decce.dypsis;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;

public class DummyFramebuffer extends Framebuffer {
    public DummyFramebuffer(int width, int height, boolean useDepthIn) {
        super(width, height, useDepthIn);
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;
        this.framebufferObject = 0;
        this.framebufferTexture = 0;
    }

    @Override
    public void createBindFramebuffer(int width, int height) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;
    }

    @Override
    public void deleteFramebuffer() {
    }

    @Override
    public void createFramebuffer(int width, int height) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;
    }

    @Override
    public void setFramebufferFilter(int framebufferFilterIn) {
    }

    @Override
    public void checkFramebufferComplete() {
    }

    @Override
    public void bindFramebufferTexture() {
    }

    @Override
    public void unbindFramebufferTexture() {
    }

    @Override
    public void bindFramebuffer(boolean viewport) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            if (viewport) {
                GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }

    @Override
    public void unbindFramebuffer() {
    }

    @Override
    public void setFramebufferColor(float red, float green, float blue, float alpha) {
    }

    @Override
    public void framebufferRender(int width, int height) {
    }

    @Override
    public void framebufferRenderExt(int width, int height, boolean p_178038_3_) {
    }

    @Override
    public void framebufferClear() {
    }

    @Override
    public boolean enableStencil() {
        return false;
    }

    @Override
    public boolean isStencilEnabled() {
        return false;
    }
}
