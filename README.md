# Vulture

Vulture is a WIP 2D engine built with LWJGL3.

## GLSL
- The standard version number for shaders is #130.
- Added the function #uniforminclude which allows one to include other GLSL sources into the shader from
the source / CPU.

Reminder: You must use setTextureSlot(...) when using multiple textures

## Planet Generation
Planet generation is done on both the CPU and GPU. First, the noise needed is created and distorted with java on the CPU.
The texture is passed on to GL, where it is colorized using a color table. Finally. the atmosphere is added using `smoothstep`.

TODO with planets
- Perhaps implement some sort of lighting system - though maintain retro style.

## Fonts
When creating font files with Hiero (libgdx font creator), **edit out all kerning information to avoid NumberException** .