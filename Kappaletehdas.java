package boulderdash;

import static boulderdash.Kayttoliittyma.OBJEKTINKOKO;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Kappaletehdas {

    private int K = OBJEKTINKOKO;
    
    public Polygon timantti() {
        Polygon kappale = new Polygon(0*K/20, -10*K/20, 10*K/20, 0*K/20, 5*K/20, 10*K/20, -5*K/20, 10*K/20, -10*K/20, 0*K/20, -1*K/20, -9*K/20, -9*K/20, 0*K/20, -4*K/20, 9*K/20, 4*K/20, 9*K/20, 9*K/20, 0*K/20, 0*K/20, -10*K/20);
        kappale.setFill(Color.BLUE);
        return kappale;
    }

    public Polygon ukkeli() {
        Polygon kappale = new Polygon(0*K/20,-10*K/20, 1*K/20,-10*K/20, 1*K/20,-6*K/20, 4*K/20,-6*K/20, 7*K/20,2*K/20, 6*K/20,2*K/20, 3*K/20,-5*K/20, 2*K/20,0*K/20, 5*K/20,10*K/20, 4*K/20,10*K/20, 0*K/20,1*K/20, -4*K/20,10*K/20, -5*K/20,10*K/20, -2*K/20,0*K/20, -3*K/20,-5*K/20, -6*K/20,2*K/20, -7*K/20,2*K/20, -4*K/20,-6*K/20, -1*K/20,-6*K/20, -1*K/20,-10*K/20, 0*K/20,-10*K/20);
        kappale.setFill(Color.rgb(0, 0, 0, 1));
        return kappale;
    }

    public Polygon kivi() {
        Polygon kappale = new Polygon(-6*K/20,-10*K/20, 2*K/20,-10*K/20, 10*K/20,-4*K/20, 10*K/20,2*K/20, 4*K/20,8*K/20, -6*K/20,10*K/20, -10*K/20,2*K/20, -10*K/20,-6*K/20, -6*K/20,-10*K/20);
        kappale.setFill(Color.rgb(0,0,0,0.6));
        return kappale;
    }

    public Polygon dirt() {
        Polygon kappale = new Polygon(-10*K/20,-10*K/20, -10*K/20,10*K/20, -10*K/20,-8*K/20, 10*K/20,-8*K/20, -10*K/20,-6*K/20, 10*K/20,-6*K/20, -10*K/20,-4*K/20, 10*K/20,-4*K/20, -10*K/20,-2*K/20, 10*K/20,-2*K/20, -10*K/20,0*K/20, 10*K/20,0*K/20, -10*K/20,2*K/20, 10*K/20,2*K/20, -10*K/20,4*K/20, 10*K/20,4*K/20, -10*K/20,6*K/20, 10*K/20,6*K/20, -10*K/20,8*K/20, 10*K/20,8*K/20, -10*K/20,10*K/20, 10*K/20,10*K/20);
        kappale.setFill(Color.BROWN);
        return kappale;
    }

    public Polygon seina() {
        Polygon kappale = new Polygon(-10*K/20,-10*K/20, -10*K/20,10*K/20, 10*K/20,10*K/20, 10*K/20,-10*K/20);
        kappale.setFill(Color.BROWN);
        return kappale;
    }

}
