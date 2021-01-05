import javax.swing.*;
import java.awt.*;

public class Flower extends MovingGameObject{

    ImageIcon flower1 = new ImageIcon("src/img/flower1.png");
    ImageIcon flower2 = new ImageIcon("src/img/flower1.png");
    ImageIcon flower3 = new ImageIcon("src/img/flower1.png");
    ImageIcon flower4 = new ImageIcon("src/img/flower2.png");
    ImageIcon Boss = new ImageIcon("src/img/flower5.png");
    ImageIcon Boss2 = new ImageIcon("src/img/flower5.png");
    ImageIcon Boss3 = new ImageIcon("src/img/flower5.png");

    private int flowertype, width, height;

    // Constructor
    public Flower(int xPosition, int yPosition, int xVelocity, int yVelocity, int flowerType, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.flowertype = flowerType;
        this.width = width;
        this.height = height;
    }

    @Override
    // Draws flower
    public void draw(Graphics g) {

        // Varient 1
        if (this.flowertype % 3 == 0) {
            flower1.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            // Varient 2
        } else if (this.flowertype % 3 == 1 && this.flowertype != 100) {
            flower2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            // Varient 3
        } else if (this.flowertype % 3 == 2) {
            flower3.paintIcon(null, g, this.getXPosition(), this.getYPosition());

            // Boss Enemy

        }if (this.flowertype == 100) {
            if (GamePanel.getBossHealth() > 20) {
                Boss.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            } else if (GamePanel.getBossHealth() > 10) {
                Boss2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            } else if (GamePanel.getBossHealth() > 0) {
                Boss3.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
        }
    }

    // Gets the hitbox for normal eneimes
    @Override
    public Rectangle getBounds() {
        Rectangle flowerHitBox = new Rectangle(this.getXPosition(), this.getYPosition(), width, height);
        return flowerHitBox;
    }

    // Used to move all enemies
    @Override
    public void move() {
        xPos += xVel;
    }
}
