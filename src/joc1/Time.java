package joc1;

final class Time {
    static double targetFrameTime = 1 / 200.0;
    static double fixedDeltaTime = 1 / 200.0;

    static double timeScale = 1;
    private static double unscaledDeltaTime = 0;
    private static double unscaledTime = 0;

    private static double deltaTime = 0;
    private static double time = 0;
    private static double unsimulatedTime = 0;

    static double targetFrameTime() {
        return targetFrameTime;
    }

    static double fixedDeltaTime() {
        return fixedDeltaTime;
    }

    static double timeScale() {
        return timeScale;
    }
    // Les tres funcions de dalt només estan per poder utilitzar el mateix format que amb la resta de variables

    static double unscaledDeltaTime() {
        return unscaledDeltaTime;
    }

    static double unscaledTime() {
        return unscaledTime;
    }

    static double deltaTime() {
        return deltaTime;
    }

    static double time() {
        return time;
    }

    static double unsimulatedTime() {
        return unsimulatedTime;
    }

    static void physicsStep() {
        unsimulatedTime -= fixedDeltaTime;
    }

    static void updateTimes(double realDeltaTime) {
        unscaledDeltaTime = realDeltaTime;
        deltaTime = unscaledDeltaTime * timeScale;
        unscaledTime += unscaledDeltaTime;
        time += deltaTime;
        unsimulatedTime += deltaTime;
    }
}