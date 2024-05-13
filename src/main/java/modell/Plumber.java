package modell;

public class Plumber extends Character {
	private NetworkPiece carries;
	private static int counter = 0;

	/**
	 * A vízvezetékszerelő lépését megvalósító függvény.
	 */
	public void step() {
		/*
		 * if (canmove) {
		 * Prototype p = new Prototype();
		 * int counter = 6;
		 * int status = 0;
		 * System.out.println(getId() + " kovetkezik, " + counter + " lepesig");
		 * p.addkimenet(getId() + " kovetkezik, " + counter + " lepesig");
		 * while (status != 2 && counter > 0) {
		 * status = p.plumbersmove(this);
		 * if (status == 1) {
		 * counter--;
		 * }
		 * }
		 * } else {
		 * System.out.println(getId() + " nem tud lepni");
		 * }
		 */

		try {
			if (carries != null && ((Pipe) carries).GetWaterflow()) {
				Network.AddSandWater();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * A vízvezetékszerelő konstruktora. Beállítja az azonosítóját.
	 */
	public Plumber() {
		currentpiece = Network.getPiece("pump0");
		Timer.AddKarakter(this);
		id = "plumber" + counter++;
	}

	public String toString() {
		if (carries != null) {
			return id + "\t[stands on: " + currentpiece.getId() + "] [carries: " + carries.getId() + "]";
		} else {
			return id + "\t[stands on: " + currentpiece.getId() + "]";
		}
	}

	public NetworkPiece GetCarries() {
		return carries;
	}

	public void Repair() {
		try {
			((Pump) currentpiece).Repair();
		} catch (Exception e) {
			try {
				((Pipe) currentpiece).Repair();
			} catch (Exception e2) {
				System.out.println("Nem csövön vagy pumpán áll");
			}
		}
	}

	/**
	 * Rácsatlakoztatja a vitt csövet arra a hálózati elemre, amin éppen áll.
	 * Meghívja a cső Repair függvényét.
	 */
	public void AttachPipe() {
		try {
			((Pump) currentpiece).AddPipe((Pipe) carries);
			((Pipe) carries).Attach(currentpiece);
			carries = null;
		} catch (Exception e) {
			System.out.println("Nem pumpán áll");
		}
	}

	/**
	 * Lecsatlakozatja a megadott csövet a pumpáról, amin áll. Meghívja a RemovePipe
	 * függvényét a pumpának.
	 */
	public void DetachPipe(Pipe p) {
		try {
			if (carries == null) {
				Pump cpump = (Pump) currentpiece;
				cpump.RemovePipe(p);
				carries = p;
				currentpiece = cpump;
			}
		} catch (Exception e) {
			System.out.println("Nem pumpán áll");
		}
	}

	/**
	 * Lehelyezi a vitt pumpát, de előtte elvágja az aktuális csövet, majd
	 * hozzáadja, kitörli a megfelelő elemeket a modell.Network-böl.
	 */
	public void PlacePump() {
		try {
			Pipe cpipe = (Pipe) currentpiece;
			cpipe.Cut((Pump) carries);
			currentpiece = carries;
		} catch (Exception e) {
			System.out.println("Nem csövön áll");
		}
	}

	/**
	 * A Ciszternától, amin éppen rajta áll, kér egy pumpát.
	 */
	public void RequestPump() {
		try {
			Cistern c = (Cistern) currentpiece;
			carries = c.GetAPump();
		} catch (Exception e) {
			System.out.println("Nem ciszternán áll");
		}
	}

	/**
	 * A ciszternánál, amin éppen rajta áll, kér egy csövet
	 */
	public void RequestPipe() {
		try {
			Cistern cc = (Cistern) currentpiece;
			carries = cc.GetNewPipe();
		} catch (Exception ex) {
			System.out.println("nem ciszternán áll!");
		}
	}
}
