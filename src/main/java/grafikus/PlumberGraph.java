package grafikus;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modell.Plumber;

public class PlumberGraph implements Drawable {

    /**
     * A PlumberGraph-hoz tartozó modellbeli Plumber objektum.
     */
    private modell.Plumber plumber;

    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;

    /**
     * A PlumberGraph x koordinátája.
     */
    private int x;
    /**
     * A PlumberGraph y koordinátája.
     */
    private int y;

    /**
     * Getter a modellbeli Plumber objektumhoz.
     * 
     * @return Visszaadja a PlumberGraph-hoz tartozó Plumber objektumot.
     */
    public Plumber getPlumber() {
        return plumber;
    }

    /**
     * Konstruktor, beállítja a privát mezők értékét.
     * 
     * @param jp      A szerelőt megjelenítő panel.
     * @param x       A szerelő x koordinátája.
     * @param y       A szerelő y koordinátája.
     * @param plumber A PlumberGraph-hoz tartozó modellbeli Plumber objektum.
     */
    public PlumberGraph(JPanel jp, int x, int y, modell.Plumber plumber) {
        playgroundpanel = jp;
        this.x = x;
        this.y = y;
        this.plumber = plumber;
    }

    /**
     * A Drawable interfészből megvalósított rajzoló metódus. Kirajzolja a csövet.
     */
    public void Draw() {
        JLabel LPlumber = new JLabel("");
        LPlumber.setHorizontalAlignment(SwingConstants.CENTER);
        LPlumber.setBounds(x, y, 40, 40);
        ImageIcon imageIcon1 = new ImageIcon(
                new ImageIcon("images//plumber2.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        LPlumber.setIcon(imageIcon1);
        playgroundpanel.add(LPlumber);

    }

    @Override
    public int getx() {
        return x + 20;
    }

    @Override
    public int gety() {
        return y + 20;
    }

    /**
     * A Drawable interfész megvalósított függvénye. A karakterek
     * különböző koordinátákon való elhelyezéséhez
     * generál koordinátákat.
     * 
     * @return A generált koordináták tömbje.
     */
    public int[] getCoord() {
        int[] m = new int[2];
        m[0] = x;
        m[1] = y;
        return m;
    }

    /***
     * A Drawable interfész megvalósított gettere.
     * 
     * @return A Drawable objektum ID-ját adja vissza.
     */
    public String getModelId() {
        return plumber.getId();
    }

    /**
     * Setter a szerelő koordinátáihoz.
     * 
     * @param _x A szerelő kívánt x koordinátája.
     * @param _y A szerelő kívánt y koordinátája.
     */
    public void setPos(int _x, int _y) {
        x = _x;
        y = _y;
    }
}
