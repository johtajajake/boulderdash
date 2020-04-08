package boulderdash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Kayttoliittyma extends Application {

    public static int LEVEYS = 15; //pelikentän leveys objektien kokona (=esim montako kiveä rinnakkain)
    public static int KORKEUS = 10; //pelikentän korkeus objektien kokona (=esim montako kiveä päällekkäin)
    public static int OBJEKTINKOKO = 40; //objektien koko pikseleinä
    public static int TIMANTTIENOSUUS = 10; //timantteja, prosenttia pelikentästä
    public static int KIVIENOSUUS = 20; //kiviä, prosenttia pelikentästä
    public static int SEINIENOSUUS = 10; //seiniä, prosenttia pelikentästä

    public Objekti[][] alue = new Objekti[LEVEYS][KORKEUS];
    public Ukkeli ukkeli;
    public ArrayList<Timantti> timantit = new ArrayList<>();
    public ArrayList<Kivi> kivet = new ArrayList<>();
    public ArrayList<Dirt> mudat = new ArrayList<>();
    public ArrayList<Seina> seinat = new ArrayList<>();
    public AtomicInteger pisteet = new AtomicInteger();
    public Text text = new Text(10, 20, "Points: 0");
 
    @Override
    public void start(Stage stage) {
        VBox puoliksi = new VBox();
        Pane ruutu = new Pane();
        ruutu.setPrefSize(LEVEYS * OBJEKTINKOKO, KORKEUS * OBJEKTINKOKO);
        puoliksi.getChildren().add(text);
        puoliksi.getChildren().add(ruutu);
        Scene scene = new Scene(puoliksi);
        stage.setTitle("Boulderdash");
        stage.setScene(scene);
        stage.show();

        for (int x = 1; x < LEVEYS; x++) {
            for (int y = KORKEUS - 1; y > 0; y--) { //näin päin, jotta tiputakivi -metodi tiputtaa ensin alimmat kivet ja sitten ylimmät. Tunnettu bugi: jos pelaaja työntää ylemmän kiven alemman alle ja sen jälkeen tiputtaa molemmat, niin kivet jää päällekkäin.
                if (x == 1 && y == 1) {
                    continue;
                }
                Random ran = new Random();
                int r = ran.nextInt(100);
                if (r < TIMANTTIENOSUUS) {
                    Timantti timantti = new Timantti(x * OBJEKTINKOKO, y * OBJEKTINKOKO);
                    ruutu.getChildren().add(timantti.getObjekti());
                    alue[x][y] = timantti;
                    timantit.add(timantti);
                    continue;
                }
                if (r < TIMANTTIENOSUUS + KIVIENOSUUS) {
                    Kivi kivi = new Kivi(x * OBJEKTINKOKO, y * OBJEKTINKOKO);
                    ruutu.getChildren().add(kivi.getObjekti());
                    alue[x][y] = kivi;
                    kivet.add(kivi);
                    continue;
                }
                if (r < TIMANTTIENOSUUS + KIVIENOSUUS + SEINIENOSUUS) {
                    Seina seina = new Seina(x * OBJEKTINKOKO, y * OBJEKTINKOKO);
                    ruutu.getChildren().add(seina.getObjekti());
                    alue[x][y] = seina;
                    seinat.add(seina);
                    continue;
                }
                Dirt dirt = new Dirt(x * OBJEKTINKOKO, y * OBJEKTINKOKO);
                ruutu.getChildren().add(dirt.getObjekti());
                alue[x][y] = dirt;
                mudat.add(dirt);
            }
        }
        ukkeli = new Ukkeli(OBJEKTINKOKO, OBJEKTINKOKO);
        alue[1][1] = ukkeli;
        ruutu.getChildren().add(ukkeli.getObjekti());

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                if (liiku((int) ukkeli.getX(), (int) ukkeli.getY(), -1, 0, ruutu)) {
                    ukkeli.vasen();
                }
            }
            if (event.getCode() == KeyCode.RIGHT) {
                if (liiku((int) ukkeli.getX(), (int) ukkeli.getY(), 1, 0, ruutu)) {
                    ukkeli.oikea();
                }
            }
            if (event.getCode() == KeyCode.UP) {
                if (liiku((int) ukkeli.getX(), (int) ukkeli.getY(), 0, -1, ruutu)) {
                    ukkeli.ylos();
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if (liiku((int) ukkeli.getX(), (int) ukkeli.getY(), 0, 1, ruutu)) {
                    ukkeli.alas();
                }
            }
        });
    }

    public void tiputaKivi() {
        kivet.forEach(kivi -> {
            int kiviTippui = 0;
            while (true) {
                int tiputettujaKivia = 0;
                int x = (int) kivi.getX() / OBJEKTINKOKO;
                int y = (int) kivi.getY() / OBJEKTINKOKO;
                if (y < KORKEUS - 1) {
                    if (alue[x][y + 1] == null) {
                        kivi.alas();
                        alue[x][y + 1] = kivi;
                        alue[x][y] = null;
                        kiviTippui = 1;
                        tiputettujaKivia++;
                    }
                    if (alue[x][y + 1] == ukkeli && kiviTippui == 1) {
                        kivi.alas();
                        text.setText("Pisteet: " + pisteet + "        GAME OVER");
                    }
                }
                if (tiputettujaKivia == 0) {
                    break;
                }
            }
        });
    }

    public boolean onkoEsine(int x, int y, ArrayList<? extends Objekti> esine) { //Onko siinä suunnassa esine, mihin ukkeli aikoo liikkua
        for (int i = 0; i < esine.size(); i++) {
            int esineenX = (int) esine.get(i).getX();
            int esineenY = (int) esine.get(i).getY();
            if (esineenX == x * OBJEKTINKOKO && esineenY == y * OBJEKTINKOKO) {
                return true;
            }
        }
        return false;
    }

    public boolean liiku(int x, int y, int dx, int dy, Pane ruutu) {
        if (x + dx * OBJEKTINKOKO - OBJEKTINKOKO / 2 < 0 || y + dy * OBJEKTINKOKO - OBJEKTINKOKO / 2 < 0 || x + dx * OBJEKTINKOKO + OBJEKTINKOKO / 2 > LEVEYS * OBJEKTINKOKO || y + dy * OBJEKTINKOKO + OBJEKTINKOKO / 2 > KORKEUS * OBJEKTINKOKO) {
            return false;
        }
        x = x / OBJEKTINKOKO;
        y = y / OBJEKTINKOKO;
        if (onkoEsine(x + dx, y + dy, kivet)) {
            if (alue[x + dx * 2][y] == null && dy == 0) { //dy == 0 --> oikea/vasen -liike
                if (dx > 0) {
                    kivet.forEach(kivi -> {
                        if (ukkeli.tormaa(kivi, dx, dy)) {
                            kivi.oikea();
                        }
                    });
                } else {
                    kivet.forEach(kivi -> {
                        if (ukkeli.tormaa(kivi, dx, dy)) {
                            kivi.vasen();
                        }
                    });
                }
            } else {
                return false;
            }
        }
        if (onkoEsine(x + dx, y + dy, mudat)) {
            mudat.forEach(dirt -> {
                if (ukkeli.tormaa(dirt, dx, dy)) {
                    dirt.setElossa(false);
                }
            });
        }
        if (onkoEsine(x + dx, y + dy, seinat)) {
            return false;
        }
        if (onkoEsine(x + dx, y + dy, timantit)) {
            timantit.forEach(timantti -> {
                if (ukkeli.tormaa(timantti, dx, dy)) {
                    timantti.setElossa(false);
                    text.setText("Pisteet: " + pisteet.addAndGet(1));
                }
            });
        }
        poistaTuhotut(mudat, ruutu);
        poistaTuhotut(timantit, ruutu);
        alue[x][y] = null;
        alue[x + dx][y + dy] = ukkeli;
        if (timantit.isEmpty()) {
            text.setText("Pisteet: " + pisteet + "        VOITIT!!!");
        }
        tiputaKivi();
        return true;
    }

    public void poistaTuhotut(List<? extends Objekti> tuhottavat, Pane ruutu) {
        tuhottavat.stream()
                .filter(tuhottava -> !tuhottava.getElossa())
                .forEach(tuhottava -> ruutu.getChildren().remove(tuhottava.getObjekti()));
        tuhottavat.removeAll(tuhottavat.stream()
                .filter(tuhottava -> !tuhottava.getElossa())
                .collect(Collectors.toList()));
    }
}
