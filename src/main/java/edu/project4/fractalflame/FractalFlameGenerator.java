package edu.project4.fractalflame;

import edu.project4.functions.HeartTransformation;
import edu.project4.functions.PolarTransformation;
import edu.project4.functions.SineTransformation;
import edu.project4.functions.SphericalTransformation;
import edu.project4.functions.Transformation;
import edu.project4.utils.AffineTransformation;
import edu.project4.utils.Coordinates;
import edu.project4.utils.Pixel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FractalFlameGenerator {

    private static final Logger LOGGER = LogManager.getLogger();

    private final int xResolution;
    private final int yResolution;
    private final int nSamples;
    private final int nThreads;

    private final Pixel[][] canvas;
    private final ExecutorService executorService;

    private final double yMin;
    private final double yMax;
    private final double xMin;
    private final double xMax;

    private final int affineTransformationsCount = 10;       // TODO.
    private final int iterationsPerSample = 10_000_000;      // TODO.

    public FractalFlameGenerator(int yResolution, int xResolution, int nSamples, int nThreads) {
        this.yResolution = yResolution;
        this.xResolution = xResolution;
        this.nSamples = nSamples;
        this.nThreads = nThreads;

        this.canvas = new Pixel[yResolution][xResolution];
        for (int y = 0; y < yResolution; ++y) {
            for (int x = 0; x < xResolution; ++x) {
                canvas[y][x] = new Pixel();
            }
        }
        this.executorService = Executors.newFixedThreadPool(nThreads);

        double ratio = (double) xResolution / yResolution;
        yMin = -1;
        yMax = 1;
        xMin = -1 * ratio;
        xMax = ratio;
    }

    public synchronized void generate() {
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < nSamples; ++i) {
            completableFutures.add(CompletableFuture.runAsync(new Worker(), executorService));
        }

        for (var completableFuture : completableFutures) {
            completableFuture.join();
        }
    }

    public synchronized Pixel[][] getCanvas() {
        return canvas;
    }

    private final class Worker implements Runnable {

        @Override
        public void run() {
            int hitsOnCanvas = 0;   // DEBUG.

            List<Transformation> transformationsList = List.of(
                new SineTransformation(),
                new SphericalTransformation(),
                new PolarTransformation(),
                new HeartTransformation()
            );

            double y = ThreadLocalRandom.current().nextDouble(yMin, yMax);
            double x = ThreadLocalRandom.current().nextDouble(xMin, xMax);
            List<AffineTransformation> affineTransformations = Stream.generate(AffineTransformation::generate)
                .limit(affineTransformationsCount)
                .collect(Collectors.toList());

            /* Looking for starting point - nothing is drawn yet.  */
            for (int i = 0; i < 20; ++i) {
                AffineTransformation affineTransformation =
                    affineTransformations.get(ThreadLocalRandom.current().nextInt(affineTransformations.size()));
                y = (affineTransformation.d() * x + affineTransformation.e() * y + affineTransformation.f());
                x = (affineTransformation.a() * x + affineTransformation.b() * y + affineTransformation.c());
            }

            Transformation transformation =
                transformationsList.get(ThreadLocalRandom.current().nextInt(transformationsList.size()));   // TODO.
            for (int i = 0; i < iterationsPerSample; ++i) {
                AffineTransformation affineTransformation =
                    affineTransformations.get(ThreadLocalRandom.current().nextInt(affineTransformations.size()));
                y = (affineTransformation.d() * x + affineTransformation.e() * y + affineTransformation.f());
                x = (affineTransformation.a() * x + affineTransformation.b() * y + affineTransformation.c());

                Coordinates coordinates = transformation.transform(y, x);
                y = coordinates.y();
                x = coordinates.x();

                if (y > yMin && y < yMax && x > xMin && x < xMax) {
                    int yCanvas = (int) (((yMax - y) / (yMax - yMin)) * yResolution);
                    int xCanvas = (int) (((xMax - x) / (xMax - xMin)) * xResolution);
                    if (yCanvas >= 0 && yCanvas < yResolution && xCanvas >= 0 && xCanvas < xResolution) {
                        ++hitsOnCanvas;
                        Pixel pixel = canvas[yCanvas][xCanvas];
                        synchronized (pixel) {
                            if (pixel.getCounter() == 0) {
                                pixel.setColor(affineTransformation.color());
                            } else {
//                                Rgb aftColor = affineTransformation.color();
//                                Rgb oldColor = pixel.getColor();
//                                Rgb newColor = pixel.getColor();
//                                newColor.setR((oldColor.getR() + aftColor.getR()) / 2);
//                                newColor.setG((oldColor.getG() + aftColor.getG()) / 2);
//                                newColor.setB((oldColor.getB() + aftColor.getB()) / 2);
//                                pixel.setColor(newColor);
                            }
                            pixel.incrementCounter();
                        }
                    }
                }
            }
            LOGGER.debug("Painting on canvas (%d): ".formatted(hitsOnCanvas) + transformation.getClass().getName());
        }
    }
}
