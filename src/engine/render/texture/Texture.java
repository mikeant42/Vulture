package engine.render.texture;

import engine.util.ResourceManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

/**
 * Created by anarchist on 8/28/16.
 */
public class Texture {
    private int width;
    private int height;
    private int id;

    private int numberOfRows = 1;
    private float textureXOffset;
    private float textureYOffset;

    private ByteBuffer imageBuffer;

    public Texture(int width, int height, ByteBuffer data) {
        this(width, height, data, GL11.GL_TEXTURE_2D, GL11.GL_LINEAR, true, GL11.GL_RGBA8, GL11.GL_RGBA, true);
        this.imageBuffer = data;
    }

    private Texture(int width, int height, ByteBuffer data, int target, int filter, boolean mipMap, int internalFormat, int format, boolean clamp) {
        id = GL11.glGenTextures();
        this.width = width;
        this.height = height;

        GL11.glBindTexture(target, id);

        GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, filter);
        GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, filter);

        if (clamp) {
            GL11.glTexParameterf(target, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameterf(target, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        GL11.glTexImage2D(target, 0, internalFormat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data);

        if (mipMap) {
            GL30.glGenerateMipmap(target);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
            GL11.glTexParameterf(target, GL14.GL_TEXTURE_LOD_BIAS, 0);
            if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
                float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                GL11.glTexParameterf(target,  EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
            }
        }
    }


    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void bind(int num) {
        GL13.glActiveTexture(num);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return id;
    }

    public float getTextureXOffset(int textureIndex) {
        int column = textureIndex % numberOfRows;
        return (float)column / (float)numberOfRows;
    }

    public void setTextureXOffset(float textureXOffset) {
        this.textureXOffset = textureXOffset;
    }

    public float getTextureYOffset(int textureIndex) {
        int row = textureIndex  / numberOfRows;
        return (float)row / (float)numberOfRows;
    }

    public void setTextureYOffset(float textureYOffset) {
        this.textureYOffset = textureYOffset;
    }

    public ByteBuffer getImageBuffer() {
        return imageBuffer;
    }

    public static Texture loadTexture(String path) {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(0);
        ByteBuffer image = stbi_load(ResourceManager.LoadTexturePath(path), w, h, comp, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + stbi_failure_reason());
        }

        int width = w.get();
        int height = h.get();

        return new Texture(width, height, image);
    }

    /*
    * Converts BufferedImage to ByteBuffer
    * Method from: http://stackoverflow.com/questions/29301838/converting-bufferedimage-to-bytebuffer
     */
    public static ByteBuffer toByteBuffer(BufferedImage bi) {
        ByteBuffer byteBuffer;
        DataBuffer dataBuffer = bi.getRaster().getDataBuffer();

        if (dataBuffer instanceof DataBufferByte) {
            byte[] pixelData = ((DataBufferByte) dataBuffer).getData();
            byteBuffer = ByteBuffer.wrap(pixelData);
        } else if (dataBuffer instanceof DataBufferUShort) {
            short[] pixelData = ((DataBufferUShort) dataBuffer).getData();
            byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
            byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        } else if (dataBuffer instanceof DataBufferShort) {
            short[] pixelData = ((DataBufferShort) dataBuffer).getData();
            byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
            byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        } else if (dataBuffer instanceof DataBufferInt) {
            int[] pixelData = ((DataBufferInt) dataBuffer).getData();
            byteBuffer = ByteBuffer.allocate(pixelData.length * 4);
            byteBuffer.asIntBuffer().put(IntBuffer.wrap(pixelData));
        } else {
            throw new IllegalArgumentException("Not implemented for data buffer type: " + dataBuffer.getClass());
        }

        return byteBuffer;

    }

}
