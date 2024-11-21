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

    private int framewidth = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;

    private Game game;
    private JPanel GPanel;
    private JPanel GUI;
    private JLabel drawpane;
    private MyImageIcon backgroundImg;
    private MySoundEffect themeSound;

    private CharLabel charLabel;

    private boolean flag = true;

    private ArrayList<Thread> allThread = new ArrayList<>();
    
    private ArrayList<Bullet> bullets = new ArrayList<>();
    
    private ArrayList<Platform> platforms = new ArrayList<>(); 
    
    // This is for platform ground check
    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
    
    private boolean GameRunning = true;
    
    private final int MAX_HP = 3;
    private JLabel heartLabels;
    private int width = 100;
    private int height = 50;
    private final String[] heartImages = {
    MyConstants.FILE_HEART_0,  // Empty heart image
    MyConstants.FILE_HEART_1,  // 1 heart image
    MyConstants.FILE_HEART_2,  // 2 hearts image
    MyConstants.FILE_HEART_3 
};

    public GFrame(Game g) {
        requestFocusInWindow();
        game = g;
        setTitle("Test Game");// Change this title when hava a game name
        setSize(framewidth, frameheight);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        GPanel = new JPanel();
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();

        themeSound = new MySoundEffect(MyConstants.sFILE_THEME);
        themeSound.setVolume(setting.MusicSound);
        
        class MyWindowListener extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent e) {
                // This windowClosing is when playing game, user close the windows.
                // When this trigger, the game close and the program is finished.
                
                GameRunning = false;
                themeSound.stop();
                
                for (Thread a : allThread) {
                    a.interrupt();
                }
                
                for (Thread thread : allThread) {
                    try {
                        thread.join(); // Wait for thread termination
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                allThread.clear(); // Cleanup thread list
                dispose(); // Close the game window
            }
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        addWindowListener(new MyWindowListener());

        setGPanel();
        //setPlatformThread();
        //setBulletThread(); 
    }

    public void setGPanel() {
        setFocusable(true);
        GUI = new JPanel();
        GUI.setLayout(new BorderLayout());

        drawpane = new JLabel();
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        setHeartPanel();
        
        JButton moveButton = new JButton("Menu");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themeSound.stop();
                dispose();
                setFocusable(false);
                game.openMenu();
            }
        });

        setCharThread();
        setPlatformRunnnerThread();

        GUI.add(moveButton, BorderLayout.WEST);
        add(GUI, BorderLayout.NORTH);
        add(drawpane, BorderLayout.CENTER);
        validate();
    }

    public void setCharThread() {
        charLabel = new CharLabel(this);
        addKeyListener(new KeyBoardControl(charLabel));
        drawpane.add(charLabel);

        repaint();
        Thread charThread = new Thread() {
            public void run() {
                //while (this.isAlive()) {
                while (GameRunning) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    if (charLabel.isMove()) {
                        flag = false;
                    }
                    requestFocusInWindow();
                    charLabel.setSprite();
                    charLabel.move();
                }
            } // end run
        }; // end thread creation
        charThread.start();
        allThread.add(charThread);
    }

    public void setPlatformRunnnerThread() {
        Thread ptrThread = new Thread() {
            public void run() {
                System.out.println("start");
                while (flag) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!flag) {
                    themeSound.playLoop();
                }
                
                setPlatform();// create the platform here...
                
                while (GameRunning) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    setBulletThread();
                    //setPlatformThread();
                    System.out.println("This thread is currnetly running!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } // end run
        }; // end thread creation
        ptrThread.start();
    }
 
    /*
    public void setPlatformThread() {
        Thread ptThread = new Thread() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    Random rand = new Random();
                    int xPos = rand.nextInt(0, MyConstants.FRAMEWIDTH - 100); // Random x position
                    int yPos = rand.nextInt(100, MyConstants.GROUND_Y - 50); // Random y position
                    Platform newPlatform = new Platform(xPos, yPos);
                    platforms.add(newPlatform);
                    drawpane.add(newPlatform);
                    
                    drawpane.repaint();
                    
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            } // end run
        }; // end thread creation
        ptThread.start();
        allThread.add(ptThread);
        
        
        Thread ptFallThread = new Thread() {
            @Override
            public void run() {
                while(GameRunning) {
                    for (Platform platform : platforms) {
                        platform.moveDown();
                        System.out.println("Platform position: " + platform.getCurY());
                    }
                }
                drawpane.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ptFallThread.start();
        allThread.add(ptFallThread);
        
    }
    */
    
    public void setPlatform() {
        for (int i = 0; i < 20; i++) {
            // Need to create the platform in Y axis a little bit far apart.
            // Could just define the zone of the Y axis that the platform can generate.
            // No need to change the move part to behave like the generate part. 
            // Because We already initialize the space between them.
            Random rand = new Random();
            int xPos = rand.nextInt(0, MyConstants.FRAMEWIDTH - 100); // Random x position
            int yPos = rand.nextInt(100, MyConstants.GROUND_Y - 50); // Random y position
            Platform newPlatform = new Platform(xPos, yPos, charLabel);
            platforms.add(newPlatform);
            drawpane.add(newPlatform);

            drawpane.repaint();
        } 
        
        Thread ptFallThread = new Thread() {
            @Override
            public void run() {
                while (GameRunning) {
                    for (Platform platform : platforms) {
                        platform.moveDown();
                        System.out.println("Platform position: " + platform.getCurY());
                    }
                }
                drawpane.repaint();
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ptFallThread.start();
        allThread.add(ptFallThread);
    }
    
    public void setBulletThread() {
        Thread CreateBulletThread = new Thread() {
            @Override
            public void run() {
                while (GameRunning) {
                    if (charLabel.isMove()){
                        Bullet bullet = new Bullet();
                        drawpane.add(bullet);
                        bullets.add(bullet);
                        
                        drawpane.repaint();
                        System.out.println("Bullet position: " + bullet.getX() + ", " + bullet.getY()); 
                    }
                    
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        CreateBulletThread.start();
        allThread.add(CreateBulletThread);

        Thread BulletFallThread = new Thread() {
            @Override
            public void run() {
                while (GameRunning) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet.getIsActive()) {
                            bullet.move();
                            if (bullet.getBounds().intersects(charLabel.getBounds())) {
                                bullet.setIsActive(false);
                                drawpane.remove(bullet);
                                drawpane.repaint();
                                bullets.remove(i);
                                i++;
                                charLabel.reducehp();
                                System.out.println("Player's HP: " + charLabel.gethp());
                            }
                        }
                    }
                    drawpane.repaint();
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        BulletFallThread.start();
        allThread.add(BulletFallThread);

    }

    public void GameOver() {
        GameRunning = false;
        
        themeSound.stop();
        setFocusable(false); 
        for (Thread thread : allThread) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
        
        GameOverDialog();
    }
    
    public void GameOverDialog() {
        // Create a modal JDialog
        JDialog gameOverDialog = new JDialog(this, "Game Over", true);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setSize(300, 150);
        gameOverDialog.setLocationRelativeTo(this); // Center on the parent frame
        
        JLabel message = new JLabel("Game Over. Click OK to return to the menu.", JLabel.CENTER);
        JButton okButton = new JButton("OK");
        
        setFocusable(true);
        requestFocusInWindow();
        
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Can add something to show on the game over down here
                // Add score

                gameOverDialog.dispose(); // Close the dialog
                setFocusable(false);
                dispose(); // Close the game frame
                game.openMenu(); // Open the menu
            }
        });
        
        // Layout components
        gameOverDialog.add(message, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show the dialog
        gameOverDialog.setVisible(true);
        
    }
    
    public void setHeartPanel() {
        heartLabels = new JLabel();
        heartLabels.setIcon(new MyImageIcon(heartImages[3]).resize(200, 64));
        heartLabels.setBounds(framewidth - 220, 10, 200, 64); // Adjust size as needed
        drawpane.add(heartLabels);
        drawpane.repaint();
    }
    
    public void updateHeartDisplay(int hp) {
    if (hp >= 0 && hp < heartImages.length) {
        heartLabels.setIcon(new MyImageIcon(heartImages[hp]).resize(200,64)); // Update image
        drawpane.repaint();
        System.out.println("Updated heart display to HP: " + hp);
    }
}
}

class KeyBoardControl implements KeyListener {

    static private CharLabel player;

    public KeyBoardControl(CharLabel p) {
        player = p;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("pause");
        }
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}