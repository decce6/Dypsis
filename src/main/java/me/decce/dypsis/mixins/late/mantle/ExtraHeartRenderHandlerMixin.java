package me.decce.dypsis.mixins.late.mantle;

import me.decce.dypsis.batching.BasicBatchingTessellator;
import me.decce.dypsis.batching.BatchingManager;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.mantle.client.ExtraHeartRenderHandler;

@Mixin(value = ExtraHeartRenderHandler.class, remap = false)
public class ExtraHeartRenderHandlerMixin {
    @Inject(method = "renderHealthbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;func_76320_a(Ljava/lang/String;)V"))
    public void dypsis$renderHealthbar$beginBatching(RenderGameOverlayEvent.Pre event, CallbackInfo ci) {
        if (DypsisConfig.MODS.MANTLE.hudBatching) {
            BatchingManager.begin(BasicBatchingTessellator.instance(), GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        }
    }

    @Inject(method = "renderHealthbar", at = @At(value = "INVOKE", target = "Lslimeknights/mantle/client/ExtraHeartRenderHandler;renderExtraHearts(IILnet/minecraft/entity/player/EntityPlayer;)V"))
    public void dypsis$renderHealthbar$endBatching(RenderGameOverlayEvent.Pre event, CallbackInfo ci) {
        if (DypsisConfig.MODS.MANTLE.hudBatching) {
            BatchingManager.end();
            BasicBatchingTessellator.instance().flush();
        }
    }

    @Inject(method = "renderCustomHearts", at = @At("HEAD"))
    public void dypsis$renderCustomHearts$beginBatching(int xBasePos, int yBasePos, int potionOffset, int count, boolean absorb, CallbackInfo ci) {
        if (DypsisConfig.MODS.MANTLE.hudBatching) {
            BatchingManager.begin(BasicBatchingTessellator.instance(), GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        }
    }

    @Inject(method = "renderCustomHearts", at = @At("TAIL"))
    public void dypsis$renderCustomHearts$endBatching(int xBasePos, int yBasePos, int potionOffset, int count, boolean absorb, CallbackInfo ci) {
        if (DypsisConfig.MODS.MANTLE.hudBatching) {
            BatchingManager.end();
            BasicBatchingTessellator.instance().flush();
        }
    }
}
