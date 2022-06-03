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
public class Queen extends Piece{

    private int[] directionOffsets;
    
    public Queen(int index, String color) {
        pieceType = 5;
        this.name = "queen";
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
        this.color = color;
        setImage();
        fenNotation = (color.equals("white")) ? 'Q' : 'q';
        directionOffsets = ChessBoard.getDirectionOffsets();
        this.timesMoved = 0;
        this.validMoves = new ArrayList();
    }

    @Override
    public ArrayList<Move> getValidMoves(Square[] board) {
        validMoves.clear();
        int directionOffsetStartIndex = 0; 
        Square start = board[getIndex()];
        for(int i = directionOffsetStartIndex; i < directionOffsets.length; i++){
            //having this distance to edge method means we wont have any out of bounds errors :)
            //ALSO THIS METHOD IS A LITERAL TIMESAVER HOLY MACKEREL THANKS SEBASTIAN
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            int offset = directionOffsets[i];
            
            for(int dist = 1; dist <= distToEdge; dist++){
                int targetIndex = getIndex() + (dist * offset);
                Square target = board[targetIndex];
                Piece targetPiece = target.getPiece();
                if(!targetPiece.isBlank()){
                    if(!targetPiece.isFriendly(this))
                        validMoves.add(new Move(start, target, board));
                    break;
                }
                else
                    validMoves.add(new Move(start, target, board));
            }
        }
        
        return validMoves;
    }

    @Override
    public ArrayList<Square> getAttackSquares(Square[] board) {
        ArrayList<Square> attackSquares = new ArrayList();
        ArrayList<Move> moves = getValidMoves(board);
        
        for(Move move : moves){
            attackSquares.add(move.getTargetSquare());
        }
        return attackSquares;
    }

    @Override
    public ArrayList<Piece> getConnectedPieces(Square[] board) {
        ArrayList<Piece> pieces = new ArrayList();
        int directionOffsetStartIndex = 0; 
        
        for(int i = directionOffsetStartIndex; i < directionOffsets.length; i++){
            int distToEdge = ChessBoard.getDistanceToEdge(getIndex(), i);
            int offset = directionOffsets[i];
            
            for(int dist = 1; dist <= distToEdge; dist++){
                int targetIndex = getIndex() + (dist * offset);
                Square target = board[targetIndex];
                Piece targetPiece = target.getPiece();
                if(targetPiece.isFriendly(this)){
                    pieces.add(targetPiece);
                    break;
                }
            }
        }
        
        return pieces;
    }
    
}
