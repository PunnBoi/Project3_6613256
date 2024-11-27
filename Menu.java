package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Menu extends JFrame {

    private JPanel contentpane;
    private Menu currentFrame;
    private JLabel drawpane;
    private JButton startButton, ttrButton, settingButton, exitButton;
    private MyImageIcon backgroundImg, buttonImg, Timg;
    private Game game;
    private CardLayout cardLayout;

    private int framewidth = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;

    private Setting set;
    private Tutorial ttr;
    
    public Menu(Game g) {
        
        setTitle("Project3");
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        game =g;
        
        cardLayout = new CardLayout();
        contentpane = new JPanel(cardLayout);
        setContentPane(contentpane);

        JPanel menuPanel = createMenuPanel();
        contentpane.add(menuPanel, "MenuPanel");
        
        set = new Setting(g, cardLayout, contentpane);
        JPanel settingPanel = set.createSetting();
        contentpane.add(settingPanel, "SettingPanel");
        
        ttr = new Tutorial(cardLayout, contentpane);
        JPanel ttrPanel = ttr.createTutorial();
        contentpane.add(ttrPanel, "ttrPanel");
        
        cardLayout.show(contentpane, "MenuPanel");

        setVisible(true);

    }
    
    public JPanel createMenuPanel()
    {
        JPanel menuPanel = new JPanel(new BorderLayout());

        // Create the layered pane for the menu panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Allows manual positioning of components
        menuPanel.add(layeredPane, BorderLayout.CENTER);

        // Add components to the layered pane
        AddBackgroundComponents(layeredPane);
        AddTitleComponents(layeredPane);
        AddButtonComponents(layeredPane);
        AddJTextComponents(layeredPane);

        return menuPanel;
    }

    public void AddBackgroundComponents(JLayeredPane layeredPane) {
        backgroundImg = new MyImageIcon(MyConstants.FILE_MAIN_BG2).resize(framewidth, frameheight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setBounds(0, -35, framewidth, frameheight);
        layeredPane.add(drawpane, JLayeredPane.DEFAULT_LAYER);

    }

    public void AddTitleComponents(JLayeredPane layeredPane) {
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setOpaque(false);
        Timg = new MyImageIcon(MyConstants.FILE_TITLE).resize(720, 320);;
        JLabel Title = new JLabel();
        Title.setIcon(Timg);
        
        int x = (framewidth - 720) / 2;
        
        
        Title.setBounds(x, 50, 720, 320);
        layeredPane.setLayout(null);
        layeredPane.add(Title, JLayeredPane.PALETTE_LAYER);

        /*JLabel title = new JLabel("Touch the sky");
        title.setFont(new Font("Algerian", Font.PLAIN, 128));
        title.setForeground(new Color(140, 9, 156));
        //titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel title2 = new JLabel("JAVA PROJECT 3");
        title2.setFont(new Font("Algerian", Font.PLAIN, 64));
        title2.setForeground(new Color(116, 9, 56));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, -25)));
        titlePanel.add(title2);*/
//        titlePanel.setBounds(0, 150, framewidth, 400);
//        layeredPane.add(titlePanel, JLayeredPane.PALETTE_LAYER);

    }

    public void AddButtonComponents(JLayeredPane layeredPane) {

        set = new Setting(game, cardLayout, contentpane);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        startButton = set.createButton(MyConstants.FILE_BUTTON_START_NORMAL,"large");
        ttrButton = set.createButton(MyConstants.FILE_BUTTON_TUTORIAL_NORMAL,"large");
        settingButton = set.createButton(MyConstants.FILE_BUTTON_SETTING_NORMAL,"large");
        exitButton = set.createButton(MyConstants.FILE_BUTTON_QUIT_NORMAL,"large");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                //dispose();
                setVisible(false);
                setFocusable(false);
                game.openGame(); // To start the game.
            }
        });

        ttrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e + " button clicked!");
                cardLayout.show(contentpane, "ttrPanel");
            }

        });
        
        // Setting button
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                cardLayout.show(contentpane, "SettingPanel");
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
        buttonPanel.add(settingButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(exitButton);

        //contentpane.add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setBounds(0, frameheight / 2 + 30, framewidth, 350);  // Position buttons
        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        validate();

    }
    
    public void AddJTextComponents(JLayeredPane layeredPane){
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        JTextField textField = new JTextField(20); 
        textField.setBorder(null);
        String placeholder = "Code here";

        textField.setText(placeholder);
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        textField.setHorizontalAlignment(JTextField.CENTER);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
        
        textField.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = textField.getText();

            // Validate input
            if (input.isEmpty() || input.equals(placeholder)) {
                // Show dialog for invalid input
                JOptionPane.showMessageDialog(
                    null,
                    "Please Enter Correct Input",
                    "Wrong Input",
                    JOptionPane.INFORMATION_MESSAGE
                );
                textField.setForeground(Color.GRAY);
                textField.setText(placeholder); // Reset to placeholder
            } else {
                // Handle specific inputs
                switch (input.toLowerCase()) {
                    case "credit":
                        JOptionPane.showMessageDialog(null,
                        "6613113 Kavee Tangmanpakdeepong\n"
                        + "6613256 Punnapat Panat\n"
                        + "6613263 Passakorn Aiamwasu\n"
                        + "6613274 Sujira Duangkaewnapalai", "Credits", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case "car":
                        System.out.println("car");
                        game.changeThemeSound();
                        break;

                    default:
                        // Handle other inputs
                        System.out.println("Unrecognized command: " + input);
                        JOptionPane.showMessageDialog(
                            null,
                            "Unrecognized input: " + input,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        break;
                }
            }
        }
    });

        panel.add(textField);
        panel.setBounds(20, frameheight-150, 200, 50); 
        layeredPane.add(panel, JLayeredPane.MODAL_LAYER);

    }

}
