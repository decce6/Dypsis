package me.decce.dypsis.mixins.late.theoneprobe;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import mcjty.theoneprobe.rendering.OverlayRenderer;
import me.decce.dypsis.config.DypsisConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(value = OverlayRenderer.class, remap = false)
public class OverlayRendererMixin {
    @Unique
    private static boolean dypsis$canceled;

    @WrapWithCondition(method = "renderHUD", at = @At(value = "INVOKE", target = "Lmcjty/theoneprobe/rendering/OverlayRenderer;setupOverlayRendering(DD)V", ordinal = 0))
    private static boolean dypsis$renderHUD$0(double sw, double sh, @Local(ordinal = 0) double scale) {
        if (DypsisConfig.MODS.THEONEPROBE.removeRedundant) {
            if (Math.abs(scale - 1.0d) <= 0.00001d) {
                dypsis$canceled = true;
                return false;
            }
        }
        return true;
    }

    @WrapWithCondition(method = "renderHUD", at = @At(value = "INVOKE", target = "Lmcjty/theoneprobe/rendering/OverlayRenderer;setupOverlayRendering(DD)V", ordinal = 1))
    private static boolean dypsis$renderHUD$1(double sw, double sh) {
        if (dypsis$canceled) {
            dypsis$canceled = false;
            return false;
        }
        return true;
    }

    @WrapWithCondition(method = "renderHUD", at = @At(value = "INVOKE", target = "Lmcjty/theoneprobe/rendering/OverlayRenderer;setupOverlayRendering(DD)V", ordinal = 2))
    private static boolean dypsis$renderHUD$2(double sw, double sh, @Local(ordinal = 3) double scale) {
        if (DypsisConfig.MODS.THEONEPROBE.removeRedundant) {
            if (Math.abs(scale - 1.0d) <= 0.00001d) {
                dypsis$canceled = true;
                return false;
            }
        }
        return true;
    }

    @WrapWithCondition(method = "renderHUD", at = @At(value = "INVOKE", target = "Lmcjty/theoneprobe/rendering/OverlayRenderer;setupOverlayRendering(DD)V", ordinal = 3))
    private static boolean dypsis$renderHUD$3(double sw, double sh) {
        if (dypsis$canceled) {
            dypsis$canceled = false;
            return false;
        }
        return true;
    }
}
