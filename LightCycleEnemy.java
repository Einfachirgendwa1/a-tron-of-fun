import greenfoot.GreenfootImage;
import greenfoot.Color;
import greenfoot.Greenfoot;

public class LightCycleEnemy extends Enemy {

    private int direction = 4;
    private int result = 0;
    private ImageHolder cycleImage;
    private boolean crashed = false;
    private boolean onGrid = true;
    private boolean hasTurned = false;

    @Override
    protected ImageHolder[] images() {
        GreenfootImage img = new GreenfootImage("images/lightcycle_enemy.png");
        img.rotate(-90);
        cycleImage = new ImageHolder(img, 0, 0);
        return new ImageHolder[]{ cycleImage };
    }

    public boolean touchesPlayer(LightCyclePlayer player) {
        return intersects(player);
    }

    public boolean isCrashed() {
        LightCyclesWorld world = (LightCyclesWorld) getWorld();
        Trail trail = (Trail) getOneIntersectingObject(Trail.class);
        if (touchesPlayer(world.player) || (getOneIntersectingObject(Trail.class) != null && trail.getAge() > 40)) {
            crashed = true;
        }
        return crashed;
    }

    //Explosionsanimation bei Kollision
    private void boomAnimation() {
        cycleImage.setImage("boom_1.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_2.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_3.png");
        Greenfoot.delay(10);
        cycleImage.setImage("boom_4.png");
        Greenfoot.delay(20);

        LightCyclesWorld world = (LightCyclesWorld) getWorld(); 
        world.gameOver();
    }

    //Prüft, ob die Bewegungsrichtung blockiert ist, um zu eine "Wendeentscheidung" zu treffen
    private boolean pathCheck(int direction) {
        int nx = getX();
        int ny = getY();

        if        (direction % 4 == 0) { //Vorhersehung der nächsten Grid-Zelle in Abhängigkeit von der Bewegungsrichtung 
            nx -= LightCyclesWorld.gridSize;
        } else if (direction % 4 == 2) {
            nx += LightCyclesWorld.gridSize;
        } else if (direction % 4 == 1) {
            ny -= LightCyclesWorld.gridSize;
        } else {
            ny += LightCyclesWorld.gridSize;
        }

        if (nx <= 0 || nx >=  getWorld().getWidth() - LightCyclesWorld.gridSize || ny <= 0 || ny >= getWorld().getHeight() - LightCyclesWorld.gridSize) { //Prüfung, ob die Bewegung über die Weltgrenzen hinausgehen würde
            return true;
        }

        if (getWorld().getObjectsAt(nx, ny, Trail.class).size() > 0) { //Prüfung, ob die Bewegung in eine Zelle mit einem Trail führen würde
        return true;
        }

        return false;
    }

    public boolean onGrid() { //Prüfung, ob das cycle auf dem Grid ist 
        if(getX() % LightCyclesWorld.gridSize == 0 && getY() % LightCyclesWorld.gridSize == 0) {
            onGrid = true;
        } else {
            onGrid = false;
        }
        return onGrid;
    }

    public void act() {

        super.act();
        if (isCrashed()) {
            boomAnimation();
            return;
        }

        if (onGrid()) { //Prüfung nur jede Grid-Zelle möglich, um ein irritierendes Drehen zu vermeiden

            if (pathCheck(direction)) {
            if (!pathCheck(direction - 1)) { //Prüfung, ob die Bewegung nach links blockiert ist
                direction = direction - 1;
            } else {
                direction = direction + 1; //Richtungsänderung nach Rechts, da eine 180-Grad Drehung nicht möglich ist und es keinen anderen Weg gibt
            }
        }

            //Zufällige Richtungsänderung, um das Verhalten des Gegners unvorhersehbar zu machen, wenn alle Wege offen sind (mit Zufälliger Richtungswahl)
            if (!pathCheck( direction - 1) && !pathCheck( direction + 1)){
                if (Math.random() < 0.1) {
                    direction = direction + 1;
                } else if (Math.random() > 0.9) {
                    direction = direction - 1;
                }
            }
        }

        int result = direction % 4;
        //Bewegung in die aktuelle Richtung

        if (result == 3 || result == -1) {
            moveDown();
            cycleImage.setRotation(270);
        } else if (result == 2 || result == -2) {
            moveRight();
            cycleImage.setRotation(180);
        } else if (result == 1 || result == -3) {
            moveUp();
            cycleImage.setRotation(90);
        } else {
            moveLeft();
            cycleImage.setRotation(0);
        }

        //Erzeugung eines Schweifs an der aktuellen Position
        getWorld().addObject(new Trail(Color.BLUE), getX(), getY());
    }
}

