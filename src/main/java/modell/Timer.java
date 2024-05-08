package modell;

import java.util.ArrayList;

public class Timer {

	/**
	 * Private láthatóságú tömb, amely modell.Steppable objektumokat
	 * tárol.
	 */
	private static ArrayList<Steppable> karakterek = new ArrayList<>();
	private static ArrayList<Steppable> palyaelemek = new ArrayList<>();

	/**
	 * A folyamatban lévő játékot reprezentáló objektumpéldány. Tartoznak
	 * hozzá getter és setter metódusok.
	 */
	private Game game;

	/**
	 * Public láthatóságú, void visszatérésű metódus, amely egységnyi
	 * időnként meghívja a modell.Steppable interfészt megvalósító objektumok
	 * (steppable[0..*]) Step metódusát. Ezen felül egységnyi időnként csökkenti a
	 * game timeleft mezőjének értékét.
	 */
	public static void Tick() {

		for (Steppable value : palyaelemek) {
			value.Step();
		}

		for (Steppable karakter : karakterek) {
			karakter.Step();
		}

		Game.SetTimeleft(Game.GetTimeleft() - 1);
	}

	public static void AddKarakter(Steppable karakter) {
		karakterek.add(karakter);
	}

	public static void AddPalyaelem(Steppable palyaelem) {
		try {
			Pump p = (Pump) palyaelem;
			palyaelemek.add(0, p);
		} catch (Exception e) {
			palyaelemek.add(palyaelem);
		}
	}

	/**
	 * Public láthatóságú, modell.Game visszatérésű metódus, amely
	 * visszatér az éppen folyamatban lévő játékot reprezentáló objektumpéldánnyal.
	 */
	public Game GetGame() {
		return game;
	}

	/**
	 * Public láthatóságú, void visszatérésű metódus,
	 * amely beállítja a game objektumpéldányt a paraméterben kapott példányra.
	 */
	public void SetGame(Game g) {
		game = g;
	}

	public static void start() {
		for (Steppable s : palyaelemek) {
			s.Step();
		}
	}
}