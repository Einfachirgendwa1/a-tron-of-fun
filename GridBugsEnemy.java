public class GridBugsEnemy extends Enemy {
    private Vector2 target;
    private final StateMachine stateMachine = new StateMachine(this::spawn);

    {
        speed = 1;
        health = 1;
    }

    private void spawn(StateMachine stateMachine) {
        stateMachine.addThread()
                .wait(30)
                .execute(() -> setImage("bug_spawn_2.png"))
                .wait(30)
                .execute(() -> setImage("bug_spawn_3.png"))
                .wait(30)
                .switchState(this::run);
    }

    private void run(StateMachine stateMachine) {
        stateMachine.addThread()
                .execute(() -> setImage("bug_1.png"))
                .wait(45)
                .execute(() -> setImage("bug_2.png"))
                .wait(45)
                .repeat();

        stateMachine.addThread()
                .waitRandom(90, 130)
                .switchState(this::dance);

        stateMachine.addThread().repeat(() -> moveWithSpeed(towards(target)));
    }

    public void dance(StateMachine stateMachine) {
        setImage("bug_spawn_3.png");
        stateMachine.addThread()
                .waitRandom(80, 130)
                .switchState(this::run);
    }

    public void setTarget(IGetVector2 target) {
        this.target = target.position();
    }

    public void act() {
        // if we have a health value that is less than or equal to zero, super.act() removes us from the world
        // but if that happens stateMachine.update() would throw an exception, which is why super.act() needs to 
        // happen last
        stateMachine.update();
        super.act();
    }
}
