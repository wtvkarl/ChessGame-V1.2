/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author 3095515
 */
public class Button extends JButton{
    
    private String name;
    
    public Button(BufferedImage image, String name){
        this.name = name;
        this.setVisible(false);
        this.setIcon(new ImageIcon(image));
        this.setBackground(Color.GRAY);
        validate();
    }
    
    public void setImage(BufferedImage image){
        this.setIcon(new ImageIcon(image));
    }
    
    @Override
    public String toString(){
        return name;
    } 
    
}
