package modell;

import java.util.ArrayList;

public class Source extends NetworkPiece {
	private Pipe flowpipe;
	private static int counter = 0;

	/**
	 * Konstruktor.
	 */
	public Source(Pump pu) {
		Pipe pi = new Pipe(pu, this);
		pi.Setwaterflow(true);
		Network.AddElement(this);
		flowpipe = pi;
		id = "source" + counter++;
	}

	public String toString(){
		return id;
	}

	public void swap(Pipe uj, Pipe regi) {
		flowpipe = uj;
	}


	@Override
	public ArrayList<NetworkPiece> GetNeighbours() {
		ArrayList<NetworkPiece> np = new ArrayList<>();
		np.add(flowpipe);
		return np;
	}

	public boolean IsNeighbour(NetworkPiece np) {
		return flowpipe == np;
	}
}
