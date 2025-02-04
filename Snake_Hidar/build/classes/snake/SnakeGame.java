package snake;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile{
        int x, y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int tileSize= 40;
    int brdWdth;
    int brdHght;
    
    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Image snakeImage;
    
    //Food
    Tile food;
    Random random;
    Image foodImage;
    
    //GameeLogic
    Timer gameLoop;
    int veloX;
    int veloY;
    boolean gameOver = false;
    
    public SnakeGame(int brdWdth, int brdHght) {
        this.brdWdth = brdWdth;
        this.brdHght = brdHght;
        setPreferredSize(new Dimension(this.brdWdth, this.brdHght));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeImage = new ImageIcon(getClass().getResource("/snake/Snake.png")).getImage();
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        
        food = new Tile(10, 10);
        foodImage = new ImageIcon(getClass().getResource("/snake/foodImage.png")).getImage();
        random = new Random();
        placeFood();
        
        veloX=0;
        veloY=0;
        
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        //Grid
        for(int i=0 ;i<brdWdth/tileSize ;i++){
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, brdHght);
            g.drawLine(0, i*tileSize, brdWdth, i*tileSize);
        }
        
        //Food
        g.setColor(Color.red);
        g.drawImage(foodImage, food.x*tileSize, food.y*tileSize, tileSize, tileSize, null);
        
        //Snakes Head
        g.setColor(Color.yellow);
        g.drawImage(snakeImage, snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, null);
        
        //Snake Body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }
        
        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+ String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        else{
            g.drawString("Catch El Zayfa!!", tileSize+400, tileSize);
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
    }
    
    public void placeFood(){
        food.x = random.nextInt(brdWdth / tileSize); 
        food.y = random.nextInt(brdHght / tileSize);
    }
    
    public void move(){
        //Eat Food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        
        //Snake Body
        for(int i=snakeBody.size()-1; i>=0 ;i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
                        
            }
        }
            
        //SnakeHead
        snakeHead.x += veloX;
        snakeHead.y += veloY;
        
        //GameOver
        for(int i=snakeBody.size()-1; i>=0 ;i--){
            Tile snakePart = snakeBody.get(i);
            //Collision with Head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > brdWdth || snakeHead.y*tileSize > brdHght || snakeHead.y*tileSize < 0){
            gameOver = true;
        }
    }
    
    public boolean collision(Tile t1, Tile t2){
        return t1.x == t2.x && t1.y == t2.y;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if((ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) && veloY != 1){
            veloX = 0;
            veloY = -1;
        }
        else if((ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S)&& veloY != -1){
            veloX = 0;
            veloY = 1;
        }
        else if((ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A)&& veloX != 1){
            veloX = -1;
            veloY = 0;
        }
        else if((ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D)&& veloX != -1){
            veloX = 1;
            veloY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
