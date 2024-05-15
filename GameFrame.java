package Vertex;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    
    private JFrame frame;

    public GameFrame() {

        // Create frame and game panel
        frame = new JFrame();
        GamePanel panel = new GamePanel();
        frame.add(panel);
        
        // Initialize frame with default settings
        frame.setTitle("Vertex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
