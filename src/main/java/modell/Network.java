package modell;

import java.util.ArrayList;

public class Network {
	/**
	 * A ciszternákba belefolyt víz mennyiségét méri. Integer típusú, private
	 * láthatóságú. Kezdőértéke 0, amely a játék előrehadtával folyamatosan
	 * növekszik.
	 */
	private static int citywater = 0;

	/**
	 * A homokba kifolyt víz mennyiségét méri. Integer típusú, private
	 * láthatóságú. Kezdőértéke 0, amely a játék előrehadtával folyamatosan
	 * növekszik.
	 */
	private static int sandwater = 0;

	/**
	 * Számontartja a játékban lévő hálózatelemeket. modell.NetworkPiece lista típusú,
	 * private láthatóságú. A hálózathoz új elemet adni az AddElement, eltávolítani
	 * pedig a
	 * RemoveElement metódussal lehet.
	 */
	private static ArrayList<NetworkPiece> pieces = new ArrayList<>();

	/**
	 * Számontartja a játékban lévő karaktereket. modell.Character lista típusú, private
	 * láthatóságú. Az elemszáma 4 (2-2 szabotőr, illetve szerelő).
	 */
	private static ArrayList<Character> characters = new ArrayList<>();


	/**
	 * kiírja a játékban található objektumok állapotát
	 */
	public static int listall() {
		for (Character c : characters) {
			System.out.println(c.toString());
		}
		for (NetworkPiece np : pieces) {
			System.out.println(np.toString());
		}
		return 0;
	}



	public static ArrayList<Character> GetCharacters() {
		return characters;
	}


	/**
	 * visszaadja a hivatkozott hálózati elemet.
	 * 
	 * @param id a hálózati elem azonosítója
	 * @return a hálózati elem, ha nincs ilyen elem, akkor null
	 */
	public static NetworkPiece getPiece(String id) {
		for (NetworkPiece np : pieces) {
			if (np.getId().equals(id)) {
				return np;
			}
		}
		return null;
	}


	/**
	 * hozzáadja a megfelelő számú játékost a játékhoz
	 */
	public static void Init(int i) {
		for (int j = 0; j < i; j++) {
			characters.add(new Saboteur());
			characters.add(new Plumber());
		}
	}

	/**
	 * Publikus láthatóságú, void visszatérésű metódus. A játék
	 * minden körében hozzáadja a citywater változó értékéhez az aktuális körben a
	 * ciszternákba juttatott vízmennyiség értékét
	 */
	public static void AddCityWater() {
		citywater++;
	}

	/*
	 * Publikus láthatóságú, void visszatérésű metódus. A játék minden körében
	 * hozzáadja a sandwater változó értékéhez az aktuális körben a homokba
	 * kifolyt vízmennyiség értékét.
	 */
	public static void AddSandWater() {
		sandwater++;
	}

	/**
	 * Publikus láthatóságú, void visszatérésű
	 * metódus. A szerelőknek lehetőségük van a vízhálózat bővítésére. Ebben az
	 * esetben ez a metódus adja hozzá a hálózathoz az új hálózatelemet, melyet
	 * paraméterben kap meg.
	 */
	public static void AddElement(NetworkPiece np) {
		pieces.add(np);
	}

	/**
	 * Publikus láthatóságú, void visszatérésű
	 * metódus. A szerelőknek lehetőségük van a vízhálózat elemeinek mozgatására,
	 * így az
	 * adott hálózatelemet el kell távolítani a hálózatból. Ebben az esetben ez a
	 * metódus
	 * távolítja el a hálózatból az adott hálózatelemet, melyet paraméterben kap
	 * meg.
	 */
	public static void RemoveElement(NetworkPiece np) {
		pieces.remove(np);
	}

	/**
	 * Publikus láthatóságú, int visszatérésű metódus. Ezzel a
	 * metódussal lekérdezhető a játék során a ciszternákba folyt víz mennyisége.
	 */
	public static int GetCityWater() {
		return citywater;
	}

	/**
	 * Publikus láthatóságú, int visszatérésű metódus. Ezzel a
	 * metódussal lekérdezhető a játék során a homokba folyt víz mennyisége.
	 */
	public static int GetSandWater() {
		return sandwater;
	}

	/**
	 * Publikus láthatóságú, modell.NetworkPiece lista típussal
	 * visszatérő metódus. Ezzel a metódussal lekérdezhető a játékban lévő
	 * hálózatelemek
	 * listája.
	 */
	public static ArrayList<NetworkPiece> GetPieces() {
		return pieces;
	}

}
