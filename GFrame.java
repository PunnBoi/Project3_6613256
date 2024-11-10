/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GFrame extends JFrame {
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private Game              game;
    private JPanel            GPanel;
    private JPanel            GUI;
    private JLabel            drawpane;
    private MyImageIcon       backgroundImg;    
    private MySoundEffect     themeSound;
    
    private CharLabel         charLabel;
    
    private boolean flag=true;
    
    private ArrayList<Thread> allThread = new ArrayList<>();
    
    public GFrame(Game g){
        requestFocusInWindow();
        game =g;
        setTitle("Test Game");
	setSize(framewidth, frameheight); 
        setResizable(false);
        setLocationRelativeTo(null);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        setVisible(true);
        
        GPanel=new JPanel();
        setLayout( new BorderLayout() );  
        setFocusable(true); 
        requestFocusInWindow();
        
        themeSound = new MySoundEffect(MyConstants.sFILE_THEME); 
        themeSound.setVolume(setting.MusicSound);
        
        class MyWindowListener extends WindowAdapter
	{
            @Override
            public void windowClosing( WindowEvent e )		
            { 
                themeSound.stop();
                for(Thread a:allThread){
                    while(a.isAlive()){
                        a.interrupt();
                    }
                    if(allThread.isEmpty()) break;
                }
            }
	}
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	addWindowListener( new MyWindowListener() );
        
        setGPanel();
    }

    public void setGPanel(){
        setFocusable(true);
        GUI=new JPanel();
        GUI.setLayout( new BorderLayout() );
        
        drawpane=new JLabel();
        backgroundImg  = new MyImageIcon(MyConstants.FILE_BG).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        JButton moveButton = new JButton("Menu");
        moveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e )
            {
               
            }
        });
        
        setCharThread();
        setPlatformRunnnerThread();
        
        GUI.add(moveButton,BorderLayout.WEST);
        add(GUI, BorderLayout.NORTH);
        add(drawpane, BorderLayout.CENTER); 
        validate();
    }
    public void setCharThread()
    {   
        charLabel=new CharLabel();
        addKeyListener(new KeyBoardControl(charLabel));
        drawpane.add(charLabel);
        repaint();
        Thread charThread = new Thread() {
            public void run()
            {
                while (this.isAlive()){
                    if ( Thread.currentThread().isInterrupted() ){
                        break;
                    }
                    if(charLabel.isMove()) flag=false;
                    requestFocusInWindow();
                    charLabel.setSprite();
                    charLabel.move();
                }          
            } // end run
        }; // end thread creation
        charThread.start();
    }
    public void setPlatformRunnnerThread()
    {   
        Thread ptrThread = new Thread() {
            public void run()
            {
                System.out.println("start");
                while(flag){
                    if ( Thread.currentThread().isInterrupted() ){
                        break;
                    }
                    try { Thread.sleep(20); } 
                    catch (InterruptedException e) { e.printStackTrace(); }  
                }
                if(!flag)themeSound.playLoop();
                while(true) {
                    if ( Thread.currentThread().isInterrupted() ){
                        break;
                    }
                    System.out.println("start spawn platform randomly");
                    try { Thread.sleep(2000); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                
                        
            } // end run
        }; // end thread creation
        ptrThread.start();
    }
    public void setPlatformThread()
    {   
        Thread ptThread = new Thread() {
            public void run()
            {
                        
            } // end run
        }; // end thread creation
        ptThread.start();
        
    }
}

class KeyBoardControl implements KeyListener{
    static private CharLabel player;
    public KeyBoardControl(CharLabel p){
        player=p;
    }
    @Override
    public void keyTyped( KeyEvent e )          { }
    public void keyPressed( KeyEvent e )	{
        player.keyPressed(e);
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            System.out.println("pause");
        }
    }
    public void keyReleased( KeyEvent e )	{ 
        player.keyReleased(e);
    }
}
