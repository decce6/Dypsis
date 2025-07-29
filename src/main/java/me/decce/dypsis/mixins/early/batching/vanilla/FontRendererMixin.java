package me.decce.dypsis.mixins.early.batching.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.decce.dypsis.batching.BatchingManager;
import me.decce.dypsis.batching.FontBatchingTessellator;
import me.decce.dypsis.config.DypsisConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class FontRendererMixin {
    @Shadow
    private boolean unicodeFlag;
    @Shadow @Final protected int[] charWidth;
    @Shadow protected float posY;
    @Shadow protected float posX;
    @Unique
    private boolean dypsis$skipColorSetting;
    @Unique
    private float dypsis$r;
    @Unique
    private float dypsis$g;
    @Unique
    private float dypsis$b;
    @Unique
    private float dypsis$a;

    @Inject(method = "setColor", at = @At("HEAD"), cancellable = true, remap = false)
    private void dypsis$setColor(float r, float g, float b, float a, CallbackInfo ci) {
        if (!DypsisConfig.VANILLA.fontBatching) {
            return;
        }
        if (dypsis$skipColorSetting) {
            ci.cancel();
        }
        this.dypsis$r = r;
        this.dypsis$g = g;
        this.dypsis$b = b;
        this.dypsis$a = a;
    }

    @Inject(method = "renderStringAtPos", at = @At("HEAD"))
    private void dypsis$renderStringAtPos$head(String text, boolean shadow, CallbackInfo ci) {
        if (!text.isEmpty() && DypsisConfig.VANILLA.fontBatching) {
            BatchingManager.begin(FontBatchingTessellator.instance(), GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        }
    }

    @Inject(method = "renderStringAtPos", at = @At("TAIL"))
    private void dypsis$renderStringAtPos$tail(String text, boolean shadow, CallbackInfo ci) {
        if (!text.isEmpty() && DypsisConfig.VANILLA.fontBatching) {
            BatchingManager.end();
            FontBatchingTessellator.instance().flush();
        }
    }

    @ModifyExpressionValue(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C"))
    private char dypsis$renderStringAtPos(char ch) {
        if (!DypsisConfig.VANILLA.fontBatching) {
            return ch;
        }
        int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(ch);
        dypsis$skipColorSetting = i != -1 && !this.unicodeFlag;
        return ch;
    }

    @Inject(method = "renderDefaultChar", at = @At("HEAD"), cancellable = true)
    protected void dypsis$renderDefaultChar(int ch, boolean italic, CallbackInfoReturnable<Float> cir) {
        if (!DypsisConfig.VANILLA.fontBatching) {
            return;
        }
        int i = ch % 16 * 8;
        int j = ch / 16 * 8;
        int k = italic ? 1 : 0;
        int l = this.charWidth[ch];
        float f = (float)l - 0.01F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        builder.pos(this.posX + (float)k, this.posY, 0.0F).tex((float)i / 128.0F, (float)j / 128.0F).color(dypsis$r, dypsis$g, dypsis$b, dypsis$a).endVertex();
        builder.pos(this.posX - (float)k, this.posY + 7.99F, 0.0F).tex((float)i / 128.0F, ((float)j + 7.99F) / 128.0F).color(dypsis$r, dypsis$g, dypsis$b, dypsis$a).endVertex();
        builder.pos(this.posX + f - 1.0F + (float)k, this.posY, 0.0F).tex(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F).color(dypsis$r, dypsis$g, dypsis$b, dypsis$a).endVertex();
        builder.pos(this.posX + f - 1.0F - (float)k, this.posY + 7.99F, 0.0F).tex(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F).color(dypsis$r, dypsis$g, dypsis$b, dypsis$a).endVertex();
        tessellator.draw(); // Note: this does not issue a draw call instantly, but after the whole string gets drawn.
        cir.setReturnValue((float) l);
    }
}
