# Vulture

Vulture is a WIP 2D engine built with LWJGL3.

## GLSL
- The standard version number for shaders is #130.
- Added the function #uniforminclude which allows one to include other GLSL sources into the shader from
the source / CPU. Right now you can only have one, but maybe create a Map<> later on so multiple can be used.

Reminder: You must use setTextureSlot(...) when using multiple textures

## General Game Mechanics
- There should be some sort of direction/compass/minimap system in space. The scenes are fairly big.
- Need to build quadtree for collision detection
- Input needs to be more organized, for the `input` Node function there should be some sort of InputManager passed in as a param

## Planet Generation
Planet generation is done on both the CPU and GPU. First, the noise needed is created and distorted with java on the CPU.
The texture is passed on to GL, where it is colorized using a color table. Finally. the atmosphere is added using `smoothstep`.

TODO with planets from space
- Perhaps implement some sort of lighting system - though maintain retro style.
- Need to get usable gas giants.
- Have all of the clouds across the planet at once - regardless of `time` veriable.

# Starship
So far, the starship is not in very good shape. The appearance badly needs reworking, and the controls go all over the place.
Hopefully I can continue to ignore using vertices, and make everything in GLSL.

## Fonts
When creating font files with Hiero (libgdx font creator), **edit out all kerning information to avoid NumberException** .

## ResourceManager
Not the prettiest thing in the world, definitely needs changing. Works for now, a little overdone.

## Notes on Seed Generation
Both the outside planet seed generator and the ground seed gen need to gives similar, if not the same results. E.g. seed of x gives them both hot, barren world attributes.

The unique planet seed should be generated like `genPlanetSeed(Seed universeSeed, Seed galaxySeed, Vec2 position)`

