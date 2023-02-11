package MyChess;

import java.util.List;

public interface movements {
    List < List<position> > getPossibleMoves ( );

    void move ( position p );
}
