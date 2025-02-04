package snake;
import javax.swing.*;

public class App {

    public static void main(String[] args) {
        
        int brdWdth = 1200;
        int brdHght = 600;
        
        JFrame frame = new JFrame("Snake Ecstinzia");
        frame.setVisible(true);
        frame.setSize(brdWdth, brdHght);
        frame.setLocationRelativeTo(null); // OPENS THE WINDOW IN CENTER OF OUR SCREEN //
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SnakeGame snakeGame = new SnakeGame(brdWdth, brdHght);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
    
}
