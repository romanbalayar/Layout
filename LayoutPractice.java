import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.TitledBorder;


/**
 * A JFrame-based application that demonstrates various layout management and graphical drawing techniques.
 * This application showcases the ability to count button clicks and display different graphical shapes based
 * on user selection.
 * @author Roman Balayar
 * @CS251L - Lab 8
 * 
 */

public class LayoutPractice extends JFrame {
    private int buttonClickCount = 0;
    private final JLabel clickCountLabel;
    private final CustomPanel customPanel;

    /**
     * Constructs the main frame of the application, initializing all UI components
     * and their respective layouts and handlers.
     */

    public LayoutPractice() {
        setTitle("Layout Practice");
        setSize(550, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        customPanel = new CustomPanel();
        add(customPanel, BorderLayout.CENTER);


        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));

        clickCountLabel = new JLabel("Button clicks = 0");
        clickCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        eastPanel.add(clickCountLabel);


        JButton button = new JButton("Click me for a dialog");
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickCount++;
                clickCountLabel.setText("Button clicks: " + buttonClickCount);
                JOptionPane.showMessageDialog(LayoutPractice.this,
                        "The button was clicked " + buttonClickCount + " times.");
            }
        });
        eastPanel.add(button);
        add(eastPanel, BorderLayout.SOUTH);



        JPanel shapeSelectionPanel = new JPanel();
        shapeSelectionPanel.setLayout(new FlowLayout());

        shapeSelectionPanel.setBorder(new TitledBorder("Select your choice"));


        JRadioButton circleButton = new JRadioButton("circle");
        circleButton.setSelected(true);
        circleButton.setActionCommand("circle");
        circleButton.addActionListener(e -> customPanel.setShape("circle"));



        JRadioButton rectangleButton = new JRadioButton("rectangle");
        rectangleButton.setActionCommand("rectangle");
        rectangleButton.addActionListener(e -> customPanel.setShape("rectangle"));


        JRadioButton roundRectangleButton = new JRadioButton("round rectangle");
        roundRectangleButton.setActionCommand("round rectangle");
        roundRectangleButton.addActionListener(e -> customPanel.setShape("round rectangle"));




        ButtonGroup group = new ButtonGroup();
        group.add(circleButton);
        group.add(rectangleButton);
        group.add(roundRectangleButton);


        shapeSelectionPanel.add(circleButton);
        shapeSelectionPanel.add(rectangleButton);
        shapeSelectionPanel.add(roundRectangleButton);


           eastPanel.add(shapeSelectionPanel);

           add(eastPanel, BorderLayout.PAGE_END);

    }

    /**
     * Initializes a custom panel with predefined minimum and
     * preferred sizes and a component listener for repaint on resize.
     */
    private class CustomPanel extends JPanel {
        private String shape = "circle";

        public CustomPanel() {
            setPreferredSize(new Dimension(600, 325)); //
            setMinimumSize(new Dimension(300, 200));
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    repaint();
                }
            });

        }

        /**
         * Sets the current shape to be drawn in the custom panel and triggers a repaint.
         *
         * @param shape A string that defines the shape to be drawn ("circle", "rectangle", "round rectangle").
         */
        public void setShape(String shape) {
            this.shape = shape;
            repaint();
        }

        /**
         * Custom paint method to draw shapes based on the current 'shape' state.
         *
         * @param g The Graphics object used for drawing on this component.
         */

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            switch (shape) {
                case "circle" -> drawCircles(g);
                case "rectangle" -> drawRectangles(g);
                case "round rectangle" -> drawRoundRectangles(g);
            }
        }

        /**
         * Draws multiple concentric circles alternating between red and yellow colors.
         *
         * @param g The Graphics object used for drawing the circles.
         */
        private void drawCircles(Graphics g) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int outerRadius = Math.min(getWidth(), getHeight()) / 3;


            for (int i = 0; i < 5; i++) {

                g.setColor(Color.RED);
                g.fillOval(centerX - outerRadius + i * 20, centerY - outerRadius + i * 20,
                        2 * (outerRadius - i * 20), 2 * (outerRadius - i * 20));

                if (outerRadius - (i * 20 + 10) > 0) {

                    g.setColor(Color.YELLOW);
                    g.fillOval(centerX - outerRadius + (i * 20 + 10), centerY - outerRadius + (i * 20 + 10),
                            2 * (outerRadius - (i * 20 + 10)), 2 * (outerRadius - (i * 20 + 10)));
                }
            }
        }

        /**
         * Draws a series of shrinking rectangles, alternating between red and yellow colors.
         *
         * @param g The Graphics object used for drawing the rectangles.
         */

        private void drawRectangles(Graphics g) {
            int initialX = getWidth() / 2;
            int initialY = getHeight() / 2;
            int width = getWidth() / 4;
            int height = getHeight() / 4;
            int inset = 15;

            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }

                int currentX = initialX - (width - i * inset / 2);
                int currentY = initialY - (height - i * inset / 2);
                int currentWidth = 2 * (width - i * inset / 2);
                int currentHeight = 2 * (height - i * inset / 2);

                g.fillRect(currentX, currentY, currentWidth, currentHeight);
            }
        }

        /**
         * Draws a series of shrinking round rectangles, alternating between red and yellow colors.
         *
         * @param g The Graphics object used for drawing the round rectangles.
         */

        private void drawRoundRectangles(Graphics g) {
            int initialX = getWidth() / 2;
            int initialY = getHeight() / 2;
            int width = getWidth() / 3;
            int height = getHeight() / 3;
            int arcWidth = 30;
            int arcHeight = 30;
            int inset = 15;

            for (int i = 0; i < 12; i++) {
                if (i % 2 == 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }

                int currentX = initialX - (width - i * inset / 2);
                int currentY = initialY - (height - i * inset / 2);
                int currentWidth = 2 * (width - i * inset / 2);
                int currentHeight = 2 * (height - i * inset / 2);

                g.fillRoundRect(currentX, currentY, currentWidth, currentHeight, arcWidth, arcHeight);
            }
        }

    }


    /**
     * The entry point of the application. It creates and shows the main frame on the event dispatch thread.
     *
     * @param args Command line arguments (not used).
     */

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LayoutPractice().setVisible(true);
            }
        });
    }
}
