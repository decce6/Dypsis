package me.decce.dypsis.mixins.early.batching;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.decce.dypsis.batching.BatchingManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TileEntityRendererDispatcher.class)
public class TileEntityRendererDispatcherMixin {
    @WrapOperation(method = "render(Lnet/minecraft/tileentity/TileEntity;FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;enableStandardItemLighting()V"))
    public void dypsis$render(Operation<Void> original, @Local(argsOnly = true) TileEntity tileEntity) {
        if (BatchingManager.isBatched(tileEntity)) {
            return; // Do not update GL states for batched TEs
        }
        original.call();
    }

    @WrapOperation(method = "render(Lnet/minecraft/tileentity/TileEntity;FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OpenGlHelper;setLightmapTextureCoords(IFF)V"))
    public void dypsis$render(int target, float x, float y, Operation<Void> original, @Local(argsOnly = true) TileEntity tileEntity) {
        if (BatchingManager.isBatched(tileEntity)) {
            return; // Do not update GL states for batched TEs
        }
        original.call(target, x, y);
    }

    @WrapOperation(method = "render(Lnet/minecraft/tileentity/TileEntity;FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void dypsis$render(float colorRed, float colorGreen, float colorBlue, float colorAlpha, Operation<Void> original, @Local(argsOnly = true) TileEntity tileEntity) {
        if (BatchingManager.isBatched(tileEntity)) {
            return; // Do not update GL states for batched TEs
        }
        original.call(colorRed, colorGreen, colorBlue, colorAlpha);
    }
}
