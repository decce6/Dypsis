package me.decce.dypsis.gl;

public abstract class GlObject {
    protected int id;

    protected GlObject() {
    }

    public abstract void delete();

    public int id() {
        return id;
    }
}
