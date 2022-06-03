/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.maps;

import com.krlv.source.chessai_v1_2.board.ChessBoard;
import com.krlv.source.chessai_v1_2.board.Piece;
import com.krlv.source.chessai_v1_2.board.Square;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class PawnAttackMap {
    
    ArrayList<Piece> pawns;
    ArrayList<Square> attackSquares;
    String color;
    
    public PawnAttackMap(String color){
        this.color = color;
        this.attackSquares = new ArrayList();
        pawns = getPawns(ChessBoard.getBoard());
    }
    
    private ArrayList<Piece> getPawns(Square[] board){
        pawns = new ArrayList();
        for(Square square : board){
            if(square.getPiece().isPawn() && square.getPiece().isColor(this.color))
                pawns.add(square.getPiece());
        }
        return pawns;
    }
    
    public void update(){
        pawns = getPawns(ChessBoard.getBoard());
        attackSquares = getAttackSquares();
    }
    
    public ArrayList<Square> getAttackSquares(){
        attackSquares.clear();
        for(Piece pawn : pawns){
            attackSquares.addAll(pawn.getAttackSquares(ChessBoard.getBoard()));
        }
        return attackSquares;
    }
    
}
