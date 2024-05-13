package modell;

import java.util.ArrayList;
import java.util.Random;

public class Pipe extends NetworkPiece implements Steppable {
	private boolean waterflow;
	private boolean canbepunctured = true;
	private boolean isslippery;
	private boolean issticky;
	private static int counter = 0;
	private ArrayList<NetworkPiece> neighbours = new ArrayList<>();
	private Random random = new Random();

	/**
	 * Konstruktor.
	 */
	public Pipe(Pump p1, Pump p2) {
		neighbours.add(p1);
		neighbours.add(p2);
		p1.AddPipe(this);
		p2.AddPipe(this);
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "pipe" + counter++;
	}

	/**
	 * Konstruktor.
	 */
	public Pipe(Pump p, NetworkPiece np) {
		neighbours.add(np);
		neighbours.add(p);

		p.AddPipe(this);
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "pipe" + counter++;
	}

	public Pipe(Pump p, Cistern c) {
		neighbours.add(p);
		neighbours.add(c);

		p.AddPipe(this);
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "pipe" + counter++;
	}

	/**
	 * Konstruktor.
	 */
	public Pipe(Cistern c) {
		neighbours.add(c);
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "pipe" + counter++;
	}

	public String toString() {
		return id + "\t[waterflow = " + waterflow + "] [punctured = " + !works + "] [connects: "
				+ neighbours.get(0).getId() + ", " + neighbours.get(1).getId() + "]";
	}

	/**
	 * Beállítja, hogy folyik-e a csőben víz.
	 */
	public void Setwaterflow(boolean b) {
		waterflow = b;
	}

	/**
	 * Visszadja, hogy kilyukasztható-e a cső.
	 */
	public boolean Getslippery() {
		return isslippery;
	}

	/**
	 * Beállítja, hogy kilyukasztható-e a cső.
	 */
	public void Setslippery() {
		isslippery = true;
	}

	/**
	 * Visszadja, hogy ragadós-e a cső.
	 */
	public boolean Getsticky() {
		return issticky;
	}

	/**
	 * Beállítja, hogy ragadós-e a cső.
	 */
	public void Setsticky() {
		issticky = true;
	}

	public boolean GetWorks() {
		return works;
	}

	public boolean GetWaterflow() {
		return waterflow;
	}

	/**
	 * Visszadja, hogy a cső üres-e.
	 */
	public boolean IsEmpty() {
		return characters.isEmpty();
	}

	/**
	 * Visszaadja a neighbours attribútumot.
	 */
	public ArrayList<NetworkPiece> GetNeighbours() {
		return neighbours;
	}

	/**
	 * A cső lyukasztófüggvénye. Ezt meghívva lehet a csövet kilyukasztani.
	 */
	public void Puncture() {
		if (canbepunctured)
			works = false;
	}

	/**
	 * A cső szerelő függvénye. Ezt meghívva majd megjavul a cső.
	 */
	public void Repair() {
		works = true;
		canbepunctured = false;
	}

	/**
	 * A cső kettévágó függvénye, lényegében létrehoz egy újat a meglévőhöz.
	 */
	public void Cut(Pump newpump) {

		Pipe new1 = new Pipe(newpump, neighbours.get(0));
		Network.AddElement(new1);

		neighbours.get(0).swap(new1, this);

		Pipe new2 = new Pipe(newpump, neighbours.get(1));
		Network.AddElement(new2);

		neighbours.get(1).swap(new2, this);

		Network.RemoveElement(this);
	}

	public void swap(Pipe uj, Pipe regi) {
		throw new UnsupportedOperationException();
	}

	/**
	 * A cső leválasztó függvénye.
	 */
	public void Detach(Pump p) {
		neighbours.remove(p);
	}

	/**
	 * A cső csatlakoztató függvénye.
	 */
	public void Attach(NetworkPiece cp) {
		neighbours.add(cp);
	}

	/**
	 * A cső állapotától függően megadja, hogy a pumpának szállít-e a vizet. (Ha
	 * lyukas, vagy nincs benne vízáramlás akkor nem szállít.)
	 */
	public boolean GivesWater() {
		return (works && waterflow);
	}

	/**
	 * Cső step metódusa.
	 * Minden körben meghívódik, és a szabotőrőknek pontot ad, ha víz folyik el
	 * belőle.
	 * Ha a cső csúszós vagy ragadós, véletlenszerűen visszaállítja.
	 */
	public void Step() {

		if (random.nextInt() % 3 == 0) {
			isslippery = false;
		}
		if (random.nextInt() % 3 == 0) {
			issticky = false;
			if (!characters.isEmpty())
				characters.get(0).setcanmove(true);
		}
		if (random.nextInt() % 3 == 0) {
			canbepunctured = true;
		}

		if (waterflow && (!works || neighbours.contains(null))) {
			Network.AddSandWater();
		}
	}

	/**
	 * Visszadja, hogy a paraméterként kapott networkpiece szomszédja-e a csőnek.
	 */
	public boolean IsNeighbour(NetworkPiece np) {
		for (NetworkPiece neighbour : neighbours) {
			if (neighbour == np) {
				return true;
			}
		}
		return false;
	}
}
