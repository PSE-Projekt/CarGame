package de.cargame.controller.input;

/**
 * Represents a generic input device that serves as the base class for all device-specific
 * input handling implementations. The {@code InputDevice} class provides a common interface
 * for input event processing and supports the observer pattern for notifying registered
 * observers about input events.
 * <p>
 * This class is abstract and must be extended by specific input device implementations, such
 * as keyboards, gamepads, or other input hardware. Each concrete implementation is responsible
 * for defining how input is read, processed, and forwarded to observers.
 * <p>
 * This class implements the {@code UserInputObservable} interface, which defines methods for
 * registering, removing, and notifying user input observers.
 */
public abstract class InputDevice implements UserInputObservable {

    protected InputDevice() {
    }
}
