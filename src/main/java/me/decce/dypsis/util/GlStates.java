package me.decce.dypsis.util;

//TODO figure out a better way to do all this

public class GlStates {
//    public boolean[] colorMask = new boolean[4];
//    public float[] color = new float[4];
//    public boolean alpha;
//    public boolean blend;
//    public boolean depth;
//    public boolean depthMask;
//    public boolean texture0;
//    public int texture0Id;
//    public boolean texture1;
//    public int texture1Id;
//    public boolean lighting;
//    public boolean[] light = new boolean[lightState.length];

    public GlStates() {
//        colorMask[0] = GlStateManager.colorMaskState.red;
//        colorMask[1] = GlStateManager.colorMaskState.green;
//        colorMask[2] = GlStateManager.colorMaskState.blue;
//        colorMask[3] = GlStateManager.colorMaskState.alpha;
//        color[0] = GlStateManager.colorState.red;
//        color[1] = GlStateManager.colorState.green;
//        color[2] = GlStateManager.colorState.blue;
//        color[3] = GlStateManager.colorState.alpha;
//        alpha = ((BooleanStateAccessor)GlStateManager.alphaState.alphaTest).get();
//        blend = ((BooleanStateAccessor)GlStateManager.blendState.blend).get();
//        depthMask = GlStateManager.depthState.maskEnabled;
//        depth = ((BooleanStateAccessor)GlStateManager.depthState.depthTest).get();
//        texture0 = ((BooleanStateAccessor)GlStateManager.textureState[0].texture2DState).get();
//        texture0Id = GlStateManager.textureState[0].textureName;
//        texture1 = ((BooleanStateAccessor)GlStateManager.textureState[1].texture2DState).get();
//        texture1Id = GlStateManager.textureState[1].textureName;
//        lighting = ((BooleanStateAccessor)GlStateManager.lightingState).get();
//        if (lighting) {
//            for (int i = 0; i < lightState.length; i++) {
//                light[i] = ((BooleanStateAccessor)lightState[i]).get();
//            }
//        }
    }

    public void apply() {
//        GlStateManager.colorMask(colorMask[0], colorMask[1], colorMask[2], colorMask[3]);
//        GlStateManager.color(color[0], color[1], color[2], color[3]);
//        if (alpha) enableAlpha();
//        else disableAlpha();
//        if (blend) enableBlend();
//        else disableBlend();
//        depthMask(depthMask);
//        if (depth) enableDepth();
//        else disableDepth();
//        if (texture1) {
//            setActiveTexture(GL13.GL_TEXTURE1);
//            enableTexture2D();
//            bindTexture(texture1Id);
//        }
//        setActiveTexture(GL13.GL_TEXTURE0);
//        if (texture0) {
//            enableTexture2D();
//            bindTexture(texture0Id);
//        }
//        else disableTexture2D();
//        if (lighting) {
//            enableLighting();
//            for (int i = 0; i < light.length; i++) {
//                if (light[i]) enableLight(i);
//                else disableLight(i);
//            }
//        }
//        else disableLighting();
    }
}
