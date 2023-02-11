package MyChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class pawn extends chessPiece {
    public pawn ( String name , position position , Image image , Color color ) {
        super ( name , position , image , color );
    }

    public List < List < position > > getPossibleMoves ( ) {
        List < List < position > > allPossiblePositions = new ArrayList <> ( );
        captureAllMoves ( ! isMoved ( ) ? 1 : 2 , allPossiblePositions , getPosition ( ).clone ( ) );
        return allPossiblePositions;
    }

    private void captureAllMoves ( int isMoved , List < List < position > > allPossiblePositions , position currentPos ) {
        List < position > nextPossiblePositions = new ArrayList <> ( );
        switch ( isMoved ) {
            case 1:
                nextPossiblePositions.add ( currentPos.setNewPosition ( currentPos.getCharacterValue ( ) , currentPos.getIntValue ( ) + (getColor ( ) == Color.WHITE ? 1 : - 1) ) );
            case 2:
                nextPossiblePositions.add ( currentPos.clone ( ).setNewPosition ( currentPos.getCharacterValue ( ) , currentPos.getIntValue ( ) + (getColor ( ) == Color.WHITE ? 1 : - 1) ) );
                break;
        }
        if ( nextPossiblePositions.size ( ) != 0 )
            allPossiblePositions.add ( nextPossiblePositions );
    }

    public List < List < position > > captureKillMoves ( ) {
        List < List <position> > allKillList = new ArrayList <> ( );
        List<position> killList = new ArrayList <> (  );
        position currentPosition = getPosition ( ).clone ( );
        if ( currentPosition.getCharacterValue ( ).getCharacter ( ) < 'H' && currentPosition.getIntValue ( ) < 8 && currentPosition.getIntValue ( ) > 1 )
            killList.add ( currentPosition.clone ().setNewPosition ( charValue.values ( )[ currentPosition.getXPos ( ) + 1 ] , currentPosition.getIntValue ( ) + (getColor ( ) == Color.WHITE ? 1 : - 1) ) );
        allKillList.add ( killList );
        killList = new ArrayList <> (  );
        if ( currentPosition.getCharacterValue ( ).getCharacter ( ) > 'A' && currentPosition.getIntValue ( ) < 8 && currentPosition.getIntValue ( ) > 1 )
            killList.add ( currentPosition.clone ().setNewPosition ( charValue.values ( )[ currentPosition.getXPos ( ) - 1 ] , currentPosition.getIntValue ( ) + (getColor ( ) == Color.WHITE ? 1 : - 1) ) );
        allKillList.add ( killList );
        return allKillList;
    }

}
