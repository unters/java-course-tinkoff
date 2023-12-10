package edu.project4.fractalflame;

import edu.project4.functions.DiskTransformation;
import edu.project4.functions.HeartTransformation;
import edu.project4.functions.PolarTransformation;
import edu.project4.functions.SineTransformation;
import edu.project4.functions.SphericalTransformation;
import edu.project4.functions.Transformation;
import edu.project4.utils.AffineTransformation;
import edu.project4.utils.Coordinates;
import edu.project4.utils.Pixel;
import edu.project4.utils.Rgb;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FractalFlameGenerator {

    private static final Logger LOGGER = LogManager.getLogger();

    private final int xResolution;
    private final int yResolution;
    private final int nSamples;
    private final int nThreads;

    private final Pixel[][] canvas;
    private ExecutorService executorService;

    private final double yMin;
    private final double yMax;
    private final double xMin;
    private final double xMax;

    private final int affineTransformationsCount = 50;  // TODO.
    private final int iterationsPerSample = 10_000_000; // TODO.
    private final double gamma = 0.65;                  // TODO.

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

        double ratio = (double) xResolution / yResolution;
        yMin = -1;
        yMax = 1;
        xMin = -1 * ratio;
        xMax = ratio;
    }

    public synchronized void generate() {
        this.executorService = Executors.newFixedThreadPool(nThreads);
        LOGGER.debug("Generating flame.");
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < nSamples; ++i) {
            completableFutures.add(CompletableFuture.runAsync(new GeneratingWorker(), executorService));
        }

        for (var completableFuture : completableFutures) {
            completableFuture.join();
        }

        LOGGER.debug("Processing image (gamma-correction).");
        completableFutures.clear();
        int stripeHeight = yResolution / nThreads;
        double maxNormal = calculateMaxNormal();
        for (int i = 0; i < nThreads; ++i) {
            completableFutures.add(CompletableFuture.runAsync(
                new CorrectionWorker(
                    i * stripeHeight,
                    (i + 1 == nThreads) ? yResolution : (i + 1) * stripeHeight,
                    maxNormal
                ),
                executorService
            ));
        }

        for (var completableFuture : completableFutures) {
            completableFuture.join();
        }

        executorService.shutdown();
    }

    public synchronized Pixel[][] getCanvas() {
        return canvas;
    }

    private double calculateMaxNormal() {
        double maxNormal = 0;
        for (int y = 0; y < yResolution; ++y) {
            for (int x = 0; x < xResolution; ++x) {
                Pixel pixel = canvas[y][x];
                if (pixel.getCounter() != 0) {
                    pixel.setNormal(Math.log10(pixel.getCounter()));
                    maxNormal = Math.max(maxNormal, pixel.getNormal());
                }
            }
        }

        return maxNormal;
    }

    private final class GeneratingWorker implements Runnable {

        @Override
        @SuppressWarnings("MagicNumber")
        public void run() {
            List<Transformation> transformationsList = List.of(
                new SineTransformation(),
                new SphericalTransformation(),
                new PolarTransformation(),
                new HeartTransformation(),
                new DiskTransformation()
            );

            double y = ThreadLocalRandom.current().nextDouble(yMin, yMax);
            double x = ThreadLocalRandom.current().nextDouble(xMin, xMax);
            List<AffineTransformation> affineTransformations = Stream.generate(AffineTransformation::generate)
                .limit(affineTransformationsCount)
                .collect(Collectors.toList());

            /* Generating starting point - nothing is drawn yet.  */
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
                        Pixel pixel = canvas[yCanvas][xCanvas];
                        synchronized (pixel) {
                            Rgb atColor = affineTransformation.color();
                            if (pixel.getCounter() == 0) {
                                Rgb color = new Rgb(atColor.getR(), atColor.getG(), atColor.getB());
                                pixel.setColor(color);
                            } else {
                                pixel.getColor().apply(atColor);
                            }
                            pixel.incrementCounter();
                        }
                    }
                }
            }
        }
    }

    private final class CorrectionWorker implements Runnable {

        private final int yTop;
        private final int yBottom;
        private final double maxNormal;

        CorrectionWorker(int yTop, int yBottom, double maxNormal) {
            this.yTop = yTop;
            this.yBottom = yBottom;
            this.maxNormal = maxNormal;
        }

        @Override
        public void run() {
            for (int y = yTop; y < yBottom; ++y) {
                for (int x = 0; x < xResolution; ++x) {
                    Pixel pixel = canvas[y][x];
                    pixel.setNormal(pixel.getNormal() / maxNormal);

                    Rgb correctedColor = new Rgb(
                        (int) (pixel.getColor().getR() * Math.pow(pixel.getNormal(), 1.0 / gamma)),
                        (int) (pixel.getColor().getG() * Math.pow(pixel.getNormal(), 1.0 / gamma)),
                        (int) (pixel.getColor().getB() * Math.pow(pixel.getNormal(), 1.0 / gamma))
                    );
                    pixel.setColor(correctedColor);
                }
            }
        }
    }
}
