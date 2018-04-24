package Helper;

import com.badlogic.gdx.graphics.Pixmap;
import java.util.ArrayList;

public class Vetor {
    public ArrayList<Ponto> pts;

    public Vetor(int... x) {
        pts = new ArrayList();
        for (int i = 0; i < x.length-1; i+=2) {
            pts.add(new Ponto(x[i], x[i+1]));
        }
    }
    
    public Vetor(Ponto... p) {
        pts = new ArrayList();
        for (int i = 0; i < p.length; i++) {
            pts.add(p[i]);
        }
    }
    
    public Pixmap draw(Pixmap img){
        for (int i = 0; i < pts.size()-1; i++) {
            Ponto p0 = pts.get(i);
            Ponto p1 = pts.get(i+1);
            img.drawLine(p0.x, p0.y, p1.x, p1.y);
        }
        return img;
    }
    
    public void add(int x, int y){
        pts.add(new Ponto(x, y));
    }
    
    public void add(Ponto p){
        pts.add(p);
    }
    
    public double modulo(){
        double mod = 0;
        for (int i = 0; i < pts.size()-1; i++) {
            mod+= pts.get(i).mod(pts.get(i+1));
        }
        return mod;
    }
}
