package ui;

import javax.swing.*;
import java.awt.*;

public class Container extends JFrame {
    public Container() throws Exception {
        this.setContentPane(new MyContentPane());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(850, 400));
        setResizable(false);
        setTitle("Mini-chat");
    }
}
