import greenfoot.*;

public class Bowser extends ScrollActor {
    private int hp = 5;
    private int timer = 0;
    private int direction = -1;
    private int animFrame = 0;
    private double vY = 0;
    private int startX;

    public Bowser(int wx, int wy) {
        super(wx, wy);
        this.startX = wx;
        renderVisual();
    }

    public void act() {
        timer++;
        animFrame++;

        if (timer % 50 == 0) {
            direction = (worldX > startX) ? -1 : 1;
        }
        if (timer % 80 == 0) {
            vY = -7.0;
        }

        vY += 0.5;
        if (vY > 8.0) vY = 8.0;
        worldY += (int) vY;
        if (worldY > 430) {
            worldY = 430;
            vY = 0;
        }

        worldX += direction * 2;

        if (timer % 110 == 0) {
            shootFireball();
        }

        renderVisual();
        if (getWorld() instanceof MarioWorld) {
            adjustPosition(((MarioWorld) getWorld()).getCameraX());
        }
    }

    private void shootFireball() {
        if (getWorld() != null) {
            Fireball fb = new Fireball(worldX - 35, worldY - 10, -6);
            getWorld().addObject(fb, getX() - 35, getY() - 10);
            try { Greenfoot.playSound("fireball.wav"); } catch (Exception e) {}
        }
    }

    public void takeDamage() {
        hp--;
        if (hp <= 0 && getWorld() != null) {
            if (getWorld() instanceof MarioWorld) {
                ((MarioWorld) getWorld()).addScore(1000);
            }
            getWorld().removeObject(this);
        }
    }

    public int getHp() { return hp; }

    private void renderVisual() {
        GreenfootImage img = new GreenfootImage(70, 70);
        img.setColor(new Color(34, 139, 34));
        img.fillOval(10, 15, 45, 45);
        img.setColor(new Color(139, 69, 19));
        img.fillOval(25, 10, 35, 40);
        img.setColor(Color.WHITE);
        img.fillOval(45, 15, 8, 8);
        img.fillOval(48, 30, 8, 8);
        img.setColor(new Color(40, 160, 40));
        img.fillOval(2, 20, 25, 25);
        img.setColor(Color.RED);
        img.fillOval(8, 22, 6, 6);
        if (timer % 110 > 85) {
            img.setColor(Color.ORANGE);
            img.fillOval(0, 30, 12, 10);
        }
        img.setColor(Color.BLACK);
        img.fillRect(10, 2, 50, 6);
        img.setColor(Color.RED);
        img.fillRect(11, 3, (int) (48 * (Math.max(0, hp) / 5.0)), 4);
        setImage(img);
    }
}
