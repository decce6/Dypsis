package me.decce.dypsis.mixins.early;

import me.decce.dypsis.DypsisMod;
import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.GlBufferUsedUpException;
import me.decce.dypsis.gl.util.GlHelper;
import me.decce.dypsis.gl.CommonShaders;
import me.decce.dypsis.gl.GlShader;
import me.decce.dypsis.util.HashCollisionException;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldVertexBufferUploader.class)
public class WorldVertexBufferUploaderMixin {
    @Unique private boolean dypsis$suppressBufferError;
    @Unique private boolean dypsis$suppressHashError;

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    public void dypsis$draw(BufferBuilder builder, CallbackInfo ci) {
        if (!DypsisConfig.ADVANCED.modernized) return;
        try {
            GlShader shader = CommonShaders.findAppropriateShader(builder.getVertexFormat());
            if (shader != null) {
                if (builder.getVertexCount() > 0) {
                    GlHelper.draw(shader, builder);
                }
                builder.reset();
                ci.cancel();
            }
        }
        catch (GlBufferUsedUpException exception) {
            if (!dypsis$suppressBufferError) {
                DypsisMod.LOGGER.warn(exception);
                dypsis$suppressBufferError = true;
            }
        }
        catch (HashCollisionException exception) {
            if (!dypsis$suppressHashError) {
                DypsisMod.LOGGER.warn(exception);
                dypsis$suppressHashError = true;
            }
        }
    }
}
