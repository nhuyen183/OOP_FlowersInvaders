import Controller.KeyboardController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends ControlledGameObject {
    private BufferedImage image;

    ImageIcon ship = new ImageIcon("src/img/whale.png");
    ImageIcon bonusEnemy = new ImageIcon("images/bonusEnemySkin.gif");

    // Constructor for all ship objects
    public Player(int xPosition, int yPosition, Color color, KeyboardController control) {
        super(xPosition, yPosition, color, control);
        image = null;

        try{
           image = ImageIO.read(new File(Constants.PLAYER_IMAGE));
       } catch (IOException e){
        }

    }

    // Draw bonus enemy ship
    public void bonusDraw(Graphics g) {

        bonusEnemy.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // Draw player controlled ship
    public void playerDraw(Graphics g) {
        ship.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // Gets the hit box for all ship objects
    @Override
    public Rectangle getBounds() {
        Rectangle shipHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 50, 50);
        return shipHitbox;
    }

    // Used to move all ship objects
    public void move() {
        // Left arrow key press
        if (control.getKeyStatus(37)) {
            xPos -= 12;
        }
        // Right arrow key press
        if (control.getKeyStatus(39)) {
            xPos += 12;
        }

        // Move from edge to edge without stopping
        if (xPos > 800) {
            xPos = -50;
        }
        if (xPos < -50) {
            xPos = 800;
        }
    }
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public void draw(Graphics g) {
    }
}