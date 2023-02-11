package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class bishop extends chessPiece {
    public bishop ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    @Override
    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > allPossiblePositions = new ArrayList <> ( );
        for ( movesList name : movesList.values ( ) )
            captureAllMoves ( name , allPossiblePositions );
        return allPossiblePositions;
    }

    private void captureAllMoves ( movesList name , List < List < position > > allPossiblePositions ) {
        List < position > nextPossiblePositions = new ArrayList <> ( );
        switch ( name ) {
            case RIGHT_UPPER_DIAGONAL:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) < 8 && p.getCharacterValue ( ).getCharacter ( ) < 'H'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) + 1 ] , p.getIntValue ( ) + 1 ).clone ( ) );
                break;
            case LEFT_UPPER_DIAGONAL:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) < 8 && p.getCharacterValue ( ).getCharacter ( ) > 'A'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) - 1 ] , p.getIntValue ( ) + 1 ).clone ( ) );
                break;
            case LEFT_LOWER_DIAGONAL:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) > 1 && p.getCharacterValue ( ).getCharacter ( ) > 'A'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) - 1 ] , p.getIntValue ( ) - 1 ).clone ( ) );
                break;
            case RIGHT_LOWER_DIAGONAL:
                for ( position p = getPosition ( ).clone ( ); p.getIntValue ( ) > 1 && p.getCharacterValue ( ).getCharacter ( ) < 'H'; )
                    nextPossiblePositions.add ( p.setNewPosition ( charValue.values ( )[ p.getCharacterValue ( ).getIndex ( ) + 1 ] , p.getIntValue ( ) - 1 ).clone ( ) );
        }
        if ( nextPossiblePositions.size ( ) != 0 )
            allPossiblePositions.add ( nextPossiblePositions );
    }

    private enum movesList {
        RIGHT_UPPER_DIAGONAL,
        LEFT_UPPER_DIAGONAL,
        LEFT_LOWER_DIAGONAL,
        RIGHT_LOWER_DIAGONAL
    }

}
