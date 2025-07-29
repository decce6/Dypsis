package me.decce.dypsis.mixins.early;

import me.decce.dypsis.DummyFramebuffer;
import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.util.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow private Framebuffer framebuffer;
    @Shadow public int displayHeight;
    @Shadow public int displayWidth;

    @Inject(method = "updateDisplay", at= @At("HEAD"))
    private void dypsis$updateDisplay(CallbackInfo ci) {
        Dispatcher.replayQueue();
    }

    @Inject(method = "init", at= @At("TAIL"))
    private void dypsis$init(CallbackInfo ci) {
        if (DypsisConfig.VANILLA.fboOptimization == DypsisConfig.Vanilla.FBOEnum.AGGRESSIVE) {
            framebuffer.deleteFramebuffer();
            framebuffer = new DummyFramebuffer(this.displayWidth, this.displayHeight, true);
        }
    }

    @Inject(method = "updateFramebufferSize", at= @At("TAIL"))
    private void dypsis$updateFramebufferSize$head(CallbackInfo ci) {
        if (DypsisConfig.VANILLA.fboOptimization != DypsisConfig.Vanilla.FBOEnum.AGGRESSIVE && framebuffer instanceof DummyFramebuffer) {
            framebuffer = new Framebuffer(this.displayWidth, this.displayHeight, true);
        }
    }

    @Inject(method = "updateFramebufferSize", at= @At("TAIL"))
    private void dypsis$updateFramebufferSize$tail(CallbackInfo ci) {
        if (DypsisConfig.VANILLA.fboOptimization == DypsisConfig.Vanilla.FBOEnum.AGGRESSIVE && !(framebuffer instanceof DummyFramebuffer)) {
            framebuffer.deleteFramebuffer();
            framebuffer = new DummyFramebuffer(this.displayWidth, this.displayHeight, true);
        }
    }
}
