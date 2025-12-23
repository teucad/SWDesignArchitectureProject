package MVC.Model.State;

import MVC.Model.Decorator.BoilingWaterMessage;
import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.JobDoneMessage;
import MVC.Model.Decorator.ResetMessage;
import MVC.Model.TeaMakerMachine;


public class BoilingWaterState implements MachineState {

    public static final BoilingWaterState INSTANCE = new BoilingWaterState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(this)));
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(this)));
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.setState(new BoilingWaterState());
        machine.notifyMessage(new BoilingWaterMessage(new DefaultMessage(this)));
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage(new ResetMessage(new DefaultMessage(this)));
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        machine.setState(new DoneState());
        machine.notifyMessage(new JobDoneMessage(new DefaultMessage(this), machine));

    }

    @Override
    public String toString() {
        return "BOILING WATER";
    }
}
