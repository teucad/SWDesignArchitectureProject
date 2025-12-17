package Service;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService {

    private Timer timer;
    private TimerTask task;

    public synchronized void startTimer(int durationMillis, Runnable callBack) throws InterruptedException {

        stopTimer();

        timer = new Timer();

        task = new TimerTask() {
            public void run() {
                callBack.run();
            }
        };

        timer.schedule(task, durationMillis);

    }

    public void stopTimer() {
        if(task != null) {
            task.cancel();
            task = null;
        }

        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}
