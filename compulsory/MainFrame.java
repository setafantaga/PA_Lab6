package compulsory;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import static java.awt.BorderLayout.*;

public class MainFrame extends JFrame{
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        //create the components
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas = new DrawingPanel(this);
        controlPanel=new ControlPanel(this);
        configPanel=new ConfigPanel(this);

        //arrange the components in the container (frame)
        //JFrame uses a BorderLayout by default
        add(canvas, CENTER);
        add(controlPanel, SOUTH);
        add(configPanel, NORTH);

        //invoke the layout manager
        pack();
    }
}
