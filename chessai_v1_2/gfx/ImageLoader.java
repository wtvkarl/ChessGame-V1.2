/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.gfx;

import com.krlv.source.chessai_v1_2.board.Piece;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author 3095515
 */
public class ImageLoader {
    
    private static BufferedImage[] whitePieces;
    private static BufferedImage[] blackPieces;
    private static BufferedImage background;
    
    private final String bPawnFilePath = "res/images/blackPieces/blackpawn.png";
    private final String bKnightFilePath = "res/images/blackPieces/blackknight.png";
    private final String bBishopFilePath = "res/images/blackPieces/blackbishop.png";
    private final String bRookFilePath = "res/images/blackPieces/blackrook.png";
    private final String bQueenFilePath = "res/images/blackPieces/blackqueen.png";
    private final String bKingFilePath = "res/images/blackPieces/blackking.png";
   
    private final String wPawnFilePath = "res/images/whitePieces/whitepawn.png";
    private final String wKnightFilePath = "res/images/whitePieces/whiteknight.png";
    private final String wBishopFilePath = "res/images/whitePieces/whitebishop.png";
    private final String wRookFilePath = "res/images/whitePieces/whiterook.png";
    private final String wQueenFilePath = "res/images/whitePieces/whitequeen.png";
    private final String wKingFilePath = "res/images/whitePieces/whiteking.png";
    
    private final String backgroundFilePath = "res/images/background/woodbackground.jpg";
    
    public ImageLoader(){
        this.whitePieces = new BufferedImage[6];
        this.blackPieces = new BufferedImage[6];
    }
    
    public void preloadAssets(){
        try{
            whitePieces[0] = ImageIO.read(new File(wPawnFilePath));
            whitePieces[1] = ImageIO.read(new File(wKnightFilePath));
            whitePieces[2] = ImageIO.read(new File(wBishopFilePath));
            whitePieces[3] = ImageIO.read(new File(wRookFilePath));
            whitePieces[4] = ImageIO.read(new File(wQueenFilePath));
            whitePieces[5] = ImageIO.read(new File(wKingFilePath));

            blackPieces[0] = ImageIO.read(new File(bPawnFilePath));
            blackPieces[1] = ImageIO.read(new File(bKnightFilePath));
            blackPieces[2] = ImageIO.read(new File(bBishopFilePath));
            blackPieces[3] = ImageIO.read(new File(bRookFilePath));
            blackPieces[4] = ImageIO.read(new File(bQueenFilePath));
            blackPieces[5] = ImageIO.read(new File(bKingFilePath));

            background = ImageIO.read(new File(backgroundFilePath));
        }
        catch(IOException e){}
        
    }
    
    public static BufferedImage getImage(Piece piece){
        int pieceType = piece.getType() - 1; //minus one for array bounds
        return (piece.isWhite()) ? whitePieces[pieceType] : blackPieces[pieceType];
    }
    
    public static BufferedImage getBackground(){
        return background;
    }
    
    public static BufferedImage[] getWhitePieceImages() {
        return whitePieces;
    }

    public static BufferedImage[] getBlackPieceImages() {
        return blackPieces;
    }
    
}
