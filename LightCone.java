import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LightCone extends Actor{
    private int position;
    private int baseY; //Die y-Koordinate, von der ausgehend die verschiebung der Bilder berechnet wird um die Kegelform zu erreichen

    public LightCone(int position, int baseY){
        this.baseY = baseY;
        setPosition(position);
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
}
