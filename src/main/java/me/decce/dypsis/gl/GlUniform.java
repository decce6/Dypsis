package me.decce.dypsis.gl;

import java.util.function.BiConsumer;

public class GlUniform<T> {
    private final BiConsumer<String, T> setter;
    public final String name;
    private final T value;

    public GlUniform(String name, T value, BiConsumer<String, T> setter) {
        this.name = name;
        this.value = value;
        this.setter = setter;
    }

    public void set() {
        setter.accept(name, value);
    }
}
