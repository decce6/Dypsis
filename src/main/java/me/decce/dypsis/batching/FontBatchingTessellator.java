package me.decce.dypsis.batching;

import me.decce.dypsis.mixins.early.FontRendererAccessor;
import net.minecraft.client.Minecraft;

public class FontBatchingTessellator extends BatchingTessellator {
    private static final FontBatchingTessellator instance = new FontBatchingTessellator();

    public FontBatchingTessellator() {
        super();
        BatchingManager.register(this);
    }

    public static FontBatchingTessellator instance() {
        return instance;
    }

    @Override
    protected void setup() {
        Minecraft.getMinecraft().renderEngine.bindTexture(((FontRendererAccessor)Minecraft.getMinecraft().fontRenderer).getLocationFontTexture());
    }
}
