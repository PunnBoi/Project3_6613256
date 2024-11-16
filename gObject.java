/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6613256;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class gObject {

}

class CharLabel extends JLabel {

    final private MyImageIcon idleImgR, idleImgL, runImgR,
            runImgL, jumpImgR, jumpImgL;
    private MySoundEffect jumpS;

    final private int width = MyConstants.CHARWIDTH;
    final private int height = MyConstants.CHARHEIGHT;
    private int curY = MyConstants.GROUND_Y - height;
    private int curX = 300;
    private int speed = 10;
    private int Jpow = 10;
    private int dirX = 0;
    private int dirY = 0;
    private boolean right = true, move = false, grounded = true, gcheck = true;
    private int hp = 3;
    private GFrame parent;

    private JLabel GroundCheck;

    public CharLabel(GFrame parent) {
        idleImgR = new MyImageIcon(MyConstants.FILE_CHAR_IDLE_R).resize(width, height);
        idleImgL = new MyImageIcon(MyConstants.FILE_CHAR_IDLE_L).resize(width, height);
        runImgR = new MyImageIcon(MyConstants.FILE_CHAR_RUN_R).resize(width, height);
        runImgL = new MyImageIcon(MyConstants.FILE_CHAR_RUN_L).resize(width, height);
        jumpImgR = new MyImageIcon(MyConstants.FILE_CHAR_JUMP_R).resize(width, height);
        jumpImgL = new MyImageIcon(MyConstants.FILE_CHAR_JUMP_L).resize(width, height);
        setIcon(idleImgR);
        setBounds(curX, curY, width, height);

        GroundCheck = new JLabel();
        GroundCheck.setBounds(curX, curY + height - 20, width, 20);

        jumpS = new MySoundEffect(MyConstants.sFILE_JUMP);
        jumpS.setVolume(setting.EffectSound);
        
        this.parent = parent;
        this.hp = 3; 
    }

    public boolean isMove() {
        return move;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public JLabel getGCheck() {
        return GroundCheck;
    }//for when check the floor

    public void setSpeed(int s) {
        speed = s;
    }

    public void setMove(boolean m) {
        move = m;
    }

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
                setIcon(jumpImgR);
            }
        } else {
            if (grounded) {
                if (move) {
                    setIcon(runImgL);
                } else {
                    setIcon(idleImgL);
                }
            } else {
                setIcon(jumpImgL);
            }
        }
        repaint();
    }

    public void jump() {
        dirY = -1;
        grounded = false;
        gcheck = false;
        jumpS.playOnce();
        Thread wait = new Thread() {
            public void run() {
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dirY = 1;
                gcheck = false;
            } // end run
        };
        wait.start();

    }

    public void move() {
        curX += dirX * speed;
        if (curX < 0) {
            curX = 0;
        } else if (curX+width > MyConstants.FRAMEWIDTH) {
            curX = MyConstants.FRAMEWIDTH - width;
        }
        if (!grounded) {
            curY += dirY * Jpow;
        }
        if (curY + height > MyConstants.GROUND_Y) {
            curY = MyConstants.GROUND_Y - height;
            grounded = true;
        }

        setLocation(curX, curY);
        GroundCheck.setLocation(curX, curY + height - 20);
        repaint();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shoot() {

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
                if (grounded) {
                    jump();
                }
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
        }
    }
    
    public int gethp(){
        return hp;
    }
    
    public void reducehp(){
        if(hp > 0){
          hp --; 
          parent.updateHeartDisplay(hp);
        }
        
        if(hp <= 0){
            hp = 0;
            parent.GameOver();
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////

class Bullet extends JLabel {

    private int curX, curY = 0, speed = 10;
    private boolean isActive;
    final private int width = MyConstants.BULLWIDTH;
    final private int height = MyConstants.BULLHEIGHT;
    final private MyImageIcon bullet;

    public Bullet() {
        Random rand = new Random();
        int ranX = rand.nextInt(0, MyConstants.FRAMEWIDTH);
        this.isActive = true;
        this.curX = ranX;
        bullet = new MyImageIcon(MyConstants.FILE_BULLET).resize(width, height);
        setIcon(bullet);
        setBounds(curX, curY, width, height);
    }

    public void move() {
        if (!isActive) {
        return;
    }
        curY += speed;
        if (curY > MyConstants.FRAMEHEIGHT){
            isActive = false;
        }
        setLocation(curX, curY);
        repaint();
    }
    
    public boolean getIsActive(){
        return isActive;
    }
    
    public boolean setIsActive(boolean a){
        return a;
    }

}

////////////////////////////////////////////////////////////////////////////////////////////

class Platform extends JLabel {
    private int curX, curY;
    private int width, height;
    
    public Platform(int x, int y, int width, int height) {
        this.curX = x;
        this.curY = y;
        this.width = width;
        this.height = height;

        // Set platform image

        setBounds(curX, curY, width, height);
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