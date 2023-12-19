package edu.project4.fractalflame;

import edu.project4.utils.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class FractalFlameRenderer {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void render(Pixel[][] canvas) {
        int width = canvas[0].length;
        int height = canvas.length;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                bufferedImage.setRGB(x, y, canvas[y][x].getColor().getRgb());
            }
        }

        Path path =
            Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "edu", "project4", "flame.png");
        try {
            LOGGER.debug("Saving image...");
            ImageIO.write(bufferedImage, "png", new File(path.toString()));
        } catch (IOException e) {
            LOGGER.error("Could not save image: " + e.getMessage());
        }

        LOGGER.debug("Image has been saved (%s).".formatted(path.toString()));
    }
}
