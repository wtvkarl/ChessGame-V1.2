/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

import com.krlv.source.chessai_v1_2.Display;
import com.krlv.source.chessai_v1_2.GameState;
import com.krlv.source.chessai_v1_2.InputHandler;
import com.krlv.source.chessai_v1_2.moves.Move;
import com.krlv.source.chessai_v1_2.moves.MoveChecker;
import com.krlv.source.chessai_v1_2.gfx.ImageLoader;
import com.krlv.source.chessai_v1_2.maps.PieceProtectionMap;
import com.krlv.source.chessai_v1_2.moves.MoveType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class ChessBoard {
    
    private static InputHandler ihHandler;
    
    private static final int rows = 8;
    private static final int cols = 8;
    private static final int boardHeight = 800;
    private static final int boardWidth = 800;
    private static final int boardOffset = 80;
    private static final int squareSize = 80;
    
    private static Square[] board;
    private static Piece draggedPiece;
    private static Piece previousPiece;
    private static Move previousMove;
    private static ArrayList<Move> validMoves;
    
    private static GameState gameState;
    
    private static final String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static String currentFEN;
    
    private static final int[] directionOffsets = {-8, 8, -1, 1, -9, -7, 7, 9};
    private static int[][] distancesToEdge;
    
    private static ArrayList<Piece> whitePieces, blackPieces;
    
    public ChessBoard(InputHandler ih){
        ihHandler = ih;
        board = new Square[64];
        currentFEN = startFEN;
        whitePieces = new ArrayList();
        blackPieces = new ArrayList();
        String[] fenSplices = currentFEN.split(" ");
        gameState = new GameState();
        initializeSquaresOnBoard();
        initializePieces(fenSplices[0]);
        updateGameState(fenSplices);
        initializeEdgeDistanceArray();
    }
    
    private void initializeSquaresOnBoard(){
        for(int i = 0; i < board.length; i++){
            board[i] = new Square(i);
        }
    }
    
    private void initializePieces(String fen){
        char[] chars = fen.toCharArray();
        int boardIndex = 0;
        for(int i = 0; i < chars.length; i++){
            char ch = chars[i];
            if(ch == ' ') //we finished initializing pieces
                break;
            if(Character.isLetter(ch)){
                if(Character.isUpperCase(ch)){
                    switch(ch){
                        case 'P' -> board[boardIndex].setPiece(new Pawn(boardIndex, "white"));
                        case 'N' -> board[boardIndex].setPiece(new Knight(boardIndex, "white"));
                        case 'B' -> board[boardIndex].setPiece(new Bishop(boardIndex, "white"));
                        case 'R' -> board[boardIndex].setPiece(new Rook(boardIndex, "white"));
                        case 'Q' -> board[boardIndex].setPiece(new Queen(boardIndex, "white"));
                        case 'K' -> board[boardIndex].setPiece(new King(boardIndex, "white"));
                    }
                }
                else if(Character.isLowerCase(ch)){
                    switch(ch){
                        case 'p' -> board[boardIndex].setPiece(new Pawn(boardIndex, "black"));
                        case 'n' -> board[boardIndex].setPiece(new Knight(boardIndex, "black"));
                        case 'b' -> board[boardIndex].setPiece(new Bishop(boardIndex, "black"));
                        case 'r' -> board[boardIndex].setPiece(new Rook(boardIndex, "black"));
                        case 'q' -> board[boardIndex].setPiece(new Queen(boardIndex, "black"));
                        case 'k' -> board[boardIndex].setPiece(new King(boardIndex, "black"));
                    }
                }
            }
            else if(ch == '/'){
                continue;
            }
            else if(Character.isDigit(ch)){
                int blanks = Integer.parseInt(ch + "");
                while(blanks > 0){
                    board[boardIndex].setPiece(new Blank(boardIndex));
                    blanks--;
                    boardIndex++;
                }
                continue;
            }
            
            boardIndex++;
        }
        
        
        
    }
    
    //first [] stores the index of the square board
    //second [] stores the distance to an edge in each cardinal direction
    //based this part of code off of Sebastian Lague's Chess AI written in C#
    private void initializeEdgeDistanceArray(){
        distancesToEdge = new int[board.length][directionOffsets.length];
        for(int i = 0; i < board.length; i++){
            int row = i / 8;
            int col = i % 8;
            int squareIndex = row * 8 + col;
            
            int distNorth = row;
            int distSouth = 7 - row;
            int distWest = col;
            int distEast = 7 - col;
            int distNorthWest = Math.min(distNorth, distWest);
            int distNorthEast = Math.min(distNorth, distEast);
            int distSouthWest = Math.min(distSouth, distWest);
            int distSouthEast = Math.min(distSouth, distEast);
            
            int[] distances = {
                distNorth,
                distSouth,
                distWest,
                distEast,
                distNorthWest,
                distNorthEast,
                distSouthWest,
                distSouthEast
            };
            
            distancesToEdge[squareIndex] = distances;
        }
        
    }  
    
    private void updateGameState(String[] fens){
        //fens[0] is the piece orientation
        gameState.addPosition(board);
        //gameState.setTurn(fens[1]); 
        gameState.setCastlingRights(fens[2]);
        //fens[3] is actually enpassant used, but we already implemented it so we ignore this for now
        gameState.setNumHalfMoves(fens[4]);
        gameState.setMoveNum(fens[5]);
    }
    
    public GameState getGameState(){
        return gameState;
    }
    
    public static ArrayList<Piece> getWhitePieces() {
        whitePieces.clear();
        for(Square square : board){
            if(square.getPiece().isWhite())
                whitePieces.add(square.getPiece());
        }
        return whitePieces;
    }
    
    public static ArrayList<Piece> getBlackPieces() {
        blackPieces.clear();
        for(Square square : board){
            if(square.getPiece().isBlack())
                blackPieces.add(square.getPiece());
        }
        return blackPieces;
    }

    public static Square[] getBoard(){
        return board;
    }
    
    public static InputHandler getInputHandler(){
        return ihHandler;
    }
    
    public static int[] getDirectionOffsets(){
        return directionOffsets;
    }
    
    public static Piece getDraggedPiece(){
        return draggedPiece;
    }
    
    public static int getDistanceToEdge(int pieceIndex, int direction){
        //first four directions are the 4 cardinal directions
        //last four directions are the 4 intermediate directions
        
        return distancesToEdge[pieceIndex][direction];
    }
    
    public boolean isInBoard(int x, int y){
        return (x >= boardOffset && x <= boardOffset * (rows+1) ) &&
               (y >= boardOffset && y <= boardOffset * (cols+1) );
    }
    
    public boolean isInBoard(int index){
        return index >= 0 && index <= 63;
    }
    
    public Square getSquare(int mouseX, int mouseY){
        int row =  (mouseY - boardOffset) / squareSize;
        int col = (mouseX - boardOffset) / squareSize;
        int index = row * 8 + col;
        if(isInBoard(index))
            return board[index];
        return null;
    }
    
    public static Piece getPreviousPiece(){
        return previousPiece;
    }
    
    //keep move execution time under a couple thousanths of a second
    public void update(){
        if(ihHandler.isPressed() && isInBoard(ihHandler.getX(), ihHandler.getY())){
            if(draggedPiece == null){
                Square selectedSquare = getSquare(ihHandler.getX(), ihHandler.getY());
                Piece piece = selectedSquare.getPiece();
                
                if(gameState.isTurn(piece.getColor())){
                    if(!piece.isBlank() && piece != null){
                        draggedPiece = piece;
                        validMoves = piece.getValidMoves(board);
                    }
                }
            }
        }
        else if(!ihHandler.isPressed()){
            if(draggedPiece != null){
                if(isInBoard(ihHandler.getX(), ihHandler.getY())){
                    Square startSquare = board[draggedPiece.getIndex()];
                    Square targetSquare = getSquare(ihHandler.getX(), ihHandler.getY());
                    if(targetSquare != null){
                        long start = System.nanoTime();
                        previousMove = MoveChecker.getMove(startSquare, targetSquare, validMoves);
                        //since we are checking in validMoves, a null move means it is invalid
                        if(startSquare != targetSquare && previousMove != null) {
                            previousMove.execute();
                            previousPiece = draggedPiece;
                            PieceProtectionMap.update();
                            
                            gameState.next();
                            currentFEN = gameState.addPosition(board);
                            updateGameState(currentFEN.split(" "));
                                    
                            System.out.println(gameState); //prints the current position
                        }
                        System.out.println("Move Excecution Time: " + ((System.nanoTime() - start) / 1000000000.0) + " s");
                    }
                }
                draggedPiece = null;
                validMoves = null;
            }
        }
    }
    
    public void draw(Graphics2D g2d){
        drawBackground(g2d);
        drawBoard(g2d);
        drawValidMoves(g2d);
        drawPieces(g2d);
        drawDraggedPiece(g2d);
    }
    
    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(ImageLoader.getBackground(), 0, 0, Display.width, Display.height, null);
    }
    
    private void drawBoard(Graphics2D g2d){
        for(int i = 0; i < board.length; i++){
            int row = board[i].getRow();
            int col = board[i].getCol();
            
            int drawX = boardOffset + (col * squareSize);
            int drawY = boardOffset + (row * squareSize);
            
            boolean isWhiteSquare = (row + col) % 2 == 0;
            Color squareColor = (isWhiteSquare) ? Color.white : Color.darkGray;
            g2d.setColor(squareColor);
            
            g2d.fillRect(drawX, drawY, squareSize, squareSize);
        }
    }
    
    private void drawPieces(Graphics2D g2d){
        for(Square square : board){
            int row = square.getRow();
            int col = square.getCol();
            int drawX = boardOffset + (col * squareSize);
            int drawY = (boardOffset + (row * squareSize));
            Piece piece = square.getPiece();
            //blank squares' images are null, skipping them saves drawing time
            if(!piece.equals(draggedPiece) && piece.getImage() != null)
                g2d.drawImage(piece.getImage(), drawX, drawY, null);
        }
    }
    
    private void drawDraggedPiece(Graphics2D g2d){
        if(draggedPiece != null){
            int drawX = ihHandler.getX() - squareSize/2;
            int drawY = ihHandler.getY() - squareSize/2;
            g2d.drawImage(draggedPiece.getImage(), drawX, drawY, null);
        }
    }
    
    private void drawValidMoves(Graphics2D g2d){
        if(draggedPiece == null)
            return;
        
        int originX = boardOffset + (draggedPiece.getCol() * squareSize); 
        int originY = boardOffset + (draggedPiece.getRow() * squareSize); 
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(originX + 5, originY + 5, squareSize - 10, squareSize - 10);
        
        for(Move move : validMoves){
            Square target = move.getTargetSquare();
            int row = target.getRow();
            int col = target.getCol();
            int drawX = boardOffset + (col * squareSize);
            int drawY = boardOffset + (row * squareSize);
            Color color = getColorByMove(move);
            g2d.setColor(color);
            g2d.fillRect(drawX + 5, drawY + 5, squareSize - 10, squareSize - 10);
        }
    }
    
    private Color getColorByMove(Move move){
        return switch (move.getType()) {
            case CAPTURE -> Color.red;
            case ENPASSANT -> Color.red;
            case REGULAR -> Color.yellow;
            case CASTLING -> Color.yellow;
            default -> Color.lightGray;
        };
    }
    
    @Override
    public String toString(){
        String str = "";
        for(Square s : board){
            if(s.getIndex() % 8 == 0)
                str += "\n";
            str += s.getPiece().getFenNotation() + " ";
        }
        return str;
    }
    
}
