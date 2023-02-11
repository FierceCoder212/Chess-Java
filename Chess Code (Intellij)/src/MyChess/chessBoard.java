package MyChess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class chessBoard {
    Image[] chessPieceImages;
    chessPiece checkedPiece;
    private List < List < position > > possibleMovesList;
    private List < List < position > > validMovesList;
    private List < position > killList;
    private JPanel panel;
    private JFrame frame;
    private chessPiece[] whitePieces;
    private chessPiece[] blackPieces;
    private chessPiece selectedPiece;
    private boolean isPlayer1Turn;
    private boolean canCastle;

    public chessBoard ( ) {
        initializeInstances ( );
        initializePieces ( );
        setFrame ( );
    }

    private void initializeInstances ( ) {
        whitePieces = new chessPiece[ 16 ];
        blackPieces = new chessPiece[ 16 ];
        chessPieceImages = new Image[ 12 ];
        possibleMovesList = new ArrayList <> ( );
        validMovesList = new ArrayList <> ( );
        killList = new ArrayList <> ( );
        selectedPiece = null;
        isPlayer1Turn = true;
        canCastle = false;
        checkedPiece = null;
    }

    private void initializePieces ( ) {
        try {
            BufferedImage imageFile = ImageIO.read ( new File ( "chess.png" ) );
            int imageIndex = 0;
            for ( int y = 0; y < 400; y += 200 )
                for ( int x = 0; x < 1200; x += 200 )
                    chessPieceImages[ imageIndex++ ] = imageFile.getSubimage ( x , y , 200 , 200 ).getScaledInstance ( box.WIDTH , box.HEIGHT , BufferedImage.SCALE_SMOOTH );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
        {
            int blackIndex = 0;
            int whiteIndex = 0;
            int blackImage = 6;
            int whiteImage = 0;
            for ( piece chessPiece : piece.values ( ) ) {
                for ( position chessPosition : chessPiece.getBlackPositions ( ) )
                    blackPieces[ blackIndex++ ] = (get_Class ( chessPiece.toString ( ) , chessPosition , chessPieceImages[ blackImage ] , Color.BLACK ));
                for ( position chessPosition : chessPiece.getWhitePositions ( ) )
                    whitePieces[ whiteIndex++ ] = (get_Class ( chessPiece.toString ( ) , chessPosition , chessPieceImages[ whiteImage ] , Color.WHITE ));
                blackImage++;
                whiteImage++;
            }
        }
    }

    private void setFrame ( ) {
        frame = new JFrame ( "ANONYMOUS CHESS" );
        frame.setBounds ( 10 , 10 , 512 , 512 );
        panel = setJPanel ( );
        frame.add ( panel );
        setJFrameMouseControl ( );
        frame.setUndecorated ( true );
        frame.setResizable ( false );
        frame.setDefaultCloseOperation ( EXIT_ON_CLOSE );
        frame.setVisible ( true );
    }

    private chessPiece get_Class ( String pieceName , position piecePosition , Image pieceImage , Color pieceColor ) {
        switch ( pieceName ) {
            case "KING":
                return new king ( pieceName , piecePosition , pieceImage , pieceColor );
            case "QUEEN":
                return new queen ( pieceName , piecePosition , pieceImage , pieceColor );
            case "BISHOP":
                return new bishop ( pieceName , piecePosition , pieceImage , pieceColor );
            case "KNIGHT":
                return new knight ( pieceName , piecePosition , pieceImage , pieceColor );
            case "ROOK":
                return new rook ( pieceName , piecePosition , pieceImage , pieceColor );
            case "PAWN":
                return new pawn ( pieceName , piecePosition , pieceImage , pieceColor );
        }
        return null;
    }

    private JPanel setJPanel ( ) {
        return new JPanel ( ) {
            public void paint ( Graphics graphics ) {
                boolean isWhiteBox = true;
                for ( int yCoordinate = 0; yCoordinate < 8; yCoordinate++ ) {
                    for ( int xCoordinate = 0; xCoordinate < 8; xCoordinate++ ) {
                        if ( isWhiteBox )
                            graphics.setColor ( Color.WHITE.darker ( ) );
                        else
                            graphics.setColor ( Color.DARK_GRAY.brighter ( ) );
                        graphics.fillRect ( xCoordinate * box.WIDTH , yCoordinate * box.HEIGHT , box.WIDTH , box.HEIGHT );
                        isWhiteBox = ! isWhiteBox;
                    }
                    isWhiteBox = ! isWhiteBox;
                }
                paintPieceSelectionAndMoves ( graphics );
                paintAllPieces ( graphics );
            }
        };
    }

    private void setJFrameMouseControl ( ) {
        frame.addMouseListener ( new MouseListener ( ) {
            @Override
            public void mouseClicked ( MouseEvent event ) {
                mouseActionPerformed ( event );
            }

            @Override
            public void mousePressed ( MouseEvent e ) {
            }

            @Override
            public void mouseReleased ( MouseEvent e ) {
            }

            @Override
            public void mouseEntered ( MouseEvent e ) {
            }

            @Override
            public void mouseExited ( MouseEvent e ) {
            }
        } );
    }

    private void paintPieceSelectionAndMoves ( Graphics graphics ) {
        if ( selectedPiece == null )
            return;
        fillRectangle ( graphics , Color.GREEN , Color.BLACK.darker ( ) , selectedPiece.getPosition ( ) );
        chessPiece[] currentPlayerPieces = isPlayer1Turn ? blackPieces : whitePieces;
        for ( List < position > possibleMoveList : possibleMovesList ) {
            boolean pieceCantMove = false;
            List < position > tempPossibleMovesList = new ArrayList <> ( );
            for ( position possibleMove : possibleMoveList ) {
                pieceCantMove = checkPieceMovement ( currentPlayerPieces , possibleMove );
                if ( selectedPiece.getName ( ).equals ( "PAWN" ) )
                    pieceCantMove = checkPieceMovement ( isPlayer1Turn ? whitePieces : blackPieces , possibleMove );
                if ( pieceCantMove )
                    break;
                tempPossibleMovesList.add ( possibleMove );
            }
            if ( tempPossibleMovesList.size ( ) != 0 && pieceCantMove )
                validMovesList.add ( tempPossibleMovesList );
            else if ( ! pieceCantMove )
                validMovesList.add ( possibleMoveList );
        }
        if ( validMovesList.size ( ) != 0 || selectedPiece.getName ( ).equals ( "PAWN" ) ) {
            setKillList ( );
            for ( List < position > validMoveList : validMovesList )
                paintMoves ( graphics , validMoveList , Color.BLUE.brighter ( ) , Color.BLACK.darker ( ) );
            paintMoves ( graphics , killList , Color.RED.brighter ( ) , Color.BLACK.darker ( ) );
        }
    }

    private void paintAllPieces ( Graphics g ) {
        for ( chessPiece piece : blackPieces )
            g.drawImage ( piece.getImage ( ) , piece.getPosition ( ).getModifiedStartXPos ( ) , piece.getPosition ( ).getModifiedStartYPos ( ) , box.WIDTH , box.HEIGHT , panel );
        for ( chessPiece piece : whitePieces )
            g.drawImage ( piece.getImage ( ) , piece.getPosition ( ).getModifiedStartXPos ( ) , piece.getPosition ( ).getModifiedStartYPos ( ) , box.WIDTH , box.HEIGHT , panel );
    }

    private void setNewPiece ( String[] pieceName , chessPiece[] chessPieces , int imageIndex ) {
        int indexToSet = 0;
        for ( int i = 1; i < chessPieces.length; i++ ) {
            if ( selectedPiece.getPosition ( ).toString ( ).equals ( chessPieces[ i ].getPosition ( ).toString ( ) ) ) {
                indexToSet = i;
                break;
            }
        }
        chessPieces[ indexToSet ] = get_Class ( pieceName[ 0 ] , selectedPiece.getPosition ( ) , chessPieceImages[ imageIndex ] , selectedPiece.getColor ( ) );
    }

    private void setPiecePossibleMovements ( MouseEvent event , chessPiece[] pieces ) {
        possibleMovesList.clear ( );
        position mouseInBoxPosition = getMouseInBoxPosition ( event );
        boolean pieceFound = false;
        for ( chessPiece piece : pieces ) {
            if ( piece.getPosition ( ).toString ( ).equals ( mouseInBoxPosition.toString ( ) ) ) {
                if ( selectedPiece != null && (selectedPiece.getPosition ( ) == piece.getPosition ( )) )
                    selectedPiece = null;
                else {
                    selectedPiece = piece;
                    if ( selectedPiece.getName ( ).equals ( "KING" ) )
                        canCastle = setCastleMove ( );
                    else
                        canCastle = false;
                    pieceFound = true;
                }
                break;
            }
        }
        if ( ! pieceFound )
            selectedPiece = null;
        else if ( selectedPiece != null )
            possibleMovesList.addAll ( selectedPiece.getPossibleMoves ( ) );
    }

    private void paintMoves ( Graphics graphics , List < position > moveList , Color fillerColor , Color strokeColor ) {
        for ( position p : moveList )
            fillRectangle ( graphics , fillerColor , strokeColor , p );
    }

    private void fillRectangle ( Graphics graphics , Color fillerColor , Color strokeColor , position drawPosition ) {
        graphics.setColor ( fillerColor );
        graphics.fillRect ( drawPosition.getModifiedStartXPos ( ) , drawPosition.getModifiedStartYPos ( ) , box.WIDTH , box.HEIGHT );
        graphics.setColor ( strokeColor );
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke ( new BasicStroke ( 1.0f , BasicStroke.CAP_BUTT , BasicStroke.JOIN_ROUND , 10.0f , new float[]{10.0f} , 0.0f ) );
        graphics2D.draw ( new RoundRectangle2D.Double ( drawPosition.getModifiedStartXPos ( ) , drawPosition.getModifiedStartYPos ( ) , box.HEIGHT , box.WIDTH , 10 , 10 ) );
    }


    private position getMouseInBoxPosition ( MouseEvent event ) {
        return new position ( event.getX ( ) , event.getY ( ) );
    }

    private void setKillList ( ) {
        chessPiece[] opponentPieces = isPlayer1Turn ? whitePieces : blackPieces;
        if ( ! selectedPiece.getName ( ).equals ( "PAWN" ) ) {
            for ( List < position > validMoveList : validMovesList ) {
                boolean canKillOpponent = false;
                int noValidMovesRemovingIndex = 0;
                for ( position validMove : validMoveList ) {
                    for ( chessPiece opponentPiece : opponentPieces )
                        if ( opponentPiece.getPosition ( ).toString ( ).equals ( validMove.toString ( ) ) ) {
                            killList.add ( validMove );
                            for ( int nextIndexes = noValidMovesRemovingIndex + 1; nextIndexes < validMoveList.size ( ); )
                                validMoveList.remove ( nextIndexes );
                            canKillOpponent = true;
                            break;
                        }
                    if ( canKillOpponent )
                        break;
                    noValidMovesRemovingIndex++;
                }
            }
        } else {
            List < List < position > > pawnKillsList = ((pawn) selectedPiece).captureKillMoves ( );
            for ( List < position > pawnKillList : pawnKillsList )
                for ( position killPosition : pawnKillList )
                    for ( chessPiece opponentPiece : opponentPieces )
                        if ( killPosition.toString ( ).equals ( opponentPiece.getPosition ( ).toString ( ) ) ) {
                            killList.add ( killPosition );
                            validMovesList.add ( pawnKillList );
                        }
        }
    }

    private chessPiece[] captureOpponentPiece ( chessPiece[] piece , int indexToCapture ) {
        if ( piece[ indexToCapture ].getName ( ).equals ( "KING" ) ) {
            frame.setVisible ( false );
            JOptionPane.showMessageDialog ( null , isPlayer1Turn ? "PLAYER 1 WON" : "PLAYER 2 WON" );
            System.exit ( 0 );
        }
        chessPiece[] newOpponentPieces = new chessPiece[ piece.length - 1 ];
        for ( int i = 0, j = 0; i < newOpponentPieces.length; j++ ) {
            if ( indexToCapture != j )
                newOpponentPieces[ i++ ] = piece[ j ];
        }
        return newOpponentPieces;
    }

    private void killOpponentPiece ( position p ) {
        for ( position killPosition : killList ) {
            if ( p.toString ( ).equals ( killPosition.toString ( ) ) ) {
                boolean foundKillPosition = false;
                int pieceRemoverIndex = 0;
                chessPiece[] opponentPieces = (isPlayer1Turn ? whitePieces : blackPieces);
                for ( chessPiece opponentPiece : opponentPieces ) {
                    if ( opponentPiece.getPosition ( ).toString ( ).equals ( p.toString ( ) ) ) {
                        foundKillPosition = true;
                        break;
                    }
                    pieceRemoverIndex++;
                }
                if ( foundKillPosition ) {
                    if ( isPlayer1Turn )
                        whitePieces = captureOpponentPiece ( opponentPieces , pieceRemoverIndex );
                    else
                        blackPieces = captureOpponentPiece ( blackPieces , pieceRemoverIndex );
                    break;
                }
            }
        }
    }

    private void pawnConversion ( ) {
        if ( selectedPiece.getName ( ).equals ( "PAWN" ) ) {
            final int[] imageIndex = {0};
            if ( selectedPiece.getPosition ( ).getIntValue ( ) == 8 || selectedPiece.getPosition ( ).getIntValue ( ) == 1 ) {
                Object[] buttons = {new JButton ( "QUEEN" ) , new JButton ( "BISHOP" ) , new JButton ( "KNIGHT" ) , new JButton ( "ROOK" )};
                final String[] newPieceName = {""};
                JDialog dialogBox = new JDialog ( frame , "Select One Piece" , true );
                dialogBox.setBounds ( 10 , 10 , 500 , 150 );
                dialogBox.setLayout ( null );
                frame.setVisible ( false );
                for ( Object button : buttons )
                    ((JButton) button).addMouseListener ( new MouseAdapter ( ) {
                        @Override
                        public void mouseClicked ( MouseEvent e ) {
                            newPieceName[ 0 ] = ((JButton) button).getText ( );
                            switch ( newPieceName[ 0 ] ) {
                                case "ROOK":
                                    imageIndex[ 0 ]++;
                                case "KNIGHT":
                                    imageIndex[ 0 ]++;
                                case "BISHOP":
                                    imageIndex[ 0 ]++;
                                case "QUEEN":
                                    imageIndex[ 0 ]++;
                            }
                            dialogBox.setVisible ( false );
                            frame.setVisible ( true );
                        }
                    } );
                int buttonXCoordinate = 20;
                for ( Object button : buttons ) {
                    ((JButton) button).setVisible ( true );
                    ((JButton) button).setBounds ( buttonXCoordinate , 50 , 100 , 20 );
                    dialogBox.add ( ((JButton) button) );
                    buttonXCoordinate += 110;
                }
                dialogBox.setDefaultCloseOperation ( DO_NOTHING_ON_CLOSE );
                dialogBox.setVisible ( true );
                if ( isPlayer1Turn )
                    setNewPiece ( newPieceName , blackPieces , imageIndex[ 0 ] + 6 );
                else
                    setNewPiece ( newPieceName , whitePieces , imageIndex[ 0 ] );
            }
        }
    }

    private void movePiece ( position newPosition ) {
        killOpponentPiece ( newPosition );
        selectedPiece.move ( newPosition );
        performCastleMove ( newPosition );
        selectedPiece.isMoved ( true );
        pawnConversion ( );
        isPlayer1Turn = ! isPlayer1Turn;
        selectedPiece = null;
        frame.repaint ( );
        killList.clear ( );
        validMovesList.clear ( );
        possibleMovesList.clear ( );
    }

    private void mouseActionPerformed ( MouseEvent event ) {
        position mouseInBoxPosition = getMouseInBoxPosition ( event );
        if ( selectedPiece != null ) {
            for ( List < position > validMoveList : validMovesList )
                for ( position validMove : validMoveList )
                    if ( validMove.toString ( ).equals ( mouseInBoxPosition.toString ( ) ) ) {
                        movePiece ( validMove );
                        return;
                    }
        }
        killList.clear ( );
        validMovesList.clear ( );
        possibleMovesList.clear ( );
        setPiecePossibleMovements ( event , isPlayer1Turn ? blackPieces : whitePieces );
        frame.repaint ( );
    }

    private boolean checkPieceMovement ( chessPiece[] chessPieces , position possiblePosition ) {
        for ( chessPiece currentPlayerPiece : chessPieces )
            if ( possiblePosition.toString ( ).equals ( currentPlayerPiece.getPosition ( ).toString ( ) ) )
                return true;
        return false;
    }

    private boolean setCastleMove ( ) {
        chessPiece[] playerPieces = isPlayer1Turn ? blackPieces : whitePieces;
        List < chessPiece > rooks = new ArrayList <> ( );
        chessPiece king = playerPieces[ 0 ];
        if ( king.isMoved ( ) )
            return false;
        List < List < position > > allPossibleMovements = new ArrayList <> ( );
        for ( chessPiece playerPiece : playerPieces )
            if ( playerPiece.getName ( ).equals ( "ROOK" ) )
                if ( ! playerPiece.isMoved ( ) ) {
                    rooks.add ( playerPiece );
                    if ( playerPiece.getPosition ( ).getCharacterValue ( ).getCharacter ( ) > 'E' )
                        allPossibleMovements.add ( ((king) king).getCastleMoves ( ).get ( 0 ) );
                    else
                        allPossibleMovements.add ( ((king) king).getCastleMoves ( ).get ( 1 ) );
                }
        if ( rooks.size ( ) == 0 )
            return false;
        chessPiece[] opponentPieces = isPlayer1Turn ? whitePieces : blackPieces;
        for ( int i = 0; i < rooks.size ( ); ) {
            int valueToAdd = 1;
            boolean isInRight = true;
            if ( rooks.get ( i ).getPosition ( ).getCharacterValue ( ).getCharacter ( ) < 'E' ) {
                valueToAdd *= - 1;
                isInRight = false;
            }
            boolean pieceExists = true;
            if ( ! doesPieceExist ( rooks , king , playerPieces , i , valueToAdd , king.getPosition ( ).clone ( ) ) )
                if ( ! doesPieceExist ( rooks , king , opponentPieces , i , valueToAdd , king.getPosition ( ).clone ( ) ) )
                    pieceExists = ++ i <= 0;
            if ( pieceExists ) {
                rooks.remove ( i );
                if ( isInRight )
                    allPossibleMovements.remove ( allPossibleMovements.size ( ) > 1 ? 1 : 0 );
                else
                    allPossibleMovements.remove ( 0 );
            }
        }
        if ( rooks.size ( ) == 0 )
            return false;
        this.validMovesList.addAll ( allPossibleMovements );
        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean doesPieceExist ( List < chessPiece > rooks , chessPiece king , chessPiece[] opponentPieces , int i , int valueToAdd , position kingPositionClone ) {
        position p = kingPositionClone.setNewPosition ( charValue.values ( )[ kingPositionClone.getCharacterValue ( ).getIndex ( ) + valueToAdd ] , kingPositionClone.getIntValue ( ) );
        while ( ! p.toString ( ).equals ( "A1" ) && ! p.toString ( ).equals ( "H1" ) && ! p.toString ( ).equals ( "A8" ) && ! p.toString ( ).equals ( "H8" ) ) {
            for ( chessPiece playerPiece : opponentPieces )
                if ( ! playerPiece.getPosition ( ).toString ( ).equals ( king.getPosition ( ).toString ( ) ) )
                    if ( ! rooks.get ( i ).getPosition ( ).toString ( ).equals ( playerPiece.getPosition ( ).toString ( ) ) )
                        if ( p.toString ( ).equals ( playerPiece.getPosition ( ).toString ( ) ) )
                            return true;
            p = kingPositionClone.setNewPosition ( charValue.values ( )[ kingPositionClone.getCharacterValue ( ).getIndex ( ) + valueToAdd ] , kingPositionClone.getIntValue ( ) );
        }
        return false;
    }

    private void performCastleMove ( position p ) {
        if ( canCastle ) {
            if ( p.getCharacterValue ( ).getCharacter ( ) == 'G' ) {
                chessPiece[] playerPieces = isPlayer1Turn ? blackPieces : whitePieces;
                for ( chessPiece playerPiece : playerPieces ) {
                    if ( playerPiece.getName ( ).equals ( "ROOK" ) ) {
                        if ( playerPiece.getPosition ( ).getCharacterValue ( ).getCharacter ( ) == 'H' ) {
                            playerPiece.getPosition ( ).setNewPosition ( charValue.F , isPlayer1Turn ? 8 : 1 );
                            break;
                        }
                    }
                }
            } else if ( p.getCharacterValue ( ).getCharacter ( ) == 'C' ) {
                chessPiece[] playerPieces = isPlayer1Turn ? blackPieces : whitePieces;
                for ( chessPiece playerPiece : playerPieces ) {
                    if ( playerPiece.getName ( ).equals ( "ROOK" ) ) {
                        if ( playerPiece.getPosition ( ).getCharacterValue ( ).getCharacter ( ) == 'A' ) {
                            playerPiece.getPosition ( ).setNewPosition ( charValue.D , isPlayer1Turn ? 8 : 1 );
                            break;
                        }
                    }
                }
            }
        }
    }

}
