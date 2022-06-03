/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.moves;

import com.krlv.source.chessai_v1_2.board.Blank;
import com.krlv.source.chessai_v1_2.board.Piece;
import com.krlv.source.chessai_v1_2.board.Square;

/**
 *
 * @author 3095515
 */
public class Move {
    private MoveType type;
    private Square startSquare, targetSquare;
    private Piece startPiece, targetPiece;
    private Square[] board;
    
    public Move(Square start, Square target, Square[] board){
        startSquare = start;
        targetSquare = target;
        startPiece = start.getPiece();
        targetPiece = target.getPiece();
        type = (isCapture()) ? MoveType.CAPTURE : MoveType.REGULAR;
        this.board = board;
    }
    
    public void execute(){
        int startIndex = startPiece.getIndex();
        int targetIndex = targetPiece.getIndex();
        board[startIndex].setPiece(new Blank(startIndex));
        startPiece.setIndex(targetIndex);
        startPiece.spacesJustMoved = squaresMoved(startSquare, targetSquare);
        board[targetIndex].setPiece(startPiece);
        startPiece.timesMoved++;
    }
    
    public int squaresMoved(Square start, Square target){
        if(start.getPiece().getName().equals("knight"))
            return 3;
        return  (start.getCol() == target.getCol()) ?
                Math.abs(start.getRow() - target.getRow()) : 
                Math.abs(start.getCol() - target.getCol());
    }
    
    public Square getStartSquare(){
        return startSquare;
    }
    
    public Square getTargetSquare(){
        return targetSquare;
    }
    
    public boolean isCapture(){
        return !startPiece.isFriendly(targetPiece) && !targetPiece.isBlank();
    }
    
    public MoveType getType(){
        return type;
    }
    
    @Override
    public String toString(){
        return startSquare.getIndex() + "->" + targetSquare.getIndex();
    }
    
}
