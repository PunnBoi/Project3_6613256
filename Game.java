package Project3_6613256;



public class Game {
    private GFrame gWindow;
    private Menu menu;
    
    private static int mSound;
    private MySoundEffect themeSound;
    
    public Game(){
        themeSound = new MySoundEffect(MyConstants.sFILE_THEME);
        themeSound.playLoop();
        menu = new Menu(this);
    }
    
    public void openGame(){
        gWindow = new GFrame(this);
        themeSound.stop();
    }
    public void openMenu(){
        menu = new Menu(this);
        themeSound.playLoop();
        
    }
    
    public MySoundEffect getThemeSound() {
        return themeSound;
    }
}