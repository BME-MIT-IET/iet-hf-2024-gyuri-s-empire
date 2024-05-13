package grafikus;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import modell.Game;

public class Window extends JFrame {
    /**
     * A menüt és a játékot megjelenítő panaleket jeleníti meg.
     */
    JPanel main;

    /**
     * A játék megjelenítését megvalósító GameView objektum.
     */
    transient GameView game;

    /**
     * A menü megjelenítését megvalósító GameView objektum.
     */
    transient MenuView menu;

    /**
     * Konstruktor, beállítja a Window tulajdonságait és példányosítja a vezérlőket.
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Sivatagi vízhálózat");

        main = new JPanel(new CardLayout());
        main.setLocation(0, 0);
        main.setSize(1200, 800);
        main.setBackground(new Color(100, 100, 100));
        this.add(main);

        menu = new MenuView();
        main.add("menu", menu.Getmenupanel());

        game = new GameView();
        main.add("game", game.Getgamepanel());

        main.setVisible(true);

        ((JButton) ((JPanel) main.getComponent(0)).getComponent(0))
                .addActionListener(e -> runGame());

        ((JButton) ((JPanel) main.getComponent(0)).getComponent(1))
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog = new JDialog();
                        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        dialog.setTitle("Options");
                        dialog.setModal(true);
                        dialog.setSize(300, 140);
                        dialog.setResizable(false);
                        dialog.setLocationRelativeTo(null);
                        dialog.setLayout(null);

                        JLabel text1 = new JLabel("Játékosok száma csapatonként:"),
                                text2 = new JLabel("Összes lépés a játék során:");
                        text1.setLocation(10, 7);
                        text1.setSize(200, 20);
                        text1.setVisible(true);
                        dialog.add(text1);

                        text2.setLocation(10, 32);
                        text2.setSize(200, 20);
                        text2.setVisible(true);
                        dialog.add(text2);
                        JSpinner player = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1)),
                                steps = new JSpinner(new SpinnerNumberModel(60, 0, 600, 6));
                        player.setLocation(210, 5);
                        player.setSize(70, 25);
                        dialog.add(player);

                        steps.setLocation(210, 30);
                        steps.setSize(70, 25);
                        dialog.add(steps);

                        JButton ok = new JButton("OK");
                        ok.setSize(130, 30);
                        ok.setLocation(10, 65);
                        dialog.add(ok);

                        JButton cancel = new JButton("Cancel");
                        cancel.setSize(130, 30);
                        cancel.setLocation(145, 65);
                        dialog.add(cancel);

                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Game.SetTimeleft((int) steps.getValue());
                                game.setPlayerCount((int) player.getValue());
                                dialog.dispose();
                            }
                        });

                        cancel.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                        dialog.setVisible(true);
                    }
                });

        ((JButton) ((JPanel) main.getComponent(0)).getComponent(2))
                .addActionListener(e -> exit());
    }

    /**
     * A játékot indítja, meghívja a játékteret inicializáló metódust.
     */
    public void runGame() {
        game.PlaygroundSetup();
        ((CardLayout) main.getLayout()).show(main, "game");
    }

    /**
     * Kilép a programból.
     */
    public void exit() {
        System.exit(0);
    }
}
