/**
 * Die Grid-Bugs Spinne.
 */
public class GridBugsEnemy extends Enemy {
    /**
     * Der Spieler.
     */
    private final GridBugsPlayer player;

    /**
     * Der animator der die verschiedenen States der Spinne managed.
     */
    private final Animator animator = new Animator(this::spawn);

    {
        speed = 1;
        health = 1;
        pointsOnDeath = 100;
    }

    public GridBugsEnemy(GridBugsPlayer player) {
        this.player = player;
    }

    /**
     * Fügt dem Spieler beim Kontakt Schaden hinzu.
     */
    @Override
    protected void onPlayerContact() {
        player.takeDamage(50);
        move(towards(player).multiply(-60));
    }

    public void act() {
        // Wenn unser Health-Wert kleiner oder gleich null ist, entfernt uns super.act() aus der Welt.
        // Falls das passiert, würde stateMachine.update() eine Exception werfen, weshalb super.act()
        // zuletzt aufgerufen werden muss.
        animator.update();
        super.act();
    }

    /**
     * Die Spawn-Phase.
     *
     * @param animator Der Animator.
     */
    private void spawn(Animator animator) {
        animator.addThread()
            .wait(30)
            .execute(() -> setImage("bug_spawn_2.png"))
            .wait(30)
            .execute(() -> setImage("bug_spawn_3.png"))
            .wait(30)
            .execute(() -> animator.switchState(this::run));
    }

    /**
     * Die Spinne rennt auf den Spieler zu.
     *
     * @param animator Der Animator.
     */
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

    /**
     * Die Spinne erzeugt eine neue Spinne.
     *
     * @param animator Der Animator.
     */
    public void duplicate(Animator animator) {
        setImage("bug_spawn_3.png");

        animator.addThread().waitRandom(50, 90).execute(() -> {
            Misc.addObject(new GridBugsEnemy(player), vec());
            animator.switchState(this::run);
        });
    }
}
