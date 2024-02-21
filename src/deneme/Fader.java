package deneme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Fader {
    private Color fadeColor;

    private int steps;

    private int interval;

    private Hashtable backgroundColors = new Hashtable();

    public Fader(Color fadeColor) {
        this(fadeColor, 10, 50);
    }

    public Fader(Color fadeColor, int steps) {
        this(fadeColor, steps, 50);
    }

    public Fader(Color fadeColor, int steps, int interval) {
        this.fadeColor = fadeColor;
        this.steps = steps;
        this.interval = interval;
    }

    public Fader add(JComponent component) {

        ArrayList colors = getColors(component.getBackground());


        new FaderTimer(colors, component, interval);

        return this;
    }

    private ArrayList getColors(Color background) {

        Object o = backgroundColors.get(background);

        if (o != null) {
            return (ArrayList) o;
        }


        ArrayList colors = new ArrayList(steps + 1);
        colors.add(background);

        int rDelta = (background.getRed() - fadeColor.getRed()) / steps;
        int gDelta = (background.getGreen() - fadeColor.getGreen()) / steps;
        int bDelta = (background.getBlue() - fadeColor.getBlue()) / steps;

        for (int i = 1; i < steps; i++) {
            int rValue = background.getRed() - (i * rDelta);
            int gValue = background.getGreen() - (i * gDelta);
            int bValue = background.getBlue() - (i * bDelta);

            colors.add(new Color(rValue, gValue, bValue));
        }

        colors.add(fadeColor);
        backgroundColors.put(background, colors);

        return colors;
    }

    class FaderTimer implements FocusListener, ActionListener, MouseListener {
        private ArrayList colors;
        private JComponent component;
        private Timer timer;
        private int alpha;
        private int increment;

        FaderTimer(ArrayList colors, JComponent component, int interval) {
            this.colors = colors;
            this.component = component;
            component.addMouseListener(this);
            timer = new Timer(interval, this);
        }

        public void focusGained(FocusEvent e) {
            alpha = 0;
            increment = 1;
            timer.start();
        }

        public void focusLost(FocusEvent e) {
            alpha = steps;
            increment = -1;
            timer.start();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            alpha = 0;
            increment = 1;
            timer.start();
        }

        public void mouseExited(MouseEvent e) {
            alpha = steps;
            increment = -1;
            timer.start();
        }

        public void actionPerformed(ActionEvent e) {
            alpha += increment;

            component.setBackground((Color) colors.get(alpha));

            if (alpha == steps || alpha == 0)
                timer.stop();
        }
    }
}