import greenfoot.*;

public abstract class Tile extends ScrollActor {
    public Tile(int worldX, int worldY) {
        super(worldX, worldY);
    }
}

class GroundTile extends Tile {
    public GroundTile(int wx, int wy) {
        super(wx, wy);
        GreenfootImage img = new GreenfootImage(32, 32);
        img.setColor(new Color(90, 160, 40));
        img.fillRect(0, 0, 32, 6);
        img.setColor(new Color(130, 75, 30));
        img.fillRect(0, 6, 32, 26);
        img.setColor(new Color(80, 40, 15));
        img.drawRect(0, 0, 31, 31);
        setImage(img);
    }
}

class BlockTile extends Tile {
    public BlockTile(int wx, int wy) {
        super(wx, wy);
        GreenfootImage img = new GreenfootImage(32, 32);
        img.setColor(new Color(185, 90, 40));
        img.fill();
        img.setColor(new Color(60, 20, 0));
        img.drawRect(0, 0, 31, 31);
        img.drawLine(0, 15, 31, 15);
        img.drawLine(15, 0, 15, 15);
        img.drawLine(7, 16, 7, 31);
        img.drawLine(23, 16, 23, 31);
        setImage(img);
    }
}

class QuestionBlock extends Tile {
    private boolean hit = false;
    public QuestionBlock(int wx, int wy) {
        super(wx, wy);
        drawBlock();
    }

    private void drawBlock() {
        GreenfootImage img = new GreenfootImage(32, 32);
        if (!hit) {
            img.setColor(new Color(245, 170, 20));
            img.fill();
            img.setColor(new Color(120, 70, 0));
            img.drawRect(0, 0, 31, 31);
            img.setColor(Color.WHITE);
            img.setFont(new Font("Monospaced", true, false, 20));
            img.drawString("?", 10, 24);
        } else {
            img.setColor(new Color(120, 110, 100));
            img.fill();
            img.setColor(new Color(60, 55, 50));
            img.drawRect(0, 0, 31, 31);
        }
        setImage(img);
    }

    public void hitBlock() {
        if (!hit) {
            hit = true;
            drawBlock();
            if (getWorld() instanceof MarioWorld) {
                MarioWorld mw = (MarioWorld) getWorld();
                mw.addScore(100);
                mw.addCoin();
                try { Greenfoot.playSound("coin.wav"); } catch (Exception e) {}
                mw.addObject(new Coin(worldX, worldY - 32), getX(), getY() - 32);
            }
        }
    }
}

class Coin extends ScrollActor {
    private int lifeTimer = 25;
    private int anim = 0;

    public Coin(int wx, int wy) {
        super(wx, wy);
        updateVisual();
    }

    public void act() {
        worldY -= 3;
        anim++;
        updateVisual();
        if (getWorld() instanceof MarioWorld) {
            adjustPosition(((MarioWorld) getWorld()).getCameraX());
        }
        if (--lifeTimer <= 0 && getWorld() != null) {
            getWorld().removeObject(this);
        }
    }

    private void updateVisual() {
        GreenfootImage img = new GreenfootImage(20, 20);
        img.setColor(new Color(255, 215, 0));
        int width = Math.max(4, (int) (18 * Math.abs(Math.cos(anim * 0.3))));
        img.fillOval(10 - width / 2, 1, width, 18);
        img.setColor(new Color(180, 140, 0));
        img.drawOval(10 - width / 2, 1, width, 18);
        setImage(img);
    }
}
