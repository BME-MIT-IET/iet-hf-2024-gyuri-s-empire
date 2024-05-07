package grafikus;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class MenuView {
    /**
     * A menüt megjelenítő panel.
     */
    JPanel menupanel;

    /**
     * A menupanel-en megjelenő gombok.
     */
    JButton newgame, options, exit;

    /**
     * Az Options menüben megjelenő JSpinner-ek.
     */
    JSpinner playercount, totalstepcount;

    /**
     * Konstruktor, meghívja a grafikus vezérlőket példányosító metódust.
     */
    public MenuView() {
        MenuSetup();
    }

    /**
     * A menü grafikus vezérlőit példányosító metódus.
     */
    public void MenuSetup() {
        menupanel = new JPanel();
        menupanel.setSize(1200, 800);
        menupanel.setLocation(0, 0);

        newgame = new JButton("New game");
        options = new JButton("Options");
        exit = new JButton("Exit");

        menupanel.add(newgame);
        menupanel.add(options);
        menupanel.add(exit);
    }

    /**
     * Getter a menupanel-hez.
     * 
     * @return Visszaadja a menupanel objektumot.
     */
    public JPanel Getmenupanel() {
        return menupanel;
    }
}
