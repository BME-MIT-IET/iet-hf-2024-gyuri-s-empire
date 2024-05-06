package modell;

import java.util.ArrayList;

public class Cistern extends NetworkPiece implements Steppable {
	private static int counter;
	private ArrayList<Pipe> pipes = new ArrayList<>();

	public String toString() {
		for (Pipe p : pipes) {
			if (p.GivesWater()) {
				return id + "\t[waterflow = true]";
			}
		}
		return id + "\t[waterflow = false]";
	}

	/**
	 * A ciszterna konstruktora. Beállítja az azonosítóját.
	 */
	public Cistern(Pump pu) {
		Pipe pi = new Pipe(pu, this);
		pipes.add(pi);
		Timer.AddPalyaelem(this);
		Network.AddElement(this);
		id = "cistern" + counter++;

	}

	/**
	 * visszaad egy új pumpát.
	 * 
	 * @return - új pumpa
	 */
	public Pump GetAPump() {
		return new Pump();
	}

	/**
	 * Ciszterna step metódusa minden körben meghívódik,
	 * és a ciszternákba jutó víz mennyiségét hozzáadja a szerelők által gyűjtött
	 * vízhez.
	 */
	public void Step() {
		for (Pipe p : pipes) {
			if (p.GivesWater()) {
				Network.AddCityWater();
			}
		}
	}

	@Override
	public void swap(Pipe uj, Pipe regi) {
		pipes.remove(regi);
		pipes.add(uj);
	}

	@Override
	public ArrayList<NetworkPiece> GetNeighbours() {
		return new ArrayList<>(pipes);
	}

	/**
	 * megvizsgaálja, hogy a paraméterben kapott modell.NetworkPiece szomszédos-e
	 * vele
	 * 
	 * @param np - keresett networkpiece
	 * @return - true, ha szomszédos, false, ha nem
	 */
	public boolean IsNeighbour(NetworkPiece np) {
		return pipes.contains(np);
	}

	/**
	 * Új pipe-ot készít és a szerelő kezébe adja az egyik végét
	 * 
	 * @return p1 : létrehozott cső
	 */
	public Pipe GetNewPipe() {
		Pipe p = new Pipe(this);
		pipes.add(p);
		return p;
	}
}