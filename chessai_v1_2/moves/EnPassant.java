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
public class EnPassant extends Move{
    
    private Square[] board;
    private MoveType type;
    private Piece startPiece, targetPiece;
    
    //YESS I FINALLY DECIDED TO USE OOP IN JAVA LIKE IM SUPPOSED TOO HOORAY
    public EnPassant(Square start, Square target, Square[] board){
        super(start, target, board);
        type = MoveType.ENPASSANT;
        startPiece = start.getPiece();
        targetPiece = target.getPiece();
        this.board = board;
    }
    
    @Override
    public void execute(){
        super.execute();
        int color = (startPiece.isWhite()) ? 1 : -1; 
        int targetIndex = targetPiece.getIndex();
        int enemyIndex = targetIndex + (8 * color);
        
        //we execute the move as regular, and then just set the pawn behind 
        //as a blank piece, effectively "capturing" it
        
        board[enemyIndex].setPiece(new Blank(enemyIndex));
    }
    
    @Override
    public MoveType getType(){
        return type;
    }
    
    @Override
    public boolean isCapture(){
        //kinda awkward to have this but it works,
        //enpassants are capture moves
        return true;
    }
    
}
