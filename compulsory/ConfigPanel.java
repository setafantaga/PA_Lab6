package compulsory;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.Random.*;

public class ConfigPanel extends JPanel{
    private final MainFrame frame;
    JLabel label; // we’re drawing regular polygons
    JSpinner sidesField; // number of sides
    JComboBox colorCombo; // the color of the shape

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        //create the label and the spinner
        label = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        sidesField.setValue(6); //default number of sides

        //create the colorCombo, containing the values: Random and Black
        colorCombo=new JComboBox();
        colorCombo.addItem("Black");
        colorCombo.addItem("Random");

        add(label); //JPanel uses FlowLayout by default
        add(sidesField);
        add(colorCombo);
    }

}
