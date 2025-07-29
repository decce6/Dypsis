package me.decce.dypsis.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @ModifyExpressionValue(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/OpenGlHelper;shadersSupported:Z"))
    public boolean dypsis$updateCameraAndRender(boolean original) {
        if (!DypsisConfig.VANILLA.skipEntityFBO) {
            return original;
        }
        return original && ((RenderGlobalAccessor)Minecraft.getMinecraft().renderGlobal).isEntityOutlinesRendered();
    }
}
