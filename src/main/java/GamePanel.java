import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    final int SCALE = 3;

    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Multiply 16px size by scale 3 for a 48px (48x48) scale up size
    final int MAX_SCREEN_COL = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    public GamePanel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Sets the width and height of the JPanel Window
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
    }

    //Instantiates and starts the "gameThread"
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //Automatically calls run method.
    }

    //Implements runnable requires run method
    @Override
    public void run() {

        //As long as this "gameThread" exists
        while(gameThread != null){


            // UPDATE: update information - character positions
            update();

            // DRAW: draw screen with updated information
            repaint();
        }

    }

    public void update(){

    }

    //Built in method - Use repaint to call it
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);

        g2.fillRect(100,100, TILE_SIZE, TILE_SIZE);

        g2.dispose(); // Dispose this graphics context to release any system resources that it's using
    }
}
