/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project3_6613256;

/**
 *
 * @author User
 */
import java.awt.Image;
import javax.swing.*;
import javax.sound.sampled.*;     // for sounds


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    //      Don't forget to change path
    static final String PATH                            = "src/main/java/Project3_6613256/resources/";
    //static final String FILE_SAMPLE          = PATH + "Example.jpg";
    
    
    static final String FILE_BG[] = {
        PATH + "gBG.jpg",
        PATH + "background2.gif",
        PATH + "background3.gif",
        PATH + "background4.gif",
        PATH + "background5.gif"
    };
    
    static final String FILE_TTRCOMPO[] = {
        PATH + "A.gif",
        PATH + "D.gif",
        PATH + "W.gif",
        PATH + "RUNLEFT.gif",
        PATH + "RUNRIGHT.gif",
        PATH + "JUMP.gif",
    };
    
    
    static final String FILE_MAIN_BG                    = PATH + "background.gif";
    static final String FILE_MAIN_BG2                   = PATH + "background2.gif";
    static final String FILE_MAIN_BG3                   = PATH + "background3.gif";
    static final String FILE_MAIN_BG4                   = PATH + "background4.gif";
    static final String FILE_MAIN_BG5                   = PATH + "background5.gif";
    static final String FILE_SETTING_BG                 = PATH + "SETTING_BG.png";
    static final String FILE_TUTORIAL_BG                 = PATH + "TUTORIAL_BG.png";
    static final String FILE_TUTORIAL_BG1                 = PATH + "TTR.png";
    
    static final String FILE_BUTTON1                    = PATH + "pixil-frame-0.png";
    
    static final String FILE_TITLE                    = PATH + "TITLE.png";
    
    static final String FILE_TUTORIAL                   = PATH + "TUTORIAL.gif";
    
    static final String FILE_BUTTON_START_NORMAL        = PATH + "START_BUTTON.png";
    static final String FILE_BUTTON_TUTORIAL_NORMAL     = PATH + "TUTORIAL_BUTTON.png";
    static final String FILE_BUTTON_SETTING_NORMAL      = PATH + "SETTING_BUTTON.png";
    static final String FILE_BUTTON_CREDITS_NORMAL      = PATH + "CREDIT_BUTTON.png";
    static final String FILE_BUTTON_QUIT_NORMAL         = PATH + "QUIT_BUTTON.png";
    static final String FILE_BUTTON_BACK_NORMAL         = PATH + "BACK_BUTTON.png";
    static final String FILE_BUTTON_HOME_NORMAL         = PATH + "HOME.png";
    
    static final String FILE_BUTTON_START_PRESSED       = PATH + "START_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_TUTORIAL_PRESSED    = PATH + "TUTORIAL_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_SETTING_PRESSED     = PATH + "SETTING_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_CREDITS_PRESSED      = PATH + "CREDIT_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_QUIT_PRESSED        = PATH + "QUIT_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_BACK_PRESSED        = PATH + "BACK_BUTTON_PRESSED.png";
    static final String FILE_BUTTON_HOME_PRESSED        = PATH + "HOME_PRESSED.png";
    
    static final String FILE_UNCHECKED_BUTTON           = PATH + "UNCHECKED_BUTTON.png";
    static final String FILE_CHECKED_BUTTON             = PATH + "CHECKED_BUTTON.png";
    
    static final String FILE_CHAR_IDLE_R                = PATH + "char_idle_right.png";
    static final String FILE_CHAR_IDLE_L                = PATH + "char_idle_left.png";
    static final String FILE_CHAR_RUN_R                 = PATH + "char_run_right.gif";
    static final String FILE_CHAR_RUN_L                 = PATH + "char_run_left.gif";
    static final String FILE_CHAR_JUMP_R                = PATH + "char_jump_right.png";
    static final String FILE_CHAR_JUMP_L                = PATH + "char_jump_left.png";
    static final String FILE_BULLET                     = PATH + "bullet.png";
    static final String FILE_HEART_0                    = PATH + "heart_0.png";
    static final String FILE_HEART_1                    = PATH + "heart_1.png";
    static final String FILE_HEART_2                    = PATH + "heart_2.png";
    static final String FILE_HEART_3                    = PATH + "heart_3.png";
    
    //static final String FILE_SOUND       = PATH + "sound.wav";
    static final String sFILE_THEME[]                   =
    {
        PATH + "theme.wav",
        PATH + "CHIPI.wav"
    };
    static final String sFILE_JUMP                      = PATH + "jumpsound.wav";
    static final String sFILE_HIT                       = PATH + "hitsound.wav";

    static final int FRAMEWIDTH  = 1366;
    static final int FRAMEHEIGHT = 768;
    
    static final int GROUND_Y    = FRAMEHEIGHT-200;
    
    static final int CHARWIDTH   = 48;
    static final int CHARHEIGHT  = 64;
    
    static final int BULLWIDTH   = 40;
    static final int BULLHEIGHT  = 40;
}


// Auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
        // Use SCALE_DEFAULT mode to support gif
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        return new MyImageIcon(newimg);
    }
}


// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;         

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
    
    public float getVolume() {
    float dB = gainControl.getValue();
    float gain = (float) Math.pow(10.0, dB / 20.0);
    float volume = gain * 100.0f;
    return volume;
}
}

abstract class sSetting
{
    static private int picturenum = 0,difficulty = 2;
    static private float MasterSound = (float) 1.0;
    
    static public void picnoset(int n)
    {
        picturenum = n;
    }
    
    static public void diffset(int n)
    {
        difficulty = n;
    }
    
    static public int picnoget()
    {
        return picturenum;
    }
    
    static public int diffget()
    {
        return difficulty;
    }
    
    static public void setSound(float a){
        MasterSound=a;
    }
    
    static public float getSound(){
        return MasterSound;
    }
    
}