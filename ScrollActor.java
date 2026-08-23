import greenfoot.*;

public abstract class ScrollActor extends Actor {
    protected int worldX;
    protected int worldY;

    public ScrollActor(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void adjustPosition(int cameraX) {
        setLocation(worldX - cameraX, worldY);
    }

    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public void setWorldX(int x) { this.worldX = x; }
    public void setWorldY(int y) { this.worldY = y; }
}
