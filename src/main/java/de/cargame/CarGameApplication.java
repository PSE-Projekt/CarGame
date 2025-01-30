package de.cargame;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import de.cargame.controller.GameApplicationManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarGameApplication {

    /**
     * The main method serves as the entry point for the application.
     * It initializes system properties, sets up native library paths, registers hooks using `GlobalScreen`,
     * enables OpenGL rendering, and creates an instance of the `GameApplicationManager`
     * to manage the game lifecycle.
     *
     * @param args the command-line arguments passed to the application, not utilized within the implementation.
     */
    public static void main(String[] args) {
        // Set java.library.path to include the extracted native libraries
        System.setProperty("java.library.path", "target/natives");

        // Force JVM to reload the library path
        try {
            System.setProperty("jna.library.path", "target/natives");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        System.setProperty("sun.java2d.opengl", "true");
        GameApplicationManager gameApplicationManager = new GameApplicationManager();
    }

}
