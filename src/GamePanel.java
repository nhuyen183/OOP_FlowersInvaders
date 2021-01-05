import Controller.KeyboardController;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GamePanel<explosion> extends JPanel implements State, ActionListener {
    private JPanel gamePanel;
    private Board board;

    private Timer gameTimer;
    private KeyboardController controller;
    // Controls size of game window and frame
    private final int gameWidth = 800;
    private final int gameHeight = 700;
    private final int framesPerSecond = 120;

    // Added Counters
    Random r = new Random();
    private int score = 0;
    private int level = 1;
    private int numberOfLives = 3;
    private int highScore;
    private int markerX, markerY;
    private static int bossHealth = 30;
    File f = new File("Highscores.txt");

    // Added Objects
    private Player playerShip;
    private Player singleLife;
    private Player bonusPlane;
    private Flower flower1;
    private Flower flower4;
    private NormalShot bullet;
    private Beam beam, beam2, beam3;

    // Added Booleans
    private boolean newBulletCanFire = true;
    private boolean newBeamCanFire = true;
    private boolean newBonusPlane = true;
    private boolean hitMarker = false;

    // Added Array Lists
    private ArrayList<Player> lifeList = new ArrayList();
    private ArrayList<Player> bonusPlaneList = new ArrayList();
    private ArrayList<Flower> flowers = new ArrayList();
    private ArrayList<Flower> flowers1 = new ArrayList();
    private ArrayList<Beam> beamList = new ArrayList();
    private ImageIcon background = new ImageIcon("images/backgroundSkin.jpg");


    // Added Audio files and streams
    private File beamSound = new File("sounds/alienBeam.wav");
    private File bulletSound = new File("sounds/bulletSound.wav");
    private File levelUpSound = new File("sounds/levelUpSound.wav");
    private File deathSound = new File("sounds/deathSound.wav");
    private File hitmarkerSound = new File("sounds/hitmarkerSound.wav");
    private File shieldSound = new File("sounds/shieldSound.wav");
    private File bossSound = new File("sounds/bossSound.wav");
    private File bonusSound = new File("sounds/bonusSound.wav");
    private File damageSound = new File("sounds/damageSound.wav");
    private AudioStream beamSoundAudio;
    private InputStream beamSoundInput;
    private AudioStream bulletSoundAudio;
    private InputStream bulletSoundInput;
    private AudioStream levelUpSoundAudio;
    private InputStream levelUpSoundInput;
    private AudioStream deathSoundAudio;
    private InputStream deathSoundInput;
    private AudioStream hitSoundAudio;
    private InputStream hitSoundInput;
    private AudioStream shieldSoundAudio;
    private InputStream shieldSoundInput;
    private AudioStream bossSoundAudio;
    private InputStream bossSoundInput;
    private AudioStream bonusSoundAudio;
    private InputStream bonusSoundInput;
    private AudioStream damageSoundAudio;
    private InputStream damageSoundInput;

    // EXTRA METHODS

    // Used in the Enemy class to help with the draw method for the boss
    public static int getBossHealth() {
        return bossHealth;
    }

    // SETUP GAME

    public final void setupGame() {

        // Sets flowers for normal levels
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            // Six rows
            for (int row = 0; row < 6; row++) {
                // 5 columns
                for (int column = 0; column < 5; column++) {
                    flower1 = new Flower((20 + (row * 100)), (20 + (column * 60)), level, 0, column, null, 40, 40); // Enemy speed will increase each level
                    flowers.add(flower1);
                }
            }
        }
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            AudioPlayer.player.start(bossSoundAudio); // Plays boss roar
            flower1 = new Flower(20, 20, 3, 0, 100, null, 150, 150);
            flowers.add(flower1);
        }
        // Gives directions on level 1
        if (level == 1) {
        }
        // Resets all controller movement
        controller.resetController();

        // Sets the player's ship values
        playerShip = new Player(270, 605, null, controller);

        // Sets the life counter
        for (int column = 0; column < numberOfLives; column++) {
            singleLife = new Player(48 + (column * 20), 10, Color.WHITE, null);
            lifeList.add(singleLife);
        }
    }

    // PAINT
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Sets background image
        background.paintIcon(null, graphics, 0, -150);

        // makes a string that says "+100" on flower hit
        if (bullet != null) {
            if (hitMarker) {
                graphics.setColor(Color.WHITE);
                if (level != 3 && level != 6 && level != 9 && level != 12) {
                    graphics.drawString("+ 100", markerX + 20, markerY -= 1);
                } else {
                    graphics.drawString("- 1", markerX + 75, markerY += 1);
                }
            }
        }
        // Draws the player's ship
        playerShip.playerDraw(graphics);

        //Draws 3 different kinds of flowers
        try {
            for (int index = 0; index < flowers.size(); index++) {
                flowers.get(index).draw(graphics);
            }

        } catch (IndexOutOfBoundsException e) {
        }

        // Draw a bullet on space bar press
        if (controller.getKeyStatus(32)) {
            if (newBulletCanFire) {
                bullet= new NormalShot(playerShip.getXPosition() + 90, playerShip.getYPosition() - 20, 0, Color.BLUE);
                AudioPlayer.player.start(bulletSoundAudio); // Plays bullet sound
                newBulletCanFire = false;
            }
        }
        // Only attempts to draw bullet after key press
        if (bullet != null) {
            bullet.draw(graphics);
        }

        // Generates random beams shot from flowers
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < flowers.size(); index++) {
                    if (r.nextInt(30) == index) {
                        beam = new Beam(flowers.get(index).getXPosition(), flowers.get(index).getYPosition(), 0, Color.YELLOW);
                        beamList.add(beam);
                        AudioPlayer.player.start(beamSoundAudio); // Plays beam sound for normal flowers
                    }
                    newBeamCanFire = false;
                }
            }
        }
        // Generates beams at a faster rate for boss
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < flowers.size(); index++) {
                    if (r.nextInt(5) == index) {
                        beam = new Beam(flowers.get(index).getXPosition() + 75, flowers.get(index).getYPosition() + 140, 0, Color.YELLOW);
                        beam2 = new Beam(flowers.get(index).getXPosition(), flowers.get(index).getYPosition() + 110, 0, Color.YELLOW);
                        beam3 = new Beam(flowers.get(index).getXPosition() + 150, flowers.get(index).getYPosition() + 110, 0, Color.YELLOW);
                        beamList.add(beam);
                        beamList.add(beam2);
                        beamList.add(beam3);
                        AudioPlayer.player.start(beamSoundAudio); // Plays beam sound for boss
                    }
                    newBeamCanFire = false;
                }
            }
        }
        // Draws the generated beams
        for (int index = 0; index < beamList.size(); index++) {
            beamList.get(index).draw(graphics);
        }
        // Generates random bonus plane
        if (newBonusPlane) {
            if (r.nextInt(3000) == 1500) {
                bonusPlane = new Player(-50, 30, Color.RED, null);
                bonusPlaneList.add(bonusPlane);
                newBonusPlane = false;
            }
        }
        // Draws bonus plane
        for (int index = 0; index < bonusPlaneList.size(); index++) {
            bonusPlaneList.get(index).bonusDraw(graphics);
        }

        // Sets the score display
        graphics.setColor(Color.WHITE);
        graphics.drawString("Score: " + score, 260, 20);

        // Sets the life counter display
        graphics.setColor(Color.WHITE);
        for (int index = 3; index < lifeList.size(); index--) {
            lifeList.get(index);;
        }
        graphics.drawString("Lives:" + lifeList.size(), 11, 20);


        // Sets level display
        graphics.setColor(Color.WHITE);
        graphics.drawString("Level " + level, 750, 20);

        // Sets Highscore display
        graphics.setColor(Color.WHITE);
        graphics.drawString("Highscore: " + highScore, 440, 20);

        // Draws a health display for boss level
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            graphics.setColor(Color.WHITE);
            graphics.drawString("Boss Health: " + bossHealth, 352, 600);
        }
    }


    // UPDATE GAME STATE

    public void updateGameState(int frameNumber) {

        // Allows player to move left and right
        playerShip.move();

        // Updates highscore
        try {
            Scanner fileScan = new Scanner(f);
            while (fileScan.hasNextInt()) {
                String nextLine = fileScan.nextLine();
                Scanner lineScan = new Scanner(nextLine);
                highScore = lineScan.nextInt();
            }
        } catch (FileNotFoundException e) {
        }
        // Adds option to reset highScore
        if (controller.getKeyStatus(82)) {
            int answer = JOptionPane.showConfirmDialog(null, "Would you like to reset the high score?", ":)", 0);
            controller.resetController();
            if (answer == 0) {
                try {
                    String scoreString = Integer.toString(0);
                    PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                    pw.write(scoreString);
                    pw.close();
                } catch (FileNotFoundException e) {
                }
            }
        }
        // Updates the high score text file if your score exceeds the previous high score
        try {
            if (score > highScore) {
                String scoreString = Integer.toString(score);
                PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                pw.write(scoreString);
                pw.close();
            }
        } catch (FileNotFoundException e) {
        }

        // Makes flowers move and change direction at borders
        if ((flowers.get(flowers.size() - 1).getXPosition() + flowers.get(flowers.size() - 1).getXVelocity()) > 760 ||
                (flowers.get(0).getXPosition() + flowers.get(0).getXVelocity()) < 0) {
            for (int index = 0; index < flowers.size(); index++) {
                flowers.get(index).setXVelocity(flowers.get(index).getXVelocity() * -1);
                flowers.get(index).setYPosition(flowers.get(index).getYPosition() + 10);
            }
        } else {
            for (int index = 0; index < flowers.size(); index++) {
                flowers.get(index).move();
            }
        }

        // Move bullet
        if (bullet != null) {
            bullet.setYPosition(bullet.getYPosition() - 15);
            if (bullet.getYPosition() < 0) {
                newBulletCanFire = true;
            }

            // Checks for collisions with normal flowers
            for (int index = 0; index < flowers.size(); index++) {
                if (bullet.isColliding(flowers.get(index))) {
                    AudioPlayer.player.start(hitSoundAudio); // Plays hitmarker sound if you hit an enemy
                    bullet = new NormalShot(0, 0, 0, null);
                    newBulletCanFire = true;
                    // Updates score for normal levels
                    if (level != 3 && level != 6 && level != 9 && level != 12) {
                        score += 100;
                        hitMarker = true;
                        markerX = flowers.get(index).getXPosition(); // Gets positions that the "+ 100" spawns off of
                        markerY = flowers.get(index).getYPosition();
                        flowers.remove(index);
                    }
                    // Updates score for boss levels
                    if (level == 3 || level == 6 || level == 9 || level == 12) {
                        hitMarker = true;
                        markerX = flowers.get(index).getXPosition(); // Gets positions that the "- 1" spawns off of
                        markerY = flowers.get(index).getYPosition() + 165;
                        bossHealth -= 1;
                        if (bossHealth == 0) {
                            flowers.remove(index);
                            score += 9000;// Bonus score for defeating boss
                        }
                    }
                }
            }
        }
        // Moves bonus plane
        if (!bonusPlaneList.isEmpty()) {
            for (int index = 0; index < bonusPlaneList.size(); index++) {
                bonusPlaneList.get(index).setXPosition(bonusPlaneList.get(index).getXPosition() + (2));
                if (bonusPlaneList.get(index).getXPosition() > 800) {
                    bonusPlaneList.remove(index);
                    newBonusPlane= true;
                }
            }
            // bonus plane and bullet collision
            for (int index = 0; index < bonusPlaneList.size(); index++) {
                if (bullet != null) {
                    if (bonusPlaneList.get(index).isColliding(bullet)) {
                        bonusPlaneList.remove(index);
                        bullet = new NormalShot(0, 0, 0, null);
                        newBulletCanFire = true;
                        newBonusPlane = true;
                        AudioPlayer.player.start(bonusSoundAudio); // Plays sound if player hits a bonus enemy
                        score += 5000; // add bonus to score on hit
                    }
                }
            }
        }

        // Moves beams on normal levels
        if (level != 3 && level != 6 && level != 9 && level != 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (4));
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }
        // Moves beams at a faster speed for boss
        if (level == 3 || level == 6 || level == 9 || level == 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (2 * level)); //Boss beam speed will increase each level
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }

        // Checks for beam and player collisions
        for (int index = 0; index < beamList.size(); index++) {
            if (beamList.get(index).isColliding(playerShip)) {
                beamList.remove(index);
                AudioPlayer.player.start(damageSoundAudio); // Plays damage sound
                lifeList.remove(lifeList.size() - 1); // Removes life if hit by bullet
            }
        }

        // Paces beam shooting by only allowing new beams to be fired once all old beams are off screen or have collided
        if (beamList.isEmpty()) {
            newBeamCanFire = true;
        }

        //Updates the life counter display 
        if (playerShip.isColliding) {
            int index = lifeList.size() - 1;
            lifeList.remove(index);
        }
        // Ends game if player runs out of lives
        else if (lifeList.isEmpty()) {
            AudioPlayer.player.start(deathSoundAudio); // Plays death sound when you run out of lives
            // Gives the player an option to play again or exit
            int answer = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "You lost the game with " + score + " points", 0);
            // If they choose to play again, this resets every element in the game
            if (answer == 0) {
                lifeList.clear();
                flowers.clear();
                beamList.clear();
                bonusPlaneList.clear();
                score = 0;
                level = 1;
                bossHealth = 30;
                numberOfLives = 3;
                newBulletCanFire = true;
                newBeamCanFire = true;
                newBonusPlane = true;
                setupGame();
            }
            // If they choose not to play again, it closes the game
            if (answer == 1) {
                System.exit(0);
            }
        }

        // Goes to next level, resets all lists, sets all counters to correct values
        if (flowers.isEmpty()) {
            beamList.clear();
            bonusPlaneList.clear();
            lifeList.clear();
            level += 1;
            bossHealth = 30;
            setupGame();
            AudioPlayer.player.start(levelUpSoundAudio); // Plays level up sound
        }

        // All streams needed for every sound in the game
        try {
            beamSoundInput = new FileInputStream(beamSound);
            beamSoundAudio = new AudioStream(beamSoundInput);
            bulletSoundInput = new FileInputStream(bulletSound);
            bulletSoundAudio = new AudioStream(bulletSoundInput);
            levelUpSoundInput = new FileInputStream(levelUpSound);
            levelUpSoundAudio = new AudioStream(levelUpSoundInput);
            deathSoundInput = new FileInputStream(deathSound);
            deathSoundAudio = new AudioStream(deathSoundInput);
            hitSoundInput = new FileInputStream(hitmarkerSound);
            hitSoundAudio = new AudioStream(hitSoundInput);
            shieldSoundInput = new FileInputStream(shieldSound);
            shieldSoundAudio = new AudioStream(shieldSoundInput);
            bossSoundInput = new FileInputStream(bossSound);
            bossSoundAudio = new AudioStream(bossSoundInput);
            bonusSoundInput = new FileInputStream(bonusSound);
            bonusSoundAudio = new AudioStream(bonusSoundInput);
            damageSoundInput = new FileInputStream(damageSound);
            damageSoundAudio = new AudioStream(damageSoundInput);
        } catch (IOException e) {
        }
    }

    // GAME PANEL
    public GamePanel(Board board) {
        this.board = board;

        gamePanel = new JPanel();

        // Set the size of the Panel
        this.setSize(gameWidth, gameHeight);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.BLACK);

        // Register KeyboardController as KeyListener
        controller = new KeyboardController();
        this.addKeyListener(controller);

        // Call setupGame to initialize fields
        this.setupGame();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }


    /**
     * Method to start the Timer that drives the animation for the game.
     */
    public void start() {
        // Set up a new Timer to repeat every 20 milliseconds (50 FPS)
        gameTimer = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Tracks the number of frames that have been produced.
            // May be useful for limiting action rates
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the game's state and repaint the screen
                updateGameState(frameNumber++);
                repaint();
            }
        });
        Timer gameTimerHitMarker = new Timer(1000, new ActionListener() {

            // Tracks the number of frames that have been produced.
            // May be useful for limiting action rates
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the game's state and repaint the screen
                hitMarker = false;
            }
        });

        gameTimer.setRepeats(true);
        gameTimer.start();
        gameTimerHitMarker.setRepeats(true);
        gameTimerHitMarker.start();
    }

    @Override
    public JPanel getMainPanel() {
        return gamePanel;
    }

    public void gameOver() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}