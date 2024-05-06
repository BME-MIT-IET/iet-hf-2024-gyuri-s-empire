package grafikus;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SaboteurGraph implements Drawable {

    /**
     * A SaboteurGraph-hoz tartozó modellbeli Saboteur objektum.
     */
    private modell.Saboteur saboteur;
    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;

    /**
     * A SaboteurGraph x koordinátája.
     */
    private int x;
    /**
     * A SaboteurGraph y koordinátája.
     */
    private int y;

    /**
     * Konstruktor, beállítja a privát mezők értékét.
     * 
     * @param jp       A szabotőrt megjelenítő panel.
     * @param x        A szabotőr x koordinátája.
     * @param y        A szabotőr y koordinátája.
     * @param saboteur A SaboteurGraph-hoz tartozó modellbeli Saboteur objektum.
     */

    public SaboteurGraph(JPanel jp, int x, int y, modell.Saboteur saboteur) {
        playgroundpanel = jp;
        this.x = x;
        this.y = y;
        this.saboteur = saboteur;
    }

    /**
     * A Drawable interfészből megvalósított rajzoló metódus. Kirajzolja a csövet.
     */
    public void Draw() {
        JLabel LSabotour = new JLabel("");
        LSabotour.setHorizontalAlignment(SwingConstants.CENTER);
        LSabotour.setBounds(x, y, 40, 40);
        ImageIcon imageIcon1 = new ImageIcon(
                new ImageIcon("images//sabotour2.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        LSabotour.setIcon(imageIcon1);
        playgroundpanel.add(LSabotour);

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
        return saboteur.getId();
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
