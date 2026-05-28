public class ConeShooterWorld extends BaseWorld {

    LightCone cone = new LightCone(1, 160);
    private int actCount = 0;
    private int rowCount = 1;

    public ConeShooterWorld() {
        super();
        ConeBorder border = new ConeBorder();


        //Erzeugung der Bahngrenzen, die an die Kegelbreite angepasst sind

        //Linke Seite
        for (int y = 160; y <= 400; y = y + 32) {
            border = new ConeBorder();
            addObject(border, 213, y);
        }

        //Rechte Seite wird gedreht, damit die volle sichtbare Breite der Bahn genutzt werden kann
        for (int y = 160; y <= 400; y = y + 32) {
            border = new ConeBorder();
            addObject(border, 395, y);
            border.border.rotate(180);
        }

        ConeGoal goal;

        //Der Zielbereich besteht aus 3 Reihen, die sich immer an einem festen Platz befinden.

        // Reihe 1
        goal = new ConeGoal("cone_goal_top.png");
        addObject(goal, 304, 64);

        // Reihe 2
        goal = new ConeGoal("cone_goal_edge.png");
        addObject(goal, 272, 96);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 304, 96);
        goal = new ConeGoal("cone_goal_edge.png");
        goal.goal.mirrorHorizontally();
        addObject(goal, 336, 96);

        // Reihe 3
        goal = new ConeGoal("cone_goal_edge.png");
        addObject(goal, 240, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 272, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 304, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 336, 128);
        goal = new ConeGoal("cone_goal_edge.png");
        goal.goal.mirrorHorizontally();
        addObject(goal, 368, 128);

        //Eine bereits am Anfang des Spiels sichtbare Kegelreihe
        spawnRow(160);

        //Der Spieler
        ConePlayer player;
        player = new ConePlayer();
        addObject(player, 304, 320);
    }

    //Erzeugt eine vollständige Kegelreihe an der angegebenen y-Koordinate
    public void spawnRow(int baseY) {
        if (rowCount > 5){
            
        } else {
            for (int i = 0; i < 6; i++) {
                cone = new LightCone(i + 1, baseY);
                addObject(
                        cone,
                        0,
                        0
                ); //Die Position wird direkt in der nächsten Zeile gesetzt, das Objekt muss aber schon vorher erzeugt 
                // werden
                cone.setLocation(224 + 32 * i + cone.getXChange(), cone.getTargetY());
            }
        }
        
    }

    public void act() {
        super.act();
        actCount++;

        /**
         * Alle 100 Acts bewegt sich jedes Objekt des Kegels um einen Schritt nach rechts.
         * Alle Objekte, die die Position sechs erreichen, werden entfernt (außerhalb der Bahngrenzen gibt es keine 
         * Kegelobjekte mehr)
         * In jeder Reihe kommt ein Objekt mit Position 1 hinzu, sodass die rotierende Kegelbewegung entsteht.
         */

        if (actCount % 100 == 0) {
            for (int i = 0; i < getObjects(LightCone.class).size(); i++) {
                LightCone cone = getObjects(LightCone.class).get(i);
                if (cone.getPosition() >= 6) { //Entfernung der letzten Position
                    removeObject(cone);
                    i--; //Liste der Objekte wird kürzer
                    continue; //Für das entfernte Objekt soll der Rest der Schleife nicht mehr ausgeführt werden.
                }

                //Um falsche Lücken in der Bewegung zu vermeiden, wird die Verschiebung mit der Änderung der Position
                // korrigiert.
                int oldXChange = cone.getXChange(); //X-Verschiebung vor dem Positionswechsel
                cone.setPosition(cone.getPosition() + 1); //Position und Bild ändern
                int newXChange = cone.getXChange(); //Verschiebung nach dem Positionswechsel
                //Korrektur der Verschiebung
                cone.setLocation(cone.getX() + 32 + newXChange - oldXChange, cone.getTargetY());
            }

            for (int i = 0; i < rowCount; i++) {
                cone = new LightCone(
                        1,
                        160 + 25 * i
                ); //neues Objekt mit der passenden y-Koordinate der jeweiligen Reihe
                addObject(
                        cone,
                        0,
                        0
                ); //Die Position wird direkt in der nächsten Zeile gesetzt, das Objekt muss aber schon vorher 
                // erzeugt werden
                cone.setLocation(224 + cone.getXChange(), cone.getTargetY());
            }
        }

        /**
         * Alle 500 Acts bewegen sich alle bestehenden Reihen des Lichtkegels um 24 Pixel nach unten
         * 24, damit lückenlos und pretty
         * Eine neue Reihe wird an die oberste Reihenposition hinzugefügt. 
         * So wird der Kegel größer und das Ziel wird geschützt.
         * */

        if (actCount % 500 == 0 && rowCount < 5) { //Verschiebung der Reihen nach unten
            for (int i = 0; i < getObjects(LightCone.class).size(); i++) {
                LightCone cone = getObjects(LightCone.class).get(i);
                cone.setBlinking(false); //Kegel hört auf, zu blinken, sobald die neue Reihe da ist
                cone.setBaseY(cone.getBaseY() + 25);
                cone.setLocation(cone.getX(), cone.getTargetY());
            }

            spawnRow(160);//Neue Reihe am Oberen Ende des Lichtkegels erzeugen
            rowCount++;
        }

        //100 Acts vor dem Erscheinen einer neuen Reihe blinkt der Kegel als Warnung vor dem Erscheinen einer neuen 
        // Reihe
        if (actCount % 500 == 400) {
            for (int i = 0; i < getObjects(LightCone.class).size(); i++) {
                LightCone cone = getObjects(LightCone.class).get(i);
                if (rowCount < 5){
                    cone.setBlinking(true);
                }
            }
        }
    }
}
