package me.decce.dypsis.gl;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

// TODO: currently unused until we figure out a reasonable way to catch GL calls (LWJGL is in the classloader exclusion)
// TODO: achieve zero allocation by allocating Matrix4f objects ahead of time
public class MatrixStack {
    private static final MatrixStack modelView = new MatrixStack();
    private static final MatrixStack projection = new MatrixStack();
    private static final MatrixStack texture = new MatrixStack();
    private static final MatrixStack color = new MatrixStack();
    private static MatrixStack current = modelView;
    private final ObjectArrayList<Matrix4f> stack = new ObjectArrayList<>();

    private MatrixStack() {
        this.stack.push(new Matrix4f());
    }

    public static MatrixStack get() {
        return current;
    }

    public static MatrixStack modelView() {
        return modelView;
    }

    public static MatrixStack projection() {
        return projection;
    }

    public static void set(int mode) {
        switch (mode) {
            case GL11.GL_MODELVIEW:
                current = modelView;
                break;
            case GL11.GL_PROJECTION:
                current = projection;
                break;
            case GL11.GL_TEXTURE:
                current = texture;
                break;
            case GL11.GL_COLOR:
                current = color;
                break;
        }
    }

    public Matrix4f last() {
        return stack.top();
    }

    public void push() {
        stack.push(new Matrix4f(stack.top()));
    }

    public void pop() {
        stack.pop();
    }
}
