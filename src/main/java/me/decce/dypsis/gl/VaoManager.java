package me.decce.dypsis.gl;

import me.decce.dypsis.util.HashHelper;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.renderer.BufferBuilder;

public final class VaoManager {
    private static final Int2ReferenceOpenHashMap<Vao> vaos = new Int2ReferenceOpenHashMap<>();

    public static Vao vaoOf(BufferBuilder builder) {
        int key = HashHelper.hash(builder);
        Vao vao = vaos.get(key);
        if (vao == null) {
            return null;
        }
        vao.verify(builder);
        return vao;
    }

    public static Vao createVao(BufferBuilder builder, GlBufferSubData vbo, GlBufferSubData ebo) {
        Vao vao = new Vao(builder, vbo, ebo);
        vbo.vaos().add(vao.hash);
        if (ebo != null) {
            ebo.vaos().add(vao.hash);
        }
        vao.configure();
        vaos.put(vao.hash, vao);
        return vao;
    }

    public static void invalidateVao(GlBufferSubData data) {
        IntArrayList vaosToRemove = data.vaos();
        for (int i = 0; i < vaosToRemove.size(); i++) {
            int key = vaosToRemove.getInt(i);
            if (vaos.containsKey(key)) {
                Vao vaoToRemove = vaos.get(key);
                IndirectDrawer.invalidateVao(vaoToRemove);
                vaoToRemove.delete();
                vaos.remove(key);
            }
        }
    }
}
