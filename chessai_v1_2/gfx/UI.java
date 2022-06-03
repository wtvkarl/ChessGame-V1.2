/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.gfx;

import com.krlv.source.chessai_v1_2.Display;
import com.krlv.source.chessai_v1_2.InputHandler;
import com.krlv.source.chessai_v1_2.board.ChessBoard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import com.krlv.source.chessai_v1_2.board.Square;
import com.krlv.source.chessai_v1_2.board.Piece;

/**
 *
 * @author 3095515
 */
public class UI implements ActionListener{

    private Display display;
    private InputHandler ih;
    private static String promotionSelection = "";
    
    private static BufferedImage[] whitePieces, blackPieces;
    private static Button knight, bishop, rook, queen;
    private static Button[] buttonMenu;
    
    private static final int squareSize = 80;
    
    
    public UI(Display d, InputHandler ihHandler) {
        display = d;
        ih = ihHandler;
        initializePromotionButtons();
    }
    
    private void initializePromotionButtons(){
        whitePieces = ImageLoader.getWhitePieceImages();
        blackPieces = ImageLoader.getBlackPieceImages();
        
        knight = new Button(whitePieces[1], "whiteknight");
        bishop = new Button(whitePieces[2], "whitebishop");
        rook = new Button(whitePieces[3], "whiterook");
        queen = new Button(whitePieces[4], "whitequeen");
        
        buttonMenu = new Button[4];
        
        buttonMenu[0] = knight;
        buttonMenu[1] = bishop;
        buttonMenu[2] = rook;
        buttonMenu[3] = queen;
        
        for(Button button : buttonMenu){
            display.add(button);
            button.addActionListener(this);
        }
        
        knight.setActionCommand("knight");
        bishop.setActionCommand("bishop");
        rook.setActionCommand("rook");
        queen.setActionCommand("queen");
    }
    
    //-----pawn promotion ui-----
    public static String getPromotionSelection(){
        return promotionSelection;
    }
    
    public static void resetPromotionSelection(){
        promotionSelection = "";
    }
    
    public static void drawPromotionButtons(){
        for(Button button : buttonMenu){
            button.setVisible(true);
        }
    }
    
    public static void hidePromotionButtons(){
        for(Button button : buttonMenu){
            button.setVisible(false);
        }
    }
    
    public static void setPromotionMenuBounds(Square square){
        setButtonIcons(ChessBoard.getDraggedPiece());
        
        int menuX = squareSize + (square.getCol() * squareSize);
        int menuY = squareSize + (square.getRow() * squareSize);
        
        for(int i = 0; i < buttonMenu.length; i++){
            if(menuY > 400) //bottom of screen, draw menu upwards
                buttonMenu[i].setBounds(menuX, menuY - (squareSize * i), squareSize, squareSize);
            else //towards top of screen, draw menu downwards
                buttonMenu[i].setBounds(menuX, menuY + (squareSize * i), squareSize, squareSize);
        }
    }
    
    public static void setButtonIcons(Piece piece){
        if(piece.isWhite()){
            for(int i = 0; i < buttonMenu.length; i++){
                buttonMenu[i].setImage(whitePieces[i+1]);
            }
        }
        else{
            for(int i = 0; i < buttonMenu.length; i++){
                buttonMenu[i].setImage(blackPieces[i+1]);
            }
        }
        
    }
    //-----pawn promotion ui-----
     

    @Override
    public void actionPerformed(ActionEvent ae) {
        promotionSelection = ae.getActionCommand();
    }
}
