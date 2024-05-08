package grafikus;

import modell.NetworkPiece;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PipeGraph implements Drawable {

    /**
     * A PipeGraph-hoz tartozó modellbeli Pipe objektum.
     */
    private modell.Pipe pipe;

    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;

    /**
     * Az adott csövet éppen mozgató játékosok listája.
     */
    private ArrayList<PlumberGraph> carriers = new ArrayList<PlumberGraph>();

    /**
     * A cső egyik végének x koordinátája.
     */
    private int x1;

    /**
     * A cső egyik végének y koordinátája.
     */
    private int y1;

    /**
     * A cső másik végének x koordinátája.
     */
    private int x2;

    /**
     * A cső másik végének y koordinátája.
     */
    private int y2;

    /**
     * A cső működőképességét jelző logikai változó (lyukas vagy nem lyukas).
     */
    private boolean works;

    /**
     * A vízfolyam állapotát jelző logikai változó (van vagy nincs).
     */
    private boolean flow;

    /**
     * A cső ragadósságának állapotát jelző logikai változó (ragad vagy nem ragad).
     */
    private boolean sticky;

    /**
     * A cső csúszósságának állapotát jelző logikai változó (csúszik vagy nem
     * csúszik).
     */
    private boolean slippery;

    /**
     * Konstruktor, beállítja a privát mezők értékét.
     * 
     * @param jp A csövet megjelenítő panel.
     * @param x1 A cső egyik végének x koordinátája.
     * @param y1 A cső másik végének y koordinátája.
     * @param x2 A cső másik végének x koordinátája.
     * @param y2 A cső másik végének y koordinátája.
     * @param p  A PipeGraph-hoz tartozó modellbeli Pipe objektum.
     */
    public PipeGraph(JPanel jp, int x1, int y1, int x2, int y2, modell.Pipe p) {
        pipe = p;
        playgroundpanel = jp;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        works = true;
        flow = false;
        sticky = false;
        slippery = false;
    }

    /**
     * A Drawable interfészből megvalósított rajzoló metódus. Kirajzolja a csövet.
     */
    public void Draw() {

        works = pipe.GetWorks();
        flow = pipe.GetWaterflow();
        sticky = pipe.Getsticky();
        slippery = pipe.Getslippery();

        ArrayList<NetworkPiece> szomszedok = pipe.GetNeighbours();

        if (szomszedok.size() == 0) {
            if (carriers.size() == 1) {
                Drawable d2 = GameView.getDrawable("-");
                carriers.add((PlumberGraph) d2);
            }

            x1 = carriers.get(1).getx();
            y1 = carriers.get(1).gety();
            x2 = carriers.get(0).getx();
            y2 = carriers.get(0).gety();
        }

        if (szomszedok.size() == 1) // egyik vége már a karakter kezében van
        {
            try {
                Drawable d1 = GameView.getDrawable(szomszedok.get(0).getId());
                if (carriers.isEmpty()) {
                    Drawable d2 = GameView.getDrawable("-");
                    carriers.add((PlumberGraph) d2);
                }

                x1 = d1.getx();
                y1 = d1.gety();
                x2 = carriers.get(0).getx();
                y2 = carriers.get(0).gety();
            } catch (Exception e) {

            }

        } else if (szomszedok.size() == 2) // mindkét vége csatlakoztatva
        {
            Drawable d1 = GameView.getDrawable(szomszedok.get(0).getId());
            Drawable d2 = GameView.getDrawable(szomszedok.get(1).getId());
            x1 = d1.getx();
            y1 = d1.gety();
            x2 = d2.getx();
            y2 = d2.gety();

        }

        JLabel id = new JLabel(getModelId());
        id.setLocation((y1 > y2) ? new Point(x1 + 40, y1 - 25) : new Point(x1 + 40, y1 + 20));
        id.setSize(50, 15);
        id.setVisible(true);
        playgroundpanel.add(id);

        int width = Math.abs(x1 - x2);
        int heigh = Math.abs(y1 - y2);
        double degree;
        String pipename;
        if (!works) {
            pipename = "images//pipepunctured.png";
        } else if (sticky) {
            pipename = "images//pipesticky.png";
        } else if (slippery) {
            pipename = "images//pipeslippery.png";
        } else {
            if (!flow)
                pipename = "images//pipenotflow.png";
            else {
                if ((x1 > x2) || (x1 == x2 && y1 > y2))
                    pipename = "images//pipebackward.png";
                else
                    pipename = "images//pipeforward.png";
            }
        }

        if (x1 == x2) {
            degree = 90;
            width = 10;
        } else if (y1 == y2) {
            degree = 0;
            heigh = 10;
        } else {
            degree = Math.toDegrees(Math.atan((double) heigh / (double) width));
            if ((x1 < x2 && y1 > y2) || (x1 > x2 && y1 < y2))
                degree *= -1;
        }

        try {
            BufferedImage originalimg = ImageIO.read(new File(pipename));
            BufferedImage rotatedimg = rotate(originalimg, degree);
            JLabel LPipe = new JLabel(new ImageIcon(rotatedimg));
            LPipe.setHorizontalAlignment(SwingConstants.CENTER);
            if (x1 <= x2 && y1 <= y2)
                LPipe.setBounds(x1, y1, width, heigh);
            else if (x1 < x2 && y1 > y2)
                LPipe.setBounds(x1, y2, width, heigh);
            else if (x1 > x2 && y1 < y2)
                LPipe.setBounds(x2, y1, width, heigh);
            else if (x1 >= x2 && y1 >= y2)
                LPipe.setBounds(x2, y2, width, heigh);
            playgroundpanel.add(LPipe);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getx() {
        return 0;
    }

    @Override
    public int gety() {
        return 0;
    }

    /**
     * A csöveket reprezentáló képek forgatását végző függvény.
     * 
     * @param image   A csövet reprezentáló kép.
     * @param degrees Ennyi fokkal szeretnénk forgatni.
     * @return Visszaadja az elforgatott képet.
     */
    public BufferedImage rotate(BufferedImage image, double degrees) {

        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }

    /**
     * Setter a cső koordinátáihoz.
     * 
     * @param px1 A cső egyik végének kívánt x koordinátája.
     * @param py1 A cső egyik végének kívánt y koordinátája.
     * @param px2 A cső másik végének kívánt x koordinátája.
     * @param py2 A cső másik végének kívánt y koordinátája.
     */
    public void SetCoordinates(int px1, int py1, int px2, int py2) {
        x1 = px1;
        y1 = py1;
        x2 = px2;
        y2 = py2;
    }

    /**
     * A Drawable interfész megvalósított függvénye. A pályelemre lépő karakterek
     * különböző koordinátákon való elhelyezéséhez
     * generál koordinátákat.
     * 
     * @return A generált koordináták tömbje.
     */
    public int[] getCoord() {
        int[] m = new int[2];
        m[0] = (x1 + x2) / 2 - 20;
        m[1] = (y1 + y2) / 2 - 20;
        return m;
    }

    /***
     * A Drawable interfész megvalósított gettere.
     * 
     * @return A Drawable objektum ID-ját adja vissza.
     */
    public String getModelId() {
        return pipe.getId();
    }
}
