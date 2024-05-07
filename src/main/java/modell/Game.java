package modell;

public class Game {

	/**
	 * A hátralévő játékidő nyilvántartásáért felel. Private láthatóságú, integer
	 * típusú változó. Van egy kezdőértéke, amely a modell.Timer Tick eseményre
	 * folyamatosan
	 * csökken. A kezdőértéke beállítható a SetTimeleft metódus segítségével.
	 * Aktuális értéke
	 * lekérdezhető a GetTimeleft metódus segítségével.
	 */
	private static int timeleft = 60;

	/**
	 * A játékban lévő hálózatot tartalmazza. Private láthatóságú, modell.Network
	 * típusú
	 * változó. A játékban a vízhálózatot reprezentáló objektum egy példánya, amely
	 * a
	 * GetNetwork és a SetNetwork metódusokon keresztül érhető el.
	 */
	private Network network;

	/**
	 * Publikus láthatóságú, void visszatérésű metódus. Amikor a timeleft
	 * értéke eléri a 0-t, akkor ez a metódus meghívódik és kihirdeti az eredményt.
	 */
	public static void Winner() {
		if (Network.GetCityWater() == Network.GetSandWater())
			System.out.println("Döntetlen");
		else
			System.out.println("A " + ((Network.GetCityWater() > Network.GetSandWater())
					? "szerelők"
					: "szabotőrök") + " nyertek");
	}

	/**
	 * Publikus láthatóságú, void visszatérésű metódus. A
	 * játékból hátralevő idő beállítását végzi el. A paraméterben kapott érték lesz
	 * a hátralévő
	 * idő új értéke.
	 */
	public static void SetTimeleft(int time) {
		timeleft = time;
	}

	/**
	 * Publikus láthatóságú, int visszatérésű metódus. Visszatér a
	 * játékból hátralévő idő aktuális értékével.
	 */
	public static int GetTimeleft() {
		return timeleft;
	}

	/**
	 * Publikus láthatóságú, modell.Network típussal visszatérő
	 * metódus. Visszatér a játékban a hálózatot reprezentáló modell.Network típusú
	 * objektum példánnyal.
	 */
	public Network GetNetwork() {
		return network;
	}

	/**
	 * Publikus láthatóságú, void visszatérésű metódus.
	 * Ez a metódus beállítja, hogy a hálózatot reprezentáló objektumpéldány a
	 * paraméterben kapott példány legyen.
	 */
	public void SetNetwork(Network nw) {
		network = nw;
	}
}
