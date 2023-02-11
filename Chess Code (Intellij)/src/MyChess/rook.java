package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class rook extends chessPiece {

    public rook ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    @Override
    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > allPossiblePositions = new ArrayList <> ( );
        for ( movesList moves : movesList.values ( ) )
            captureAllMoves ( moves , allPossiblePositions );
        return allPossiblePositions;
    }

    private void captureAllMoves ( movesList name , List < List < position > > allPossiblePositions ) {
        List < position > nextPossiblePositions = new ArrayList <> ( );
        switch ( name ) {
            case UP:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) < 8; )
                    nextPossiblePositions.add ( p.setNewPosition ( p.getCharacterValue ( ) , p.getIntValue ( ) + 1 ).clone ( ) );
                break;
            case DOWN:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) > 1; )
                    nextPossiblePositions.add ( p.setNewPosition ( p.getCharacterValue ( ) , p.getIntValue ( ) - 1 ).clone ( ) );
                break;
            case RIGHT:
                for ( position p = getPosition ( ).clone ( ); p.getCharacterValue ( ).getCharacter ( ) < 'H'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) + 1 ] , p.getIntValue ( ) ).clone ( ) );
                break;
            case LEFT:
                for ( position p = getPosition ( ).clone ( ); p.getCharacterValue ( ).getCharacter ( ) > 'A'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) - 1 ] , p.getIntValue ( ) ).clone ( ) );
        }
        if ( nextPossiblePositions.size ( ) != 0 )
            allPossiblePositions.add ( nextPossiblePositions );
    }

    private enum movesList {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
