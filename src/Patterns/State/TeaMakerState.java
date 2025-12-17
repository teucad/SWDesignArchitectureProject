package Patterns.State;

import MVC.Model.TeaMakerMachine;

public interface TeaMakerState {

    @Override
    String toString();

    void onFilled(TeaMakerMachine machine, int cups);
    void onStart(TeaMakerMachine machine);
    void onBoilWater(TeaMakerMachine machine);
    void onReset(TeaMakerMachine machine);
    void onTimerExpired(TeaMakerMachine machine);

}
