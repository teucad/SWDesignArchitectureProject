package MVC.View;

import MVC.Controller.Database.TeaLogToDB;
import MVC.Model.Decorator.DefaultMessage;
import MVC.Model.Decorator.HealthWarningDecorator;
import MVC.Model.Decorator.Message;
import MVC.Model.Service.CupStatsService;
import MVC.Model.State.*;
import MVC.Model.TeaMakerMachine;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.*;


public class TeaMakerFrame extends JFrame {

    private final CupStatsService cupStatsService;
    private final TeaMakerMachine machine;
    private LedIndicatorPanel idleLed, teaLed, boilLed, doneLed;
    int cups;
    private final TeaLogToDB teaLogToDB;

    public TeaMakerFrame(TeaMakerMachine machine, CupStatsService cupStatsService) {
        this.cupStatsService = cupStatsService;
        this.machine = machine;
        teaLogToDB = new TeaLogToDB();

    }

    public void run() throws SQLException {

        JPanel content = new  JPanel();
        content.setLayout(new BorderLayout(20, 10));
        setContentPane(content);

        // Initialize the notifs area.
        JPanel msgPanel = buildMessagesPanel();

        // Initialize the controls area.
        JPanel controls = buildControlPanel();

        // Initialize the "LED lights" that displays the machine's states.

        JPanel statesLED = buildLEDBar();

        // Initialize the center area that displays the messages, controls and the LED indicators.
        JPanel center = new JPanel(new  BorderLayout(10, 0));

        center.add(new JLabel("Selected cups: " + cups), BorderLayout.EAST);

        center.add(controls, BorderLayout.WEST);
        center.add(statesLED, BorderLayout.NORTH);
        center.add(msgPanel, BorderLayout.SOUTH);

        // Add the center area to the GUI.
        content.add(center, BorderLayout.CENTER);


        // Bottom area of the GUI that contains the date, day, and monthly cups.
        JPanel bottom = buildBottomPanel();
        content.add(bottom, BorderLayout.SOUTH);


        setTitle("Tea Making Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(700, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildBottomPanel() throws SQLException {
        JPanel bottom = new JPanel(new GridLayout(1, 2, 10, 0));
        String dayVal = LocalDate.now().getDayOfWeek().toString();
        String dateVal = LocalDate.now().toString();
        String monthlyVal = cupStatsService.getMonthlyTotal();
        JLabel dayLabel = boxedValue("Day", dayVal);
        JLabel dateLabel = boxedValue("Date ", dateVal);
        JLabel monthlyLabel = boxedValue("Monthly cups ", monthlyVal);

        bottom.add(dayLabel);
        bottom.add(dateLabel);
        bottom.add(monthlyLabel);

        return bottom;
    }
    private JPanel buildMessagesPanel() {
            JPanel notifs = new JPanel(new BorderLayout());
            notifs.setBorder(BorderFactory.createTitledBorder("Messages / Warnings / Notifications"));

            JTextArea notifsArea = new JTextArea(8, 25);
            notifsArea.setEditable(false);
            notifsArea.setLineWrap(true);
            notifsArea.setWrapStyleWord(true);
            notifsArea.setText(machine.getMessageString());
            JScrollPane scroll = new JScrollPane(notifsArea);
            scroll.setPreferredSize(new Dimension(200, 150)); // helps prevent collapse

            notifs.add(scroll, BorderLayout.CENTER);
            return notifs;
    }

    private JPanel buildControlPanel() {
        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(220, 0));
        control.setMaximumSize(new Dimension(220, 0));
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
        JButton filledButton = new  JButton("FILLED");
        JButton startButton = new  JButton("MAKE TEA");
        JButton boilWaterButton = new JButton("BOIL WATER");
        JButton resetButton = new JButton("RESET");
        JButton setCupsButton = new  JButton("SET CUPS");
        JTextField cupAmount = new JTextField(10);
        for (JComponent c : new JComponent[]{cupAmount, filledButton, startButton, boilWaterButton, resetButton, setCupsButton}) {
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        control.add(new JLabel("CUPS: "));
        control.add(labeledRow(cupAmount));
        control.add(filledButton);
        control.add(startButton);
        control.add(boilWaterButton);
        control.add(resetButton);
        control.add(setCupsButton);

        initButtons(startButton,filledButton,resetButton,boilWaterButton);
        setCupsInit(setCupsButton, cupAmount);
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
        control.setPreferredSize(new Dimension(200, 0));

        return control;
    }

    private JPanel buildLEDBar() {
        JPanel ledBar = new JPanel(new GridLayout(1, 4, 8, 0));

        idleLed = new LedIndicatorPanel("IDLE");
        teaLed = new LedIndicatorPanel("MAKING TEA");
        boilLed = new LedIndicatorPanel("BOIL WATER");
        doneLed = new LedIndicatorPanel("DONE");

        ledBar.add(idleLed);
        ledBar.add(teaLed);
        ledBar.add(boilLed);
        ledBar.add(doneLed);

        return ledBar;

    }

    private void setCupsInit(JButton setCups, JTextField cupAmount) {
        setCups.addActionListener(_ -> {
        int testCups = Integer.parseInt(cupAmount.getText());
            if(testCups <= 0 || testCups >= 20) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 20");
                throw new ArithmeticException("No.");
            }
            this.cups = testCups;
            setMachineCups(cups);
            machine.notifyMessage("Total cups was successfully updated to: " + cups);
            this.revalidate();
            this.repaint();
        });
    }


    private void initButtons(JButton sButton, JButton fButton, JButton rButton, JButton bButton) {
        sButton.addActionListener(_ -> {
            machine.getState().onStart(machine);
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        fButton.addActionListener(_ -> {
            machine.getState().onFilled(machine, cups);
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        rButton.addActionListener(_ -> {
            machine.getState().onReset(machine);
            try{
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        bButton.addActionListener(_ -> {
            machine.getState().onBoilWater(machine);
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    void setMachineCups(int cups) {
        machine.setCups(cups);
    }

    void stateAction() throws SQLException {
        if(cupStatsService.isDailyLimitExceeded()) {
            int dailyCups = cupStatsService.getDailyTotal();
            Message currentMessage = machine.getMessage();
            currentMessage = new HealthWarningDecorator((DefaultMessage) currentMessage, dailyCups, machine.getState());
            machine.setMessage((DefaultMessage) currentMessage);
            this.revalidate();
            this.repaint();
            return;
        }
        if(machine.getState() == DoneState.INSTANCE) {
            teaLogToDB.insertLog(cups);
        }
        updateLed(machine.getState());
        this.revalidate();
        this.repaint();
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

    private void updateLed(MachineState current) {
        idleLed.setActive(current instanceof IdleState);
        teaLed.setActive(current instanceof MakingTeaState);
        boilLed.setActive(current instanceof BoilingWaterState);
        doneLed.setActive(current instanceof DoneState);
    }

}
