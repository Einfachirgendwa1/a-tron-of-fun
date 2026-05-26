import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LightCone extends Actor{
    private int position;
    private int baseY; //Die y-Koordinate, von der ausgehend die verschiebung der Bilder berechnet wird um die Kegelform zu erreichen
    private int boomFrame = -1;
    private boolean blinking = false;
    private int blinkFrame = 0;

    public LightCone(int position, int baseY){
        this.baseY = baseY;
        setPosition(position);
    }

    public void setBlinking(boolean blinking) {
        this.blinking = blinking;
    }
    
    public int getPosition(){
        return position;
    }

    //Änderung des Bildes in Abhängigkeit von der Position 
    public void setPosition(int position){
        this.position = position;
        setImage("cone_" + position + ".png");
    }


    public int getYChange() { //Verschiebung des Bildes, die später ausschlaggebend für die Verschiebung des Objekts verwendet wird, damit der Kegel die richtige Form hat
        if (position == 1 || position == 6){
            return -9;
        }else if (position == 2 || position == 5){
            return -4;
        } else {
            return 0;
        }
    }

    //Annäherung der äußeren Bilder zur Mitte, damit die Kegelform zustande kommt
    public int getXChange() { 
        if (position == 1){
            return +8;
        } else if (position == 6){
            return -8;
        } else {
            return 0;
        }
    }
    
    //Berechnung des tatsächlichen Y-Werts des objekts mitsamt der Verschiebung
    public int getTargetY(){
        return baseY + getYChange();
    }

    public void setBaseY(int baseY){
        this.baseY = baseY;
    }

    public int getBaseY(){
        return baseY;
    }

    private void boomAnimation() { //Explosionsanimation, die laufen kann, ohne dass der Spielfluss angehalten wird
        if (boomFrame == 0) {
            setImage("boom_1.png");
        } else if (boomFrame == 10) {
            setImage("boom_2.png");
        } else if (boomFrame == 20) {
            setImage("boom_3.png");
        } else if (boomFrame == 30) {
            setImage("boom_4.png");
        } else if (boomFrame == 40) {
            getWorld().removeObject(this);
        } else if (boomFrame > 40) { //Zurücksetzten des Werts, der die Animation auslösen kann, wenn die Animation endet
            boomFrame = -1;
        }
        boomFrame++;
    }

    public void act() {

        if (boomFrame >= 0) {
            boomAnimation();
            return;
        } 

        /**
         * Die einzelnen Teile des Kegels gelten erst als "getroffen", wenn der Schuss sich in einem Umkreis von 20 Pixeln befindet.
         * Unabhängig davon, wo sich der SChuss in Relation zum Element befindet, wird der Treffer registriert.
         * Der Schuss wird vor der animation entfernt.
         */
        Bullet bullet = (Bullet) getOneIntersectingObject(Bullet.class);
        if (bullet != null && Math.abs(getX() - bullet.getX()) < 20 && Math.abs(getY() - bullet.getY()) < 20) {
            getWorld().removeObject(bullet);
            boomFrame = 0;
        }
        
        if (blinking) { //Das Warnblinken des Kegels. Die Transparenz wird alle 10 Frames geändert.
            blinkFrame++;
            if (blinkFrame % 10 == 0) {
                GreenfootImage img = getImage();
                if (img.getTransparency() == 255) {
                    img.setTransparency(100);
                } else {
                    img.setTransparency(255);
                }
            }
        }
    }
}
