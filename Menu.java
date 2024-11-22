package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Menu extends JFrame {

    
    private JPanel              contentpane;
    private Menu                currentFrame;
    private JLabel              drawpane;
    private JButton             startButton, ttrButton, creditButton, exitButton;
    private MyImageIcon         backgroundImg,buttonImg; 
    private Game                game;
    
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private int btwidth  = 300;
    private int btheight = 50;
    
    /*public static void main(String[] args) {
        
        Menu menu = new Menu();
        
    }*/
    
    public Menu(Game g)
    {
        setTitle("Project3");
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
        setResizable(false);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );    
        
        game = g;
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);  // Set null layout to manually position components
        contentpane.add(layeredPane, BorderLayout.CENTER);

        AddBackgroundComponents(layeredPane);
        AddTitleComponents(layeredPane);
        AddButtonComponents(layeredPane);
        
        
        
    }
    
    public void AddBackgroundComponents(JLayeredPane layeredPane)
    {
        backgroundImg  = new MyImageIcon(MyConstants.FILE_MAIN_BG2).resize(framewidth, frameheight);
	drawpane = new JLabel();
	drawpane.setIcon(backgroundImg);
        drawpane.setBounds(0, -35, framewidth, frameheight);
        layeredPane.add(drawpane, JLayeredPane.DEFAULT_LAYER);
        
    }
    
    public void AddTitleComponents(JLayeredPane layeredPane)
    {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("PROJECT 3");
        title.setFont(new Font("Algerian", Font.PLAIN, 128));
        title.setForeground(new Color(116, 9, 56));
        //titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel title2 = new JLabel("PROJECT 3");
        title2.setFont(new Font("Algerian", Font.PLAIN, 64));
        title2.setForeground(new Color(116, 9, 56));
        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, -25)));
        titlePanel.add(title2);
        
        titlePanel.setBounds(0, 150, framewidth, 400);
        layeredPane.add(titlePanel, JLayeredPane.PALETTE_LAYER);
        
        
    }
    
    public void AddButtonComponents(JLayeredPane layeredPane)
    {
        //contentpane = (JPanel)getContentPane();
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        startButton     = createButton(MyConstants.FILE_BUTTON_START_NORMAL);
        ttrButton       = createButton(MyConstants.FILE_BUTTON_TUTORIAL_NORMAL);
        creditButton    = createButton(MyConstants.FILE_BUTTON_CREDITS_NORMAL);
        exitButton      = createButton(MyConstants.FILE_BUTTON_QUIT_NORMAL);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                dispose();
                setFocusable(false);
		game.openGame(); // To start the game.
            }
        });
        
        ttrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                // Write tutoreal down here...
                
            }
            
        });
        
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                Setting setting = new Setting(game);
                dispose();
                setFocusable(false);
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(ttrButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(creditButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(exitButton);

        //contentpane.add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setBounds(0, frameheight / 2 + 30, framewidth, 350);  // Position buttons
        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);
        
        validate();
        
    }
    
    private JButton createButton(String text) {
        
       
        
        buttonImg = new MyImageIcon(text).resize(btwidth, btheight);
        JButton button = new JButton(buttonImg);
        
        
        
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(300,50));
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        
        
        button.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // Change the button's image when pressed
            ImageIcon pressedIcon = new MyImageIcon(text.replace(".png", "_PRESSED.png")).resize(btwidth, btheight);
            button.setIcon(pressedIcon);  // Set the new image when button is pressed
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Restore the original image when released
            ImageIcon releasedIcon = new MyImageIcon(text).resize(btwidth, btheight);
            button.setIcon(releasedIcon);  // Restore the original image
        }
    });
        
        return button;
    }
    
}