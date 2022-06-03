/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krlv.source.chessai_v1_2;

import com.krlv.source.chessai_v1_2.board.ChessBoard;
import com.krlv.source.chessai_v1_2.gfx.ImageLoader;
import com.krlv.source.chessai_v1_2.gfx.UI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author 3095515
 */
public class Display extends JPanel implements Runnable{

    public static final int width = 800, height = 800;
    
    private static boolean running;
    private static Thread gameThread;
    
    private UI ui;
    private ChessBoard board;
    
    public Display() {
        long start = System.nanoTime();
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        InputHandler ihHandler = new InputHandler();
        this.addMouseListener(ihHandler);
        this.addMouseMotionListener(ihHandler);
        ImageLoader imgLoader = new ImageLoader();
        imgLoader.preloadAssets();
        board = new ChessBoard(ihHandler);
        ui = new UI(this, ihHandler);
        System.out.println("Bootup Time: " + (System.nanoTime() - start) / 1_000_000_000.0);
    }
    
    public void start(){
        running = true;
        gameThread = new Thread(this, "gameThread");
        gameThread.start();
    }
    
    @Override
    public void run() {
        int targetUpdates = 60;
        double nsPerSecond = 1000000000.0;
        double drawInterval = nsPerSecond/targetUpdates;
        double delta = 0;
        long then = System.nanoTime();
        long now;
        boolean shouldRender = false;
        
        while(running){
            now = System.nanoTime();
            delta += (now - then) / drawInterval;
            then = now;
            
            if(delta >= 1){
                update();
                delta--;
                shouldRender = true;
            }
            
            if(shouldRender){
                repaint();
                shouldRender = false;
            }
            
        }
    }
    
    private void update(){
        board.update();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);
    } 
    
}
