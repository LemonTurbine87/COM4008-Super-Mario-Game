import greenfoot.*;

public class Mario extends Actor {
    private double vX = 0;
    private double vY = 0;
    private final double GRAVITY = 0.75;
    private final double JUMP_STRENGTH = -14.5;
    private final double MOVE_SPEED = 4.5;
    private boolean onGround = false;
    private int facing = 1; // 1 = right, -1 = left
    private int animTick = 0;

    public Mario() {
        renderVisual();
    }

    public void act() {
        handleInput();
        applyGravity();
        moveHorizontally();
        moveVertically();
        checkHazards();
        renderVisual();
    }

    private void handleInput() {
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            vX = -MOVE_SPEED;
            facing = -1;
            animTick++;
        } else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            vX = MOVE_SPEED;
            facing = 1;
            animTick++;
        } else {
            vX = 0;
        }

        if ((Greenfoot.isKeyDown("space") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) && onGround) {
            vY = JUMP_STRENGTH;
            onGround = false;
        }
    }

    private void applyGravity() {
        vY += GRAVITY;
        if (vY > 13.0) vY = 13.0; // Terminal velocity
    }

    private void moveHorizontally() {
        setLocation(getX() + (int) vX, getY());
        Tile block = (Tile) getOneIntersectingObject(Tile.class);
        if (block != null) {
            if (vX > 0) {
                setLocation(block.getX() - (getImage().getWidth() / 2) - (block.getImage().getWidth() / 2), getY());
            } else if (vX < 0) {
                setLocation(block.getX() + (getImage().getWidth() / 2) + (block.getImage().getWidth() / 2), getY());
            }
        }
    }

    private void moveVertically() {
        setLocation(getX(), getY() + (int) vY);
        Tile block = (Tile) getOneIntersectingObject(Tile.class);
        if (block != null) {
            if (vY > 0) { // Landing on top
                setLocation(getX(), block.getY() - (getImage().getHeight() / 2) - (block.getImage().getHeight() / 2));
                vY = 0;
                onGround = true;
            } else if (vY < 0) { // Hitting from underneath
                setLocation(getX(), block.getY() + (getImage().getHeight() / 2) + (block.getImage().getHeight() / 2));
                vY = 0;
                if (block instanceof QuestionBlock) {
                    ((QuestionBlock) block).hitBlock();
                }
            }
        } else {
            onGround = false;
        }
    }

    private void checkHazards() {
        Fireball fb = (Fireball) getOneIntersectingObject(Fireball.class);
        Bowser bowser = (Bowser) getOneIntersectingObject(Bowser.class);

        // Jump on Bowser to damage him
        if (bowser != null) {
            if (vY > 1.0 && getY() < bowser.getY() - 15) {
                bowser.takeDamage();
                vY = -10.0; // Bounce Mario up
                return;
            } else {
                takeDamageAndRespawn();
                return;
            }
        }

        if (fb != null) {
            getWorld().removeObject(fb);
            takeDamageAndRespawn();
            return;
        }

        if (getWorld() != null && getY() > getWorld().getHeight() - 15) {
            takeDamageAndRespawn();
        }
    }

    private void takeDamageAndRespawn() {
        if (getWorld() instanceof MarioWorld) {
            MarioWorld mw = (MarioWorld) getWorld();
            mw.loseLife();
            setLocation(100, 300);
            vX = 0;
            vY = 0;
        }
    }

    private void renderVisual() {
        GreenfootImage img = new GreenfootImage(30, 42);
        // Cap & Shirt (Red)
        img.setColor(new Color(220, 20, 20));
        img.fillOval(5, 2, 20, 14); // Cap
        img.fillRect(6, 16, 18, 14); // Torso
        // Face & Skin
        img.setColor(new Color(255, 205, 150));
        img.fillOval(facing == 1 ? 12 : 4, 6, 12, 10);
        // Overalls (Blue)
        img.setColor(new Color(30, 80, 200));
        img.fillRect(7, 26, 16, 10);
        // Running animation offset
        int legOffset = (onGround && vX != 0) ? (int) (Math.sin(animTick * 0.4) * 4) : 0;
        img.setColor(new Color(110, 50, 20)); // Shoes
        img.fillRect(5, 36 + legOffset, 8, 6);
        img.fillRect(17, 36 - legOffset, 8, 6);

        setImage(img);
    }
}
