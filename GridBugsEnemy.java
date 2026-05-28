public class GridBugsEnemy extends Enemy {
    private final GridBugsPlayer player;
    private final Animator animator = new Animator(this::spawn);

    {
        speed = 1;
        health = 1;
        pointsOnDeath = 100;
    }

    public GridBugsEnemy(GridBugsPlayer player) {
        this.player = player;
    }

    public void act() {
        if (intersects(player)) {
            player.takeDamage(50);
            move(towards(player).scale(-60));
        }

        // if we have a health value that is less than or equal to zero, super.act() removes us from the world
        // but if that happens stateMachine.update() would throw an exception, which is why super.act() needs to
        // happen last
        animator.update();
        super.act();
    }

    private void spawn(Animator animator) {
        animator.addThread()
            .wait(30)
            .execute(() -> setImage("bug_spawn_2.png"))
            .wait(30)
            .execute(() -> setImage("bug_spawn_3.png"))
            .wait(30)
            .execute(() -> animator.switchState(this::run));
    }

    private void run(Animator animator) {
        animator.addThread()
            .execute(() -> setImage("bug_1.png"))
            .wait(45)
            .execute(() -> setImage("bug_2.png"))
            .wait(45)
            .repeat();

        animator.addThread().waitRandom(40, 200).execute(() -> animator.switchState(this::duplicate));

        animator.addThread().repeat(() -> moveWithSpeed(towards(player)));
    }

    public void duplicate(Animator animator) {
        setImage("bug_spawn_3.png");

        animator.addThread().waitRandom(50, 90).execute(() -> {
            Misc.addObject(new GridBugsEnemy(player), position());
            animator.switchState(this::run);
        });
    }
}
