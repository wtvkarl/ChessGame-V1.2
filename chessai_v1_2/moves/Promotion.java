/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.moves;

import com.krlv.source.chessai_v1_2.InputHandler;
import com.krlv.source.chessai_v1_2.board.Bishop;
import com.krlv.source.chessai_v1_2.board.ChessBoard;
import com.krlv.source.chessai_v1_2.board.Knight;
import com.krlv.source.chessai_v1_2.board.Piece;
import com.krlv.source.chessai_v1_2.board.Queen;
import com.krlv.source.chessai_v1_2.board.Rook;
import com.krlv.source.chessai_v1_2.board.Square;
import com.krlv.source.chessai_v1_2.gfx.UI;

/**
 *
 * @author 3095515
 */
public class Promotion extends Move{
    
    private Piece startPiece, targetPiece;
    private Square targetSquare;
    private Square[] board;
    private MoveType type;
    private InputHandler ih;
        
    public Promotion(Square start, Square target, Square[] board){
        super(start, target, board);
        targetSquare = target;
        startPiece = start.getPiece();
        targetPiece = target.getPiece();
        this.type = MoveType.PROMOTION;
        this.board = board;
        ih = ChessBoard.getInputHandler();
    }
    
    //when executing a promotion, the pawn must be in the 
    //opposite side of the board, and can promote to any piece 
    //but a king.
    
    @Override 
    public void execute(){
        System.out.println("promoting");
        super.execute(); //move pawn to the row
        promote();
    }
    
    private void promote(){
        int targetIndex = targetPiece.getIndex();
        Piece selection = null;
        
        UI.setPromotionMenuBounds(targetSquare);
        UI.drawPromotionButtons();
        do{
            String pieceSelection = UI.getPromotionSelection();
            switch(pieceSelection){
                case "knight" -> selection = new Knight(targetIndex, startPiece.getColor());
                case "bishop" -> selection = new Bishop(targetIndex, startPiece.getColor());
                case "rook" -> selection = new Rook(targetIndex, startPiece.getColor());
                case "queen" -> selection = new Queen(targetIndex, startPiece.getColor());
            }
            System.out.println(pieceSelection);
        }while(selection == null);
        
        UI.hidePromotionButtons();
        UI.resetPromotionSelection();
        
        board[targetIndex].setPiece(selection);
        
    }
}
