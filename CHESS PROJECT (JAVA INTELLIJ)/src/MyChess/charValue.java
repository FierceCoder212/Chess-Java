package MyChess;

public enum charValue {
    A ( 0 , 'A' ), B ( 1 , 'B' ), C ( 2 , 'C' ), D ( 3 , 'D' ),
    E ( 4 , 'E' ), F ( 5 , 'F' ), G ( 6 , 'G' ), H ( 7 , 'H' );
    private final int index;
    private final char character;

    charValue ( int index , char character ) {
        this.index = index;
        this.character = character;
    }

    public int getIndex ( ) {
        return index;
    }

    public char getCharacter ( ) {
        return character;
    }
}