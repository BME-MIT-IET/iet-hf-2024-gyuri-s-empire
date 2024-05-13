package modell;

import java.util.ArrayList;
import java.util.Random;

public class Pump extends NetworkPiece implements Steppable {
	private Pipe flowfrom;
	private Pipe flowto;
	private int watertank;
	private boolean waterflow;
	private static int counter = 0;
	private ArrayList<Pipe> pipes = new ArrayList<>();
	private Random random = new Random();

	/**
	 * Konstruktor.
	 */
	public Pump() {
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "pump" + counter++;
	}

	public String toString() {
		return id + "\t[waterflow = " + waterflow + "] [works = " + works + "] [watertank = " + watertank + "]";
	}

	public Pipe GetFlowto() {
		return flowto;
	}

	public Pipe GetFlowfrom() {
		return flowfrom;
	}

	public boolean GetWorks() {
		return works;
	}

	/**
	 * @param p1 - egyik cső, amiből folyik a víz.
	 * @param p2 - másik cső, amibe folyik a víz.
	 *           Ezzel a függvénnyel lehet beállítani a pumpán keresztüli
	 *           vízáramlást.
	 */
	public void SetWaterFlow(Pipe p1, Pipe p2) {

		flowfrom = p1;
		flowto = p2;
	}

	/**
	 * A pumpa szerelő függvénye. Ezt meghívva majd megjavul a cső.
	 */
	public void Repair() {
		works = true;
	}

	/**
	 * Leválasztja a csövet a pumpáról.
	 * 
	 * @param p - a cső, amit leválasztunk.
	 */

	public void RemovePipe(Pipe p) {
		pipes.remove(p);
		if (flowfrom == p) {
			flowfrom = null;
		} else if (flowto == p) {
			flowto.Setwaterflow(false);
			flowto = null;
		}
		p.Detach(this);
	}

	public void swap(Pipe uj, Pipe regi) {
		pipes.remove(regi);
		pipes.add(uj);
	}

	/**
	 * Hozzáadja a csövet a pumpához.
	 * 
	 * @param p - a cső, amit hozzáadunk.
	 */

	public void AddPipe(Pipe p) {
		if (pipes.size() < 4)
			pipes.add(p);
	}

	public ArrayList<Pipe> GetPipes() {
		return pipes;
	}

	/**
	 * Pumpa step metódusa,
	 * minden körben meghívódik, megvizsgája, hogy működik-e, illetve, hogy folyik-e
	 * bele víz,
	 * ezek az információk alapján beállítja a vízfolyását, és a homokba folyt vizet
	 */
	public void Step() {

		if (random.nextInt() % 7 == 0) {
			// works = false;
		}

		if (flowfrom != null)
			waterflow = flowfrom.GivesWater();
		try {
			if (!waterflow) {
				if (watertank > 0) {
					flowto.Setwaterflow(true);
					watertank--;
				} else {
					flowto.Setwaterflow(false);
				}
			} else if (watertank < 2) {
				flowto.Setwaterflow(false);
				watertank++;
			} else if (!works) {
				flowto.Setwaterflow(false);
				Network.AddSandWater();
			} else {
				flowto.Setwaterflow(true);
			}
		} catch (NullPointerException e) {
		}

		if (flowfrom != null && flowto != null) {
			for (Pipe p : pipes) {
				if (p != flowfrom && p != flowto) {
					p.Setwaterflow(false);
				}
			}
		}

	}

	@Override
	public ArrayList<NetworkPiece> GetNeighbours() {
		return new ArrayList<>(pipes);
	}

	/**
	 * Megnézi, hogy a paraméterként kapott cső szomszédja-e a pumpának.
	 * 
	 * @param np - a networkpiece, amit vizsgálunk.
	 * @return - true, ha szomszédja, false, ha nem.
	 */
	public boolean IsNeighbour(NetworkPiece np) {
		for (Pipe pipe : pipes) {
			if (pipe == np) {
				return true;
			}
		}
		return false;
	}
}
