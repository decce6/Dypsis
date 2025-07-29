package me.decce.dypsis.mixins.early;

import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Framebuffer.class)
public class FramebufferMixin {
    @Unique private static final boolean dypsis$supported = GLContext.getCapabilities().OpenGL30; // For macOS
    @Shadow public int framebufferObject;

    @Inject(method = "framebufferRenderExt", at = @At("HEAD"), cancellable = true)
    public void dypsis$framebufferRenderExt(int width, int height, boolean disableBlend, CallbackInfo ci) {
        // TODO: investigate custom main menu incompatiblity and remove the world==null condition
        if (!dypsis$supported || DypsisConfig.VANILLA.fboOptimization == DypsisConfig.Vanilla.FBOEnum.NONE || Minecraft.getMinecraft().world == null) {
            return;
        }
        if (disableBlend) {
            ci.cancel();
            OpenGlHelper.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, framebufferObject);
            GL30.glBlitFramebuffer(
                    0, 0, width, height,
                    0, 0, width, height,
                    GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST
            );
            OpenGlHelper.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);

            // Ensure GL states (as done in the original method) to prevent issues
            GlStateManager.disableDepth();
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0D, (double)width, (double)height, 0.0D, 1000.0D, 3000.0D);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);
            GlStateManager.viewport(0, 0, width, height);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableColorMaterial();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.bindTexture(0);
        }
    }
}
