package me.decce.dypsis.mixins.early;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenShotHelper.class)
public class ScreenShotHelperMixin {
    @WrapOperation(method = "createScreenshot", at = @At(value = "INVOKE", target= "Lnet/minecraft/client/renderer/OpenGlHelper;isFramebufferEnabled()Z", ordinal = 1))
    private static boolean dypsis$createScreenshot(Operation<Boolean> original) {
        if (DypsisConfig.VANILLA.fboOptimization == DypsisConfig.Vanilla.FBOEnum.AGGRESSIVE) {
            return false;
        }
        return original.call();
    }
}
