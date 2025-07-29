package me.decce.dypsis.batching.enderio;

import crazypants.enderio.conduits.conduit.TileConduitBundle;
import me.decce.dypsis.batching.BatchingManager;
import me.decce.dypsis.batching.BatchingTessellator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class EIOBatchingTessellator extends BatchingTessellator {
    public EIOBatchingTessellator() {
        super();
        BatchingManager.register(this);
    }

    @Override
    protected void setup() {
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableNormalize();
        GlStateManager.enableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    protected void cleanup() {
        GlStateManager.disableNormalize();
        GlStateManager.disableBlend();
        GlStateManager.disableCull();
    }

    @Override
    public boolean canBatch(TileEntity tileEntity) {
        return tileEntity instanceof TileConduitBundle;
    }
}
