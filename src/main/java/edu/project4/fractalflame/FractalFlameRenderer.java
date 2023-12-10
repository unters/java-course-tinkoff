package edu.project4.fractalflame;

import edu.project4.utils.Pixel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FractalFlameRenderer extends JFrame {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Pixel[][] canvas;
    private final BufferedImage bufferedImage;

    public FractalFlameRenderer(Pixel[][] canvas) {
        super("Fractal flame");
        this.canvas = canvas;
        setSize(canvas[0].length, canvas.length);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    private void drawPixelInPoint(int x, int y, int color) {
        bufferedImage.setRGB(x, y, color);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public void saveToFile() {
        Path path =
            Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "edu", "project4", "flame.png");
        try {
            ImageIO.write(bufferedImage, "png", new File(path.toString()));
        } catch (IOException e) {
            System.out.println("error printing the file: " + e.getMessage());
        }
    }

    public void drawFlame() {
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                drawPixelInPoint(x, y, canvas[y][x].getColor().getRgb());
                repaint();
            }
        }
    }
}
