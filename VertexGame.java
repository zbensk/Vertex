package Vertex;

import javax.swing.*;

public class VertexGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameFrame frame = new GameFrame();
            }
        });
    }
    
}  
