package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Setting {

    
    private JPanel              contentpane;
    private Setting             currentFrame;
    private JLabel              drawpane,stpane;
    private MyImageIcon         backgroundImg,uncheckedIcon,checkedIcon,buttonImg,settingIn,pressedIcon,releasedIcon;
    private JButton             backButton;
    private ButtonGroup         bgroup;
    private Game                game;
    private JComboBox           combo;
    private JToggleButton [] tb;
    private JPanel slpanel;
    private CardLayout cardLayout;
    
    private int framewidth  = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private int btwidth  = 300;
    private int btheight = 50;
    private int smallbtwidth  = 50;
    
    private int bgn;
        
    public Setting(Game g, CardLayout cardlayout, JPanel contentPane)
    {   
        this.cardLayout = cardlayout;
        this.contentpane = contentPane;
        this.game = g;
        
        bgn = sSetting.picnoget();
        
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG[bgn]).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
    }
    
    public Setting(){}
    
    public JPanel createSetting()
    {
        JPanel settingPanel = new JPanel(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        settingPanel.add(layeredPane, BorderLayout.CENTER);

        try {
            String look4 = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(look4);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Add components to the layeredPane
        AddBackgroundComponents(layeredPane);
        AddSettingInterface(layeredPane);
        AddCheckboxComponents(layeredPane);
        AddComboComponents(layeredPane);
        AddSliderComponents(layeredPane);
        AddButtonComponents(layeredPane);

        settingPanel.validate();
        return settingPanel;
    }
    
    public void AddSettingInterface(JLayeredPane layeredPane)
    {
        settingIn  = new MyImageIcon(MyConstants.FILE_SETTING_BG);
	stpane = new JLabel();
	stpane.setIcon(settingIn);
        
        int drawpaneWidth = stpane.getPreferredSize().width;
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
        
        int hardnum = sSetting.diffget()-1;
        
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
            
            final int index = i;
            
            tb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                sSetting.diffset(index);
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

            switch (selectedImage) {
                case "Background 1":
                    ChangeBG(0);
                    break;
                case "Background 2":
                    ChangeBG(1);
                    break;
                case "Background 3":
                    ChangeBG(2);
                    break;
                case "Background 4":
                    ChangeBG(3);
                    break;
                case "Background 5":
                    ChangeBG(4);
                    break;
                default:
                    break;
            }
        });
        
        combo.setSelectedIndex(sSetting.picnoget());
        cbpanel.add(combo);
        cbpanel.setBounds(170, frameheight/2 + 3, framewidth, frameheight);
        layeredPane.add(cbpanel, JLayeredPane.MODAL_LAYER);
    }
    
    public void AddSliderComponents(JLayeredPane layeredPane)
    {
        slpanel = new JPanel();
        slpanel.setOpaque(false);
        
        int volumetemp = (int)game.getThemeSound().getVolume();
        
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, volumetemp);

        slider.setPreferredSize(new Dimension(300, 100));
        slider.setOpaque(false);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setFocusable(false);

        slider.addChangeListener(e -> {
            int volume = slider.getValue();
            sSetting.setSound(volume);
            if (game != null && game.getThemeSound() != null) {
            game.getThemeSound().setVolume(sSetting.getSound()/100f);
        }
        });
        
        slpanel.add(slider);
        slpanel.add(Box.createRigidArea(new Dimension(10, 0)));
        slpanel.setBounds(150, frameheight/2+70 , framewidth, frameheight);
        layeredPane.add(slpanel, JLayeredPane.MODAL_LAYER);
    }
    
    public void AddButtonComponents(JLayeredPane layeredPane)
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        backButton     = createButton(MyConstants.FILE_BUTTON_BACK_NORMAL,"large");
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        cardLayout.show(contentpane, "MenuPanel");
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.setBounds(0, frameheight-200, framewidth, 350);
        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);
        
    }
    
    public JButton createButton(String text, String size) {
        
        if(size=="large"){
            buttonImg = new MyImageIcon(text).resize(btwidth, btheight);
        } else {
            buttonImg = new MyImageIcon(text).resize(smallbtwidth, btheight);
        }
        JButton button = new JButton(buttonImg);
        
        button.setFocusPainted(false);
        if(size=="large"){
        button.setMaximumSize(new Dimension(300,50));
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        } else {
            button.setMaximumSize(new Dimension(50,50));
            button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        }
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        
        
        button.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(size=="large")
            pressedIcon = new MyImageIcon(text.replace(".png", "_PRESSED.png")).resize(btwidth, btheight);
            else pressedIcon = new MyImageIcon(text.replace(".png", "_PRESSED.png")).resize(smallbtwidth, btheight);
            button.setIcon(pressedIcon);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(size=="large")
            releasedIcon = new MyImageIcon(text).resize(btwidth, btheight);
            else releasedIcon = new MyImageIcon(text).resize(smallbtwidth, btheight);
            button.setIcon(releasedIcon);
        }
    });
        
        return button;
    }
    
    public void ChangeBG(int i){
        sSetting.picnoset(i);
        bgn = sSetting.picnoget();
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG[bgn]).
                resize(MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        drawpane.setIcon(backgroundImg);
        drawpane.repaint();
    }
    
}