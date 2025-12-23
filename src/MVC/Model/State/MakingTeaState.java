package MVC.Model.State;

import MVC.Model.Decorator.*;
import MVC.Model.TeaMakerMachine;

public class MakingTeaState implements MachineState {


    public static final MakingTeaState INSTANCE = new MakingTeaState();

    @Override
    public void onFilled(TeaMakerMachine machine, int cups) {
        machine.notifyMessage(new NoChangeCupsMessage(new DefaultMessage(this)));
    }

    @Override
    public void onStart(TeaMakerMachine machine) {
        machine.notifyMessage(new MakingTeaMessage(new DefaultMessage(this)));
    }

    @Override
    public void onBoilWater(TeaMakerMachine machine) {
        machine.notifyMessage(new MakingTeaMessage(new DefaultMessage(this)));
    }

    @Override
    public void onReset(TeaMakerMachine machine) {
        machine.stopTimer();
        machine.setState(new EmptyState());
        machine.notifyMessage(new ResetRequestMessage(new DefaultMessage(this)));
    }

    @Override
    public void onTimerExpired(TeaMakerMachine machine) {
        machine.setState(new DoneState());
        machine.notifyMessage(new TeaReadyMessage(new DefaultMessage(this)));
    }

    @Override
    public String toString() {
        return "MAKING TEA";
    }

}
