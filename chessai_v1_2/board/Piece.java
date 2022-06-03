/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

import com.krlv.source.chessai_v1_2.moves.Move;
import com.krlv.source.chessai_v1_2.gfx.ImageLoader;
import com.krlv.source.chessai_v1_2.maps.PieceProtectionMap;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public abstract class Piece {
    
    public ArrayList<Move> validMoves;
    public int row, col, index, pieceType, timesMoved, spacesJustMoved;
    public char fenNotation;
    public String color, name;
    BufferedImage image;
       
    public abstract ArrayList<Move> getValidMoves(Square[] board);
    public abstract ArrayList<Square> getAttackSquares(Square[] board);
    public abstract ArrayList<Piece> getConnectedPieces(Square[] board);
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public char getFenNotation(){
        return fenNotation;
    }
    
    public int getType(){
        return pieceType;
    }
    
    public boolean hasMoved(){
        return timesMoved > 0;
    }
    
    public int getTimesMoved(){
        return timesMoved;
    }
    
    public void setRow(int newRow){
        row = newRow;
    }
    
    public void setCol(int newCol){
        col = newCol;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void setIndex(int newIndex){
        index = newIndex;
        row = newIndex / 8;
        col = newIndex % 8;
    }
    
    public String getName(){
        return name;
    }
    
    public String getColor(){
        return color;
    }
    
    public void setImage(){
        image = ImageLoader.getImage(this);
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public boolean isFriendly(Piece piece){
        return this.color.equals(piece.getColor());
    }
    
    public boolean isWhite(){
        return color.equals("white");
    }
    
    public boolean isBlack(){
        return color.equals("black");
    }
    
    public boolean isColor(String color){
        return this.color.equals(color);
    }
    
    public boolean isBlank(){
        return name.equals("blank");
    }
    
    public boolean isKing(){
        return name.equals("king");
    }
    
    public boolean isInBoard(int in){
        return in >=0 && in <= 63;
    }
    
    public boolean isPawn(){
        return name.equals("pawn");
    }
    
    public boolean isKnight(){
        return name.equals("knight");
    }
    
    public boolean isBishop(){
        return name.equals("bishop");
    }
    
    public boolean isRook(){
        return name.equals("rook");
    }
    
    public boolean isQueen(){
        return name.equals("queen");
    }
    
    public boolean isProtected(){
        return isWhite() ? 
                PieceProtectionMap.getProtectedWhitePieces().contains(this) : 
                PieceProtectionMap.getProtectedBlackPieces().contains(this) ;
    }
    
    @Override 
    public String toString(){
        return String.format("%s%s @ [%d,%d]", getColor(), getName(), getRow(), getCol());
    }
    
}
