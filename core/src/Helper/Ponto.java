package Helper;

public class Ponto {
    public int x, y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public double mod(Ponto p){
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx+dy*dy);
    }
}
