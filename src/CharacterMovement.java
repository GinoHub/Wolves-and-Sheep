public class CharacterMovement extends Thread {
    Character c;
    Stage stage;
    volatile boolean running = true;

    public CharacterMovement(Character c, Stage stage) {
        this.c = c;
        this.stage = stage;
    }

    public void stopRunning() {
        running = false;
    }


    @Override
    public void run() {
        checkTerrain();
        while (running) {
            if (c.getMovesLeft() > 0) {
                c.aiMove(stage).perform();
                c.setMovesLeft(c.getMovesLeft() - 1);
            }

            try {
                if (c.getMovement() > 0)
                sleep(2000/c.getMovement());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            try {
                sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (IllegalMonitorStateException e2) {
                System.out.println("GINO!");
            }
            e.printStackTrace();
        }
    }

    private void checkTerrain() {
        if (c.getLocation().grassy && c.getMovesLeft() > 0) {
            c.addGunk(new GrassyDecorator());
        } else if (c.getLocation().muddy && c.getMovesLeft() > 0) {
            c.addGunk(new MuddyDecorator());
        } else if (c.getLocation().cleaner && c.getMovesLeft() > 0) {
            c.removeGunk();
        }
    }
}