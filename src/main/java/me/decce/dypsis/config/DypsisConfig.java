package me.decce.dypsis.config;

import me.decce.dypsis.Tags;
import me.decce.dypsis.mixins.early.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MODID)
public class DypsisConfig {
    @Config.Name("Use Uniform Cache")
    @Config.Comment("Enable to use cache for uniform locations and values. Offers a small performance boost.")
    @Config.RequiresMcRestart
    @Config.Ignore
    public static boolean uniformCache = true;

    @Config.Name("Track Matrices")
    @Config.Ignore
    public static boolean trackMatrices = false;

    @Config.Name("Indirect Drawing")
    @Config.Ignore
    public static boolean indirectDrawing = false;

    @Config.Name("Batch Drawing")
    @Config.Ignore //TODO
    public static boolean shaderBatching = false;

    @Config.Name("Advanced")
    @Config.LangKey("dypsis.config.advanced")
    public static final Advanced ADVANCED = new Advanced();

    @Config.Name("Mods")
    @Config.LangKey("dypsis.config.mods")
    public static final Mods MODS = new Mods();

    @Config.Name("Vanilla")
    @Config.LangKey("dypsis.config.vanilla")
    public static final Vanilla VANILLA = new Vanilla();

    public static class Mods {
        @Config.Name("Ender IO")
        @Config.LangKey("dypsis.config.mods.enderio")
        public final EnderIO ENDERIO = new EnderIO();

        @Config.Name("The One Probe")
        @Config.LangKey("dypsis.config.mods.theoneprobe")
        public final TheOneProbe THEONEPROBE = new TheOneProbe();

        public static class EnderIO {
            @Config.Name("Batched Conduit Rendering")
            @Config.Comment("Enable to render EnderIO conduits in batches, significantly reducing the number of GL state changes, draw calls and vertex pointer setups.\n\nMassively improves performance when you have a handful of EnderIO conduits in the world.")
            public boolean batchConduits = true;
        }

        public static class TheOneProbe {
            @Config.Name("Remove Redundant GL Calls")
            @Config.Comment("Removes unneeded OpenGL calls to improve performance")
            public boolean removeRedundant = true;
        }
    }

    public static class Vanilla {
        public enum FBOEnum {
            NONE,
            CONSERVATIVE,
            AGGRESSIVE
        }

        @Config.Name("Framebuffer Optimization Strategy")
        @Config.Comment({
                "Valid values:",
                "",
                "NONE: Disables the optimization",
                "CONSERVATIVE: Applies conservative optimizations to make framebuffer blitting faster",
                "AGGRESSIVE: Applies aggressive optimizations that skips the framebuffer blitting altogether. Incompatible with most shaders. Should be compatible with most mods - bug reports welcome",
                "",
                "The aggressive option provides similar levels of performance as OptiFine's Fast Render option, but has much better mod compatibility. You should not be using both at the same time."
        })
        public FBOEnum fboOptimization = FBOEnum.CONSERVATIVE;

        @Config.Name("Smart Entity Outline Rendering")
        @Config.Comment("Skips entity outline (a.k.a. glowing effect) rendering when there is no entity visible with outline.")
        public boolean skipEntityFBO = true;

        @Config.Name("HUD Batching")
        @Config.Comment("Draw vanilla HUD elements in batches to improve performance")
        public boolean hudBatching = true;

        @Config.Name("Optimized Font Renderer")
        @Config.Comment("Optimizes the font renderer with vertex arrays and batching techniques")
        @Config.Ignore //TODO not working
        public boolean fontBatching = false;
    }

    public static class Advanced {
        @Config.Name("(WIP) Modern Rendering Pipeline")
        @Config.Comment({
                "Enable to use a modern rendering pipeline replacing some of the fixed-functionality pipeline usages in Tessellator.",
                "",
                "Work-in-progress. For now, do not report issues when enabled."})
        public boolean modernized = false;

        @Config.Name("VBO Size")
        @Config.Comment("Specifies the size of the VBO in KBs")
        @Config.RangeInt(min = 256, max = 16384)
        @Config.RequiresMcRestart
        public int vboSize = 4096;

        @Config.Name("EBO Size")
        @Config.Comment("Specifies the size of the EBO in KBs")
        @Config.RangeInt(min = 256, max = 16384)
        @Config.RequiresMcRestart
        public int eboSize = 4096;

        @Config.Name("Indirect Buffer Size")
        @Config.Comment("Specifies the size of the indirect drawing command buffer object in KBs")
        @Config.RangeInt(min = 256, max = 16384)
        @Config.RequiresMcRestart
        public int indirectSize = 1024;

        @Config.Name("Max Buffer Count")
        @Config.Comment("Specifies the maximum count of persistent buffers for each target. It is generally not recommended to change this value unless you know what you are doing.")
        @Config.RangeInt(min = 1, max = 5)
        @Config.RequiresMcRestart
        public int maxBufferCount = 2;
    }

    @Mod.EventBusSubscriber(modid = Tags.MODID)
    public static class ConfigSyncHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Tags.MODID)) {
                ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);

                ((MinecraftAccessor)Minecraft.getMinecraft()).callUpdateFramebufferSize();
            }
        }
    }

}
