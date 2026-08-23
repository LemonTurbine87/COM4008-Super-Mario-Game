import greenfoot.*;
import java.util.List;

public class MarioWorld extends World {
    public static final int CELL_SIZE = 32;
    public static final int VIEW_WIDTH = 800;
    public static final int VIEW_HEIGHT = 600;

    private int cameraX = 0;
    private int score = 0;
    private int coins = 0;
    private int lives = 3;
    private Mario mario;
    private Bowser bowser;

    // 0=Air, 1=Ground, 2=Brick, 3=Question, 4=Bowser
    private final int[][] LEVEL_MAP = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,3,2,3,0,0,0,0,0,2,3,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
        {0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public MarioWorld() {
        super(VIEW_WIDTH, VIEW_HEIGHT, 1, false);
        setBackgroundImage();
        loadTileMap();
        mario = new Mario();
        addObject(mario, 120, 350);
        updateHUD();
    }

    private void setBackgroundImage() {
        GreenfootImage bg = new GreenfootImage(VIEW_WIDTH, VIEW_HEIGHT);
        bg.setColor(new Color(110, 165, 255)); // Sky blue
        bg.fill();
        // Clouds
        bg.setColor(new Color(245, 245, 255, 220));
        bg.fillOval(80, 80, 120, 45);
        bg.fillOval(380, 120, 140, 50);
        bg.fillOval(620, 70, 110, 40);
        setBackground(bg);
    }

    private void loadTileMap() {
        for (int row = 0; row < LEVEL_MAP.length; row++) {
            for (int col = 0; col < LEVEL_MAP[row].length; col++) {
                int worldX = col * CELL_SIZE + (CELL_SIZE / 2);
                int worldY = row * CELL_SIZE + (CELL_SIZE / 2);
                int type = LEVEL_MAP[row][col];

                if (type == 1) {
                    addObject(new GroundTile(worldX, worldY), worldX, worldY);
                } else if (type == 2) {
                    addObject(new BlockTile(worldX, worldY), worldX, worldY);
                } else if (type == 3) {
                    addObject(new QuestionBlock(worldX, worldY), worldX, worldY);
                } else if (type == 4) {
                    bowser = new Bowser(worldX, worldY);
                    addObject(bowser, worldX, worldY);
                }
            }
        }
    }

    public void act() {
        updateCamera();
        updateHUD();
    }

    private void updateCamera() {
        if (mario != null) {
            int marioScreenX = mario.getX();
            if (marioScreenX > VIEW_WIDTH / 2) {
                int shift = marioScreenX - (VIEW_WIDTH / 2);
                cameraX += shift;
                mario.setLocation(VIEW_WIDTH / 2, mario.getY());

                List<ScrollActor> scrollables = getObjects(ScrollActor.class);
                for (ScrollActor sa : scrollables) {
                    sa.adjustPosition(cameraX);
                }
            }
        }
    }

    public void addScore(int pts) { score += pts; }
    public void addCoin() { coins++; }
    public void loseLife() {
        lives--;
        if (lives <= 0) {
            showText("GAME OVER - Press Reset", VIEW_WIDTH / 2, VIEW_HEIGHT / 2);
            Greenfoot.stop();
        }
    }

    private void updateHUD() {
        showText("MARIO SCORE: " + score + "   COINS: " + coins + "   LIVES: " + lives, 240, 25);
        if (bowser != null && bowser.getWorld() != null) {
            showText("BOWSER HP: " + bowser.getHp(), 680, 25);
        } else {
            showText("STAGE CLEAR!", 680, 25);
        }
    }

    public int getCameraX() { return cameraX; }
}
