package modell;

public class Saboteur extends Character {

	private static int counter = 0;

	/**
	 * A szabotőr konstruktora. Beállítja az azonosítóját.
	 */
	public Saboteur() {
		currentpiece = Network.getPiece("pump0");
		Timer.AddKarakter(this);
		id = "saboteur" + counter++;
	}

	public String toString() {
		return id + "\t[stands on: " + currentpiece.getId() + "]";
	}

	/**
	 * A szabotőr lépését megvalósító függvény.
	 */
	public void Step() {

	}

	/**
	 * csúszóssá teszi a csövet
	 */
	public void MakeSlippery() {
		try {
			Pipe cpipe = (Pipe) currentpiece;
			cpipe.Setslippery();
			currentpiece = cpipe;
		} catch (Exception e) {
			System.out.println("Nem csövön áll");
		}
	}
}
