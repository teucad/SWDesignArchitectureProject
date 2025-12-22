package MVC.View.Panels;



import javax.swing.*;
import java.awt.*;

public class LedIndicatorPanel extends JPanel {


    private final JLabel label;

    public LedIndicatorPanel(String text) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        add(label, BorderLayout.NORTH);
    }



    public void setActive(boolean active) {
        label.setBackground(active ? Color.YELLOW : Color.LIGHT_GRAY);
        label.setForeground(active ? Color.BLACK : Color.DARK_GRAY);
    }


}
