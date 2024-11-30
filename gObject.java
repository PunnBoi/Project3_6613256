/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class gObject {

}

class CharLabel extends JLabel {

    final private MyImageIcon idleImgR, idleImgL, runImgR,
            runImgL, jumpImgR, jumpImgL;
    private MySoundEffect jumpS,hitS;

    final private int width = MyConstants.CHARWIDTH,
                    height = MyConstants.CHARHEIGHT;
    private int curY = MyConstants.GROUND_Y - height,
                curX = MyConstants.FRAMEWIDTH/2,
                speed = 10,
                Jpow = 7,
                dirX = 0,
                dirY = 1,
                hp = 3;
    private float u=0;
    private boolean right = true, move = false, grounded = false, gcheck = true
            ,callJump=false , jumped=false, falling=true;
    

    private GFrame parent;
    private JLabel ppanel;
    private JLabel GroundCheck;

    public CharLabel(GFrame p, JLabel pp) {
        idleImgR = new MyImageIcon(MyConstants.FILE_CHAR_IDLE_R).resize(width, height);
        idleImgL = new MyImageIcon(MyConstants.FILE_CHAR_IDLE_L).resize(width, height);
        runImgR = new MyImageIcon(MyConstants.FILE_CHAR_RUN_R).resize(width, height);
        runImgL = new MyImageIcon(MyConstants.FILE_CHAR_RUN_L).resize(width, height);
        jumpImgR = new MyImageIcon(MyConstants.FILE_CHAR_JUMP_R).resize(width, height);
        jumpImgL = new MyImageIcon(MyConstants.FILE_CHAR_JUMP_L).resize(width, height);
        setIcon(idleImgR);
        setBounds(curX, curY, width, height);

        GroundCheck = new JLabel();
        GroundCheck.setBounds(curX+width/2, curY + height, width/4, 1);

        jumpS = new MySoundEffect(MyConstants.sFILE_JUMP);
        
        hitS = new MySoundEffect(MyConstants.sFILE_HIT);

        this.ppanel = pp;
        this.parent = p;
        hp = 3;
    }

    public boolean isJump() {  return jumped; }

    public boolean isCheckG() { return gcheck; }

    public void setGrounded(boolean t) { grounded = t; }

    public int getCharCurX() { return curX; }

    public int getCharCurY() { return curY; }
    
    public JLabel getGCheck() { return GroundCheck; }//for when check the floor

    public void setSprite() {
        //make sprite turn left/right
        if (right) {
            if (grounded) {
                if (move) {
                    setIcon(runImgR);
                } else {
                    setIcon(idleImgR);
                }
            } else {
                if (jumped||falling) {
                    setIcon(jumpImgR);
                }
            }
        } else {
            if (grounded) {
                if (move) {
                    setIcon(runImgL);
                } else {
                    setIcon(idleImgL);
                }
            } else {
                if (jumped||falling) {
                    setIcon(jumpImgL);
                }
            }
        }
    }

    public void jump() {
        if(!jumped){
            dirY = -1;
            grounded = false;
            gcheck = false;
            jumpS.playOnce();
            jumped=true;
            falling=false;
            Thread wait = new Thread() {
                public void run() {
                    try {
                        this.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dirY = 1;
                    gcheck = true;
                    falling=true;
                }
            };
            wait.start();
        }

    }

    public void move() {
        if(callJump&&grounded){
            callJump=false;
            jump();
            
        }
        curX += dirX * speed;
        if (curX < 0) {
            curX = 0;
        } else if (curX + width > MyConstants.FRAMEWIDTH) {
            curX = MyConstants.FRAMEWIDTH - width;
        }

        if (!grounded) {
            if(u>Jpow)u=Jpow;
            curY += dirY * u;
            u+=0.7;
        }
        else {
            falling=false;
            u=0;
        }
        if (!isIntersectingWithAny(this, ppanel)) {
            if (curY + height >= MyConstants.FRAMEHEIGHT) {
                parent.GameOver(-1);
            } else {
                grounded = false;
            }
        }
        setLocation(curX, curY);
        GroundCheck.setLocation(curX+width/2, curY + height);
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isIntersectingWithAny(JLabel label, JLabel panel) {

        for (Component component : panel.getComponents()) {
            if (component == label) {
                continue;
            }

            if ((label.getBounds()).intersects(component.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                dirX = 1;
                right = true;
                move = true;
                break;
            case KeyEvent.VK_A:
                dirX = -1;
                right = false;
                move = true;
                break;
            case KeyEvent.VK_W:
                if(!jumped)callJump=true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                dirX = 0;
                move = false;
                break;
            case KeyEvent.VK_A:
                dirX = 0;
                move = false;
                break;
            case KeyEvent.VK_W:
                jumped = false;
                break;
        }
    }

    public int gethp() {
        return hp;
    }

    public void reducehp() {
        hitS.playOnce();
        if (hp > 0) {
            hp--;
            parent.updateHeartDisplay(hp);
        }

        if (hp <= 0) {
            hp = 0;
            parent.GameOver(-1);
        }
    }
}

class Bullet extends JLabel {

    private int curX, curY = 0, speed = 7;
    private boolean isActive;
    final private int width = MyConstants.BULLWIDTH;
    final private int height = MyConstants.BULLHEIGHT;
    final private MyImageIcon bullet;
    private JLabel parentpane;

    public Bullet(int pPOS,JLabel p) {
        Random rand = new Random();
        int ranX = rand.nextInt(pPOS-150, pPOS+150);
        isActive = true;
        parentpane=p;
        curX = ranX;
        bullet = new MyImageIcon(MyConstants.FILE_BULLET).resize(width, height);
        setIcon(bullet);
        setBounds(curX, curY, width, height);
    }

    public void move() {
        curY += speed;

        if (curY > MyConstants.FRAMEHEIGHT) {
            isActive = false;
            parentpane.remove(this);
        }
        setLocation(curX, curY);
        repaint();
    }

    public boolean getIsActive() {
        return isActive;
    }

    public boolean setIsActive(boolean a) {
        return a;
    }

}

class Platform extends JLabel {

    private int curX, curY;
    private int width = 150, height = 15;
    private int speed = 1;
    private boolean isActive;
    private boolean isVisit= false;
    private MyImageIcon PlatformImg;
    private CharLabel character;

    public Platform(int x, int y, CharLabel Char) {
        this.curX = x;
        this.curY = y;
        this.character = Char;

        PlatformImg = new MyImageIcon(MyConstants.FILE_PLATFORM).resize(width, height);
        setOpaque(false);
        setIcon(PlatformImg);
        //setBackground(Color.LIGHT_GRAY);

        setBounds(curX, curY, width, height);
    }

    public void moveDown() {
        Random rand = new Random();
        int farLeft,farRight;
        curY += speed;

        if (curY > MyConstants.FRAMEHEIGHT) {
            farLeft = MyConstants.FRAMEWIDTH/2-300;
            farRight = MyConstants.FRAMEWIDTH/2+300;
            curX = rand.nextInt(farLeft, farRight);
            curY=0;
        }
        setLocation(curX, curY);
        repaint();
    }
    
    public boolean getIsActive() {
        return isActive;
    }
    
    public boolean getisVisit() {
        return isVisit;
    }
    
     public void setisVisit(boolean visited) {
        this.isVisit = visited;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}


