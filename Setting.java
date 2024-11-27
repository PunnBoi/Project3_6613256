package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Setting extends JFrame {

    
    private JPanel              contentpane;
    private Setting             currentFrame;
    private JLabel              drawpane,stpane;
    private MyImageIcon         backgroundImg,uncheckedIcon,checkedIcon,buttonImg,settingIn;
    private JButton             backButton;
    private ButtonGroup         bgroup;
    private Game                game;
    private JComboBox           combo;
    private JToggleButton [] tb;
    private JPanel slpanel;
    private Menu menuFrame;
    
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private int btwidth  = 300;
    private int btheight = 50;
    
    private int bgn;
    
    public Setting(Game g,Menu menuFrame)
    {
        setTitle("Setting");
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        this.menuFrame = menuFrame;
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );  
        
        bgn = BGimg.picnoget();
        
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG[bgn]).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        
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
	stpane = new JLabel();
	stpane.setIcon(settingIn);
        
        int drawpaneWidth = stpane.getPreferredSize().width;  // Replace with actual size if fixed
        int drawpaneHeight = stpane.getPreferredSize().height;
        
        int x = (framewidth - drawpaneWidth) / 2;
        int y = (frameheight - drawpaneHeight) / 2;

        stpane.setBounds(x, y, drawpaneWidth, drawpaneHeight);
        layeredPane.add(stpane, JLayeredPane.PALETTE_LAYER);
        
    }
    
    public void AddBackgroundComponents(JLayeredPane layeredPane)
    {
        
        
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
        int hardnum = BGimg.diffget()-1;
        
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
            
            final int index = i+1;
            
            tb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Selected option: " + index);
                BGimg.diffset(index);
                }
            });
            
            bgroup.add( tb[i] );
            bpanel.add( tb[i] );
            if (i == hardnum) {
                tb[i].setSelected(true);
            }
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
        for(int i = 0; i < 5; i++)
        {
            items[i] = "Background "+(i+1);
        }
        
        
        combo = new JComboBox( items );
        combo.setFont(new Font("Athiti", Font.PLAIN, 20));
        combo.setFocusable(false);
        
        combo.addActionListener(e -> {

            String selectedImage = (String) combo.getSelectedItem();

            // Change background image based on selection
            switch (selectedImage) {
                case "Background 1":
                    System.out.println("Background 1");
                    ChangeBG(0);
                    break;
                case "Background 2":
                    System.out.println("Background 2");
                    ChangeBG(1);
                    break;
                case "Background 3":
                    System.out.println("Background 3");
                    ChangeBG(2);
                    break;
                case "Background 4":
                    System.out.println("Background 4");
                    ChangeBG(3);
                    break;
                case "Background 5":
                    System.out.println("Background 5");
                    ChangeBG(4);
                    break;
                default:
                    System.out.println(e + " button clicked!");; // No background
                    break;
            }
        });
        
        combo.setSelectedIndex(BGimg.picnoget());
        cbpanel.add(combo);
        cbpanel.setBounds(170, frameheight/2 + 3, framewidth, frameheight);
        layeredPane.add(cbpanel, JLayeredPane.MODAL_LAYER);
    }
    
    public void AddSliderComponents(JLayeredPane layeredPane)
    {
        slpanel = new JPanel();
        slpanel.setOpaque(false);
        
        int volumetemp = (int)game.getThemeSound().getVolume();
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, volumetemp);  // (min, max, initial value)

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
                //dispose();
                 setVisible(false);
                menuFrame.setVisible(true);
            menuFrame.setFocusable(true);
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
    
    private void ChangeBG(int i){
        BGimg.picnoset(i);
        bgn = BGimg.picnoget();
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG[bgn]).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        drawpane.setIcon(backgroundImg);
        drawpane.repaint();
    }
    
}