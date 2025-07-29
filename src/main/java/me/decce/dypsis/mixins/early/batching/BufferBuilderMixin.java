package me.decce.dypsis.mixins.early.batching;

import me.decce.dypsis.batching.BatchingManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {
    @Shadow private boolean isDrawing;
    @Shadow private int drawMode;
    @Shadow private VertexFormat vertexFormat;

    @Inject(method = "begin", at = @At("HEAD"), cancellable = true)
    private void dypsis$begin(int glMode, VertexFormat format, CallbackInfo ci) {
        if (this.isDrawing && BatchingManager.isBatching()) {
            if (this.drawMode != glMode || this.vertexFormat != format) {
                throw new IllegalStateException(String.format("Batching with different mode or format! Previously %s, {%s}, attempting %s, {%s}", this.drawMode, this.vertexFormat.toString(), glMode, format.toString()));
            }
            ci.cancel(); // suppress throw
        }
    }
}
