/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.board;

import com.krlv.source.chessai_v1_2.moves.EnPassant;
import com.krlv.source.chessai_v1_2.moves.Move;
import com.krlv.source.chessai_v1_2.moves.Promotion;
import java.util.ArrayList;

/**
 *
 * @author 3095515
 */
public class Pawn extends Piece {
    
    public Pawn(int index, String color) {
        pieceType = 1;
        this.name = "pawn";
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
        this.color = color;
        fenNotation = (color.equals("white")) ? 'P' : 'p';
        this.timesMoved = 0;
        setImage();
        this.validMoves = new ArrayList();
    }

    @Override
    public ArrayList<Move> getValidMoves(Square[] board) {
        validMoves.clear();
        //determine how many spaces it can move
        int spaces = hasMoved() ? 1 : 2;
        int color = isWhite() ? -1 : 1; //white pieces go down array, black goes up
        for(int i = 0; i < spaces; i++){
            int pseudoIndex = getIndex() + ((i+1)*8*color);
            if(isInBoard(pseudoIndex)){
                if(board[pseudoIndex].isBlank()){
                    Square start = board[getIndex()];
                    Square target = board[pseudoIndex];
                    if(isAboutToPromote()){
                        validMoves.add(new Promotion(start, target, board));
                    }
                    else
                        validMoves.add(new Move(start, target, board));
                }
                else 
                    break;
            }
            else
                break;
        }
        
        validMoves.addAll(getValidCaptureMoves(board));
        validMoves.addAll(getEnPassantMoves(board));
        System.out.println(validMoves);
        
        return validMoves;
    }
    
    public ArrayList<Move> getValidCaptureMoves(Square[] board){
        ArrayList<Move> captures = new ArrayList();
        Square left = null, right = null;
        int color = isWhite() ? -1 : 1;
        int nextRow = getIndex() + (8 * color);
        if(isInBoard(nextRow)){
            int leftIndex = nextRow - 1;
            int rightIndex = nextRow + 1;
            if(isInBoard(leftIndex)){
                left = board[leftIndex];
            }
            if(isInBoard(rightIndex)){
                right = board[rightIndex];
            }
        }
        
        
        //if the Square is in the board and 
        //the row of the capture Square is the same as the one in front of the pawn
        if(left != null){
            if(isInBoard(left.getIndex()) && nextRow / 8 == left.getRow()){
                Piece piece = left.getPiece();
                if(isAboutToPromote()){ //promotions are prioritized over captures
                    captures.add(new Promotion(board[getIndex()], left, board));
                }
                else if(!piece.isFriendly(this) && !piece.isBlank()){
                    captures.add(new Move(board[getIndex()], left, board));
                }
            }
        }
        if(right != null){
            if(isInBoard(right.getIndex()) && nextRow / 8 == right.getRow()){
                Piece piece = right.getPiece();
                if(isAboutToPromote()){ //promotions are prioritized over captures
                    captures.add(new Promotion(board[getIndex()], right, board));
                }
                else if(!piece.isFriendly(this) && !piece.isBlank()){
                    captures.add(new Move(board[getIndex()], right, board));
                }
            }
        }
        
        return captures;
    }
    
    //methods for pawn attack map
    @Override
    public ArrayList<Square> getAttackSquares(Square[] board){
        ArrayList<Square> attackSquares = new ArrayList();
        
        Square left = null, right = null;
        int color = isWhite() ? -1 : 1;
        int nextRow = getIndex() + (8 * color);
        if(isInBoard(nextRow)){
            int leftIndex = nextRow - 1;
            int rightIndex = nextRow + 1;
            if(isInBoard(leftIndex)){
                left = board[leftIndex];
            }
            if(isInBoard(rightIndex)){
                right = board[rightIndex];
            }
        }
        
        
        //if the Square is in the board and 
        //the row of the capture Square is the same as the one in front of the pawn
        if(left != null){
            if(isInBoard(left.getIndex()) && nextRow / 8 == left.getRow()){
                attackSquares.add(left);
            }
        }
        if(right != null){
            if(isInBoard(right.getIndex()) && nextRow / 8 == right.getRow()){
                attackSquares.add(right);
            }
        }
        
        return attackSquares;
    }
    
    /**FIX CASE WHERE THE PAWN JUST MOVED BUT ONLY MOVED 1 SQUARE.*/
    private ArrayList<Move> getEnPassantMoves(Square[] board){
        Piece previousPiece = ChessBoard.getPreviousPiece();
        if(previousPiece == null)   //if the game just started, there is no previous piece
            return new ArrayList();
        if(!previousPiece.getName().equals("pawn")) //if the previous piece is a pawn
            return new ArrayList(); 
        if(previousPiece.isFriendly(this))  //if the previous piece is a pawn but is friendly
            return new ArrayList();
        
        ArrayList<Move> enPassantMoves = new ArrayList();
        /** rules for enpassant :
         *  the piece that will be taken has to have only just moved
         *  justMoved = piece.timesMoved == 1 && lastMovedPiece = piece.
        */
        Square start = board[getIndex()];
        int leftAdj = getIndex() - 1; //piece to the left of the pawn
        int rightAdj = getIndex() + 1; //piece to the right of the pawn
        int pieceColor = (isWhite()) ? -1 : 1; //direction the pawn moves based off color 
        
        if(isInBoard(leftAdj)){ //if the square is in the board
            Square leftSquare = board[leftAdj];  
            if(previousPiece == leftSquare.getPiece()) //if the previous piece is the pawn we wanna capture
                if(canEnPassant(leftSquare.getPiece())){ 
                    int leftIndex = leftSquare.getIndex() + (8 * pieceColor); //index behind the pawn we wanna take
                    enPassantMoves.add(new EnPassant(start, board[leftIndex], board));
                }
        }
        if(isInBoard(rightAdj)){
            Square rightSquare = board[rightAdj];
            if(previousPiece == rightSquare.getPiece())
                if(canEnPassant(rightSquare.getPiece())){
                    int rightIndex = rightSquare.getIndex() + (8 * pieceColor);
                    enPassantMoves.add(new EnPassant(start, board[rightIndex], board));
                }
        }
        
        return enPassantMoves;
    }
    
    private boolean canEnPassant(Piece target){
         //if the pawn has just moved, we can do enpassant
         //if the pawn only moved one square and we land next to it,
         //we cannot perform enpassant since the pawn MUST move 2 spaces
         //in its first move
        if(target.timesMoved == 1 && !target.isFriendly(this)){
            if(target.getName().equals("pawn") && target.spacesJustMoved == 2){
                return true;
            }
        }
        return false;
    }
    
    private boolean isAboutToPromote(){
        return (isWhite()) ? row == 1 : row == 6;
    }

    @Override
    public ArrayList<Piece> getConnectedPieces(Square[] board) {
        ArrayList<Piece> pieces = new ArrayList();
        
        Square left = null, right = null;
        int color = isWhite() ? -1 : 1;
        int nextRow = getIndex() + (8 * color);
        if(isInBoard(nextRow)){
            int leftIndex = nextRow - 1;
            int rightIndex = nextRow + 1;
            if(isInBoard(leftIndex)){
                left = board[leftIndex];
            }
            if(isInBoard(rightIndex)){
                right = board[rightIndex];
            }
        }
        
        //if the Square is in the board and 
        //the row of the capture Square is the same as the one in front of the pawn
        if(left != null){
            if(isInBoard(left.getIndex()) && nextRow / 8 == left.getRow()){
                Piece piece = left.getPiece();
                if(piece.isFriendly(this))
                    pieces.add(piece);
            }
        }
        if(right != null){
            if(isInBoard(right.getIndex()) && nextRow / 8 == right.getRow()){
                Piece piece = right.getPiece();
                if(piece.isFriendly(this))
                    pieces.add(piece);
            }
        }
        
        return pieces;
    }
}
