import greenfoot.Greenfoot;

public class GridBugsEnemy extends Enemy {
    private static final float speed = 1;
    private State state = State.Spawn;
    private Vector2 target;
    private int frameCounter = 0;

    public GridBugsEnemy() {
        super();
        setImage("bug_spawn_1.png");
    }

    private void setState(State state, String newImage) {
        this.state = state;
        frameCounter = 0;
        setImage(newImage);
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public void act() {
        frameCounter++;
        switch (state) {
            case State.Run:
                if (frameCounter % 45 == 0 && frameCounter % 2 == 0) {
                    setImage("bug_1.png");
                } else if (frameCounter % 45 == 0) {
                    setImage("bug_2.png");
                }

                if (frameCounter == 90 && Greenfoot.getRandomNumber(2) == 1 || frameCounter == 130) {
                    setState(State.Dance, "bug_spawn_3.png");
                }

                Vector2.towards(this, target).times(-speed).move(this);
                break;

            case State.Spawn:
                if (frameCounter == 30) {
                    setImage("bug_spawn_2.png");
                }

                if (frameCounter == 60) {
                    setImage("bug_spawn_3.png");
                }

                if (frameCounter == 90) {
                    setState(State.Run, "bug_2.png");
                }

                break;

            case State.Dance:
                if (frameCounter == 80 && Greenfoot.getRandomNumber(2) == 1 || frameCounter == 130) {
                    setState(State.Run, "bug_2.png");
                }
        }
    }

    public void dance() {
        setImage("bug_spawn_3.png");
    }

    enum State {
        Spawn,
        Run,
        Dance
    }
}
