package me.decce.dypsis.mixins.early.batching.vanilla;

import me.decce.dypsis.batching.BasicBatchingTessellator;
import me.decce.dypsis.batching.BatchingManager;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public class GuiIngameForgeMixin {
    @Inject(method = { "renderHealth", "renderArmor", "renderAir", "renderFood" } , at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void dypsis$renderOverlay$head(CallbackInfo ci) {
        if (DypsisConfig.VANILLA.hudBatching) {
            BatchingManager.begin(BasicBatchingTessellator.instance(), GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        }
    }

    @Inject(method = { "renderHealth", "renderArmor", "renderAir", "renderFood" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V"))
    private void dypsis$renderOverlay$tail(CallbackInfo ci) {
        if (DypsisConfig.VANILLA.hudBatching) {
            BatchingManager.end();
            BasicBatchingTessellator.instance().flush();
        }
    }
}
