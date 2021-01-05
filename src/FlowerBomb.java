import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlowerBomb extends MovingGameObject implements Constants{
    private BufferedImage image;

    //Flowers shoot beams
    //constructor for beam
    public FlowerBomb(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, 0, color);

        try{
            image = ImageIO.read(new File(Constants.FLOWER_BOMB_IMAGE));
        } catch (IOException e){

        }
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 7, 15);
    }

    // Used to get the hit box of a beam
    @Override
    public Rectangle getBounds() {
        Rectangle beamHitbox = new Rectangle(xPos, yPos, 7, 15);
        return beamHitbox;
    }

    }
