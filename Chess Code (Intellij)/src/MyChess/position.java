package MyChess;

public class position {
    protected int xPos;
    protected int yPos;
    private charValue characterValue;
    private int intValue;

    public position ( int mouseXPos , int mouseYPos ) {
        this.xPos = mouseXPos / 64;
        this.yPos = mouseYPos / 64;
        this.characterValue = charValue.values ( )[ this.xPos ];
        this.intValue = 8 - this.yPos;
    }

    public position ( charValue characterValue , int intValue ) {
        this.characterValue = characterValue;
        this.intValue = intValue;
        this.xPos = characterValue.getIndex ( );
        this.yPos = 8 - intValue;
    }

    public charValue getCharacterValue ( ) {
        return characterValue;
    }

    public int getIntValue ( ) {
        return intValue;
    }

    public int getXPos ( ) {
        return xPos;
    }

    public int getModifiedStartXPos ( ) {
        return (xPos * 64);
    }

    public int getYPos ( ) {
        return yPos;
    }

    public int getModifiedStartYPos ( ) {
        return (yPos * 64) ;
    }

    public position clone ( ) {
        return new position ( characterValue , intValue );
    }

    public position setNewPosition ( charValue charValue , int intValue ) {
        this.characterValue = charValue;
        this.intValue = intValue;
        this.xPos = charValue.getIndex ( );
        this.yPos = 8 - intValue;
        return this;
    }

    public String toString ( ) {
        return characterValue.toString ( ) + intValue;
    }
}