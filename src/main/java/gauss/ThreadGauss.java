package gauss;

public class ThreadGauss implements Runnable {
    Thread thread;
    private boolean finished = false;
    final Object conditionA = new Object();
    final Object conditionC = new Object();
    final int i;
    int j;
    final int k;

    ThreadGauss(int i, int k) {
        this.i = i;
        this.k = k;
    }

    ThreadGauss(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public void setFinished() {
        finished = true;
    }

    public boolean hasFinished() {
        return finished;
    }

    @Override
    public void run() {

    }
}
