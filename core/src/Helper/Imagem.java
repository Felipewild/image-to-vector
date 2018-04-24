package Helper;

import com.badlogic.gdx.graphics.Pixmap;

public class Imagem {

    public static Pixel[][] getRecursiveEdge(Pixel[][] pixelmap, int rad) {
        Pixel[][] deriv;
        if (rad > 0) {
            deriv = getRecursiveEdge(pixelmap, rad - 1);
            for (int h = rad; h < pixelmap[0].length - rad; h += 1) {
                for (int w = rad; w < pixelmap.length - rad; w += 1) {
                    Pixel center = getMediane(pixelmap, w, h, rad - 1);
                    Pixel left = getMediane(pixelmap, w - rad, h, rad - 1);
                    Pixel rigth = getMediane(pixelmap, w + rad, h, rad - 1);
                    Pixel top = getMediane(pixelmap, w, h - rad, rad - 1);
                    Pixel bottom = getMediane(pixelmap, w, h + rad, rad - 1);
                    deriv[w][h].add(getConvolution(center, left, rigth, top, bottom));
                }
            }
            System.out.println("calculou getDerivate(center, left, rigth, top, bottom) com rad=" + rad);
        } else {
            deriv = new Pixel[pixelmap.length][pixelmap[0].length];
            for (int h = 0; h < pixelmap[0].length; h++) {
                for (int w = 0; w < pixelmap.length; w++) {
                    deriv[w][h] = new Pixel();
                }
            }
            System.out.println("criou deriv[][]");
        }
        return deriv;
    }

    private static Pixel getMediane(Pixel[][] pixelmap, int width, int heigth, int radius) {
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

    private static Pixel getConvolution(Pixel center, Pixel left, Pixel rigth, Pixel top, Pixel bottom) {
        //Convolution
        // 0 -1  0
        //-1  4 -1
        // 0 -1  0
        Pixel d = new Pixel();
        d.setRed(center.getRed() * 4 - left.getRed() - rigth.getRed() - top.getRed() - bottom.getRed());
        d.setGreen(center.getGreen() * 4 - left.getGreen() - rigth.getGreen() - top.getGreen() - bottom.getGreen());
        d.setBlue(center.getBlue() * 4 - left.getBlue() - rigth.getBlue() - top.getBlue() - bottom.getBlue());
        d.setGray(d.getGray());
        return d;
    }

    public static Pixel[][] getPixelMap(Pixmap img) {
        int height = img.getHeight();
        int width = img.getWidth();
        Pixel[][] pixelmap = new Pixel[width][height];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixelmap[w][h] = new Pixel(img.getPixel(w, h), true);
            }
        }
        return pixelmap;
    }

    public static Pixmap getPixelMapToImage(Pixel[][] pixelmap) {
        Pixmap img = new Pixmap(pixelmap.length, pixelmap[0].length, Pixmap.Format.RGB888);
        for (int h = 0; h < pixelmap[0].length; h++) {
            for (int w = 0; w < pixelmap.length; w++) {
                img.drawPixel(w, h, pixelmap[w][h].getARGB());
            }
        }
        return img;
    }
    
    public static Pixmap getPixelMapToImage(Pixel[][] pixelmap, Pixmap img) {
        for (int h = 0; h < pixelmap[0].length; h++) {
            for (int w = 0; w < pixelmap.length; w++) {
                img.drawPixel(w, h, pixelmap[w][h].getARGB());
            }
        }
        return img;
    }
}
