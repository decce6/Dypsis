package me.decce.dypsis.mixins.early;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.decce.dypsis.DypsisMod;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Mixin;

import java.awt.image.BufferedImage;

@Mixin(ScreenShotHelper.class)
public class ScreenShotHelperMixin {
    @WrapMethod(method = "createScreenshot")
    private static BufferedImage dypsis$createScreenshot(int width, int height, Framebuffer framebufferIn, Operation<BufferedImage> original) {

        if (DypsisConfig.VANILLA.fboOptimization == DypsisConfig.Vanilla.FBOEnum.AGGRESSIVE) {
            DypsisMod.pretendFramebufferUnsupported = true;
        }
        BufferedImage image = original.call(width, height, framebufferIn);
        DypsisMod.pretendFramebufferUnsupported = false;
        return image;
    }
}
