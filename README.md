# Vulture

Vulture is a WIP 2D engine built with LWJGL3.

 GLSL
- The standard version number for shaders is #130.

Reminder: You must use setTextureSlot(..) when using multiple textures

## Planet Generation
Planet generation is done on both the CPU and GPU. First, the noise needed is created and distorted with java on the CPU.
The texture is passed on to GL, where it is colorized using a color table. Finally. the atmosphere is added using `smoothstep`.
