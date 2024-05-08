package grafikus;

import modell.*;
import modell.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class GameView {
    /**
     * A fő panel, amelyen minden játékhoz szükséges dolog megjelenik.
     */
    private JPanel gamepanel;

    /**
     * A játékosok által végrehajtható műveleteket reprezentáló gombok listája.
     */
    private ArrayList<JButton> actions = new ArrayList<>();

    /**
     * A játék állapotáról információt szolgáltató JLabel-ek listája.
     */
    private ArrayList<JLabel> informations = new ArrayList<>();

    /**
     * A játék során megjelenítendő Drawable (pályaelemek és játékosok) objektumok
     * listája.
     */
    private static ArrayList<Drawable> drawables = new ArrayList<>();

    /**
     * Az actions lista gombjaihoz függvényeket rendelő HashMap.
     */
    private HashMap<String, Runnable> gombfuggvenyek = new HashMap<>();

    /**
     * Gombok szövegét tároló tömb (actions).
     */
    private String[] buttontext = new String[] { "Move", "Set pump", "Puncture pipe",
            "Make pipe sticky"/* eddig general 0-3 */, "Make pipe slippery"/* Saboteur 4 */,
            "Repair pump", "Repair pipe", "Attach pipe", "Detach pipe", "Place pump",
            "Request pump", "Request pipe" /* plumber 5-11 */ };

    /**
     * JLabel-ek default szövegét tároló tömb (informations).
     */
    private String[] labeltext = new String[] { "", "[timeleft]", "X steps left", "Saboteurwater: 0",
            "Cisternwater: 0" };

    /**
     * A játékteret reprezentáló JPanel. Ezen jelennek meg a pályaelemek és a
     * játékosok ikonjai.
     */
    private JPanel playgroundpanel;

    /**
     * Ennyi játékos van csapatonként, alapértelmezett értéke 2.
     */
    private static int playercount = 2;

    /**
     * Az éppen soron következő játékost reprezentáló objektum.
     */
    private static Character character;

    /**
     * Az éppen soronkövetkező játékos által még végrehajtható lépések száma.
     * Kezdőértéke 6.
     */
    private int stepsLeft = 6;

    /**
     * A játékban lévő játékosokat tároló lista.
     */
    ArrayList<Character> players = new ArrayList<>();

    /**
     * Az éppen soronkövetkező játékos indexe a listában, alapértelmezetten az első
     * játékos kezd.
     */
    private int playerIdex = 0;

    /**
     * Setter, beállítja a playercount értékét.
     * 
     * @param value Az options menüben megadott játékosszám csapatonként.
     */
    public void setPlayerCount(int value) {
        playercount = value;
    }

    /**
     * Konstruktor. Meghívja a gombokat példányosító függvényt és hozzájuk rendeli a
     * metódusokat.
     */
    public GameView() {

        GameSetup();

        gombfuggvenyek.put("Move", this::move);
        gombfuggvenyek.put("Set pump", this::setpump);
        gombfuggvenyek.put("Puncture pipe", this::puncturepipe);
        gombfuggvenyek.put("Make pipe slippery", this::makepipeslippery);
        gombfuggvenyek.put("Make pipe sticky", this::makepipesticky);
        gombfuggvenyek.put("Repair pump", this::repairpump);
        gombfuggvenyek.put("Repair pipe", this::repairpipe);
        gombfuggvenyek.put("Attach pipe", this::attachpipe);
        gombfuggvenyek.put("Detach pipe", this::detachpipe);
        gombfuggvenyek.put("Place pump", this::placepump);
        gombfuggvenyek.put("Request pump", this::requestpump);
        gombfuggvenyek.put("Request pipe", this::requestpipe);

    }

    /**
     * Példányosítja a játék vezérléséhez szükséges vezérlőket.
     */
    public void GameSetup() {
        gamepanel = new JPanel(null);

        for (int i = 0; i < buttontext.length; i++) {
            JButton JB = new JButton(buttontext[i]);
            JB.setLocation(10, i * 50 + 60);
            JB.setSize(150, 40);
            JB.addActionListener(e -> ButtonPressed((JButton) e.getSource()));
            JB.setVisible(true);
            actions.add(i, JB);
            gamepanel.add(JB);
        }

        JLabel muveletek = new JLabel("Available actions:");
        muveletek.setLocation(10, 10);
        muveletek.setSize(150, 40);
        muveletek.setVisible(true);
        informations.add(muveletek);
        gamepanel.add(muveletek);
        for (int i = 0; i < 5; i++) {
            JLabel JL = new JLabel(labeltext[i]);
            JL.setLocation(1000, i * 40 + 10);
            JL.setSize(150, 40);
            JL.setVisible(true);
            informations.add(JL);
            gamepanel.add(JL);
        }

        gamepanel.setVisible(true);
    }

    /**
     * Inicializálja a játékteret és kirajzolja a pályát és a játékokat reprezentáló
     * grafikus objektumokat.
     */
    public void PlaygroundSetup() {

        playgroundpanel = new JPanel(null);
        playgroundpanel.setLocation(190, 20);
        playgroundpanel.setSize(790, 720);
        playgroundpanel.setBackground(Color.getHSBColor(230, 200, 150));
        gamepanel.add(playgroundpanel);
        playgroundpanel.setVisible(true);

        // Pálya Létrehozása
        Pump pump0 = new Pump();
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();
        Pump pump3 = new Pump();
        Pump pump4 = new Pump();
        Pump pump5 = new Pump();
        Pump pump6 = new Pump();

        Source source0 = new Source(pump0);
        Source source1 = new Source(pump1);

        Cistern cistern0 = new Cistern(pump5);
        Cistern cistern1 = new Cistern(pump6);

        // objektumok grafikus cucchoz rendelese
        SourceGraph sourceGraph0 = new SourceGraph(playgroundpanel, 40, 180, source0);
        SourceGraph sourceGraph1 = new SourceGraph(playgroundpanel, 40, 460, source1);

        CisternGraph cisternGraph0 = new CisternGraph(playgroundpanel, 700, 180, cistern0);
        CisternGraph cisternGraph1 = new CisternGraph(playgroundpanel, 700, 460, cistern1);

        PumpGraph pumpGraph0 = new PumpGraph(playgroundpanel, 187, 200, pump0);
        PumpGraph pumpGraph1 = new PumpGraph(playgroundpanel, 187, 440, pump1);
        PumpGraph pumpGraph2 = new PumpGraph(playgroundpanel, 354, 140, pump2);
        PumpGraph pumpGraph3 = new PumpGraph(playgroundpanel, 354, 320, pump3);
        PumpGraph pumpGraph4 = new PumpGraph(playgroundpanel, 354, 500, pump4);
        PumpGraph pumpGraph5 = new PumpGraph(playgroundpanel, 521, 200, pump5);
        PumpGraph pumpGraph6 = new PumpGraph(playgroundpanel, 521, 440, pump6);

        Network.Init(playercount);
        players = Network.GetCharacters();

        try {
            for (int i = 0; i < (players.size()); i++) {
                int[] c = pumpGraph0.getCoord();
                if (i % 2 == 0) {
                    Saboteur s = (Saboteur) Network.GetCharacters().get(i);
                    SaboteurGraph sg = new SaboteurGraph(playgroundpanel, c[0], c[1], s);
                    drawables.add(sg);
                } else {
                    Plumber p = (Plumber) players.get(i);
                    PlumberGraph pg = new PlumberGraph(playgroundpanel, c[0], c[1], p);
                    drawables.add(pg);
                }
            }
        } catch (Exception ex) {
            System.out.println("rossz cast");
        }

        character = players.get(playerIdex);

        /// forrasok
        drawables.add(sourceGraph0);
        drawables.add(sourceGraph1);
        /// ciszternak
        drawables.add(cisternGraph0);
        drawables.add(cisternGraph1);

        // pumpák
        drawables.add(pumpGraph0);
        drawables.add(pumpGraph1);
        drawables.add(pumpGraph2);
        drawables.add(pumpGraph3);
        drawables.add(pumpGraph4);
        drawables.add(pumpGraph5);
        drawables.add(pumpGraph6);

        drawables.add(new PipeGraph(playgroundpanel, sourceGraph0.GetX(), sourceGraph0.GetY(), pumpGraph0.GetX(),
                pumpGraph0.GetY(), (Pipe) Network.getPiece("pipe0")));
        drawables.add(new PipeGraph(playgroundpanel, sourceGraph1.GetX(), sourceGraph1.GetY(), pumpGraph1.GetX(),
                pumpGraph1.GetY(), (Pipe) Network.getPiece("pipe1")));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph5.GetX(), pumpGraph5.GetY(), cisternGraph0.GetX(),
                cisternGraph0.GetY(), (Pipe) Network.getPiece("pipe2")));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph6.GetX(), pumpGraph6.GetY(), cisternGraph1.GetX(),
                cisternGraph1.GetY(), (Pipe) Network.getPiece("pipe3")));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph0.GetX(), pumpGraph0.GetY(), pumpGraph2.GetX(),
                pumpGraph2.GetY(), new Pipe(pump0, pump2)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph2.GetX(), pumpGraph2.GetY(), pumpGraph5.GetX(),
                pumpGraph5.GetY(), new Pipe(pump2, pump5)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph0.GetX(), pumpGraph0.GetY(), pumpGraph3.GetX(),
                pumpGraph3.GetY(), new Pipe(pump0, pump3)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph3.GetX(), pumpGraph3.GetY(), pumpGraph5.GetX(),
                pumpGraph5.GetY(), new Pipe(pump3, pump5)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph1.GetX(), pumpGraph1.GetY(), pumpGraph3.GetX(),
                pumpGraph3.GetY(), new Pipe(pump1, pump3)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph3.GetX(), pumpGraph3.GetY(), pumpGraph6.GetX(),
                pumpGraph6.GetY(), new Pipe(pump3, pump6)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph1.GetX(), pumpGraph1.GetY(), pumpGraph4.GetX(),
                pumpGraph4.GetY(), new Pipe(pump1, pump4)));
        drawables.add(new PipeGraph(playgroundpanel, pumpGraph4.GetX(), pumpGraph4.GetY(), pumpGraph6.GetX(),
                pumpGraph6.GetY(), new Pipe(pump4, pump6)));

        informations.get(3).setText(String.valueOf(stepsLeft) + " steps left");
        informations.get(2).setText(String.valueOf(modell.Game.GetTimeleft()) + " steps till the end");
        informations.get(1).setText(character.getId() + "'s turn");
        Update();

    }

    /**
     * A gombok eseménykezelője, meghívja a megnyomott gombnak megfelelő metódust.
     * 
     * @param jb Az eseményt kiváltó gomb.
     */
    public void ButtonPressed(JButton jb) {
        informations.get(1).setText(character.getId() + "'s turn");
        gombfuggvenyek.get(jb.getText()).run();
    }

    /**
     * Frissíti a játékról a JLabel-ekben megjelenő információkat.
     */
    public void FreshTime() {

        informations.get(2).setText(String.valueOf(modell.Game.GetTimeleft()) + " steps till the end");
        informations.get(3).setText(String.valueOf(stepsLeft - 1) + " steps left");
        informations.get(4).setText("Saboteurwater: " + Network.GetSandWater());
        informations.get(5).setText("Cisternwater: " + Network.GetCityWater());
        stepsLeft = stepsLeft - 1;
        if (modell.Game.GetTimeleft() == 0) {
            JOptionPane.showMessageDialog(null,
                    (Network.GetSandWater() > Network.GetCityWater()) ? "A szabotőrök nyertek!"
                            : (Network.GetSandWater() < Network.GetCityWater()) ? "A szerelők nyertek!" : "Döntetlen!",
                    "Game over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        if (stepsLeft == 0) {
            stepsLeft = 6;

            playerIdex = (playerIdex + 1) % players.size();
            character = players.get(playerIdex);

            informations.get(3).setText(String.valueOf(stepsLeft) + " steps left");
            informations.get(1).setText(character.getId() + "'s turn");
        }
        Update();
    }

    /**
     * Frissíti a pálya kirajzolását a változásoknak megfelelően.
     */
    public void Update() {

        playgroundpanel.removeAll();

        if (informations.get(1).getText().contains("saboteur")) {
            actions.get(4).setVisible(true);
            for (int i = 5; i < actions.size(); i++) {
                actions.get(i).setVisible(false);
            }
        }

        else if (informations.get(1).getText().contains("plumber")) {
            actions.get(4).setVisible(false);
            for (int i = 5; i < actions.size(); i++) {
                actions.get(i).setVisible(true);
            }
        }

        modell.Timer.Tick();

        for (Drawable drawable : drawables) {
            drawable.Draw();
        }
        playgroundpanel.repaint();

    }

    /**
     * Getter
     * 
     * @param id A keresett Drawable objektum ID-ja.
     * @return Az ID-nak megfelelő objektum. Ha nincs ilyen akkor null.
     */
    public static Drawable getDrawable(String id) {
        for (Drawable drawable : drawables) {
            if (drawable.getModelId().equals(id)) {
                return drawable;
            }
        }
        for (Drawable drawable : drawables) {
            if (drawable.getModelId().equals(character.getId())) {
                return drawable;
            }
        }
        return null;
    }

    /**
     * Getter
     * 
     * @return A fő JPanel.
     */
    public JPanel Getgamepanel() {
        return gamepanel;
    }

    /**
     * Megkeresi az adott objektum koordinátáit és visszatér azokkal.
     * 
     * @param current A keresett objektum.
     * @returnA keresett objektum koordinátái.
     */

    public static int[] findGraphCoord(NetworkPiece current) {
        String currentId = current.getId();
        int[] coord = new int[2];
        for (int i = 0; i < drawables.size(); i++) {
            if (currentId.equals(drawables.get(i).getModelId())) {
                coord = drawables.get(i).getCoord();
                break;
            }
        }
        return coord;
    }

    /**
     * Setter, a paraméterben megadott karakter koordinátáit állítja be.
     * 
     * @param c    Az adott karakter.
     * @param tomb A beállítani kívánt koordináták.
     */
    public void findAndSetPlayerGraph(Character c, int[] tomb) {
        String playerId = c.getId();
        for (int i = 0; i < drawables.size(); i++) {
            if (playerId.equals(drawables.get(i).getModelId())) {
                try {
                    ((SaboteurGraph) drawables.get(i)).setPos(tomb[0], tomb[1]);
                } catch (Exception ex1) {
                    try {
                        ((PlumberGraph) drawables.get(i)).setPos(tomb[0], tomb[1]);
                    } catch (Exception ex2) {
                        System.out.println("Huha, nagy a baj!!");
                    }
                }
                break;
            }
        }
    }

    /**
     * A játékosok mozgását megvalósító metódus.
     */
    private void move() {

        if (character.getcanmove()) {
            JDialog jd = new JDialog();
            jd.setTitle("Choose field");
            jd.setModal(true);
            jd.setResizable(false);

            JPanel jp = new JPanel(new GridLayout(2, 1));

            ArrayList<NetworkPiece> szomszedok = character.GetCurrentPiece().GetNeighbours();
            String[] tomb = new String[szomszedok.size()];
            for (int i = 0; i < szomszedok.size(); i++) {
                tomb[i] = szomszedok.get(i).getId();
            }

            JComboBox<String> hova = new JComboBox<>(tomb);
            jp.add(hova);

            JButton ok = new JButton("OK");
            ok.addActionListener(e -> {
                character.Move(Network.getPiece((String) hova.getSelectedItem()));
                NetworkPiece curr = character.GetCurrentPiece();
                int[] coord = new int[2];
                coord = findGraphCoord(curr);
                findAndSetPlayerGraph(character, coord);
                FreshTime();
                jd.dispose();
            });

            jp.add(ok);
            jd.add(jp);
            jd.pack();

            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Oda vagy ragadva mint a bélyeg!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott pumpa ki- és bemenetének átállítását megvalósító metódus.
     */
    private void setpump() {
        System.out.println("setpump");

        if (character.GetCurrentPiece().getId().contains("pump")) {
            JDialog jd = new JDialog();
            jd.setTitle("choose flow direction");
            jd.setModal(true);
            jd.setResizable(false);

            ArrayList<NetworkPiece> szomszedok = character.GetCurrentPiece().GetNeighbours();
            String[] tomb = new String[szomszedok.size()];
            for (int i = 0; i < szomszedok.size(); i++) {
                tomb[i] = szomszedok.get(i).getId();
            }
            JComboBox<String> beBox = new JComboBox<>(tomb);
            JComboBox<String> kiBox = new JComboBox<>(tomb);

            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            okButton.addActionListener(e -> {
                String bemenet = (String) beBox.getSelectedItem();
                String kimenet = (String) kiBox.getSelectedItem();
                try {
                    character.SetPump((Pipe) Network.getPiece(bemenet), (Pipe) Network.getPiece(kimenet));
                    FreshTime();
                } catch (Exception ex) {

                }
                jd.dispose();
            });

            cancelButton.addActionListener(e -> {
                jd.dispose();
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            JPanel mainPanel = new JPanel(new GridLayout(3, 2));
            mainPanel.add(new JLabel("Bemeneti irány:"));
            mainPanel.add(beBox);
            mainPanel.add(new JLabel("Kimeneti irány 2:"));
            mainPanel.add(kiBox);
            mainPanel.add(new JLabel());
            mainPanel.add(buttonPanel);

            jd.getContentPane().add(mainPanel);
            jd.pack();
            jd.setResizable(false);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nem pumpán áll a karakter", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Egy adott cső kilyukasztását megvalósító metódus.
     */
    private void puncturepipe() {
        System.out.println("puncturepipe");
        if (character.GetCurrentPiece().getId().contains("pipe")) {
            try {
                character.PuncturePipe();
                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a lyukasztás");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem csovon all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott cső csúszóssá tételét megvalósító metódus.
     */
    private void makepipeslippery() {
        System.out.println("makepipeslippery");
        if (character.GetCurrentPiece().getId().contains("pipe")) {
            try {
                ((Saboteur) character).MakeSlippery();
                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a sikositas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem csovon all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott cső ragadóssá tételét megvalósító metódus.
     */
    private void makepipesticky() {
        System.out.println("makepipesticky");
        if (character.GetCurrentPiece().getId().contains("pipe")) {
            try {
                character.MakeSticky();
                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a ragacs");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem csovon all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott pumpa megjavítását megvalósító metódus.
     */
    private void repairpump() {
        System.out.println("repairpump");
        if (character.GetCurrentPiece().getId().contains("pump")) {
            try {
                ((Plumber) character).Repair();
                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a javitas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem pumpan all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott cső megjavítását megvalósító metódus.
     */
    private void repairpipe() {
        System.out.println("repairpipe");
        if (character.GetCurrentPiece().getId().contains("pipe")) {
            try {
                ((Plumber) character).Repair();
                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a javitas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem csovon all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott cső pumpához való csatlakoztatását megvalósító metódus.
     */
    private void attachpipe() {
        System.out.println("attachpipe");
        if (character.GetCurrentPiece().getId().contains("pump")
                && character.GetCurrentPiece().GetNeighbours().size() < 4) {
            try {
                Plumber c = (Plumber) character;
                if (!c.GetCurrentPiece().GetNeighbours().contains(c.GetCarries())) {
                    c.AttachPipe();
                    FreshTime();
                }

                else {
                    JOptionPane.showMessageDialog(null,
                            "Csőnek két vége nem lehet ugyanahhoz a pumpához csatlakoztatva!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                System.out.println("nem hívható a csatlakoztatas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem pumpán áll vagy túl sok cső van a pumpához csatlakoztatva!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy adott cső pumpáról való lecsatlakoztatását megvalósító metódus.
     */
    private void detachpipe() {
        System.out.println("detachpipe");

        if (character.GetCurrentPiece().getId().contains("pump")) {
            JDialog jd = new JDialog();
            jd.setTitle("Choose a pipe to detach");
            jd.setModal(true);
            jd.setResizable(false);

            ArrayList<NetworkPiece> szomszedok = character.GetCurrentPiece().GetNeighbours();
            String[] tomb = new String[szomszedok.size()];
            for (int i = 0; i < szomszedok.size(); i++) {
                tomb[i] = szomszedok.get(i).getId();
            }
            JComboBox<String> csoBox = new JComboBox<>(tomb);

            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            okButton.addActionListener(e -> {
                try {
                    String cso = (String) csoBox.getSelectedItem();
                    Pipe p = (Pipe) Network.getPiece(cso);
                    if (p.GetCharacters().isEmpty()) {
                        ((Plumber) character).DetachPipe(p);
                        FreshTime();
                    } else {
                        JOptionPane.showMessageDialog(null, "A cső foglalt!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {

                }

                jd.dispose();
            });

            cancelButton.addActionListener(e -> {
                jd.dispose();
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            JPanel mainPanel = new JPanel(new GridLayout(3, 2));
            mainPanel.add(new JLabel("leválasztandó cső:"));
            mainPanel.add(csoBox);
            mainPanel.add(buttonPanel);

            jd.getContentPane().add(mainPanel);
            jd.pack();
            jd.setResizable(false);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nem pumpan all", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Egy adott cső közepére pumpa lerakását megvalósító metódus.
     */
    private void placepump() {
        System.out.println("placepump");
        if (character.GetCurrentPiece().getId().contains("pipe")) {
            try {
                String pipeId = character.GetCurrentPiece().getId();
                Pump pump = (Pump) (((Plumber) character).GetCarries());
                ((Plumber) character).PlacePump();

                int[] coord = new int[2];

                for (int i = 0; i < drawables.size(); i++) {
                    if (pipeId.equals(drawables.get(i).getModelId())) {
                        coord = drawables.get(i).getCoord();
                        drawables.remove(i);
                        break;
                    }
                }
                drawables.add(new PumpGraph(playgroundpanel, coord[0], coord[1], pump));
                ArrayList<NetworkPiece> pipes = character.GetCurrentPiece().GetNeighbours();
                int[] coord2 = new int[2];
                int[] coord3 = new int[2];
                for (int i = 0; i < pipes.size(); i++) {
                    Pipe pi = (Pipe) pipes.get(i);
                    ArrayList<NetworkPiece> pumps = pi.GetNeighbours();
                    coord2 = findGraphCoord(pumps.get(0));
                    coord3 = findGraphCoord(pumps.get(1));
                    drawables.add(new PipeGraph(playgroundpanel, coord2[0], coord2[1], coord3[0], coord3[1], pi));
                }

                FreshTime();
            } catch (Exception ex) {
                System.out.println("nem hívható a pumpa elhelyezes");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nem csovon all", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy ciszternától pumpa felvételét megvalósító metódus.
     */
    private void requestpump() {
        System.out.println("requestpump");
        try {
            ((Plumber) character).RequestPump();
            FreshTime();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nem hívható a pumpa kérés!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Egy ciszternától cső felvételét megvalósító metódus.
     */
    private void requestpipe() {
        System.out.println("requestpipe");
        try {
            ((Plumber) character).RequestPipe();
            FreshTime();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nem hívható a cső kérés!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}