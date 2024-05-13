package grafikus;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SourceGraph implements Drawable {

    /**
     * A SourceGraph-hoz tartozó modellbeli Source objektum.
     */
    private modell.Source source;
    /**
     * A játékteret megjelenítő panel.
     */
    private JPanel playgroundpanel;
    /**
     * A SourceGraph x koordinátája.
     */
    private int x;
    /**
     * A SourceGraph x koordinátája.
     */
    private int y;

    /**
     * Getter
     * 
     * @return A forrásra lépő karakter karakter leendő x koordinátáját adja meg.
     */
    public int GetX() {
        return x + 30;
    }

    /**
     * Getter
     * 
     * @return A forrásra lépő karakter karakter leendő y koordinátáját adja meg.
     */
    public int GetY() {
        return y + 30;
    }

    private Random random = new Random();

    /**
     * Konstruktor
     * 
     * @param jp     Ezen a panelen helyezzük el a forrást.
     * @param x      A forrás x koordinátája.
     * @param y      A forrás y koordinátája.
     * @param source A kirajzolni kívánt forrás.
     */
    public SourceGraph(JPanel jp, int x, int y, modell.Source source) {
        playgroundpanel = jp;
        this.x = x;
        this.y = y;
        this.source = source;
        JLabel id = new JLabel(getModelId());
        id.setLocation(x, y - 15);
        id.setSize(50, 15);
        id.setVisible(true);
        playgroundpanel.add(id);
    }

    /**
     * A Drawable interfészből megvalósított rajzoló metódus. Kirajzolja a csövet.
     */
    public void Draw() {
        JLabel LSource = new JLabel("");
        LSource.setHorizontalAlignment(SwingConstants.CENTER);
        LSource.setBounds(x, y, 60, 60);
        ImageIcon imageIcon1 = new ImageIcon(
                new ImageIcon("images//source.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        LSource.setIcon(imageIcon1);
        playgroundpanel.add(LSource);
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
        int[] m = new int[2];
        m[0] = x + random.nextInt(30);
        m[1] = y + random.nextInt(30);
        return m;
    }

    /***
     * A Drawable interfész megvalósított gettere.
     * 
     * @return A Drawable objektum ID-ját adja vissza.
     */
    public String getModelId() {
        return source.getId();
    }
}
