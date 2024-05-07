package grafikus;

public interface Drawable {
    /**
     * A Drawable objektumok kirajzolását valósítja meg az interfészt megvalósító
     * osztályokban.
     */
    public void Draw();

    /**
     * Getter
     * 
     * @return A Drawable objektumokra lépő karakterek x koordinátáját adja vissza.
     */
    public int getx();

    /**
     * Getter
     * 
     * @return A Drawable objektumokra lépő karakterek y koordinátáját adja vissza.
     */
    public int gety();

    /**
     * A pályelemre lépő karakterek különböző koordinátákon való elhelyezéséhez
     * generál
     * koordinátákat.
     * 
     * @return A generált koordináták tömbje.
     */
    public int[] getCoord();

    /***
     * Getter
     * 
     * @return A Drawable objektum ID-ját adja vissza.
     */
    public String getModelId();
}
