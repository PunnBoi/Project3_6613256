package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tutorial {
    
    private JPanel contentpane;
    private JLabel drawpane,gifpane[];
    private JButton homeButton, creditsButton;
    private MyImageIcon backgroundImg, buttonImg,tutorialcomponent[];
    private CardLayout cardLayout;
    private Setting set;
    
    private int framewidth = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;
    
    private int squaresize = 100;
    
    
    public Tutorial(CardLayout cardlayout, JPanel contentPane){
    
        this.cardLayout = cardlayout;
        this.contentpane = contentPane;
        
    }
    
    public JPanel createTutorial()
    {
        JPanel tutorialPanel = new JPanel(new BorderLayout()); // Main panel with BorderLayout
        JLayeredPane layeredPane = new JLayeredPane();        // Layered pane for components
        layeredPane.setLayout(null);                         // Set null layout for manual positioning
        tutorialPanel.add(layeredPane, BorderLayout.CENTER);
        
        AddBackgroundComponent(layeredPane);
        AddButtonComponents(layeredPane);
        AddImgComponents(layeredPane);
        tutorialPanel.validate();
        return tutorialPanel;
    }
    
    public void AddBackgroundComponent(JLayeredPane layeredPane)
    {
        backgroundImg = new MyImageIcon(MyConstants.FILE_TUTORIAL_BG1).resize(framewidth, frameheight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setBounds(0, -35, framewidth, frameheight);
        layeredPane.add(drawpane, JLayeredPane.DEFAULT_LAYER);
        
    }
    
    public void AddImgComponents(JLayeredPane layeredPane) {
        
        tutorialcomponent = new MyImageIcon[6];
        gifpane = new JLabel[6];
        
        for (int i = 0; i<3;i++)
        {   
            int xPos = framewidth/6-squaresize/5+(framewidth/3-squaresize/3)*i;
            tutorialcomponent[i]  = new MyImageIcon(MyConstants.FILE_TTRCOMPO[i]).resize(squaresize, squaresize);
            gifpane[i] = new JLabel();
            gifpane[i].setIcon(tutorialcomponent[i]);
            gifpane[i].setBounds(xPos, 250, squaresize, squaresize);
            layeredPane.add(gifpane[i], JLayeredPane.PALETTE_LAYER);
        }
        
        for (int i = 3; i<5;i++)
        {   
            int xPos = framewidth/6+framewidth/3*(i-3)-144;
            tutorialcomponent[i]  = new MyImageIcon(MyConstants.FILE_TTRCOMPO[i]).resize(288, 72);
            gifpane[i] = new JLabel();
            gifpane[i].setIcon(tutorialcomponent[i]);
            gifpane[i].setBounds(xPos, 450, 288, 72);
            layeredPane.add(gifpane[i], JLayeredPane.PALETTE_LAYER);
        }
            
            int xPos = framewidth/6+framewidth/3*(2)-72;
            tutorialcomponent[5]  = new MyImageIcon(MyConstants.FILE_TTRCOMPO[5]).resize(72, 144);
            gifpane[5] = new JLabel();
            gifpane[5].setIcon(tutorialcomponent[5]);
            gifpane[5].setBounds(xPos, 378, 72, 144);
            layeredPane.add(gifpane[5], JLayeredPane.PALETTE_LAYER);
        
        
    }
    
    public void AddButtonComponents(JLayeredPane layeredPane) {

        set = new Setting();
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        
        int drawpaneWidth = buttonPanel.getPreferredSize().width;  // Replace with actual size if fixed
        int x = framewidth - 450;

        creditsButton = set.createButton(MyConstants.FILE_BUTTON_CREDITS_NORMAL,"large");
        homeButton = set.createButton(MyConstants.FILE_BUTTON_HOME_NORMAL,"small");

        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "6613113 Kavee Tangmanpakdeepong\n"
                        + "6613256 Punnapat Panat\n"
                        + "6613263 Passakorn Aiamwasu\n"
                        + "6613274 Sujira Duangkaewnapalai", "Credits", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                cardLayout.show(contentpane, "MenuPanel");
            }

        });
        

        buttonPanel.add(creditsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        buttonPanel.add(homeButton);

        buttonPanel.setBounds(x, frameheight / 2 + 70, framewidth, 350); 
        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

    }
}
