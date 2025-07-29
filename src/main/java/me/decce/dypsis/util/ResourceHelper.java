package me.decce.dypsis.util;

import me.decce.dypsis.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceHelper {
    public static String readShader(String filename) {
        return readResource("shaders/" + filename);
    }

    public static String readResource(String path) {
        return readResource(Tags.MODID, path);
    }

    public static String readResource(String namespace, String path) {
        try {
            IResource res = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(namespace, path));
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to read resource " + path + "!", throwable);
        }
    }
}
