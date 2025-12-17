package MVC.Model.Observer;

import MVC.Model.State.TeaMakerState;

public interface Observer {
    void onStateChanged(TeaMakerState state);

    void onMessageChanged(String message);

    void onMonthlyTotalChanged(int total);
}
