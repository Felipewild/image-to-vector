package manipulacaoimagem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

public class ManipulacaoImagem {

    public static void main(String[] args) {
        Pixel[][] pixelmap = getPixelMap("img1.bmp");
        showImage(pixelmap, "default");
        Pixel[][] deriv = getDerivate(pixelmap, 3);
        showImage(deriv, "final");
    }

    public static Pixel[][] getDerivate(Pixel[][] pixelmap, int rad) {
        Pixel[][] deriv;
        if (rad>0) {
            deriv = getDerivate(pixelmap, rad - 1);
            for (int h = rad; h < pixelmap[0].length - rad; h += 1) {
                for (int w = rad; w < pixelmap.length - rad; w += 1) {
                    Pixel center = getMediane(pixelmap, w, h, rad-1);
                    Pixel left = getMediane(pixelmap, w - rad, h, rad-1);
                    Pixel rigth = getMediane(pixelmap, w + rad, h, rad-1);
                    Pixel top = getMediane(pixelmap, w, h - rad, rad-1);
                    Pixel bottom = getMediane(pixelmap, w, h + rad, rad-1);
                    deriv[w][h].add(getDerivate(center, left, rigth, top, bottom));
                }
            }
            System.out.println("calculou getDerivate(center, left, rigth, top, bottom) com rad="+rad);
        } else {
            deriv = new Pixel[pixelmap.length][pixelmap[0].length];
            for (int h = 0; h < pixelmap[0].length; h++) {
                for (int w = 0; w < pixelmap.length; w++) {
                    deriv[w][h] = new Pixel();
                }
            }
            System.out.println("criou deriv[][]");
        }
        showImage(deriv, "rad="+rad);
        return deriv;
    }

    public static Pixel getMediane(Pixel[][] pixelmap, int width, int heigth, int radius) {
        int[] sum = new int[]{0, 0, 0};
        int count = 0;
        for (int h = heigth - radius; h <= heigth + radius; h++) {
            for (int w = width - radius; w <= width + radius; w++) {
                if (w >= 0 && w < pixelmap.length) {
                    if (h >= 0 && h < pixelmap[0].length) {
                        sum[0] += pixelmap[w][h].getRed();
                        sum[1] += pixelmap[w][h].getGreen();
                        sum[2] += pixelmap[w][h].getBlue();
                        count++;
                    }
                }
            }
        }
        if (count == 0) {
            return pixelmap[width][heigth];
        } else {
            Pixel p = new Pixel();
            p.setRed(sum[0] / count);
            p.setGreen(sum[1] / count);
            p.setBlue(sum[2] / count);
            return p;
        }
    }

    public static Pixel getDerivate(Pixel center, Pixel left, Pixel rigth, Pixel top, Pixel bottom) {
        Pixel d = new Pixel();
        d.setRed(center.getRed() * 4 - left.getRed() - rigth.getRed() - top.getRed() - bottom.getRed());
        d.setGreen(center.getGreen() * 4 - left.getGreen() - rigth.getGreen() - top.getGreen() - bottom.getGreen());
        d.setBlue(center.getBlue() * 4 - left.getBlue() - rigth.getBlue() - top.getBlue() - bottom.getBlue());
        return d;
    }

    public static Pixel[][] getPixelMap(String file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(file));
            int height = img.getHeight();
            int width = img.getWidth();
            Pixel[][] pixelmap = new Pixel[width][height];
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    pixelmap[w][h] = new Pixel(img.getRGB(w, h));
                }
            }
            return pixelmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getPixelMapToImage(Pixel[][] pixelmap) {
        BufferedImage img = new BufferedImage(pixelmap.length, pixelmap[0].length, BufferedImage.TYPE_INT_RGB);
        for (int h = 0; h < pixelmap[0].length; h++) {
            for (int w = 0; w < pixelmap.length; w++) {
                img.setRGB(w, h, pixelmap[w][h].getRGB());
            }
        }
        return img;
    }

    private static void showImage(Pixel[][] pixelmap, String title) {
        BufferedImage img = getPixelMapToImage(pixelmap);
        JFrame editorFrame = new JFrame(title);
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
}
