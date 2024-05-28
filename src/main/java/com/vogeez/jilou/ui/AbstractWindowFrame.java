package com.vogeez.jilou.ui;

import com.vogeez.jilou.ApplicationFactory;
import com.vogeez.jilou.logic.Renderer;
import com.vogeez.jilou.logic.graphics.WidgetBackgroundRenderer;
import com.vogeez.jilou.style.Color;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class AbstractWindowFrame implements ResizeAble {

    private final Logger LOG = LoggerFactory.getLogger(AbstractWindowFrame.class);

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    @Getter
    private final List<Renderer> rendererList = new ArrayList<>();

    private final CountDownLatch async;

    @Getter
    protected Context context;

    @Getter
    private final Thread thread;
    @Getter
    private final String uid;

    protected long openglID;

    @Getter
    private String title;
    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    protected Color background;

    private boolean created;
    private boolean vsync;

    /* ############################################################################################
     *
     *                                           Constructors
     *
     * ############################################################################################ */

    /**
     * Constructor for creating a new {@link AbstractWindowFrame} object. The constructor will be implemented at all
     * {@link Class}'s which implements (extends) this class. Please note that for window create the {@link ApplicationFactory}
     * class is super helpful and flexible for include your own window implementations.
     * @param uid the specified and unique window id.
     * @see ApplicationFactory
     */
    public AbstractWindowFrame(String uid) {
        this.async = new CountDownLatch(1);
        this.uid = generateUID(uid);
        this.openglID = 0;
        this.title = getClass().getSimpleName();
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.setBackground(Color.WHITE);
        this.created = false;
        this.thread = ApplicationFactory.generateThread(this::run, this);
        if(thread != null) {
            thread.start();
        }
    }

    /* ############################################################################################
     *
     *                             Abstract / Implementation Functions
     *
     * ############################################################################################ */


    /**
     * Function which runs our implementations. This is called at the window {@link Thread}.
     * This is the lifecycle for the {@link AbstractWindowFrame}.
     * First call {@link #initialize()} for initialize things like special events or handler's.
     * Second call {@link #update()} and this will loop! Note that.
     * Last call {@link #destroy()} for clean up.
     */
    protected void run() {
        initialize();
        update();
        destroy();
    }

    /**
     * Function is used for the initialization state at a window.
     * You can create here object like one use thinks or variables like final. Note I mean like final you can't
     * use here final as key word because this can only in constructors. This function is called automatically
     * in the {@link #run()} life span.
     */
    protected abstract void initialize();

    /**
     * Function is used for the update/looping state for a window.
     * You can create here object like multi use thinks. Note don't use this function for initialization thinks
     * because it will call all milliseconds. If you need an object which need one time initialization then
     * use the {@link #initialize()} function instance of this.
     * This function is called automatically
     * in the {@link #run()} life span.
     */
    protected abstract void update();

    /**
     * Function is used for clean up your window. Use this after the window is closed for
     * clean list, maps or buffers. This was an example, you can here make what you want.
     * This is the end of a window life span and not the best location to create or initialize thinks.
     */
    public abstract void destroy();

    @Override
    public void setResizeable(boolean resizable) {
        GLFW.glfwSetWindowAttrib(getOpenglID(), GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    @Override
    public boolean isResizeable() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_RESIZABLE) == GLFW.GLFW_TRUE;
    }

    /* ############################################################################################
     *
     *                                        Internal Functions
     *
     * ############################################################################################ */

    /**
     * Record for store important information about the opengl context. This is a simple object which stores
     * openglID{@link GLFW#glfwCreateWindow(int, int, ByteBuffer, long, long)},
     * nvgID{@link NanoVGGL3#nvgCreate(int)}or{@link NanoVGGL2#nvgCreate(int)},
     * capabilities{@link GL#createCapabilities()}.
     * @since 0.1.0-alpha
     * @see NanoVGGL2
     * @see NanoVGGL3
     * @see GLFW
     * @see GLCapabilities
     * @author exepta
     * @param openglID window id which is given from{@link GLFW}
     * @param nvgID mem address from{@link NanoVGGL3}or{@link NanoVGGL2}
     * @param capabilities the current context capabilities by {@link GLCapabilities}
     */
    public record Context(long openglID, long nvgID, GLCapabilities capabilities) { }

    /**
     * Function for generating unique identification as{@link String}. Is needed for handle
     * multiply{@link AbstractWindowFrame}in one project. Every{@link AbstractWindowFrame}has his own uid
     * which can be give by{@link #getUid()}.
     * @param serial indicator{@link String}for give special id's. Can be the same as another one because the function
     *               will check and include an index at the end of the{@link String}.
     * @return {@link String}- the created serial id.
     */
    private String generateUID(String serial) {
        if(serial == null || serial.isEmpty()) {
            serial = getClass().getSimpleName();
        }

        if(ApplicationFactory.WINDOW_MAP.containsKey(serial)) {
            int index = 0;
            for(String IDs : ApplicationFactory.WINDOW_MAP.keySet()) {
                if(IDs.startsWith(serial) || IDs.startsWith(serial + "-")) {
                    index++;
                }
            }
            return generateUID(serial + "-" + index);
        }
        return serial;
    }

    /**
     * Function for printing graphics information's about the users graphic processor.
     * This function using{@link GL11}for fetch information.
     */
    private void printGraphics() {
        LOG.info("OpenGL Version [ {} ]", Objects.requireNonNull(GL11.glGetString(GL11.GL_VERSION)).substring(0, 3));
        LOG.info("Graphic Card [ {} ]", GL11.glGetString(GL11.GL_RENDERER));
        LOG.info("Graphic Provider [ {} ]", GL11.glGetString(GL11.GL_VENDOR));
    }

    /**
     * Function because we need to wait for window creation.
     */
    private void _wait() {
        try { async.await(); } catch (InterruptedException ignored) {}
    }

    /* ############################################################################################
     *
     *                                       Default Functions
     *
     * ############################################################################################ */

    /**
     * Function generates a new GLFW window by using {@link GLFW#glfwCreateWindow(int, int, ByteBuffer, long, long)}.
     * This will set up the class and usable for the user. You can override this function if you need your own system.
     * But make sure you set the openglID.
     */
    protected void build() {
        LOG.info("Build window [ {} ]", getUid());
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        this.openglID = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if(openglID <= NULL) {
            this.created = false;
            LOG.error("Build state error by [ {} ]", getUid());
            return;
        }

        registerDefaultRenderers();

        GLFW.glfwMakeContextCurrent(openglID);
        GLFW.glfwSwapInterval(1);
        LOG.info("Build state [ {} ], successful!", getUid());
        this.created = true;
        async.countDown();
    }

    /**
     * Function creates the configuration for the created window by {@link #build()}. Make sure you're using
     * this function after {@link #build()} was called. If you swap this to or ignores the build state, the window
     * will not work, because the function needs the openglID.
     */
    protected void defaultConfigure() {
        GLCapabilities capabilities = GL.createCapabilities();
        long nvgID;
        if(true) {
            nvgID = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS);
        } else {
            nvgID = NanoVGGL2.nvgCreate(NanoVGGL2.NVG_STENCIL_STROKES | NanoVGGL2.NVG_ANTIALIAS);
        }
        if(nvgID <= NULL) {
            LOG.error("Nano init state failed [ {} ]", getUid());
            return;
        }
        this.context = new Context(openglID, nvgID, capabilities);
        printGraphics();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        LOG.info("Configuration state [ {} ], successful!", getUid());
        this.show();
    }

    /**
     * Function set the current viewport of opengl by calling {@link GL11#glViewport(int, int, int, int)}.
     * This is helpful for games which need more flexibility.
     */
    protected void useViewport() {
        GL11.glViewport(0, 0, getWidth(), getHeight());
    }

    private void registerDefaultRenderers() {
        addRenderer(new WidgetBackgroundRenderer());
    }

    /* ############################################################################################
     *
     *                                    Renderer Function
     *
     * ############################################################################################ */

    public void addRenderer(Renderer renderer) {
        if(hasRenderer(renderer.getName())) {
            return;
        }

        rendererList.add(renderer);
        LOG.info("Renderer [ {} ] added to [ {} ]", renderer.getName(), getUid());
    }

    public void removeRenderer(Renderer renderer) {

    }

    public void removeRenderer(String name) {

    }

    public void removeAllRenderers() {

    }

    public boolean hasRenderer(Renderer renderer) {
        return false;
    }

    public boolean hasRenderer(String name) {
        return false;
    }

    public Renderer getRenderer(String name) {
        return null;
    }

    /* ############################################################################################
     *
     *                                       Events / Callbacks
     *
     * ############################################################################################ */

    /* ############################################################################################
     *
     *                                       Functions
     *
     * ############################################################################################ */

    public void alwaysOnTop(boolean alwaysOnTop) {
        GLFW.glfwSetWindowAttrib(getOpenglID(), GLFW.GLFW_FLOATING, alwaysOnTop ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    public void alwaysOnTop() {
        this.alwaysOnTop(!isAlwaysOnTop());
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(getOpenglID(), true);
    }

    public void focus(boolean focus) {
        if(focus) {
            GLFW.glfwFocusWindow(getOpenglID());
            return;
        }
        this.restore();
    }

    public void focus() {
        this.focus(!isFocused());
    }

    public void hide() {
        if(isVisible()) {
            GLFW.glfwHideWindow(getOpenglID());
        }
    }

    public void restore() {
        GLFW.glfwRestoreWindow(getOpenglID());
    }

    public void show() {
        if(!isVisible()) {
            GLFW.glfwShowWindow(getOpenglID());
        }
    }

    public void maximize(boolean maximize) {
        if(maximize) {
            GLFW.glfwMaximizeWindow(getOpenglID());
            return;
        }
        this.restore();
    }

    public void maximize() {
        this.maximize(!isMaximized());
    }

    public void minimize(boolean minimize) {
        if(minimize) {
            GLFW.glfwIconifyWindow(getOpenglID());
            return;
        }
        this.restore();
    }

    public void minimize() {
        this.minimize(!isMinimized());
    }

    /* ############################################################################################
     *
     *                                       Getter / Setter
     *
     * ############################################################################################ */

    public boolean isAlwaysOnTop() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_FLOATING) == GLFW.GLFW_TRUE;
    }

    public void setBackground(Color color) {
        if(color == null) {
            color = Color.GREY;
        }
        this.background = color;
    }

    public void setBackground(int red, int green, int blue) {
        this.setBackground(Color.rgb(red, green, blue));
    }

    public void setBackground(int red, int green, int blue, int alpha) {
        this.setBackground(Color.rgba(red, green, blue, alpha));
    }

    public void setBackground(String hexadecimal) {
        this.setBackground(Color.hexadecimal(hexadecimal));
    }

    public boolean isClosing() {
        _wait();
        return GLFW.glfwWindowShouldClose(getOpenglID());
    }

    public boolean isCreated() {
        _wait();
        return created;
    }

    public boolean isFocused() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_FOCUSED) == GLFW.GLFW_TRUE;
    }

    public void setHeight(int height) {
        _wait();
        if(height < 0) {
            height = 0;
        }
        this.height = height;
        GLFW.glfwSetWindowSize(getOpenglID(), getWidth(), height);
    }

    public boolean isMaximized() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
    }

    public boolean isMinimized() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_ICONIFIED) == GLFW.GLFW_TRUE;
    }

    public long getOpenglID() {
        _wait();
        return openglID;
    }

    public void setTitle(String title) {
        if(title == null) {
            title = getType();
        }
        this.title = title;
        GLFW.glfwSetWindowTitle(getOpenglID(), title);
    }

    public String getType() {
        return getClass().getSimpleName();
    }

    public boolean isVisible() {
        return GLFW.glfwGetWindowAttrib(getOpenglID(), GLFW.GLFW_VISIBLE) == GLFW.GLFW_TRUE;
    }

    public void setVsync(boolean vsync) {
        _wait();
        this.vsync = vsync;
        GLFW.glfwSwapInterval(vsync ? 1 : 0);
    }

    public boolean isVsync() {
        _wait();
        return vsync;
    }

    public void setWidth(int width) {
        _wait();
        if(width < 0) {
            width = 0;
        }
        this.width = width;
        GLFW.glfwSetWindowSize(getOpenglID(), width, getHeight());
    }
}
