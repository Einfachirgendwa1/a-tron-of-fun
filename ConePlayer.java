public class ConePlayer extends HumanoidPlayer {
    private int goalCount = 0;

    public void act() {

        ConeShooterWorld world = (ConeShooterWorld) getWorld();
        if (getOneIntersectingObject(LightCone.class) != null) { //Der Spieler verliert, wenn er den Lichtkegel berührt
           // world.lost();
        }

        if (getOneIntersectingObject(ConeGoal.class) != null) { //Der Spieler gewinnt, wenn er eine gewisse Zeit in 
            // Kontakt mit dem Ziel ist
            goalCount++;
            if (goalCount >= 150) {
                world.won();
            }
        }

        //Macht die Übeschreitung der Bahngrenzen unmöglich
        if (getX() < 242) {
            teleport(new Vector2(242, getY()));
        } else if (getX() > 365) {
            teleport(new Vector2(365, getY()));
        }
        super.act();
    }
}
