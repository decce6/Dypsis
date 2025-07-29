package me.decce.dypsis.mixins.early.state_tracking.matrix;

import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;

// TODO GLU directly calls GL11
@Mixin(value = GlStateManager.class)
public class GlStateManagerMixin {
//    @Unique
//    private static int w() {
//        return Minecraft.getMinecraft().displayWidth;
//    }
//
//    @Unique
//    private static int h() {
//        return Minecraft.getMinecraft().displayHeight;
//    }
//
//    @Inject(method = "pushMatrix", at = @At("TAIL"))
//    private static void dypsis$glPushMatrix(CallbackInfo ci) {
//        MatrixStack.get().push();
//    }
//
//    @Inject(method = "popMatrix", at = @At("TAIL"))
//    private static void dypsis$glPopMatrix(CallbackInfo ci) {
//        MatrixStack.get().pop();
//    }
//
//    @Inject(method = "translate(FFF)V", at = @At("TAIL"))
//    private static void dypsis$glTranslatef(float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().translate(x / w(), y / h(), z);
//    }
//
//    @Inject(method = "translate(DDD)V", at = @At("TAIL"))
//    private static void dypsis$translate(double x, double y, double z, CallbackInfo ci) {
//        MatrixStack.get().last().translate((float) x, (float) y, (float) z);
//    }
//
//    @Inject(method = "scale(FFF)V", at = @At("TAIL"))
//    private static void dypsis$scale(float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().scale(x, y, z);
//    }
//
//    @Inject(method = "scale(DDD)V", at = @At("TAIL"))
//    private static void dypsis$scale(double x, double y, double z, CallbackInfo ci) {
//        MatrixStack.get().last().scale((float) x, (float) y, (float) z);
//    }
//
//    @Inject(method = "rotate(FFFF)V", at = @At("TAIL"))
//    private static void dypsis$rotate(float angle, float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().rotate(angle, x, y, z);
//    }
//
//
//    @Inject(method = "loadIdentity", at = @At("TAIL"))
//    private static void dypsis$glLoadIdentity(CallbackInfo ci) {
//        MatrixStack.get().last().identity();
//    }
//
//    @Inject(method = "ortho", at = @At("TAIL"))
//    private static void dypsis$glOrtho(double left, double right, double bottom, double top, double zNear, double zFar, CallbackInfo ci) {
//        MatrixStack.get().last().ortho((float) left, (float) right, (float) bottom, (float) top, (float) zNear, (float) zFar);
//    }
//
//    @Inject(method = "multMatrix", at = @At("TAIL"))
//    private static void dypsis$glMultMatrix(FloatBuffer m, CallbackInfo ci) {
//        MatrixStack.get().last().mul(new Matrix4f(m));
//    }
//
//    @Inject(method = "matrixMode", at = @At("TAIL"))
//    private static void dypsis$glMatrixMode(int mode, CallbackInfo ci) {
//        MatrixStack.set(mode);
//    }
}
