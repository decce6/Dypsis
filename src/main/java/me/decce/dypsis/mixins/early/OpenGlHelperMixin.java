package me.decce.dypsis.mixins.early;

import me.decce.dypsis.DypsisMod;
import net.minecraft.client.renderer.OpenGlHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OpenGlHelper.class)
public class OpenGlHelperMixin {
    @Inject(method = "isFramebufferEnabled", at = @At("RETURN"), cancellable = true)
    private static void isFramebufferEnabled(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValueZ() && !DypsisMod.pretendFramebufferUnsupported);
    }
}
