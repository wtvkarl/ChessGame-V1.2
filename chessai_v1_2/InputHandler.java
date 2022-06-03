/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author 3095515
 */
public class InputHandler implements MouseMotionListener, MouseListener{

    private int mouseX, mouseY;
    private boolean mousePressed;
    
    public int getX(){
        return mouseX;
    }
    
    public int getY(){
        return mouseY;
    }
    
    public boolean isPressed(){
        return mousePressed;
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    } //unimplemented

    @Override
    public void mousePressed(MouseEvent me) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    } //unimplemented

    @Override
    public void mouseExited(MouseEvent me) {} //unimplemented
    
}
