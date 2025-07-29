package me.decce.dypsis.gl;

import me.decce.dypsis.config.DypsisConfig;
import me.decce.dypsis.gl.util.CommonUniforms;
import me.decce.dypsis.util.GlStates;
import me.decce.dypsis.util.String2IntNoopMap;
import me.decce.dypsis.util.Int2ObjectNoopMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.minecraft.client.renderer.GLAllocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.Objects;
import java.util.function.Function;

public class GlShader {
    private static final FloatBuffer matrixBuffer = GLAllocation.createDirectFloatBuffer(16);
    private GlShader(int program) {
        this.program = program;
        this.uniforms = DypsisConfig.uniformCache ? new Int2ObjectOpenHashMap<>() : new Int2ObjectNoopMap();
        this.locations = DypsisConfig.uniformCache ? new Object2IntOpenHashMap<>() : new String2IntNoopMap();
        this.drawCommands = new ObjectArrayFIFOQueue<>();
    }

    private final ObjectArrayFIFOQueue<DeferredDrawCommand> drawCommands;
    // uniform name -> location
    private final Object2IntOpenHashMap<String> locations;
    // uniform location -> value
    private final Int2ObjectOpenHashMap<Object> uniforms;
    private final int program;

    public void use() {
        GL20.glUseProgram(program);
    }

    public void unuse() {
        GL20.glUseProgram(0);
    }

    public void delete() {
        GL20.glDeleteProgram(program);
    }

    public void drawLater(Vao vao) {
        GlUniform<?>[] uniforms = CommonUniforms.get(this);
        this.drawCommands.enqueue(new DeferredDrawCommand(vao, new GlStates(), uniforms));
    }

    public void flush() {
        if (this.drawCommands.isEmpty()) {
            return;
        }
        GlStates states = new GlStates();
        this.use();
        while (!this.drawCommands.isEmpty()) {
            DeferredDrawCommand command = this.drawCommands.dequeue();
            command.run();
        }
        this.unuse();
        states.apply();
    }

    private int getLocation(String uniform) {
        if (!this.locations.containsKey(uniform)) {
            int location = GL20.glGetUniformLocation(program, uniform);
            this.locations.put(uniform, location);
            return location;
        }
        return this.locations.getInt(uniform);
    }

    public void setInt(String name, int value) {
        int location = getLocation(name);
        if (!Objects.equals(value, uniforms.get(location))) {
            GL20.glUniform1i(location, value);
            uniforms.put(location, Integer.valueOf(value));
        }
    }

    public void setBool(String name, boolean value) {
        int location = getLocation(name);
        if (!Objects.equals(value, uniforms.get(location))) {
            GL20.glUniform1i(location, value ? 1 : 0);
            uniforms.put(location, Boolean.valueOf(value));
        }
    }

    public void setVec2f(String name, Vector2f value) {
        int location = getLocation(name);
        if (!Objects.equals(value, uniforms.get(location))) {
            GL20.glUniform2f(location, value.x, value.y);
            uniforms.put(location, new Vector2f(value));
        }
    }

    public void setVec4f(String name, Vector4f value) {
        int location = getLocation(name);
        if (!Objects.equals(value, uniforms.get(location))) {
            GL20.glUniform4f(location, value.x, value.y, value.z, value.w);
            uniforms.put(location, new Vector4f(value));
        }
    }

    public void setMatrix4f(String name, Matrix4f value) {
        this.setMatrix4f(name, value, false);
    }

    public void setMatrix4f(String name, Matrix4f value, boolean noCopy) {
        int location = getLocation(name);
        if (!Objects.equals(value, uniforms.get(location))) {
            value.get(matrixBuffer);
            GL20.glUniformMatrix4(location, false, matrixBuffer);
            uniforms.put(location, noCopy ? value : new Matrix4f(value));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int vsh;
        private int fsh;

        private Builder() { }

        public Builder withVertexShader(String filename, Function<String, String> reader) {
            this.vsh = createShader(GL20.GL_VERTEX_SHADER, filename, reader);
            return this;
        }

        public Builder withFragmentShader(String filename, Function<String, String> reader) {
            this.fsh = createShader(GL20.GL_FRAGMENT_SHADER, filename, reader);
            return this;
        }

        public GlShader build() {
            int program = GL20.glCreateProgram();
            GL20.glAttachShader(program, vsh);
            GL20.glAttachShader(program, fsh);
            GL20.glLinkProgram(program);
            int result = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
            if(result == 0) {
                throw new GlRuntimeException("Failed to link shaders!\n" + GL20.glGetProgramInfoLog(program, 512));
            }
            GL20.glDeleteShader(vsh);
            GL20.glDeleteShader(fsh);
            return new GlShader(program);
        }

        private static String processSource(String source, Function<String, String> reader) {
            String str = "#include \"";
            while (source.contains(str)) {
                int i = source.indexOf(str);
                int left = i + str.length();
                int right = source.indexOf('"', left);
                String filename = source.substring(left, right);
                String file = reader.apply(filename);
                String toReplace = str + filename + "\"";
                source = source.replace(toReplace, file);
            }
            return source;
        }

        private int createShader(int type, String filename, Function<String, String> reader) {
            int sh = GL20.glCreateShader(type);
            GL20.glShaderSource(sh, processSource(reader.apply(filename), reader));
            GL20.glCompileShader(sh);
            int result = GL20.glGetShaderi(sh, GL20.GL_COMPILE_STATUS);
            if (result == 0) {
                throw new GlRuntimeException("Failed to compile shader!\n" + GL20.glGetShaderInfoLog(sh, 512));
            }
            return sh;
        }
    }
}
