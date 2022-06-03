/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

import com.krlv.source.chessai_v1_2.moves.Move;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Knight extends Piece{

    private static final int[] jumpIndecies = {-15, -6, 10, 17, 15, 6, -10, -17};
    
    public Knight(int index, String color) {
        pieceType = 2;
        this.name = "knight";
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
        this.color = color;
        fenNotation = (color.equals("white")) ? 'N' : 'n';
        this.timesMoved = 0;
        setImage();
        this.validMoves = new ArrayList();
    }

    @Override
    public ArrayList<Move> getValidMoves(Square[] board) {
        validMoves.clear();
        for(Integer in : jumpIndecies){
            int pseudoIndex = getIndex() + in;
            if(isInBoard(pseudoIndex)){
                Square start = board[getIndex()];
                Square target = board[pseudoIndex];
                if(target.isBlank() || !target.getPiece().isFriendly(this)){
                    if(Math.abs(target.getCol() - start.getCol()) <= 2)
                        validMoves.add(new Move(start, target, board));
                }
            }
        }
        
        return validMoves;
    }
    
    @Override
    public ArrayList<Square> getAttackSquares(Square[] board) {
        ArrayList<Square> attackSquares = new ArrayList();
        ArrayList<Move> moves = getValidMoves(board);
        
        for(Move move : moves){
            attackSquares.add(move.getTargetSquare());
        }
        return attackSquares;
    }

    @Override
    public ArrayList<Piece> getConnectedPieces(Square[] board) {
        ArrayList<Piece> pieces = new ArrayList();
        for(Integer in : jumpIndecies){
            int pseudoIndex = getIndex() + in;
            if(isInBoard(pseudoIndex)){
                Square start = board[getIndex()];
                Square target = board[pseudoIndex];
                if(target.getPiece().isFriendly(this)){
                    if(Math.abs(target.getCol() - start.getCol()) <= 2)
                        pieces.add(target.getPiece());
                }
            }
        }
        return pieces;
    }
}
