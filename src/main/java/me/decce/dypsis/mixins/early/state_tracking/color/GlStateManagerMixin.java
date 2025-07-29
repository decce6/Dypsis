package me.decce.dypsis.mixins.early.state_tracking.color;

import me.decce.dypsis.gl.util.GlStateTracker;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {
    @Inject(method = "color(FFF)V", at = @At("TAIL"))
    private static void dypsis$color(float red, float green, float blue, CallbackInfo ci) {
        GlStateTracker.color.set(red, green, blue, 1.0f);
    }

    @Inject(method = "color(FFFF)V", at = @At("TAIL"))
    private static void dypsis$color(float red, float green, float blue, float alpha, CallbackInfo ci) {
        GlStateTracker.color.set(red, green, blue, alpha);
    }
}
