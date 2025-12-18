package MVC.View;

import MVC.Model.State.*;
import MVC.Model.TeaMakerMachine;
import MVC.View.Panels.LedIndicatorPanel;
import java.awt.*;
import javax.swing.*;

public class TeaMakerFrame extends JFrame {

    private final TeaMakerMachine machine;

    int cups;

    public TeaMakerFrame(TeaMakerMachine machine) {

        this.machine = machine;


    }

    public void run() {
        this.setLayout(new BoxLayout(this ,BoxLayout.Y_AXIS));
        this.setTitle("Tea Making Machine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton filledButton = new JButton("FILLED");
        JButton startButton = new JButton("START");
        JButton boilWaterButton = new JButton("BOIL WATER");
        JButton resetButton = new JButton("RESET");

        JPanel notifications = new JPanel();
        JLabel dayLabel = new  JLabel();
        JLabel dateLabel = new  JLabel();
        JLabel monthlyCups = new  JLabel();
        JLabel totalCupsLabel = new JLabel("TOTAL CUPS");

        JTextField cupAmount = new  JTextField();


        LedIndicatorPanel statePanel = new LedIndicatorPanel();

        statePanel.setLayout(new BoxLayout(statePanel,BoxLayout.Y_AXIS));
        statePanel.setPreferredSize(new Dimension(1,4));
        statePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel idleState = new JLabel("IDLE");
        JLabel makingTeaState = new JLabel("MAKING TEA");
        JLabel boilWaterState = new JLabel("BOIL WATER");
        JLabel doneState =  new JLabel("DONE");

        statePanel.add(idleState);
        statePanel.add(makingTeaState);
        statePanel.add(boilWaterState);
        statePanel.add(doneState);

        filledButton.addActionListener(_ -> filledAction());
        startButton.addActionListener(_ -> startAction());
        boilWaterButton.addActionListener(_ -> boilWaterAction());
        resetButton.addActionListener(_ -> resetAction());
    }

    void filledAction() {
        MachineState curState = machine.getState();
        curState.onFilled(machine, cups);
    }


    void startAction() {
        MachineState curState = machine.getState();
        curState.onStart(machine);
    }


    void boilWaterAction() {
        MachineState curState = machine.getState();
        curState.onBoilWater(machine);
    }

    void resetAction() {
        MachineState curState = machine.getState();
        curState.onReset(machine);
    }
}
