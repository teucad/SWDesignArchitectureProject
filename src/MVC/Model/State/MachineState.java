package MVC.Model.State;

import MVC.Model.TeaMakerMachine;

public interface MachineState {

    @Override
    String toString();

    void onFilled(TeaMakerMachine machine, int cups);
    void onStart(TeaMakerMachine machine);
    void onBoilWater(TeaMakerMachine machine);
    void onReset(TeaMakerMachine machine);
    void onTimerExpired(TeaMakerMachine machine);

}
