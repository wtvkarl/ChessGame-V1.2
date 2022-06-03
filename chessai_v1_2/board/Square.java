/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

/**
 *
 * @author 3095515
 */
public class Square {

    private int index;
    private int row, col;
    private Piece piece;
    
    
    public Square(int index) {
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getRow(){
        return row;
    }
    
    public void setIndex(int index){
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }
    
    public int getIndex(){
        return index;
    }
    
    public Piece getPiece(){
        return piece;
    }
    
    public void setPiece(Piece newPiece){
        this.piece = newPiece;
    }
    
    public boolean isBlank(){
        return piece.isBlank();
    }
    
    public boolean isInBoard(){
        return index >=0 && index <= 63;
    }
    
    @Override 
    public String toString(){
        return String.format("Piece: %s @ index %d\n", getPiece(), getIndex());
    }
    
}
