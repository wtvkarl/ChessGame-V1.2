/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.maps;

import com.krlv.source.chessai_v1_2.board.ChessBoard;
import com.krlv.source.chessai_v1_2.board.Piece;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class PieceProtectionMap {
    
    private static ArrayList<Piece> protectedWhitePieces = new ArrayList();
    private static ArrayList<Piece> protectedBlackPieces = new ArrayList();
    
    public static ArrayList<Piece> calculateProtectedWhitePieces(){
        protectedWhitePieces.clear();
        
        ArrayList<Piece> whitePieces = ChessBoard.getWhitePieces();
        for(Piece piece : whitePieces){
            for(Piece protectedPiece : piece.getConnectedPieces(ChessBoard.getBoard())){
                if(!protectedWhitePieces.contains(protectedPiece))
                    protectedWhitePieces.add(protectedPiece);
            }
        }
        
        return protectedWhitePieces;
    }
    
    public static ArrayList<Piece> calculateProtectedBlackPieces(){
        protectedBlackPieces.clear();
        
        ArrayList<Piece> blackPieces = ChessBoard.getBlackPieces();
        
        for(Piece piece : blackPieces){
            for(Piece protectedPiece : piece.getConnectedPieces(ChessBoard.getBoard())){
                if(!protectedBlackPieces.contains(protectedPiece))
                    protectedBlackPieces.add(protectedPiece);
            }
        }
        
        return protectedBlackPieces;
    }
    
    public static void update(){
        protectedWhitePieces = calculateProtectedWhitePieces();
        protectedBlackPieces = calculateProtectedBlackPieces();
    }
    
    public static ArrayList<Piece> getProtectedWhitePieces(){
        return protectedWhitePieces;
    }
    
    public static ArrayList<Piece> getProtectedBlackPieces(){
        return protectedBlackPieces;
    }
    
}
