package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class knight extends chessPiece {
    public knight ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    @Override
    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > allPossiblePositions = new ArrayList <> ( );
        for ( movesList name : movesList.values ( ) )
            for ( furtherMove moveName : furtherMove.values ( ) )
                captureAllMoves ( name , moveName , allPossiblePositions );
        return allPossiblePositions;
    }

    private void captureAllMoves ( movesList name , furtherMove moveName , List < List < position > > allPossiblePositions ) {
        List < position > nextPossiblePositions = new ArrayList <> ( );
        switch ( name ) {
            case UPPER_HALF:
                if ( getPosition ( ).getIntValue ( ) < 7 ) {
                    switch ( moveName ) {
                        case FIRST:
                            if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'H' )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 1 ] , getPosition ( ).getIntValue ( ) + 2 ) );
                            break;
                        case SECOND:
                            if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'A' )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 1 ] , getPosition ( ).getIntValue ( ) + 2 ) );
                    }
                }
                break;
            case LOWER_HALF:
                if ( getPosition ( ).getIntValue ( ) > 2 ) {
                    switch ( moveName ) {
                        case FIRST:
                            if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'H' )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 1 ] , getPosition ( ).getIntValue ( ) - 2 ) );
                            break;
                        case SECOND:
                            if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'A' )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 1 ] , getPosition ( ).getIntValue ( ) - 2 ) );
                    }
                }
                break;
            case RIGHT_HALF:

                if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'G' ) {
                    switch ( moveName ) {
                        case FIRST:
                            if ( getPosition ( ).getIntValue ( ) < 8 )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 2 ] , getPosition ( ).getIntValue ( ) + 1 ) );
                            break;
                        case SECOND:
                            if ( getPosition ( ).getIntValue ( ) > 1 )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) + 2 ] , getPosition ( ).getIntValue ( ) - 1 ) );
                    }
                }
                break;
            case LEFT_HALF:
                if ( getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'B' ) {
                    switch ( moveName ) {
                        case FIRST:
                            if ( getPosition ( ).getIntValue ( ) < 8 )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 2 ] , getPosition ( ).getIntValue ( ) + 1 ) );
                            break;
                        case SECOND:
                            if ( getPosition ( ).getIntValue ( ) > 1 )
                                nextPossiblePositions.add ( getPosition ( ).clone ( ).setNewPosition ( charValue.values ( )[ getPosition ( ).getCharacterValue ( ).getIndex ( ) - 2 ] , getPosition ( ).getIntValue ( ) - 1 ) );
                    }
                }
        }
        if ( nextPossiblePositions.size ( ) != 0 )
            allPossiblePositions.add ( nextPossiblePositions );
    }

    private enum movesList {
        UPPER_HALF,
        LOWER_HALF,
        RIGHT_HALF,
        LEFT_HALF
    }

    private enum furtherMove {
        FIRST,
        SECOND
    }
}
