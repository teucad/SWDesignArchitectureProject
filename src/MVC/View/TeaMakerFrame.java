package MVC.View;

import MVC.Controller.Database.TeaLogToDB;
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
    private LedIndicatorPanel emptyLed, idleLed, teaLed, boilLed, doneLed;
    private JTextArea messagesArea;
    private JLabel selectedCupsLabel;
    private JLabel timerLabel;
    private javax.swing.Timer countdownTimer;
    private long timerEndMillis;
    private MachineState lastState;
    int cups;
    private final TeaLogToDB teaLogToDB;
    JButton filledButton = new  JButton("FILLED");
    JButton startButton = new  JButton("MAKE TEA");
    JButton boilWaterButton = new JButton("BOIL WATER");
    JButton resetButton = new JButton("RESET");
    JButton setCupsButton = new  JButton("SET CUPS");
    JTextField cupAmount = new JTextField(10);

    public TeaMakerFrame(TeaMakerMachine machine, CupStatsService cupStatsService) {
        this.cupStatsService = cupStatsService;
        this.machine = machine;
        teaLogToDB = new TeaLogToDB();
        machine.addObserver(m ->
                SwingUtilities.invokeLater(() -> appendMessage(m.getMessage()))
        );
        machine.addPropertyChangeListener(event -> SwingUtilities.invokeLater(this::handleStateChange));
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

        selectedCupsLabel = new JLabel("Selected cups: " + this.cups);
        timerLabel = new JLabel("Timer: --");
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.add(selectedCupsLabel);
        statusPanel.add(timerLabel);

        center.add(statusPanel, BorderLayout.EAST);

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

            messagesArea = new JTextArea(8, 25);
            messagesArea.setEditable(false);
            messagesArea.setLineWrap(true);
            messagesArea.setWrapStyleWord(true);
            messagesArea.setText(machine.getMessageString());
            JScrollPane scroll = new JScrollPane(messagesArea);

            notifs.add(scroll, BorderLayout.CENTER);
            return notifs;
    }

    private JPanel buildControlPanel() {
        JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));

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

        return control;
    }

    private JPanel buildLEDBar() {
        JPanel ledBar = new JPanel(new GridLayout(1, 4, 8, 0));

        emptyLed = new LedIndicatorPanel("EMPTY");
        idleLed = new LedIndicatorPanel("IDLE");
        teaLed = new LedIndicatorPanel("MAKING TEA");
        boilLed = new LedIndicatorPanel("BOIL WATER");
        doneLed = new LedIndicatorPanel("DONE");


        ledBar.add(emptyLed);
        ledBar.add(idleLed);
        ledBar.add(teaLed);
        ledBar.add(boilLed);
        ledBar.add(doneLed);

        return ledBar;

    }

    private void setCupsInit(JButton setCups, JTextField cupAmount) {
        setCups.addActionListener(event -> readCupsFromField(cupAmount));
    }

    private void appendMessage(String message) {
        messagesArea.append(message);
        messagesArea.setCaretPosition(messagesArea.getDocument().getLength());
    }

    private boolean readCupsFromField(JTextField cupAmountField) {
        String cupIn = cupAmountField.getText().trim();

        try {
            int value = Integer.parseInt(cupIn);

            if (value <= 0) {
                appendMessage("\nCups must be a positive number.");
                refreshUI();
                return false;
            }
            this.cups = value;
            appendMessage("\nCups set to: " + cups);
            if (selectedCupsLabel != null) {
                selectedCupsLabel.setText("Selected cups: " + this.cups);
            }
            revalidate();
            repaint();
            return true;
        } catch (NumberFormatException e) {
            appendMessage("\nInvalid value. Please enter a number.");
            refreshUI();
            return false;
        }
    }




    private void initButtons(JButton sButton, JButton fButton, JButton rButton, JButton bButton) {
        sButton.addActionListener(event -> {
            machine.startTea();
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        fButton.addActionListener(event -> {
            if (!readCupsFromField(cupAmount)) {
                return;
            }
            machine.filled(cups);
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        rButton.addActionListener(event -> {
            machine.reset();
            try{
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        bButton.addActionListener(event -> {
            machine.boilWater();
            try {
                stateAction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    void stateAction() throws SQLException {
        if(cupStatsService.isDailyLimitExceeded()) {
            int dailyCups = cupStatsService.getDailyTotal();
            Message currentMessage = machine.getMessage();
            currentMessage = new HealthWarningDecorator(currentMessage, dailyCups, machine.getState());
            machine.setMessage(currentMessage);
            refreshUI();
            return;
        }
        if(machine.getState() == DoneState.INSTANCE) {
            teaLogToDB.insertLog(cups);
        }
        refreshUI();
    }

    private void refreshUI() {
        MachineState s = machine.getState();

        updateLed(s);
        updateButtons(s);

        revalidate();
        repaint();
    }

    private void handleStateChange() {
        MachineState current = machine.getState();
        if (lastState == null || !lastState.getClass().equals(current.getClass())) {
            updateTimerForState(current);
            lastState = current;
        }
        refreshUI();
    }

    private void updateTimerForState(MachineState state) {
        if (state instanceof MakingTeaState || state instanceof BoilingWaterState) {
            startCountdown();
        } else {
            stopCountdown();
        }
    }

    private void startCountdown() {
        if (timerLabel == null) {
            return;
        }
        if (machine.getMode() == null) {
            timerLabel.setText("Timer: --");
            return;
        }
        timerEndMillis = System.currentTimeMillis() + machine.getMode().getDurationMillis();
        updateTimerLabel();
        if (countdownTimer == null) {
            countdownTimer = new javax.swing.Timer(1000, event -> updateTimerLabel());
        }
        if (!countdownTimer.isRunning()) {
            countdownTimer.start();
        }
    }

    private void stopCountdown() {
        if (timerLabel == null) {
            return;
        }
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        timerLabel.setText("Timer: --");
    }

    private void updateTimerLabel() {
        long remainingMillis = Math.max(0, timerEndMillis - System.currentTimeMillis());
        long totalSeconds = remainingMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        timerLabel.setText(String.format("Timer: %d:%02d", minutes, seconds));
    }

    private void updateButtons(MachineState state) {

        // default: everything disabled
        setCupsButton.setEnabled(false);
        filledButton.setEnabled(false);
        startButton.setEnabled(false);
        boilWaterButton.setEnabled(false);
        resetButton.setEnabled(true); // reset is usually always allowed


        if(state instanceof EmptyState) {
            setCupsButton.setEnabled(true);
            filledButton.setEnabled(true);
            startButton.setEnabled(true);
            boilWaterButton.setEnabled(true);
        }

        if (state instanceof IdleState) {
            setCupsButton.setEnabled(true);
            filledButton.setEnabled(true);
        }

        else if (state instanceof MakingTeaState) {
            // usually nothing clickable while making tea
        }

        else if (state instanceof BoilingWaterState) {
            // also nothing clickable
        }

        else if (state instanceof DoneState) {
            setCupsButton.setEnabled(true);
            filledButton.setEnabled(true);
            startButton.setEnabled(true);
        }
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
