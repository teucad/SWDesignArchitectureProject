package MVC.View;

import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.HealthWarningDecorator;
import MVC.Model.Service.CupStatsService;
import MVC.Model.State.*;
import MVC.Model.TeaMakerMachine;
import MVC.View.Panels.LedIndicatorPanel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.*;

public class TeaMakerFrame extends JFrame {

    private final CupStatsService cupStatsService;
    private final TeaMakerMachine machine;
    private final JPanel statesLED;
    private LedIndicatorPanel idleLed, teaLed, boilLed, doneLed;
    private DefaultMessage messageObj;
    int cups;

    public TeaMakerFrame(TeaMakerMachine machine, CupStatsService cupStatsService) {
        this.cupStatsService = cupStatsService;
        this.machine = machine;
        messageObj = new DefaultMessage(machine.getState());
        statesLED = new JPanel(new GridLayout(1, 4, 8, 0));

    }

    public void run() throws SQLException {

        JPanel content = new  JPanel();
        content.setLayout(new BorderLayout(10, 10));
        setContentPane(content);



        // Center area that includes controls and notifications
        JPanel centerArea = new  JPanel(new BorderLayout(10, 0));
        content.add(centerArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JTextField cupAmount = new JTextField(10);


        // init control buttons
        JButton filledButton = new JButton("FILLED");
        JButton startButton = new JButton("START");
        JButton boilWaterButton = new JButton("BOIL WATER");
        JButton resetButton = new JButton("RESET");

        initButtons(filledButton);
        initButtons(boilWaterButton);
        initButtons(resetButton);
        initButtons(startButton);

        // init notifs, date, cup labels
        JLabel notifLabel = new JLabel("NOTIFICATIONS");
        JLabel totalCupsLabel = new JLabel("TOTAL CUPS");


        // Creating the "LED lights" that shows the machine's states.

        idleLed = new LedIndicatorPanel("IDLE");
        teaLed  = new LedIndicatorPanel("MAKING TEA");
        boilLed = new LedIndicatorPanel("BOIL WATER");
        doneLed = new LedIndicatorPanel("DONE");



        statesLED.add(idleLed);
        statesLED.add(teaLed);
        statesLED.add(boilLed);
        statesLED.add(doneLed);
        content.add(statesLED, BorderLayout.NORTH);

        // Adding the control buttons to the controls label.
        controls.add(labeledRow(cupAmount));
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



        // This is the notifications area on the right side of the GUI.
        JPanel msgPanel = new  JPanel(new BorderLayout(0, 6));
        msgPanel.setBorder(BorderFactory.createTitledBorder("Messages / Warnings / Notifications"));

        JTextArea messages = new JTextArea();
        messages.setEditable(false);
        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        messages.setText(messageObj.getMessage());
        msgPanel.add(new JScrollPane(messages), BorderLayout.CENTER);

        centerArea.add(msgPanel, BorderLayout.CENTER);

        content.add(centerArea, BorderLayout.CENTER);

        // Day-Date-Monthly Total area
        JPanel bottom = new  JPanel(new GridLayout(1, 3, 10, 0));
        String dayVal = LocalDate.now().getDayOfWeek().toString();
        String dateVal = LocalDate.now().toString();
        String monthlyVal = cupStatsService.getMonthlyTotal();
        JLabel dayLabel = boxedValue("Day", dayVal);
        JLabel dateLabel =boxedValue("Date", dateVal);

        JLabel monthlyLabel = boxedValue("Monthly cups", monthlyVal);
        bottom.add(dayLabel);
        bottom.add(dateLabel);
        bottom.add(monthlyLabel);
        content.add(bottom, BorderLayout.SOUTH);


        content.add(dayLabel);
        content.add(notifLabel);
        content.add(cupAmount);
        content.add(totalCupsLabel);
        content.add(cupAmount);

        setTitle("Tea Making Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initButtons(JButton button) {
        button.addActionListener(_ -> {
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    void stateAction() throws SQLException {
        MachineState curState = machine.getState();
        curState.onReset(machine);
        if(cupStatsService.isDailyLimitExceeded()) {
            int dailyCups = cupStatsService.getDailyTotal();
            messageObj = new HealthWarningDecorator(messageObj, dailyCups, machine.getState());

        }
        updateLeds(machine.getState());
    }

    public void setCups(int cups) {
        this.cups = cups;
    }



    private JPanel labeledRow(JComponent field) {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(new JLabel("Cups: "), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
        return p;
    }

    private JLabel boxedValue(String title, String value) {
        JLabel l = new JLabel(title + ": " + value);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return l;
    }

    private void updateLeds(MachineState current) {
        idleLed.setActive(current instanceof IdleState);
        teaLed.setActive(current instanceof MakingTeaState);
        boilLed.setActive(current instanceof BoilingWaterState);
        doneLed.setActive(current instanceof DoneState);
    }

}
