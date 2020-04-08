package boulderdash;

import static boulderdash.Kayttoliittyma.OBJEKTINKOKO;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Objekti {

    private Polygon objekti;
    private boolean elossa;

    public Objekti(Polygon monikulmio, int x, int y) {
        this.objekti = monikulmio;
        this.objekti.setTranslateX(x);
        this.objekti.setTranslateY(y);
        this.elossa = true;
    }

    public Polygon getObjekti() {
        return this.objekti;
    }

    public double getX() {
        return this.objekti.getTranslateX();
    }

    public double getY() {
        return this.objekti.getTranslateY();
    }

    public void setX(int x) {
        this.objekti.setTranslateX(this.objekti.getTranslateX() + x);
    }

    public void setY(int y) {
        this.objekti.setTranslateY(this.objekti.getTranslateY() + y);
    }

    public void vasen() {
        this.objekti.setTranslateX(this.objekti.getTranslateX() - OBJEKTINKOKO);
    }

    public void oikea() {
        this.objekti.setTranslateX(this.objekti.getTranslateX() + OBJEKTINKOKO);
    }

    public void ylos() {
        this.objekti.setTranslateY(this.objekti.getTranslateY() - OBJEKTINKOKO);
    }

    public void alas() {
        this.objekti.setTranslateY(this.objekti.getTranslateY() + OBJEKTINKOKO);
    }

    public void setElossa(boolean status) {
        elossa = status;
    }

    public boolean getElossa() {
        return elossa;
    }

    public boolean tormaa(Objekti toinen, int dx, int dy) {
        this.objekti.setTranslateX(this.objekti.getTranslateX() + dx * OBJEKTINKOKO);
        this.objekti.setTranslateY(this.objekti.getTranslateY() + dy * OBJEKTINKOKO);
        Shape tormaysalue = Shape.intersect(this.objekti, toinen.getObjekti());
        this.objekti.setTranslateX(this.objekti.getTranslateX() - dx * OBJEKTINKOKO);
        this.objekti.setTranslateY(this.objekti.getTranslateY() - dy * OBJEKTINKOKO);
        return tormaysalue.getBoundsInLocal().getWidth() != -1;
    }

}
