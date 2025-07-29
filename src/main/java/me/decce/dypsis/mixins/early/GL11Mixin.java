//package me.decce.dypsis.mixins;
//
//import me.decce.dypsis.gl.MatrixStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.client.renderer.GlStateManager;
//import org.joml.Matrix4d;
//import org.joml.Matrix4f;
//import org.lwjgl.opengl.GL11;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.nio.DoubleBuffer;
//import java.nio.FloatBuffer;
//
//// TODO LWJGL is not loaded by the LaunchClassLoader
//@Mixin(value = GL11.class, remap = false)
//public class GL11Mixin {
//    @Unique
//    private static int w() {
//        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();//Minecraft.getMinecraft().displayWidth;
//    }
//
//    @Unique
//    private static int h() {
//        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
//    }
//
//    @Inject(method = "glPushMatrix", at = @At("TAIL"))
//    private static void dypsis$glPushMatrix(CallbackInfo ci) {
//        MatrixStack.get().push();
//    }
//
//    @Inject(method = "glPopMatrix", at = @At("TAIL"))
//    private static void dypsis$glPopMatrix(CallbackInfo ci) {
//        MatrixStack.get().pop();
//    }
//
//    @Inject(method = "glTranslatef(FFF)V", at = @At("TAIL"))
//    private static void dypsis$glTranslatef(float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().translate(x / w(), y / h(), z);
//    }
//
//    @Inject(method = "glTranslated(DDD)V", at = @At("TAIL"))
//    private static void dypsis$glTranslated(double x, double y, double z, CallbackInfo ci) {
//        MatrixStack.get().last().translate((float) x / w(), (float) y / h(), (float) z);
//    }
//
//    @Inject(method = "glScalef(FFF)V", at = @At("TAIL"))
//    private static void dypsis$glScalef(float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().scale(x, y, z);
//    }
//
//    @Inject(method = "glScaled(DDD)V", at = @At("TAIL"))
//    private static void dypsis$glScaled(double x, double y, double z, CallbackInfo ci) {
//        MatrixStack.get().last().scale((float) x, (float) y, (float) z);
//    }
//
//    @Inject(method = "glRotatef(FFFF)V", at = @At("TAIL"))
//    private static void dypsis$glRotatef(float angle, float x, float y, float z, CallbackInfo ci) {
//        MatrixStack.get().last().rotate(angle, x, y, z);
//    }
//
//
//    @Inject(method = "glRotated(DDDD)V", at = @At("TAIL"))
//    private static void dypsis$glRotated(double angle, double x, double y, double z, CallbackInfo ci) {
//        MatrixStack.get().last().rotate((float) angle, (float) x, (float) y, (float) z);
//    }
//
//
//    @Inject(method = "glLoadIdentity", at = @At("TAIL"))
//    private static void dypsis$glLoadIdentity(CallbackInfo ci) {
//        MatrixStack.get().last().identity();
//    }
//
//    @Inject(method = "glOrtho", at = @At("TAIL"))
//    private static void dypsis$glOrtho(double left, double right, double bottom, double top, double zNear, double zFar, CallbackInfo ci) {
//        MatrixStack.get().last().ortho((float) left, (float) right, (float) bottom, (float) top, (float) zNear, (float) zFar);
//    }
//
//    @Inject(method = "glMultMatrix(Ljava/nio/FloatBuffer;)V", at = @At("TAIL"))
//    private static void dypsis$glMultMatrix(FloatBuffer m, CallbackInfo ci) {
//        MatrixStack.get().last().mul(new Matrix4f(m));
//    }
//
//    @Inject(method = "glMultMatrix(Ljava/nio/DoubleBuffer;)V", at = @At("TAIL"))
//    private static void dypsis$glMultMatrix(DoubleBuffer m, CallbackInfo ci) {
//        MatrixStack.get().last().mul(new Matrix4f(new Matrix4d(m)));
//    }
//
//    @Inject(method = "glMatrixMode", at = @At("TAIL"))
//    private static void dypsis$glMatrixMode(int mode, CallbackInfo ci) {
//        MatrixStack.set(mode);
//    }
//}
