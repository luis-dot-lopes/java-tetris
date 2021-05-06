package engine.core;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements KeyListener, MouseMotionListener, MouseListener{
    private static InputManager ourInstance;

    private HashMap<Integer, Integer> keyCache;

    private ArrayList<Integer> pressedKeys;
    private ArrayList<Integer> releasedKeys;

    private final int KEY_PRESSED = 1;
    private final int KEY_RELEASED = 2;
    private final int KEY_JUST_PRESSED = 3;


    private Point mouse;
    private int mouseButton;

    public static InputManager getInstance() {
        if(ourInstance == null) ourInstance = new InputManager();
        return ourInstance;
    }

    private InputManager() {
        keyCache = new HashMap<>();
        pressedKeys = new ArrayList<>();
        releasedKeys = new ArrayList<>();
        mouse = new Point();
    }

    void update() {
        for (Integer keyCode : keyCache.keySet()) {
            if (isJustPressed(keyCode)){
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        for (Integer keyCode : releasedKeys) {
            keyCache.put(keyCode, KEY_RELEASED);
        }
        for (Integer keyCode : pressedKeys) {
            if (isReleased(keyCode)) {
                keyCache.put(keyCode, KEY_JUST_PRESSED);
            } else {
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        pressedKeys.clear();
        releasedKeys.clear();

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //Nothing here
    }

    public int getMouseX() {
        return mouse.x;
    }
    public int getMouseY() {
        return mouse.y;
    }

    public boolean isMousePressed(int btn) {
        return mouseButton == btn;
    }

    public boolean isPressed(int keyId) {
        return keyCache.containsKey(keyId)
                && keyCache.get(keyId).equals(KEY_PRESSED);
    }

    public boolean isReleased(int keyId) {
        return !keyCache.containsKey(keyId)
                || keyCache.get(keyId).equals(KEY_RELEASED);
    }

    public boolean isJustPressed(int keyId) {
        return keyCache.containsKey(keyId)
                && keyCache.get(keyId).equals(KEY_JUST_PRESSED);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
       releasedKeys.add(e.getKeyCode());

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseButton = mouseEvent.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseButton = 0;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouse.setLocation(mouseEvent.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouse.setLocation(mouseEvent.getPoint());
    }
}
