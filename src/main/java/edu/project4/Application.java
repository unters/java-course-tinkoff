package edu.project4;

import edu.project4.fractalflame.FractalFlameGenerator;
import edu.project4.fractalflame.FractalFlameRenderer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Application {

    private static final int DEFAULT_X_RESOLUTION = 1240;
    private static final int DEFAULT_Y_RESOLUTION = 720;
    private static final int DEFAULT_N_SAMPLES = 3;
    private static final int DEFAULT_N_THREADS = 6;

    public static void run(String[] args) {
        FractalFlameGenerator fractalFlameGenerator =
            new FractalFlameGenerator(DEFAULT_Y_RESOLUTION, DEFAULT_X_RESOLUTION, DEFAULT_N_SAMPLES, DEFAULT_N_THREADS);
        fractalFlameGenerator.generate();

        FractalFlameRenderer renderer = new FractalFlameRenderer(fractalFlameGenerator.getCanvas());
        renderer.drawFlame();
        renderer.saveToFile();
    }
}
