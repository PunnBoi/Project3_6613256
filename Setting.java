package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Setting extends JFrame {

    
    private JPanel              contentpane;
    private Setting             currentFrame;
    private JLabel              drawpane;
    private MyImageIcon         backgroundImg,uncheckedIcon,checkedIcon,buttonImg,settingIn;
    private JButton             backButton;
    private ButtonGroup         bgroup;
    private Game                game;
    private JComboBox           combo;
    private JToggleButton [] tb;
    private JPanel slpanel;
    
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private int btwidth  = 300;
    private int btheight = 50;
    
    public Setting(Game g)
    {
        setTitle("Setting");
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
        setResizable(false);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );    
        
        game = g;
        
        try
	{
            String look4 = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(look4);
	}
	catch (Exception e) { System.out.println(e); }
        
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);  // Set null layout to manually position components
        contentpane.add(layeredPane, BorderLayout.CENTER);
        
        AddBackgroundComponents(layeredPane);
        AddSettingInterface(layeredPane);
        AddCheckboxComponents(layeredPane);
        AddComboComponents(layeredPane);
        AddSliderComponents(layeredPane);
        AddButtonComponents(layeredPane);
        validate();
        
    }
    
    public void AddSettingInterface(JLayeredPane layeredPane)
    {
        settingIn  = new MyImageIcon(MyConstants.FILE_SETTING_BG);
	drawpane = new JLabel();
	drawpane.setIcon(settingIn);
        
        int drawpaneWidth = drawpane.getPreferredSize().width;  // Replace with actual size if fixed
        int drawpaneHeight = drawpane.getPreferredSize().height;
        
        int x = (framewidth - drawpaneWidth) / 2;
        int y = (frameheight - drawpaneHeight) / 2;

        drawpane.setBounds(x, y, drawpaneWidth, drawpaneHeight);
        layeredPane.add(drawpane, JLayeredPane.PALETTE_LAYER);
        
    }
    
    public void AddBackgroundComponents(JLayeredPane layeredPane)
    {
        backgroundImg  = new MyImageIcon(MyConstants.FILE_MAIN_BG).resize(framewidth, frameheight);
	drawpane = new JLabel();
	drawpane.setIcon(backgroundImg);
        
        drawpane.setBounds(0, 0, framewidth, frameheight);
        layeredPane.add(drawpane, JLayeredPane.DEFAULT_LAYER);
        
    }
    
    public void AddCheckboxComponents(JLayeredPane layeredPane)
    {
        String [] items = new String[5];
	items[0] = "Easy  ";
	items[1] = "Normal  ";
	items[2] = "Hard  ";
	items[3] = "Nightmare  ";
	items[4] = "Endless";
         
        uncheckedIcon = new MyImageIcon(MyConstants.FILE_UNCHECKED_BUTTON );
        checkedIcon = new MyImageIcon(MyConstants.FILE_CHECKED_BUTTON );
        //Blank = new MyImageIcon(MyConstants.FILE_BLANK);
        
        bgroup  = new ButtonGroup();
	tb      = new JToggleButton[5];
	JPanel bpanel = new JPanel();
        bpanel.setLayout(new BoxLayout(bpanel, BoxLayout.X_AXIS));
        bpanel.setOpaque(false);
	for (int i=0; i < 5; i++) 
	{ 
            tb[i] = new JRadioButton( items[i] );
            tb[i].setIcon(uncheckedIcon);
            tb[i].setSelectedIcon(checkedIcon);
            tb[i].setOpaque(false);
            tb[i].setFocusable(false);
            tb[i].setFont(new Font(" ", Font.BOLD, 20));
            bgroup.add( tb[i] );
            //JLabel label = new JLabel();
            //label.setIcon(Blank);
            bpanel.add( tb[i] );
            //bpanel.add( label );
	}
        
        int bpaneleWidth = bpanel.getPreferredSize().width;
        int x = (framewidth - bpaneleWidth) / 2;
        
        bpanel.setBounds(x, frameheight / 2 - 420, framewidth, frameheight);
        layeredPane.add(bpanel, JLayeredPane.MODAL_LAYER);

        
    }
    
    public void AddComboComponents(JLayeredPane layeredPane)
    {
        
        //Blank = new MyImageIcon(MyConstants.FILE_BLANK );
        
        JPanel cbpanel = new JPanel();
        cbpanel.setOpaque(false);
        
        String [] items = new String[5];
	items[0] = "Background 1";
	items[1] = "Background 2";
	items[2] = "Background 3";
	items[3] = "Background 4";
	items[4] = "Background 5";
        
        
        combo = new JComboBox( items );
        combo.setFont(new Font("Athiti", Font.PLAIN, 20));
        combo.setFocusable(false);
        
        cbpanel.add(combo);
        cbpanel.setBounds(170, frameheight/2 + 3, framewidth, frameheight);
        layeredPane.add(cbpanel, JLayeredPane.MODAL_LAYER);
    }
    
    public void AddSliderComponents(JLayeredPane layeredPane)
    {
        slpanel = new JPanel();
        slpanel.setOpaque(false);
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);  // (min, max, initial value)

        // Optionally, set tick marks and labels
        slider.setPreferredSize(new Dimension(300, 100));
        slider.setOpaque(false);
        slider.setPaintTicks(false);      // Show tick marks
        slider.setPaintLabels(false);    // Show labels for major ticks
        slider.setFocusable(false);

        slider.addChangeListener(e -> {
            int volume = slider.getValue();
            //volumeLabel.setText("Volume: " + volume + "%");
            if (game != null && game.getThemeSound() != null) {
            game.getThemeSound().setVolume(volume / 100.0f);  // Assuming game has a method getThemeSound()
        }
        });
        
        slpanel.add(slider);
        slpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        //slpanel.add(valueLabel);
        slpanel.setBounds(150, frameheight/2+70 , framewidth, frameheight);
        layeredPane.add(slpanel, JLayeredPane.MODAL_LAYER);
    }
    
    public void AddButtonComponents(JLayeredPane layeredPane)
    {
        //contentpane = (JPanel)getContentPane();
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        backButton     = createButton(MyConstants.FILE_BUTTON_BACK_NORMAL);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                dispose();
                setFocusable(false);
                game.openMenu();
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.setBounds(0, frameheight-200, framewidth, 350);  // Position buttons
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