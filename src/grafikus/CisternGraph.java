package grafikus;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modell.Cistern;

public class CisternGraph implements Drawable {

    /**
     * A CisternGraph-hoz tartozó modellbeli Cistern objektum.
     */
    private Cistern cistern;

    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;

    /**
     * A CisternGraph x koordinátája.
     */
    private int x;

    /**
     * A CisternGraph y koordinátája.
     */
    private int y;

    /**
     * Getter
     * 
     * @return A ciszternára lépő karakter karakter leendő x koordinátáját adja meg.
     */
    public int GetX() {
        return x + 30;
    }

    /**
     * Getter
     * 
     * @return A ciszternára lépő karakter karakter leendő y koordinátáját adja meg.
     */
    public int GetY() {
        return y + 30;
    }

    /**
     * Konstruktor
     * 
     * @param jp      Ezen a panelen helyezzük el a ciszternát.
     * @param x       A ciszterna x koordinátája.
     * @param y       A ciszerna y koordinátája.
     * @param cistern A kirajzolni kívánt ciszterna.
     */
    public CisternGraph(JPanel jp, int x, int y, modell.Cistern cistern) {
        playgroundpanel = jp;
        this.x = x;
        this.y = y;
        this.cistern = cistern;
    }

    /**
     * A Drawable interfészből örökölt Draw() metódus megvalósítása. Kirajzolja a
     * ciszternát és a ciszterna ID-ját.
     */
    public void Draw() {
        JLabel LCistern = new JLabel("");
        LCistern.setHorizontalAlignment(SwingConstants.CENTER);
        LCistern.setBounds(x, y, 60, 60);
        ImageIcon imageIcon1 = new ImageIcon(
                new ImageIcon("images//cistern2.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        LCistern.setIcon(imageIcon1);
        playgroundpanel.add(LCistern);
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
     * A pályelemre lépő karakterek különböző koordinátákon való elhelyezéséhez
     * generál
     * koordinátákat.
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
     * Getter
     * 
     * @return A CisternGraph ID-ját adja vissza.
     */

    public String getModelId() {
        return cistern.getId();
    }
}
