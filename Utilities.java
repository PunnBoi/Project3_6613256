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
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    //      Don't forget to change path
    static final String PATH             = "src/main/java/Project3_6613256/resources/";
    //static final String FILE_SAMPLE          = PATH + "Example.jpg";
    static final String FILE_BG                 = PATH + "gBG.jpg";
    static final String FILE_CHAR_IDLE_R        = PATH + "char_idle_right.png";
    static final String FILE_CHAR_IDLE_L        = PATH + "char_idle_left.png";
    static final String FILE_CHAR_RUN_R         = PATH + "char_run_right.gif";
    static final String FILE_CHAR_RUN_L         = PATH + "char_run_left.gif";
    static final String FILE_CHAR_JUMP_R        = PATH + "char_jump_right.png";
    static final String FILE_CHAR_JUMP_L        = PATH + "char_jump_left.png";
    static final String FILE_BULLET             = PATH + "bullet.png";
    static final String FILE_HEART_0            = PATH + "heart_0.png";
    static final String FILE_HEART_1            = PATH + "heart_1.png";
    static final String FILE_HEART_2            = PATH + "heart_2.png";
    static final String FILE_HEART_3            = PATH + "heart_3.png";
    
    //static final String FILE_SOUND       = PATH + "sound.wav";
    static final String sFILE_THEME      = PATH + "theme.wav";
    static final String sFILE_JUMP       = PATH + "jumpsound.wav";
    //sizes
    static final int FRAMEWIDTH  = 1366;
    static final int FRAMEHEIGHT = 768;
    
    static final int GROUND_Y    = FRAMEHEIGHT-200;
    
    static final int CHARWIDTH   = 64;
    static final int CHARHEIGHT  = 64;
    
    static final int BULLWIDTH   = 40;
    static final int BULLHEIGHT  = 40;
}

//modify this from setting menu
interface setting
{
    static float MasterSound = (float) 1.0;
    static float EffectSound = (float) 1.0;
    static float MusicSound = (float) 1.0;
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
}
