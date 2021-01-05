import javax.swing.*;
import java.awt.*;

interface Constants {

    //Dimensions
    Dimension SCREEN_SIZE = new Dimension(1360, 700);
    Dimension HEADER_SIZE = new Dimension(1200, 150);
    Dimension BUTTON_SIZE = new Dimension(150, 75);
    Dimension BUTTON_RETURN_SIZE = new Dimension(300,75);

    Dimension HEADER_PADDING = new Dimension(0, 20);
    Dimension HEADER_BUTTON_PADDING = new Dimension(0, 100);
    Dimension BUTTON_PADDING = new Dimension(0,  30);
    Dimension RETURN_BUTTON_PADDING = new Dimension(0,150);
    Dimension QUIT_BUTTON_PADDING = new Dimension(0, 350);

    Dimension HIGH_SCORES_TEXT_BOX = new Dimension(1100,100);

    Dimension LOST_BUTTON_PADDING = new Dimension(0, 400);

    //Images

    //Fonts
    Font FONT_HEADER = new Font("sans", Font.BOLD, 60);
    Font FONT_BUTTON = new Font("sans", Font.BOLD, 20);
    Font FONT_TEXT = new Font("sans", Font.PLAIN, 16);

    String FLOWER_BOMB_IMAGE = "src/img/waterdrop.png";

    int FLOWER_MOVEMENT_MODIFIER = 25;

    String PLAYER_IMAGE = "src/img/whale.gif";

    ImageIcon EXPLOSION_IMG = new ImageIcon("src/img/flower2.png");

    static final int BLOOM_W = 128;
    static final int BLOOM_ROWS = 3;
    static final int BLOOM_COL = 3;
    static final int BLOOM_H = 128;
    static final int BLOOM_STEP = 15;

}
