package me.decce.dypsis.util;

public class LwjglHelper {
    public static void applyLwjgl2Workarounds() {
        // We use GL45#glVertexArrayElementBuffer to specify the EBO, but LWJGL2 is not aware of this and throws an
        // exception complaining no EBO is bound when we try to call GL11#glDrawElements. We disable the checks from
        // LWJGL to prevent game crashes.
        System.setProperty("org.lwjgl.util.NoChecks", "true");
    }
}
