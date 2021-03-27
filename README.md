# GeometryDrawing
Create an application with graphical user interface for creating images (layouts) containing standard or custom geometric figures: diamonds, trapezes, regular polygons, snow flakes, etc.

You may use either Swing or JavaFX. 

## Compulsory

 Create the following components:

   The main frame of the application.
    
   A configuration panel for introducing parameters regarding the shapes that will be drawn: the size, the number of sides, the stroke, etc.
    
   The panel must be placed at the top part of the frame. The panel must contain at least one label and one input component for specifying the size of the component.
    
   A canvas (drawing panel) for drawing various types of shapes. You must implement at least one shape type at your own choice. This panel must be placed in the center part of the frame.
    
   When the users execute mouse pressed operation, a shape must be drawn at the mouse location. You must use the properties defined in the configuration panel (at least one) and generate random values for others (color, etc.).
    
   A control panel for managing the image being created. This panel will contains the buttons: Load, Save, Reset, Exit and it will be placed at the bottom part of the frame.
    
   Use a file chooser in order to specify the file where the image will be saved (or load). 

## Graphical User Interface (GUI)

### Swing

–Standard toolkit, included in JDK

–Standard UI components, lots of them

–No new functionality introduction for the future

### Java FX

–GUI library, not include in JDK (anymore...)

–Rich GUI components, fancy

–Modern, new features added with new version

### The MainFrame class
```python
public class MainFrameextends JFrame {
   ConfigPanel configPanel;
   ControlPanel controlPanel;
   DrawingPanel canvas;
   
   public MainFrame() {
      super("My Drawing Application");
      init();
   }
   
   private void init() {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      //create the components
      canvas = new DrawingPanel(this);
      ...TODO
      //arrange the components in the container (frame)
      //JFrame uses a BorderLayout by default
      add(canvas, CENTER); //this is BorderLayout.CENTER
      ...TODO
      
      //invoke the layout manager
      pack();        
   }
}
```

### The ConfigPanel class
In the MainFrame class, create an instance of this class, and add it in NORTH
```python
public class ConfigPanelextends JPanel {
   final MainFrame frame;
   JLabel label; // we’re drawing regular polygons  
   JSpinner sidesField;  // number of sides
   JComboBox colorCombo; // the color of the shape
   
   public ConfigPanel(MainFrame frame) {
      this.frame = frame;init();
   }
   
   private void init() {
      //create the label and the spinner
      sidesLabel = new JLabel("Number of sides:");
      sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
      sidesField.setValue(6); //default number of sides
      
      //create the colorCombo, containing the values: Random and Black
      ...TODO
      add(sidesLabel); //JPanel uses FlowLayout by defaultadd(sidesField);
      add(colorCombo);        
   }
}
```

### The ControlPanel class
In the MainFrame class, create an instance of this class, and add it in SOUTH
```python
public class ControlPanelextends JPanel {
   final MainFrame frame;
   JButton saveBtn = new JButton("Save");
   //create all buttons (Load, Reset, Exit) 
   ...TODO
   public ControlPanel(MainFrame frame) {
      this.frame = frame; 
      init();
   }
   
   private void init() {
   //change the default layout manager (just for fun)
   setLayout(new GridLayout(1, 4));
   //add all buttons
   ...TODO
   //configure listeners for all buttons
   saveBtn.addActionListener(this::save);
   ...TODO
   }   
   
   private void save(ActionEvent e) {
      try {
         ImageIO.write(frame.canvas.image, "PNG", new File("d:/test.png"));
      } catch (IOException ex) { 
          System.err.println(ex); 
      }
   }
   ...TODO
}
```

### The DrawingPanel class
In the MainFrame class, create an instance of this class, and add it in SOUTH.

We use direct drawing into an offscreen Image.
```python
   public class DrawingPanel extends JPanel {
      final MainFrame frame;
      final static int W = 800, H = 600;
      BufferedImage image;  //the offscreen 
      imageGraphics2D graphics; //the "tools" needed to draw in the image  
      
      public DrawingPanel(MainFrame frame) {
         this.frame = frame; createOffscreenImage(); 
         init();
      }
      
      private void createOffscreenImage() {
         image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
         graphics = image.createGraphics();
         graphics.setColor(Color.WHITE);  //fill the image with whitegraphics.fillRect(0, 0, W, H);
      }
      
      private void init() {
         setPreferredSize(new Dimension(W, H)); //don’t use setSize. Why?
         setBorder(BorderFactory.createEtchedBorder()); //for fun
         this.addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(MouseEvent e) {
               drawShape(e.getX(), e.getY()); 
               repaint();} //Can’t use lambdas, JavaFX does a better job in these cases
             });
       }
       private void drawShape(int x, int y) {
          int radius = ... TODO; //generate a random number
          int sides = ...TODO;   //get the value from UI (in ConfigPanel)
          Color color = ...TODO; //create a transparent random Color.
          graphics.setColor(color);
          graphics.fill(new RegularPolygon(x, y, radius, sides));
       }
       @Override
       public void update(Graphics g) { } //Why did I do that?
       
       @Override
       protected void paintComponent(Graphics g) {g.drawImage(image, 0, 0, this);
 }     
```

### The RegularPolygon class
```python
   public class RegularPolygon extends Polygon {
      public RegularPolygon(int x0, int y0, int radius, int sides) {
         double alpha = 2 * Math.PI / sides;
         for (int i = 0; i < sides; i++) {
            double x = x0 + radius * Math.cos(alpha * i);
            double y = y0 + radius * Math.sin(alpha * i);
            this.addPoint((int) x, (int) y);
         }
      }
   }
```

### Create other Shapes
```python
 public class NodeShape extends Ellipse2D.Double {
   public NodeShape(double x0, double y0, double radius) {
     super(x0 - radius / 2, y0 - radius / 2, radius, radius);
   }
 }
```

## Direct vs. Retained Drawing

### Direct mode (like in Paint)
  –We store the drawing using a single image
  
  –Individual Shapes are lost
  
  –paintComponent simply draws the image

### Retained mode (like in PhotoShop)
  –We store ALL Shapes in a data structure
  
  –List<Shape> shapes
  
  –paintComponent draws all the shapes, one by one

