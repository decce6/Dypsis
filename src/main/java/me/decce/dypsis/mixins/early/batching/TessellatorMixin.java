package me.decce.dypsis.mixins.early.batching;

import me.decce.dypsis.batching.BatchingManager;
import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tessellator.class)
public class TessellatorMixin {
    @Inject(method = "getInstance", at = @At("HEAD"),cancellable = true)
    private static void dypsis$getInstance(CallbackInfoReturnable<Tessellator> cir) {
        if (BatchingManager.isBatching()) {
            cir.setReturnValue(BatchingManager.getTessellator());
        }
    }
}
