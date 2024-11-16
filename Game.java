package Project3_6613256;



public class Game {
    private GFrame gWindow;
    private MMenu menu;
    
    private static int mSound;
    
    public Game(){
        menu = new MMenu(this);
    }
    
    public void openGame(){
        gWindow = new GFrame(this);
    }
    public void openMenu(){
        menu = new MMenu(this);
    }
}