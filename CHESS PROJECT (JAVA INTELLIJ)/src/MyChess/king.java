package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class king extends chessPiece {
    public king ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    @Override
    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > allPossiblePositions = new ArrayList <> ( );
        for ( movesList name : movesList.values ( ) )
            captureAllMoves ( name , allPossiblePositions );
        return allPossiblePositions;
    }

    public List<List<position>> getCastleMoves(){
        List<List<position>>allPossibleCastleMoves = new ArrayList <> (  );
        List<position> possiblePosition = new ArrayList <> (  );
        possiblePosition.add ( new position ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 2 ] ,getPosition ().getIntValue ()));
        allPossibleCastleMoves.add ( possiblePosition );
        possiblePosition = new ArrayList <> (  );
        possiblePosition.add ( new position ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 2 ] ,getPosition ().getIntValue ()));
        allPossibleCastleMoves.add ( possiblePosition );
        return allPossibleCastleMoves;
    }

    private void captureAllMoves ( movesList name , List < List < position > > allPossiblePositions ) {
        List < position > nextPossiblePositions = new ArrayList <> ( );
        switch ( name ) {
            case RIGHT_UPPER_DIAGONAL:
                if ( getPosition ( ).getIntValue ( ) < 8 && getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'H' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 1 ] , getPosition ( ).getIntValue ( ) + 1 ) );
                break;
            case LEFT_UPPER_DIAGONAL:
                if ( getPosition ( ).getIntValue ( ) < 8 && getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'A' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 1 ] , getPosition ( ).getIntValue ( ) + 1 ) );
                break;
            case LEFT_LOWER_DIAGONAL:
                if ( getPosition ( ).getIntValue ( ) > 1 && getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'A' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 1 ] , getPosition ( ).getIntValue ( ) - 1 ) );
                break;
            case RIGHT_LOWER_DIAGONAL:
                if ( getPosition ( ).getIntValue ( ) > 1 && getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'H' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 1 ] , getPosition ( ).getIntValue ( ) - 1 ) );
                break;
            case UP:
                if ( getPosition ( ).getIntValue ( ) < 8 )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( getPosition ( ).getCharacterValue ( ) , getPosition ( ).getIntValue ( ) + 1 ) );
                break;
            case DOWN:
                if ( getPosition ( ).getIntValue ( ) > 1 )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( getPosition ( ).getCharacterValue ( ) , getPosition ( ).getIntValue ( ) - 1 ) );
                break;
            case LEFT:
                if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'A' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 1 ] , getPosition ( ).getIntValue ( ) ) );
                break;
            case RIGHT:
                if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'H' )
                    nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 1 ] , getPosition ( ).getIntValue ( ) ) );
        }
        if ( nextPossiblePositions.size ( ) != 0 )
            allPossiblePositions.add ( nextPossiblePositions );
    }

    private enum movesList {
        RIGHT_UPPER_DIAGONAL,
        LEFT_UPPER_DIAGONAL,
        LEFT_LOWER_DIAGONAL,
        RIGHT_LOWER_DIAGONAL,
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
