package manipulacaoimagem;

public class Pixel {
    private short[] rgb;

    Pixel(int r, int g, int b) {
        this.rgb = new short[3];
        this.rgb[0] = (short) (r); //red
        this.rgb[1] = (short) (g); //green
        this.rgb[2] = (short) (b); //blue
        verfLimite();
    }
    
    Pixel(int rgb) {
        this.rgb = new short[3];
        this.rgb[0] = (short) ((rgb >> 16) & 0x000000FF); //red
        this.rgb[1] = (short) ((rgb >> 8) & 0x000000FF); //green
        this.rgb[2] = (short) ((rgb) & 0x000000FF); //blue
    }
    
    Pixel() {
        this.rgb = new short[3];
        this.rgb[0] = 0; //red
        this.rgb[1] = 0; //green
        this.rgb[2] = 0; //blue
    }

    int getRGB() {
        return (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    public void setGray(int c) {
        short g = (short) c;
        rgb[0] = g;
        rgb[1] = g;
        rgb[2] = g;
        verfLimite();
    }

    public void setRed(int c) {
        rgb[0] = (short) c;
        verfLimite(0);
    }

    public void setGreen(int c) {
        rgb[1] = (short) c;
        verfLimite(1);
    }

    public void setBlue(int c) {
        rgb[2] = (short) c;
        verfLimite(2);
    }
    
    public void add(Pixel pixel){
        rgb[0] += pixel.rgb[0];
        rgb[1] += pixel.rgb[1];
        rgb[2] += pixel.rgb[2];
        verfLimite();
        
    }
    
    public void addGray(int g) {
        rgb[0] += g;
        rgb[1] += g;
        rgb[2] += g;
        verfLimite();
    }

    public void addRed(int g) {
        rgb[0] += g;
        verfLimite(0);
    }

    public void addGreen(int g) {
        rgb[1] += g;
        verfLimite(1);
    }

    public void addBlue(int g) {
        rgb[2] += g;
        verfLimite(2);
    }

    public short getRed() {
        return rgb[0];
    }

    public short getGreen() {
        return rgb[1];
    }

    public short getBlue() {
        return rgb[2];
    }
    
    public int getGray() {
        int soma = rgb[0];
        soma += rgb[1];
        soma += rgb[2];
        return soma/3;
    }

    private void verfLimite() {
        verfLimite(0);
        verfLimite(1);
        verfLimite(2);
    }

    private void verfLimite(int p) {
        if (rgb[p] < 0) {
            rgb[p] = 0;
        } else if (rgb[p] > 255) {
            rgb[p] = 255;
        }
    }
}
