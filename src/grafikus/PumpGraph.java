package grafikus;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PumpGraph implements Drawable {

    /**
     * A PumpGraph-hoz tartozó modellbeli Pump objektum.
     */
    private modell.Pump pump;
    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;
    /**
     * A PumpGraph x koordinátája.
     */
    private int x;
    /**
     * A PumpGraph y koordinátája.
     */
    private int y;
    /**
     * A pumpa működőképességét jelző logikai változó (működik vagy elromlott).
     */
    private boolean works;

    /**
     * Getter
     * 
     * @return A pumpára lépő karakter karakter leendő x koordinátáját adja meg.
     */
    public int GetX() {
        return x + 30;
    }

    /**
     * Getter
     * 
     * @return A pumpára lépő karakter karakter leendő y koordinátáját adja meg.
     */
    public int GetY() {
        return y + 30;
    }

    /**
     * Konstruktor, beállítja a privát mezők értékét.
     * 
     * @param jp   A pumpát megjelenítő panel.
     * @param x    A pumpa x koordinátája.
     * @param y    A pumpa y koordinátája.
     * @param pump A PumpGraph-hoz tartozó modellbeli Pump objektum.
     */
    public PumpGraph(JPanel jp, int x, int y, modell.Pump pump) {
        playgroundpanel = jp;
        this.x = x;
        this.y = y;
        this.pump = pump;
    }

    /**
     * A Drawable interfészből megvalósított rajzoló metódus. Kirajzolja a csövet.
     */
    public void Draw() {
        works = pump.GetWorks();
        String pumpname = "";
        if (!works) {
            pumpname = "images//pumppunctured2.png";
        } else {
            pumpname = "images//pump2.png";
        }
        JLabel LPump = new JLabel("");
        LPump.setHorizontalAlignment(SwingConstants.CENTER);
        LPump.setBounds(x, y, 60, 60);
        ImageIcon imageIcon1 = new ImageIcon(
                new ImageIcon(pumpname).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        LPump.setIcon(imageIcon1);
        playgroundpanel.add(LPump);
        JLabel id = new JLabel(getModelId());
        id.setLocation(x, y - 15);
        id.setSize(50, 15);
        id.setVisible(true);
        playgroundpanel.add(id);
    }

    @Override
    public int getx() {
        return x + 30;
    }

    @Override
    public int gety() {
        return y + 30;
    }

    /**
     * A Drawable interfész megvalósított függvénye. A pályelemre lépő karakterek
     * különböző koordinátákon való elhelyezéséhez
     * generál koordinátákat.
     * 
     * @return A generált koordináták tömbje.
     */
    public int[] getCoord() {
        Random r = new Random();
        int[] m = new int[2];
        m[0] = x + r.nextInt(30);
        m[1] = y + r.nextInt(30);
        return m;
    }

    /***
     * A Drawable interfész megvalósított gettere.
     * 
     * @return A Drawable objektum ID-ját adja vissza.
     */
    public String getModelId() {
        return pump.getId();
    }
}
