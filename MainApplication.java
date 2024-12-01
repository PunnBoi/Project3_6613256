package Project3_6613256;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainApplication extends JFrame {

    private JPanel contentpane;
    private MainApplication currentFrame;
    private JLabel drawpane;
    private JButton startButton, ttrButton, settingButton, exitButton;
    private MyImageIcon backgroundImg, buttonImg, Timg;
    private Game game;
    private CardLayout cardLayout;

    private int framewidth = MyConstants.FRAMEWIDTH;
    private int frameheight = MyConstants.FRAMEHEIGHT;

    private Setting set;
    private Tutorial ttr;
    
    public MainApplication(Game g) {
        
        setTitle("Project3: Reach the Sky");
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

        JLayeredPane layeredPane = new JLayeredPane();
        menuPanel.add(layeredPane, BorderLayout.CENTER);

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
                setVisible(false);
                setFocusable(false);
                game.openGame();
            }
        });

        ttrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        buttonPanel.setBounds(0, frameheight / 2 + 30, framewidth, 350);
        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);

        validate();

    }
    
    public void AddJTextComponents(JLayeredPane layeredPane){
        
        set = new Setting(game, cardLayout, contentpane);
        
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

            if (input.isEmpty() || input.equals(placeholder)) {
                JOptionPane.showMessageDialog(
                    null,
                    "Please Enter Correct Input",
                    "Wrong Input",
                    JOptionPane.INFORMATION_MESSAGE
                );
                textField.setForeground(Color.GRAY);
                textField.setText(placeholder);
            } else {
                switch (input.toLowerCase()) {
                    case "credit":
                        JOptionPane.showMessageDialog(null,
                        "6613113 Kavee Tangmanpakdeepong\n"
                        + "6613256 Punnapat Panat\n"
                        + "6613263 Passakorn Aiamwasu\n"
                        + "6613274 Sujira Duangkaewnapalai", "Credits", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case "car":
                        sSetting.picnoset(5);
                        game.changeThemeSound();
                        set.ChangeBG(5);
                        int x = sSetting.picnoget();
                        break;

                    default:
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
