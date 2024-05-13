package modell;

import java.util.Random;

public abstract class Character implements Steppable {
	protected NetworkPiece currentpiece;
	protected boolean canmove = true;
	protected String id;
	private Random random = new Random();

	/**
	 * A karakter léptetéséért felelős függvény.
	 */
	public abstract void Step();

	public void setcanmove(boolean b) {
		canmove = b;
	}

	public boolean getcanmove() {
		return canmove;
	}

	public String getId() {
		return id;
	}

	/**
	 * Áthelyezi a karaktert az egyik hálózatelemről a másikra.
	 * 
	 * @param hova - a hálózatelem, ahova áthelyezzük a karaktert.
	 */

	public void Move(NetworkPiece hova) {
		try {
			if (currentpiece.IsNeighbour(hova)) {
				Pipe p = (Pipe) hova;
				if (p.IsEmpty()) {
					if (p.Getslippery()) {
						currentpiece.Remove(this);
						currentpiece = p.GetNeighbours().get(random.nextInt(2));
						currentpiece.Add(this);
					} else {
						currentpiece.Remove(this);
						currentpiece = Network.getPiece(hova.getId());
						currentpiece.Add(this);
						if (((Pipe) currentpiece).Getsticky()) {
							canmove = false;
						}
					}
				}
			}
		} catch (Exception e) {
			if (currentpiece.IsNeighbour(hova)) {
				currentpiece.Remove(this);
				hova.Add(this);
				currentpiece = hova;
			}
		}
	}

	/**
	 *
	 * @param p1 - egyik cső, amelyikből folyik majd a víz.
	 * @param p2 - másik cső, amibe majd folyik a víz.
	 *           A karakter beállítja azt, hogy a pumpa melyik hozzákapcsolódó
	 *           csőből melyikbe hozzákapcsolódó csőbe pumpálja a vizet azon a
	 *           pumpán, amin éppen tartózkodik.
	 */
	public void SetPump(Pipe p1, Pipe p2) {
		try {
			if (p1 != p2) {
				Pump cpump = (Pump) currentpiece;
				cpump.SetWaterFlow(p1, p2);
				currentpiece = cpump;
			}
		} catch (Exception ex) {
			System.out.println("nem pumpan all!!");
		}
	}

	/**
	 * A karakter lyukasztófüggvénye. Ezt meghívva kilyukasztja a csövet, amin éppen
	 * áll.
	 */
	public void PuncturePipe() {
		try {
			Pipe cpipe = (Pipe) currentpiece;
			cpipe.Puncture();
			currentpiece = cpipe;
		} catch (Exception e) {
			System.out.println("Nem csövön áll");
		}
	}

	/**
	 * A karakter ragadóssá teszi a csövet, amin éppen tartózkodik.
	 */
	public void MakeSticky() {
		try {
			Pipe cpipe = (Pipe) currentpiece;
			cpipe.Setsticky();
			currentpiece = cpipe;
		} catch (Exception e) {
			System.out.println("Nem csövön áll");
		}
	}

	/**
	 * @return - A currentpiece attribútum értékét adja vissza.
	 */
	public NetworkPiece GetCurrentPiece() {
		return currentpiece;
	}
}
