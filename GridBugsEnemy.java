public class GridBugsEnemy extends Enemy {
    private final GridBugsPlayer player;
    private final StateMachine stateMachine = new StateMachine(this::spawn);

    {
        speed = 1;
        health = 1;
        pointsOnDeath = 100;
    }

    public GridBugsEnemy(GridBugsPlayer player) {
        this.player = player;
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

        stateMachine.addThread().waitRandom(90, 130).switchState(this::dance);

        stateMachine.addThread().repeat(() -> moveWithSpeed(towards(player)));
    }

    public void dance(StateMachine stateMachine) {
        setImage("bug_spawn_3.png");

        stateMachine.addThread()
            .waitRandom(80, 130)
            .execute(() -> Misc.addObject(new GridBugsEnemy(player), position()))
            .switchState(this::run);
    }

    public void act() {
        if (intersects(player)) {
            player.takeDamage(50);
            move(towards(player).scale(-60));
        }

        // if we have a health value that is less than or equal to zero, super.act() removes us from the world
        // but if that happens stateMachine.update() would throw an exception, which is why super.act() needs to 
        // happen last
        stateMachine.update();
        super.act();
    }
}
