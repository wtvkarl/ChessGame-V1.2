/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.moves;

import com.krlv.source.chessai_v1_2.board.Blank;
import com.krlv.source.chessai_v1_2.board.King;
import com.krlv.source.chessai_v1_2.board.Piece;
import com.krlv.source.chessai_v1_2.board.Square;

/**
 *
 * @author 3095515
 */
public class Castle extends Move{
    
    private Piece king;
    private Piece rook;
    private MoveType type;
    private Square rookSquare, kingSquare;
    private Square newKingSquare, newRookSquare;
    private int newKingIndex, newRookIndex;
    private Square[] board;
    
    public Castle(Square kingSquare, Square rookSquare, Square[] board, String side){
        super(kingSquare, rookSquare, board);
        type = MoveType.CASTLING;
        this.board = board;
        this.kingSquare = kingSquare;
        this.rookSquare = rookSquare;
        this.king = kingSquare.getPiece();
        this.rook = rookSquare.getPiece();
        
        newKingIndex = (side.equals("kingside")) ? king.getIndex() + 2 : king.getIndex() - 2;
        newKingSquare = board[newKingIndex];
        newRookIndex = (side.equals("kingside")) ? newKingIndex - 1 : newKingIndex + 1;
        newRookSquare = board[newRookIndex];
    }
    
    @Override
    public void execute(){
        kingSquare.setPiece(new Blank(kingSquare.getIndex()));
        rookSquare.setPiece(new Blank(rookSquare.getIndex()));
        newKingSquare.setPiece(king);
        king.setIndex(newKingIndex);
        newRookSquare.setPiece(rook);
        rook.setIndex(newRookIndex);
        king.timesMoved++;
        rook.timesMoved++;
    }    
    
    @Override
    public Square getTargetSquare(){
        return newKingSquare;
    }
}
