# Dypsis

Dypsis is an optimization mod with a focus on improving client-side rendering performance. It contains both improvements for vanilla and targeted optimizations for mods.

### Vanilla

- **Framebuffer Optimization Strategy**
  - **Conservative** mode optimizes framebuffer blitting with a more efficient method.
  - **Aggressive** mode makes the game directly render to the default framebuffer to skip the blitting process altogether.
- **HUD Batching**: Draw HUD elements in batches to improve performance.
- **Smart Entity Outline Rendering**: Skips entity outline (a.k.a. glowing effect) rendering when there is no entity visible with outline.

### Mods

- **Ender IO**: Batched rendering for conduits, significantly improves performance when you have a handful of conduits in the world.
- **The One Probe**: Skips redundant GL calls.

### Advanced

- **(WIP) Modern Rendering Pipeline**: Very experimental and immature replacement of the vertex array usage in Tessellator. Utilizes VAO and DSA.
