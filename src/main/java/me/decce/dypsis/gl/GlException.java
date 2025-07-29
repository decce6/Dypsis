package me.decce.dypsis.gl;

public class GlException extends Exception {
    public GlException() {
        this("An OpenGL error occurred!");
    }

    public GlException(String message) {
        super(message);
    }
}
