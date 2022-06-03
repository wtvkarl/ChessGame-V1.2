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
public class Blank extends Piece {

    public Blank(int index) {
        pieceType = 0;
        this.name = "blank";
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
        this.color = "blank";
        this.image = null;
        fenNotation = '-';
    }

    @Override
    public ArrayList<Move> getValidMoves(Square[] board) {
        return null;
    } 
    
    @Override
    public ArrayList<Square> getAttackSquares(Square[] board) {
        return new ArrayList();
    }

    @Override
    public ArrayList<Piece> getConnectedPieces(Square[] board) {
        return new ArrayList();
    }
    
}
