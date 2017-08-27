package calculator.gui;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(Image image) {
        super(true);
        this.setImage(image);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, this);
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }
}
