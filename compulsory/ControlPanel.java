package compulsory;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.File;
import static java.awt.BorderLayout.*;

public class ControlPanel extends JPanel{
    final MainFrame frame;
    Frame MainFrame=new Frame("O noua fereastra");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    JButton resetBtn = new JButton("Reset");
    JButton exitBtn = new JButton("Exit");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        MainFrame.setLayout(new GridLayout(1, 4));
        frame.add(saveBtn);
        frame.add(loadBtn);
        frame.add(resetBtn);
        frame.add(exitBtn);

        saveBtn.addActionListener(this::save);
        loadBtn.addActionListener(this::load);
        resetBtn.addActionListener(this::reset);
        exitBtn.addActionListener(this::exit);
    }

    private void save(ActionEvent e) {
        try {
            ImageIO.write(frame.canvas.image, "PNG", new File("d:/test.png"));
        } catch (IOException ex) { System.err.println(ex); }
    }

    private void load(ActionEvent e) {
        try{
            BufferedImage image= ImageIO.read(new File("d:/test.png"));
            Graphics g=image.getGraphics();
            g.drawImage(image,0,0,frame.canvas);
        }catch (IOException ex) {System.err.println(ex);}
    }

    private void reset(ActionEvent e) {
        frame.canvas=new DrawingPanel(frame);
        frame.add(frame.canvas,CENTER);
        frame.pack();
    }

    private void exit(ActionEvent e) {
        frame.setVisible(false);
    }

}
