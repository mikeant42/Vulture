package engine.render;

import engine.input.CursorHandler;
import engine.input.KeyboardHandler;
import engine.input.MouseHandler;
import engine.input.ScrollHandler;
import engine.math.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.WGLARBCreateContext.nwglCreateContextAttribsARB;


/**
 * Created by anarchist on 6/13/16.
 *
 */
public class DisplayManager {

    private static String title = "Window";

    private static long window;

    private static double lastFrameTime;
    private static double delta;

    // Note that these have to continue to change until 4k moniters scale correctly.
    private static int WINDOW_WIDTH = 1024;
    private static int WINDOW_HEIGHT = 768;
    private static float ASPECT_RATIO = WINDOW_WIDTH / WINDOW_HEIGHT;

    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseCallback;
    private static GLFWScrollCallback scrollCallback;
    private static GLFWCursorPosCallback cursorCallback;

    private static int[] glw;
    private static int[] glh;

    public static void createDisplay(String txt) {
        title = txt;


        GLFWErrorCallback.createPrint(System.err).set();

        if ( !GLFW.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");


        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, GLFW.GLFW_NATIVE_CONTEXT_API);


        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, title, MemoryUtil.NULL, MemoryUtil.NULL);


        glw = new int[5];
        glh = new int[5];
        GLFW.glfwGetFramebufferSize(window, glw, glh);


        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create window");
        }

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // Center our window
        GLFW.glfwSetWindowPos(
                window,
                (vidmode.width() - WINDOW_WIDTH) / 2,
                (vidmode.height() - WINDOW_HEIGHT) / 2
        );

//        int[] widthMM = new int[5];
//        int[] heightMM = new int[5];
//        GLFW.glfwGetMonitorPhysicalSize(GLFW.glfwGetPrimaryMonitor(), widthMM, heightMM);
//
//        double dpi = vidmode.width() / (widthMM[0] / 25.4);
//        System.out.println(widthMM[0]);

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);


        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(window);

        GLFW.glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
        GLFW.glfwSetScrollCallback(window, scrollCallback = new ScrollHandler());
        GLFW.glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        GLFW.glfwSetCursorPosCallback(window, cursorCallback = new CursorHandler());

        lastFrameTime = currentTimeMillis();

        GL.createCapabilities();


        GL11.glViewport(0, 0, glw[0], glh[0]);

    }

    public static double getTime() {
        return GLFW.glfwGetTime();
    }


    public static boolean getShouldWindowClose() {
        return (GLFW.glfwWindowShouldClose(window));
    }

    public static void loop() {
        inputGraphicsUpdate();

        double currentTimeFrame = GLFW.glfwGetTime();
        delta = (currentTimeFrame - lastFrameTime);
        lastFrameTime = currentTimeFrame;


        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
    }

    private static void inputGraphicsUpdate() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static void closeDisplay() {
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static double getFrameTimeSeconds() {
        return delta;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static float getAspectRatio() {
        return ASPECT_RATIO;
    }

    private static double currentTimeMillis() {
        return GLFW.glfwGetTime();
    }

}
