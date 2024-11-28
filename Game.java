package Project3_6613256;

/*
6613113 Kavee Tangmanpakdeepong
6613256 Punnapat Panat
6613263 Passakorn Aiamwasu
6613274 Sujira Duangkaewnapalai
 */

public class Game {
    private GFrame gWindow;
    private MainApplication menu = null;
    
    private static int mSound;
    private MySoundEffect themeSound = new MySoundEffect(MyConstants.sFILE_THEME[0]);
    
    public static void main(String[] args) {
        Game game= new Game();
        game.startGame();
    }
    
    public void startGame(){
        themeSound.playLoop();
        menu = new MainApplication(this);
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
            menu = new MainApplication(this);
        }
        else {
            
            menu.setVisible(true);
            menu.requestFocus();
        }
    }
    
    public MySoundEffect getThemeSound() {
        return themeSound;
    }
    
    public void changeThemeSound() {
        if (themeSound != null) {
            themeSound.stop();
        }
        themeSound = new MySoundEffect(MyConstants.sFILE_THEME[1]);
        themeSound.setVolume(sSetting.getSound()/100f);
        themeSound.playLoop();
    }
}