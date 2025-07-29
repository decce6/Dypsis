package me.decce.dypsis;

import me.decce.dypsis.util.LwjglHelper;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@IFMLLoadingPlugin.Name(Tags.MODNAME)
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class DypsisPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
    @Override
    public void injectData(Map<String, Object> data) {
        // TODO: do not run when the modern rendering pipeline is disabled in config
        // Must be run before class org.lwjgl.LWJGLUtil is loaded because the system property is read and stored into a final boolean field
        // Should not apply on LWJGL3, but calling Sys.getVersion() classloads the aforementioned class
        LwjglHelper.applyLwjgl2Workarounds();
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.dypsis.early.json");
    }
}