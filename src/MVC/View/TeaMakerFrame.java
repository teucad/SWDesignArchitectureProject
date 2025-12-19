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

        JPanel content = new  JPanel();
        content.setLayout(new BorderLayout(10, 10));
        setContentPane(content);
        setTitle("Tea Making Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,500);


        // Center area that includes controls and notifications
        JPanel centerArea = new  JPanel(new BorderLayout(10, 0));
        content.add(centerArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JTextField cupAmount = new JTextField();
        cupAmount.setMaximumSize(new Dimension(Integer.MAX_VALUE, cupAmount.getPreferredSize().height));


        // init control buttons
        JButton filledButton = new JButton("FILLED");
        JButton startButton = new JButton("START");
        JButton boilWaterButton = new JButton("BOIL WATER");
        JButton resetButton = new JButton("RESET");

        // init notifs, date, cup labels
        JLabel notifLabel = new JLabel("NOTIFICATIONS");
        JLabel totalCupsLabel = new JLabel("TOTAL CUPS");




        // Adding the control buttons to the controls label. Again, used ChatGPT to implement the labeledRow() method.
        controls.add(labeledRow("Cups: ", cupAmount));
        controls.add(Box.createVerticalStrut(8));
        controls.add(filledButton);
        controls.add(Box.createVerticalStrut(6));
        controls.add(startButton);
        controls.add(Box.createVerticalStrut(6));
        controls.add(boilWaterButton);
        controls.add(Box.createVerticalStrut(6));
        controls.add(resetButton);
        controls.add(Box.createVerticalStrut(10));

        centerArea.add(controls, BorderLayout.WEST);

        LedIndicatorPanel statesLED = getLedIndicatorPanel();

        filledButton.addActionListener(_ -> stateAction());
        startButton.addActionListener(_ -> stateAction());
        boilWaterButton.addActionListener(_ -> stateAction());
        resetButton.addActionListener(_ -> stateAction());

        // Going to be honest here, I used ChatGPT to help me out in here. This is the notifications area on the right side of the GUI.
        JPanel msgPanel = new  JPanel(new BorderLayout(0, 6));
        msgPanel.setBorder(BorderFactory.createTitledBorder("Messages / Warnings / Notifications"));
        JTextArea messages = new JTextArea();
        messages.setEditable(false);
        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        msgPanel.add(new JScrollPane(messages), BorderLayout.CENTER);

        centerArea.add(msgPanel, BorderLayout.EAST);

        // Day-Date-Monthly Total area
        JPanel bottom = new  JPanel(new GridLayout(1, 3, 10, 0));
        JLabel dayLabel = new  JLabel("Day");
        content.add(statesLED, BorderLayout.NORTH);


        content.add(dayLabel);
        content.add(notifLabel);
        content.add(cupAmount);
        content.add(totalCupsLabel);
        content.add(cupAmount);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private LedIndicatorPanel getLedIndicatorPanel() {
        LedIndicatorPanel statePanel = new LedIndicatorPanel();

        statePanel.setLayout(new GridLayout(1,4,8,0));
        statePanel.setPreferredSize(new Dimension(50,65));
        statePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel idleState = new JLabel("IDLE");

        idleState.setOpaque(true);
        idleState.setBackground(Color.BLACK);
        idleState.setForeground(Color.GREEN);

        JLabel makingTeaState = new JLabel("MAKING TEA");

        makingTeaState.setOpaque(true);
        makingTeaState.setBackground(Color.BLACK);
        makingTeaState.setForeground(Color.GREEN);

        JLabel boilWaterState = new JLabel("BOILING WATER");

        boilWaterState.setOpaque(true);
        boilWaterState.setBackground(Color.BLACK);
        boilWaterState.setForeground(Color.GREEN);

        JLabel doneState =  new JLabel("DONE");

        doneState.setOpaque(true);
        doneState.setBackground(Color.BLACK);
        doneState.setForeground(Color.GREEN);

        statePanel.add(idleState);

        statePanel.add(makingTeaState);

        statePanel.add(boilWaterState);

        statePanel.add(doneState);

        statePanel.setOpaque(true);
        statePanel.setBackground(Color.BLACK);

        return statePanel;
    }

    void stateAction() {
        MachineState curState = machine.getState();
        curState.onReset(machine);
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getCups() {
        return cups;
    }


    private JPanel labeledRow(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
        return p;
    }

}
