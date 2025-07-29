package me.decce.dypsis.gl;

import me.decce.dypsis.util.GlStates;

public class DeferredDrawCommand {
    private final Vao vao;
    private final GlStates states;
    private final GlUniform<?>[] uniforms;

    public DeferredDrawCommand(Vao vao, GlStates states, GlUniform<?>[] uniforms) {
        this.vao = vao;
        this.states = states;
        this.uniforms = uniforms;
    }

    public void run() {
        states.apply();
        for (int i = 0; i < uniforms.length; i++) {
            uniforms[i].set();
        }
        vao.bind();
        vao.draw();
        vao.unbind();
    }
}
