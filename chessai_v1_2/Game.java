/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2;

import javax.swing.JFrame;

/**
 *
 * @author 3095515
 */
public class Game {

    public Game() {
        long start = System.nanoTime();
        
        JFrame window = new JFrame("Chess Engine V 1.2");
        Display display = new Display();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(display);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        display.start();
        
        //this shows the boot up time, it shouldn't be too long 
        //but it is device dependent, averages about 3/4 a second on my laptop
        System.out.println("Boot Up Time: " + (System.nanoTime() - start)/1000000000.0 + " s"); 
    }
    
}
