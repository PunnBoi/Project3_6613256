package Project3_6613256;



public class Game {
    private GFrame gWindow;
    private Menu menu;
    
    private static int mSound;
    
    public Game(){
        menu = new Menu(this);
    }
    
    public void openGame(){
        gWindow = new GFrame(this);
    }
    public void openMenu(){
        menu = new Menu(this);
    }
}