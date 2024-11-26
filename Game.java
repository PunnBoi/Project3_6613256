package Project3_6613256;



public class Game {
    private GFrame gWindow;
    private Menu menu = null;
    
    private static int mSound;
    private MySoundEffect themeSound = new MySoundEffect(MyConstants.sFILE_THEME);;
    
    public Game(){
        //themeSound = new MySoundEffect(MyConstants.sFILE_THEME);
        themeSound.playLoop();
        menu = new Menu(this);
    }
    
    public void openGame(){
        gWindow = new GFrame(this);
        themeSound.stop();
    }
    public void openMenu(){
        
        //themeSound = new MySoundEffect(MyConstants.sFILE_THEME);
        themeSound.playLoop();
        if(menu == null)
        {
            menu = new Menu(this);
        }
        else {
            
            menu.setVisible(true);
            menu.requestFocus();
        }
    }
    
    public MySoundEffect getThemeSound() {
        return themeSound;
    }
}