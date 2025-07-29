package me.decce.dypsis.gl;

public class GlRuntimeException extends RuntimeException {
    public GlRuntimeException() {
        this("An OpenGL error occurred!");
    }

    public GlRuntimeException(String message) {
        super(message);
    }
}
