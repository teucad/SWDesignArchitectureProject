package MVC.Model.Observer;

import MVC.Model.State.MachineState;

public interface Observer {
    void onStateChanged(MachineState state);

    void onMessageChanged(String message);

    void onMonthlyTotalChanged(int total);
}
