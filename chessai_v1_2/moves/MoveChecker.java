/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.moves;

import com.krlv.source.chessai_v1_2.board.ChessBoard;
import com.krlv.source.chessai_v1_2.board.Piece;
import com.krlv.source.chessai_v1_2.board.Square;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class MoveChecker {
    
    public static boolean isValid(Move move, ArrayList<Move> validMoves){
        return validMoves.contains(move);
    }

    public static Move getMove(Square startSquare, Square targetSquare, ArrayList<Move> validMoves) {
        for(Move move : validMoves){
            if((move.getStartSquare() == startSquare) && 
               (move.getTargetSquare() == targetSquare))
            {
                return move;
            } 
        }
        return null;
    }

    public static ArrayList<Square> getWhiteAttackSquares() {
        ArrayList<Piece> whitePieces = ChessBoard.getWhitePieces();
        ArrayList<Square> enemySquares = new ArrayList();
        for(Piece piece : whitePieces){
            enemySquares.addAll(piece.getAttackSquares(ChessBoard.getBoard()));
        }
        return enemySquares;
    }

    public static ArrayList<Square> getBlackAttackSquares() {
        ArrayList<Piece> blackPieces = ChessBoard.getBlackPieces();
        ArrayList<Square> enemySquares = new ArrayList();
        for(Piece piece : blackPieces){
            enemySquares.addAll(piece.getAttackSquares(ChessBoard.getBoard()));
        }
        return enemySquares;
    }
    
}
