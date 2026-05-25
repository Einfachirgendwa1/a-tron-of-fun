/**
 * Write a description of class LightConeWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ConeShooterWorld extends BaseWorld {

    public ConeShooterWorld() {
        super();
        ConeBorder border = new ConeBorder();


        //Erzeugung der Bahngrenzen an jeder 32. y-Koordinate, da die Bilder 32*32 groß sind

        for (int y = 160; y <= 400; y = y + 32) {
            border = new ConeBorder();
            addObject(border, 192, y);
        }

        for (int y = 160; y <= 400; y = y + 32) {
            border = new ConeBorder();
            addObject(border, 416, y);
            border.border.rotate(180);
        }

        ConeGoal goal;

        //Der Zielbereich besteht aus 3 Reihen, die sich imer an einem festen Platz befinden.

        // Reihe 1  
        goal = new ConeGoal("cone_goal_edge.png");
        addObject(goal, 288, 64);
        goal = new ConeGoal("cone_goal_edge.png");
        goal.goal.mirrorHorizontally();
        addObject(goal, 320, 64);

        // Reihe 2
        goal = new ConeGoal("cone_goal_edge.png");
        addObject(goal, 256, 96);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 288, 96);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 320, 96);
        goal = new ConeGoal("cone_goal_edge.png");
        goal.goal.mirrorHorizontally();
        addObject(goal, 352, 96);

        // Reihe 3
        goal = new ConeGoal("cone_goal_edge.png");
        addObject(goal, 224, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 256, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 288, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 320, 128);
        goal = new ConeGoal("cone_content.png");
        addObject(goal, 352, 128);
        goal = new ConeGoal("cone_goal_edge.png");
        goal.goal.mirrorHorizontally();
        addObject(goal, 384, 128);
    }
}
