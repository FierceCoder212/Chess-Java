package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class queen extends chessPiece {

    public queen ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    @Override
    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > tempList = new ArrayList <> ( );
        tempList.addAll ( new bishop ( getName () , getPosition ( ) , getImage () , getColor ( ) ).getPossibleMoves ( ) );
        tempList.addAll ( new rook ( getName () , getPosition ( ) , getImage () , getColor ( ) ).getPossibleMoves ( ) );
        return tempList;
    }
}
