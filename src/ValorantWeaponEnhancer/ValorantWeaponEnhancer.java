package ValorantWeaponEnhancer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.util.logging.Level.OFF;
import static java.util.logging.Logger.getLogger;
import static org.jnativehook.GlobalScreen.addNativeKeyListener;
import static org.jnativehook.GlobalScreen.addNativeMouseListener;
import static org.jnativehook.GlobalScreen.registerNativeHook;
import static org.jnativehook.GlobalScreen.removeNativeMouseListener;

public class ValorantWeaponEnhancer implements NativeMouseListener, NativeKeyListener {

    private final static int FIRE_MOUSE_BUTTON = NativeMouseEvent.BUTTON1;
    private final static int FIRE_KEY_FROM_CONTROLS = KeyEvent.VK_HOME;

    private static boolean isMousePressed;
    private static Robot robot = null;

    public int cooldownMillis = 20; //default

    public ValorantWeaponEnhancer() throws AWTException {
        robot = new Robot();

        disableNativeHookLogger();
    }

    private class Clicker extends Thread {
        @Override
        public void run() {
            while (isMousePressed) {
                simulateKeyClick(FIRE_KEY_FROM_CONTROLS);
                try {
                    simulateCooldown(cooldownMillis);
                } catch (InterruptedException ignore) {}
            }
        }
    }

    //start-stop
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        //ignore
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if (nativeMouseEvent.getButton() == FIRE_MOUSE_BUTTON) {
            isMousePressed = true;
            Thread clicker = new Clicker();
            clicker.start();
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        isMousePressed = false;
    }


    void run() throws NativeHookException {
        registerNativeHook();

        addNativeKeyListener(this);
    }

    private void simulateCooldown(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    private void simulateKeyClick(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public void deactivate() {
        removeNativeMouseListener(this);
    }

    public void activate() {
        addNativeMouseListener(this);
    }

    private static void disableNativeHookLogger() {
        final var logger = getLogger( GlobalScreen.class.getPackage().getName() );
        logger.setLevel( OFF );
        logger.setUseParentHandlers( false );
    }

    //Ignored

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        //ignore
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        //ignore
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        //ignore
    }
}
