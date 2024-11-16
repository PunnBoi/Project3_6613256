/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
