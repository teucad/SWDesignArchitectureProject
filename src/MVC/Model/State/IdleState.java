package MVC.Model.State;

import MVC.Model.Decorator.*;
import MVC.Model.TeaMakerMachine;
import MVC.Model.Strategy.BoilWater;
import MVC.Model.Strategy.MakeTea;

public class IdleState implements MachineState {

    public static final IdleState INSTANCE = new IdleState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        if(cups < 0) {
            return;
        }
        machine.setCups(cups);
        machine.notifyMessage(new UpdatedMessage(new DefaultMessage(this), cups));
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.setStrategy(new MakeTea());
        machine.setState(new MakingTeaState());
        machine.notifyMessage(new initMakeTeaMessage(new DefaultMessage(this)));
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.setStrategy(new BoilWater());
        machine.setState(new BoilingWaterState());
        machine.notifyMessage(new initBoilWaterMessage(new DefaultMessage(this)));
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage(new FillCupsMessage(new DefaultMessage(this)));
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        // No timer is needed when the machine is not running.
        // Therefore, this method was left empty intentionally.
    }

    @Override
    public String toString() {
        return "IDLE";
    }


}
