/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

import com.krlv.source.chessai_v1_2.moves.Castle;
import com.krlv.source.chessai_v1_2.moves.Move;
import com.krlv.source.chessai_v1_2.moves.MoveChecker;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class King extends Piece{
    
    private int[] directionOffsets;
    private ArrayList<Square> enemySquares;
    private static boolean isInCheck;
    
    public King(int index, String color) {
        pieceType = 6;
        this.name = "king";
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
        this.color = color;
        fenNotation = (color.equals("white")) ? 'K' : 'k';
        directionOffsets = ChessBoard.getDirectionOffsets();
        this.timesMoved = 0;
        setImage();
        this.validMoves = new ArrayList();
    }

    @Override
    public ArrayList<Move> getValidMoves(Square[] board) {     
        enemySquares = (isWhite()) ? 
                MoveChecker.getBlackAttackSquares() : 
                MoveChecker.getWhiteAttackSquares() ;
        
        validMoves.clear();
        int directionOffsetStartIndex = 0; 
        Square start = board[getIndex()];
        for(int i = directionOffsetStartIndex; i < directionOffsets.length; i++){
            //having this distance to edge method means we wont have any out of bounds errors :)
            //ALSO THIS METHOD IS A LITERAL TIMESAVER HOLY MACKEREL THANKS SEBASTIAN LAGUE
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            int offset = directionOffsets[i];
            
            for(int dist = 1; dist <= distToEdge; dist++){
                int targetIndex = getIndex() + (dist * offset);
                Square target = board[targetIndex];
                Piece targetPiece = target.getPiece();
                if(!enemySquares.contains(target)){
                    if(targetPiece.isBlank()){
                        validMoves.add(new Move(start, target, board));
                    }
                    else{
                        if(!targetPiece.isFriendly(this) && !targetPiece.isProtected())
                            validMoves.add(new Move(start, target, board));
                    }
                }
                break; //since we can only move one square as a king
            }
            
            //move onto the next direction
        }
        
        if(canCastleKingSide()){
            Square rookSquare = isWhite() ? board[63] : board[7];
            validMoves.add(new Castle(board[getIndex()], rookSquare, board, "kingside"));
        }
        if(canCastleQueenside()){
            Square rookSquare = isWhite() ? board[56]: board[0];
            validMoves.add(new Castle(board[getIndex()], rookSquare, board, "queenside"));
        }
        
        return validMoves;
    }
    
    @Override
    public ArrayList<Square> getAttackSquares(Square[] board) {
        ArrayList<Square> attackSquares = new ArrayList();
        int directionOffsetStartIndex = 0; 
        for(int i = directionOffsetStartIndex; i < directionOffsets.length; i++){
            int offset = directionOffsets[i];
            
            int targetIndex = getIndex() + offset;
            if(isInBoard(targetIndex))
                attackSquares.add(board[targetIndex]);
            
            //since we can only move one square as a king
            //move onto the next direction
        }
        return attackSquares;
    }

    @Override
    public ArrayList<Piece> getConnectedPieces(Square[] board) {
        ArrayList<Piece> pieces = new ArrayList();
        int directionOffsetStartIndex = 0; 
        
        for(int i = directionOffsetStartIndex; i < directionOffsets.length; i++){
            int offset = directionOffsets[i];
            int targetIndex = getIndex() + offset;
            
            if(isInBoard(targetIndex)){
                Square target = board[targetIndex];
                Piece targetPiece = target.getPiece();

                if(targetPiece.isFriendly(this)){
                    pieces.add(targetPiece);
                }
            }
        }
        
        return pieces;
    }
    
    private boolean canCastleQueenside(){
        //if the king is check, we cannot castle
        if(isInCheck()){
            return false;
        }
        
        //if the king has already moved prior, we cannot castle
        if(getTimesMoved() > 0)
            return false;
        
        Square[] board = ChessBoard.getBoard();
        Piece piece = (isWhite()) ?
                board[56].getPiece() : board[0].getPiece();
        //if the piece where the rook should be is not a rook 
        //or if the rook has moved already, we cannot castle.
        if(!piece.isRook() || piece.getTimesMoved() > 0)
            return false;
        
        //there should be no pieces in the way or else we cannot castle
        //the squares in between the rook and king should not be under attack as well
        if(isWhite()){
            if( (!board[57].isBlank() || enemySquares.contains(board[57]) ) ||
                (!board[58].isBlank() || enemySquares.contains(board[58]) ) ||
                (!board[59].isBlank() || enemySquares.contains(board[59]) ))
            {
                return false;
            }    
        }
        else{
            if( (!board[1].isBlank() || enemySquares.contains(board[1]) ) ||
                (!board[2].isBlank() || enemySquares.contains(board[2]) ) ||
                (!board[3].isBlank() || enemySquares.contains(board[3]) ))
            {
                return false;
            }   
        }
        
        
        return true;
    }
    
    private boolean canCastleKingSide(){
        if(isInCheck()){
            return false;
        }
        
        if(getTimesMoved() > 0)
            return false;
        
        Square[] board = ChessBoard.getBoard();
        Piece piece = (isWhite()) ?
                board[63].getPiece() : board[7].getPiece();
        //if the piece where the rook should be is not a rook 
        //or if the rook has moved already, we cannot castle.
        if(!piece.isRook() || piece.getTimesMoved() > 0)
            return false;
        
        //there should be no pieces in the way or else we cannot castle
        //the squares in between the rook and king should not be under attack as well
        if(isWhite()){
            if( (!board[61].isBlank() || enemySquares.contains(board[61]) ) ||
                (!board[62].isBlank() || enemySquares.contains(board[62]) ))
            {
                return false;
            }    
        }
        else{
            if( (!board[5].isBlank() || enemySquares.contains(board[5]) ) ||
                (!board[6].isBlank() || enemySquares.contains(board[6]) ))
            {
                return false;
            } 
        }
        
        
        return true;
    }
    
    public boolean isInCheck(){
        return enemySquares.contains(ChessBoard.getBoard()[getIndex()]);
    }
    
}
