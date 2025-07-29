package me.decce.dypsis.mixins.late.enderio;

import com.enderio.core.client.render.RenderUtil;
import com.enderio.core.common.util.NNList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import crazypants.enderio.base.conduit.IClientConduit;
import crazypants.enderio.base.conduit.IConduitRenderer;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import crazypants.enderio.conduits.render.ConduitBundleRenderer;
import me.decce.dypsis.batching.BatchingManager;
import me.decce.dypsis.DypsisMod;
import me.decce.dypsis.batching.enderio.EIOBatchingTessellator;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

@Pseudo
@Mixin(value = ConduitBundleRenderer.class, remap = false)
public class ConduitBundleRendererMixin {
    @Unique private static EIOBatchingTessellator dypsis$tessellator;
    @Unique private static MethodHandle dypsis$getRendererHandle;
    @Unique private static MethodHandle dypsis$getConduitHandle;
    @Unique private static boolean dypsis$success;

    static {
        if (Loader.isModLoaded("enderio")) {
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                Class<?> clazz = Class.forName("crazypants.enderio.conduits.render.ConduitBundleRenderer$RenderPair");
                Method method0 = clazz.getDeclaredMethod("getRenderer");
                Method method1 = clazz.getDeclaredMethod("getConduit");
                dypsis$getRendererHandle = lookup.unreflect(method0);
                dypsis$getConduitHandle = lookup.unreflect(method1);
                dypsis$tessellator = new EIOBatchingTessellator();
                dypsis$success = true;
            } catch (Throwable exception) {
                DypsisMod.LOGGER.error("Failed to initialize EnderIO classes!", exception);
            }
        }
    }

    @WrapOperation(method = "render(Lcrazypants/enderio/conduits/conduit/TileConduitBundle;DDDFIF)V", at = @At(value = "INVOKE", target = "Lcom/enderio/core/common/util/NNList;isEmpty()Z"))
    private boolean dypsis$render$redirectEmpty(NNList<?> renderers, Operation<Boolean> original, @Local(argsOnly = true) TileConduitBundle bundle, @Local(argsOnly = true, ordinal = 0) double x, @Local(argsOnly = true, ordinal = 1) double y, @Local(argsOnly = true, ordinal = 2) double z, @Local(argsOnly = true, ordinal = 0) float partialTick) {
        if (!dypsis$success || !DypsisConfig.MODS.ENDERIO.batchConduits || renderers.isEmpty()) {
            return original.call(renderers);
        }

        BatchingManager.begin(dypsis$tessellator, GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        dypsis$tessellator.getBuffer().setTranslation(x, y, z);

        float brightness = (float)bundle.getEntity().getWorld().getLightFor(EnumSkyBlock.SKY, bundle.getLocation());
        //RenderUtil.setupLightmapCoords(bundle.getPos(), bundle.getWorld());
        try {
            for (int i = 0; i < renderers.size(); i++) {
                Object renderPair = renderers.get(i); // ConduitBundleRenderer.RenderPair is package-private
                IConduitRenderer renderer = (IConduitRenderer) dypsis$getRendererHandle.invoke(renderPair);
                Object conduit = dypsis$getConduitHandle.invoke(renderPair);
                renderer.renderDynamicEntity((ConduitBundleRenderer)(Object)this, bundle, (IClientConduit.WithDefaultRendering) conduit, x, y, z, partialTick, brightness);
            }
        }
        catch (Throwable throwable) {
            DypsisMod.LOGGER.error("EnderIO redirect: ", throwable);
            dypsis$success = false;
        }
        finally {
            BatchingManager.end();
        }

        return true;
    }
}
