/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.*;

/**
 *
 * @author User
 */
public class Menu extends JFrame{
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    Game game;
    
    public Menu(Game g){
        setFocusable(true);
        requestFocusInWindow();
        game =g;
        setTitle("Test Game");
	setSize(framewidth, frameheight); 
        setResizable(false);
        setLocationRelativeTo(null);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        setVisible(true);
        
        JButton moveButton = new JButton("Game");
        moveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e )
            {
                dispose();
                setFocusable(false);
		game.openGame();
            }
        });
        
        add(moveButton);
        validate();
    }
}
