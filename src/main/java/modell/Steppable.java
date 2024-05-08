package modell;

public interface Steppable {

	/**
	 * Public láthatóságú, void visszatérésű metódus, amelyet a modell.Timer
	 * Tick eseménye hív meg. Egységnyi időnként bekövetkező belső változást,
	 * lépést eredményez. Ezt a metódust az interfészt megvalósító osztályok
	 * valósítják meg.
	 */
	public void Step();
}
