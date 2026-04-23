import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GridBugsEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends MultipleImages
{
    
    public Enemy(World world) {
        super(world);
    }

    /**
     * Act - do whatever the GridBugsEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    protected void moveUp() {
        setLocation(getX(), getY() - 1);
    }

    protected void moveDown() {
        setLocation(getX(), getY() + 1);
    }

    protected void moveLeft() {
        setLocation(getX() - 1, getY());
    }

    protected void moveRight() {
        setLocation(getX() + 1, getY());
    }
}

