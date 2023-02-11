package MyChess;

import java.util.ArrayList;

public enum piece {
    KING ( new position[]{new position ( charValue.E , 8 )} ),
    QUEEN ( new position[]{new position ( charValue.D , 8 )} ),
    BISHOP ( new position[]{new position ( charValue.C , 8 ) , new position ( charValue.F , 8 )} ),
    KNIGHT ( new position[]{new position ( charValue.B , 8 ) , new position ( charValue.G , 8 )} ),
    ROOK ( new position[]{new position ( charValue.A , 8 ) , new position ( charValue.H , 8 )} ),
    PAWN ( );
    private final position[] positions;

    piece ( position[] positions ) {
        this.positions = positions;
    }

    piece ( ) {
        this.positions = pawnPositions ( );
    }

    private position[] pawnPositions ( ) {
        position[] positions = new position[ 8 ];
        for ( int i = 1; i <= 8; i++ )
            positions[ i - 1 ] = new position ( charValue.values ( )[ i - 1 ] , 7 );
        return positions;
    }

    public position[] getBlackPositions ( ) {
        return positions;
    }

    public position[] getWhitePositions ( ) {
        ArrayList < position > positions = new ArrayList <> ( );
        for ( position position : this.positions )
            positions.add ( position.clone ( ).setNewPosition ( position.getCharacterValue ( ) , 9 - position.getIntValue ( ) ) );
        position[] position = new position[ positions.size ( ) ];
        return positions.toArray ( position );
    }
}
