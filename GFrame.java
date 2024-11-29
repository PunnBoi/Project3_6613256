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
    private int CountScore;

    private Game game;
    private JPanel GUI;
    private JLabel drawpane;
    private MyImageIcon backgroundImg;
    private MySoundEffect themeSound;

    private CharLabel charLabel;

    private JTextField score;
    
    private javax.swing.Timer countdownTimer;

    private boolean flag = true;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Platform> platforms = new ArrayList<>();
    
    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    private boolean GameRunning = true;

    private JLabel heartLabels;
    private final String[] heartImages = {
        MyConstants.FILE_HEART_0,
        MyConstants.FILE_HEART_1,
        MyConstants.FILE_HEART_2,
        MyConstants.FILE_HEART_3
    };

    public GFrame(Game g) {
        requestFocusInWindow();
        game = g;
        setTitle("Test Game");
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();

        themeSound = game.getThemeSound();

        class MyWindowListener extends WindowAdapter {

            @Override
            public void windowClosing(WindowEvent e) {

                GameRunning = false;
                themeSound.stop();

                dispose();
            }
        }
        
        addWindowListener(new MyWindowListener());

        setGPanel();
    }

    public void setGPanel() {
        setFocusable(true);
        GUI = new JPanel();
        GUI.setLayout(new BorderLayout());

        drawpane = new JLabel();

        int bgn = sSetting.picnoget();

        backgroundImg = new MyImageIcon(MyConstants.FILE_BG[bgn]).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);

        repaint();

        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        setHeartPanel();

        JButton moveButton = new JButton("Back To Menu");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRunning = false;
                themeSound.stop();
                dispose();
                setFocusable(false);
                game.openMenu();
            }
        });
        JLabel timerLabel = setupCountdownTimer();
        
        score = new JTextField("0", 5);
        score.setEditable(false);
        JPanel ScorePanel = new JPanel();
        ScorePanel.add(new JLabel("Score: "));
        ScorePanel.add(score);

        setCharThread();
        setPlatformRunnnerThread();

        GUI.add(moveButton, BorderLayout.WEST);
        GUI.add(timerLabel, BorderLayout.CENTER);
        GUI.add(ScorePanel, BorderLayout.EAST);
        add(GUI, BorderLayout.NORTH);
        add(drawpane, BorderLayout.CENTER);
        validate();
    }

    public void setBackgroundImage(MyImageIcon image) {
        this.backgroundImg = image;
        repaint();
    }

    public void setCharThread() {
        charLabel = new CharLabel(this, drawpane);
        addKeyListener(new KeyBoardControl(charLabel));
        drawpane.add(charLabel);

        repaint();
        Thread charThread = new Thread() {
            public void run() {
                while (GameRunning) {
                    if (charLabel.isJump()) {
                        flag = false;
                    }
                    requestFocusInWindow();
                    charLabel.setSprite();
                    charLabel.move();
                    repaint();
                }
            }
        };
        charThread.start();
    }

    public void setPlatformRunnnerThread() {
        Thread ptrThread = new Thread() {
            public void run() {
                setPlatform();
                while (flag) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!flag) {
                    themeSound.playLoop();
                }
                setBulletThread();

                while (GameRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        ptrThread.start();
    }

    public void setPlatform() {
        Random rand = new Random();
        int difficulty = sSetting.diffget();
        int farLeft, farRight, xPos, yPos;
        boolean firstplatform = true;

        for (int i = 0; i < 12 - difficulty-2; i++) {
            
            farLeft = MyConstants.FRAMEWIDTH / 2 - 300;
            farRight = MyConstants.FRAMEWIDTH / 2 + 300;
            if (firstplatform) {
                xPos = charLabel.getCharCurX() + 30;
                firstplatform = false;
            } else {
                xPos = rand.nextInt(farLeft, farRight);
            }
            yPos = (charLabel.getCharCurY() + 70) - ((34 * (difficulty+2)) *i);
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
                        if (!flag) {
                            platform.moveDown();
                        }
                        if (platform.getBounds().intersects((charLabel.getGCheck()).getBounds())) {
                            if (!platform.getisVisit()) {
                                
                                if (charLabel.isCheckG()) {
                                    charLabel.setGrounded(true);
                                    platform.setisVisit(true);
                                    UpdateScore(10);
                                }
                            } 
                            else {
                                if (charLabel.isCheckG()) {
                                    charLabel.setGrounded(true);

                                }
                            }
                        } 
                        else {
                            platform.setisVisit(false);
                        }

                    }
                    drawpane.repaint();

                    try {
                        Thread.sleep(20); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ptFallThread.start();
    }

    public void setBulletThread() {
        int delay = 3000 / (sSetting.diffget()+1);
        Thread CreateBulletThread = new Thread() {
            @Override
            public void run() {
                while (GameRunning) {
                    Bullet bullet = new Bullet(charLabel.getCharCurX(), drawpane);
                    drawpane.add(bullet);
                    bullets.add(bullet);
                    drawpane.repaint();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        CreateBulletThread.start();

        Thread BulletFallThread = new Thread() {
            @Override
            public void run() {
                while (GameRunning) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet.getIsActive()) {
                            bullet.move();
                            if (bullet.getBounds().intersects(charLabel.getBounds())) {
                                UpdateScore(-100);
                                bullet.setIsActive(false);
                                drawpane.remove(bullet);
                                drawpane.repaint();
                                bullets.remove(i);
                                charLabel.reducehp();
                            }
                        } else {
                            bullets.remove(i);
                        }
                    }
                    drawpane.repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        BulletFallThread.start();

    }
    
    public JLabel setupCountdownTimer(){
        
        JLabel timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);


         int chooseTime; 
        int difficulty = sSetting.diffget();
        if (difficulty == 4) {
            chooseTime = 0;
        } else {
            chooseTime = 60;
        }

        countdownTimer = new javax.swing.Timer(1000, new ActionListener() {
            int timeLeft = chooseTime;
            @Override
            public void actionPerformed(ActionEvent e) {
                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                
                if (!flag && GameRunning) {  
                    
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

                    // Decrement the countdown
                    if (difficulty == 4) { // Endless mode
                        timeLeft++;
                    }
                    
                    else if (timeLeft > 0) {
                        timeLeft--;
                    } else {
                        
                        ((javax.swing.Timer) e.getSource()).stop();
                        GameOver(sSetting.diffget()); 
                    }
                }
            }
        });
        countdownTimer.start();
        return timerLabel;
    }

    public synchronized void UpdateScore(int count) {
        CountScore += count;
        score.setText(String.valueOf(CountScore));
    }

    public void GameOver(int i) {
        GameRunning = false;
        
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        themeSound.stop();
        setFocusable(false);
        GameOverDialog(i);
    }

    public void GameOverDialog(int i) {
        
        String Text="You win!, Score = "+CountScore,
                EXtext="Code: ";
        JDialog gameOverDialog = new JDialog(this, "Game Over", true);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setSize(300, 150);
        gameOverDialog.setLocationRelativeTo(this);
        switch(i){
            case -1:
                Text="Game Over!, Score = "+CountScore;
                break;
            case 0:
                EXtext = EXtext + "car";
                break;
            default:
                EXtext = EXtext + "credit";
                break;
        }
        JPanel mGroup = new JPanel(new GridLayout(0, 1));
        JLabel message = new JLabel(Text, JLabel.CENTER),
                EXmessage = new JLabel(EXtext, JLabel.CENTER);
        JButton okButton = new JButton("Back To Menu");

        setFocusable(true);
        requestFocusInWindow();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameOverDialog.dispose();
                setFocusable(false);
                dispose();
                game.openMenu();
            }
        });

        mGroup.add(message);
        if(i>=0)mGroup.add(EXmessage);
        gameOverDialog.add(mGroup, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        gameOverDialog.setVisible(true);

    }

    public void setHeartPanel() {
        heartLabels = new JLabel();
        heartLabels.setIcon(new MyImageIcon(heartImages[3]).resize(200, 64));
        heartLabels.setBounds(framewidth - 220, 10, 200, 64);
        drawpane.add(heartLabels);
        drawpane.repaint();
    }

    public void updateHeartDisplay(int hp) {
        if (hp >= 0 && hp < heartImages.length) {
            heartLabels.setIcon(new MyImageIcon(heartImages[hp]).resize(200, 64));
            drawpane.repaint();
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
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}
