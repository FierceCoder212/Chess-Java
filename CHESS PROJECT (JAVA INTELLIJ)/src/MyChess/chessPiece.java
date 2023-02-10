package MyChess;

import java.awt.*;

public abstract class chessPiece implements movements {
    private final Image image;
    private final Color color;
    private final String name;
    private boolean isMoved;
    private position position;

    public chessPiece ( String name , position position , Image image , Color color ) {
        this.name = name;
        this.color = color;
        this.isMoved = false;
        this.position = position;
        this.image = image;
    }

    public boolean isMoved ( ) {
        return isMoved;
    }

    public void isMoved ( boolean isMoved ) {
        this.isMoved = isMoved;
    }

    public position getPosition ( ) {
        return position;
    }

    public void move ( position position ) {
        this.position = position;
    }

    public Color getColor ( ) {
        return color;
    }

    public Image getImage ( ) {
        return image;
    }

    public String getName ( ) {
        return name;
    }
}
