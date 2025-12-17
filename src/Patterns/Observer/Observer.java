package Patterns.Observer;

import Patterns.State.TeaMakerState;

public interface Observer {
    void onStateChanged(TeaMakerState state);

    void onButtonsEnabled(boolean filled, boolean start, boolean boil);

    void onMessageChanged(String message);

    void onMonthlyTotalChanged(int total);
}
