package engine.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

public abstract class Game implements WindowListener {

    private boolean quit;
    private BufferStrategy bufferStrategy;
    private JFrame frame;
    private SpeedTracker speedTracker;
    private int expectedTPS;
    private double expectedNanosPerTick;
    private int maxFrameSkip;


    public Game() {
        frame = new JFrame("TetrisGame");
        frame.setUndecorated(true);
        frame.setSize(800, 600);
        frame.addWindowListener(this);
        frame.addKeyListener(InputManager.getInstance());
        frame.addMouseListener(InputManager.getInstance());
        frame.addMouseMotionListener(InputManager.getInstance());
        quit = false;
    }

    public void run() {
        load();
        expectedTPS = 60;
        expectedNanosPerTick = SpeedTracker.NANOS_IN_ONE_SECOND / expectedTPS;
        maxFrameSkip = 10;
        long nanoTimeAtNextTick = System.nanoTime();
        int skippedFrames = 0;
        while (!quit) {
            speedTracker.update();
            if (System.nanoTime() > nanoTimeAtNextTick && skippedFrames < maxFrameSkip) {
                nanoTimeAtNextTick += expectedNanosPerTick;
                InputManager.getInstance().update();
                update();
                skippedFrames ++;
            } else {
                render();
                skippedFrames = 0;
            }
        }
        unload();
    }

    private void load() {
        frame.setIgnoreRepaint(true);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.createBufferStrategy(2);
        bufferStrategy = frame.getBufferStrategy();
        speedTracker = new SpeedTracker();
        onLoad();
        speedTracker.start();
    }

    private void unload() {
        onUnload();
        bufferStrategy.dispose();
        frame.dispose();
    }

    private void update() {
        speedTracker.countTick();
        onUpdate(speedTracker.getTotalTicks());
        Thread.yield();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        onRender(g);
        g.dispose();
        bufferStrategy.show();
    }

    protected void terminate() {
        quit = true;
    }

    protected abstract void onLoad();

    protected abstract void onUnload();

    protected abstract void onUpdate(int ticks);

    protected abstract void onRender(Graphics2D g);

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        terminate();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
