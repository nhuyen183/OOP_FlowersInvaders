import javax.swing.*;

public class Board extends JFrame
{
    private GamePanel game;
    private JFrame jFrame;
    private State currentState;

    public void setCurrentState(State newState) {
        this.jFrame.getContentPane().removeAll();
        this.jFrame.repaint();
        this.currentState = newState;
        this.jFrame.getContentPane().add(this.currentState.getMainPanel());
        this.jFrame.pack();
    }

    public Board()
    {
        // Add text to title bar
        super("Flowers Invaders");

        // Make sure the program exits when the close button is clicked
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.jFrame = new JFrame("Flowers Invaders GUI");
        this.jFrame.setPreferredSize(Constants.SCREEN_SIZE);
        this.jFrame.setDefaultCloseOperation(3);
        this.jFrame.getContentPane().setLayout(new BoxLayout(this.jFrame.getContentPane(), 1));
        this.jFrame.setResizable(false);
        this.jFrame.pack();
        this.jFrame.setVisible(true);
        this.setCurrentState(new StateMenu(this));

        // Create an instance of the Game class and turn on double buffering
        //  to ensure smooth animation
        game = new GamePanel(null);
        game.setDoubleBuffered(true);

        //Add the Breakout instance to this frame's content pane to display it
        this.getContentPane().add(game);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Start the game
        game.start();
    }


    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new Board().setVisible(false);

            }
        });

    }
}