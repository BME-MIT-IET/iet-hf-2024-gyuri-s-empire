package modell;

import java.util.ArrayList;

public abstract class NetworkPiece {
	protected ArrayList<Character> characters = new ArrayList<>();
	protected String id;
	protected boolean works = true;

	abstract public void swap(Pipe uj, Pipe regi);

	public String getId() {
		return id;
	}

	public boolean getWorkingState() {
		return works;
	}

	/**
	 * Eltávolítja a karaktert az adott elemről.
	 */
	public void Remove(Character c) {
		characters.remove(c);
	}

	/**
	 * Hozzáadja a karaktert az adott elemhez.
	 */
	public void Add(Character c) {
		characters.add(c);
	}

	public abstract ArrayList<NetworkPiece> GetNeighbours();

	public ArrayList<Character> GetCharacters() {
		return characters;
	}

	/**
	 * megadja, hogy a paraméterként kapott elemre lehet-e lépni (szomszédosak-e)
	 */
	public abstract boolean IsNeighbour(NetworkPiece np);
}
