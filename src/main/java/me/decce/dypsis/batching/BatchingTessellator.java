package me.decce.dypsis.batching;

import me.decce.dypsis.mixins.early.batching.BufferBuilderAccessor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

public class BatchingTessellator extends Tessellator {
    public static final int KB = 1024;
    public static final int INITIAL_CAPACITY = 512 * KB;

    protected BatchingTessellator() {
        super(INITIAL_CAPACITY);
    }

    @Override
    public void draw() {
        // no-op
    }

    protected void setup() {

    }

    protected void cleanup() {

    }

    public void flush() {
        if (((BufferBuilderAccessor) super.getBuffer()).isDrawing()) {
            if (super.getBuffer().getVertexCount() > 0) {
                this.setup();
                super.draw();
                this.cleanup();
            }
            else {
                super.getBuffer().finishDrawing();
            }
        }
    }

    public boolean canBatch(TileEntity tile) {
        return false;
    }
}
