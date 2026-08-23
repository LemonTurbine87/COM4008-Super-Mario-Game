import greenfoot.*;

public class Fireball extends ScrollActor {
    private int speed;
    private int anim = 0;

    public Fireball(int wx, int wy, int speed) {
        super(wx, wy);
        this.speed = speed;
        renderVisual();
    }

    public void act() {
        worldX += speed;
        anim++;
        renderVisual();
        if (getWorld() instanceof MarioWorld) {
            adjustPosition(((MarioWorld) getWorld()).getCameraX());
        }
        if (getWorld() != null && (getX() < -50 || getX() > getWorld().getWidth() + 50)) {
            getWorld().removeObject(this);
        }
    }

    private void renderVisual() {
        GreenfootImage img = new GreenfootImage(24, 24);
        img.setColor(new Color(255, 60, 0));
        img.fillOval(2, 2, 20, 20);
        img.setColor(new Color(255, 220, 0));
        img.fillOval(6, 6, 12, 12);
        setImage(img);
    }
}
