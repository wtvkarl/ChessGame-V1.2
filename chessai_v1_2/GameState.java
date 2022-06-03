/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2;

import com.krlv.source.chessai_v1_2.board.Square;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class GameState {
    
    private String turn, castlingRights;
    private int move, halfMoves;
    private ArrayList<String> fenHistory;
    
    public GameState(){
        move = 0;
        halfMoves = 1;
        turn = "white";
        castlingRights = "KQkq";
        fenHistory = new ArrayList();
    }
    
    public void next(){
        turn = (isWhitesTurn()) ? "black" : "white";
        if(isWhitesTurn())
            move++;
    }
    
    public String getTurn(){
        return turn;
    }
    
    public void setTurn(String str){
        turn = str;
    }
    
    public void setCastlingRights(String str){
        castlingRights = str;   
    }
    
    public boolean isWhitesTurn(){
        return turn.equals("white");
    }
    
    public boolean isBlacksTurn(){
        return turn.equals("black");
    }
    
    public boolean isTurn(String color){
        return turn.equals(color);
    }
    
    public void setMoveNum(String fen) {
        move = Integer.parseInt(fen);
    }
    
    public int getMoveNum(){
        return move;
    }
    
    public int getNumHalfMoves(){
        return halfMoves;
    }
    
    public void setNumHalfMoves(String str){
        halfMoves = Integer.parseInt(str);
    }
    
    public String addPosition(Square[] board){
        String fen = "";
        int blanks = 0; //should not exceed 8
        for(int i = 0; i < board.length; i++){
            if(i % 8 == 0 && i > 0)
                fen += "/";
            
            Square square = board[i];
            
            char pieceFEN = square.getPiece().getFenNotation();
            if(pieceFEN == '-'){ //this is a blank piece
                blanks++;
                if(blanks == 8){
                    fen+= blanks;
                    blanks = 0;
                }
            }
            else{
                if(blanks > 0){
                    fen += blanks;
                    blanks = 0;
                }
                fen += pieceFEN;
            }
            
        }
        
        //turn
        fen += " " + turn;
        //castlingRights
        fen += " " + castlingRights;
        //enpassant
        fen += " " + "-";
        //halfmoves
        fen += " " + Integer.toString(halfMoves);
        //move
        fen += " " + Integer.toString(move);
        
        fenHistory.add(fen);
        return fen;
    }
    
    public void updateCastlingRights(){
        
    }
    
    public ArrayList<String> getFenHistory(){
        return fenHistory;
    }
    
    @Override
    public String toString(){
        String fen = getFenHistory().get(fenHistory.size()-1);
        return String.format("Color to Move: %s | Move: %d\nFEN: %s", getTurn(), getMoveNum(), fen);
    }
    
}
