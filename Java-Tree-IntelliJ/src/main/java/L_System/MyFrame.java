package L_System;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Creates the frame of the program,
 * calls LSystem.java
 * reads line coordinates from XML file,
 * customizes coordinates to fit drawing field,
 * draws tree.
 */
public class MyFrame extends JFrame{
    /**
     * Length of toolbar.
     */
    private static final int TOOLBAR_LENGTH = 300;
    /**
     * Height of drawing panel.
     */
    private static final  int HEIGHT = 1000;
    /**
     * Width of drawing panel.
     */
    private static final int WIDTH = 1000;
    /**
     * Vertical Padding for the drawing.
     */
    private static final int VERTICAL_PADDING = 30;

    /**
     * TreeType to send when LSystem is called,
     * can be selected from popupmenu.
     */
    TreeType selectedType;
    /**
     *  Recursion index to send, when LSystem is called,
     *  can be  changed in slider.
     */
    int recursions = 5;
    /**
     * ArrayList of lines to be drawn
     */
    ArrayList<Line> lines;
    /**
     * Height of the drawn tree.
     */
    double drawnHeight;
    /**
     * Width of the drawn tree.
     */
    double drawnWidth;
    /**
     * Minimum x value of the drawn tree,used to calculate offset.
     */
    double minX;
    /**
     * Minimum y value of the drawn tree,used to calculate offset.
     */
    double minY;
    /**
     * Multiplier to fit the drawn tree into the panel.
     */
    double lineSize;
    /**
     * Offset for x value of the drawn tree.
     */
    double lineOffsetX;
    /**
     * Offset for x value of the drawn tree.
     */
    double lineOffsetY;
    /**
     * Constructor,
     * Sets up frame,
     * Creates drawPanel,
     * Creates optionPanel,
     * Adds popupMenu for types,
     * Adds slider for number of recursions,
     * Adds generator button that: reads line coordinates from XML File, draws read coordinates after customization.
     */
    MyFrame(){

        lines = new ArrayList<>();

        // setting up frame

        this.setTitle("L-system");
        this.setSize(WIDTH + TOOLBAR_LENGTH,HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("leaf.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // drawPanel

        DrawPanel drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.BLACK);
        add(drawPanel,BorderLayout.CENTER);

        // optionsPanel

        JPanel optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension(TOOLBAR_LENGTH,HEIGHT));
        optionsPanel.setBackground(new Color(3,29,33));
        optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.Y_AXIS));

        // Selector

        JLabel selectorLabel = new JLabel("Type");
        selectorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectorLabel.setForeground(Color.white);
        selectorLabel.setPreferredSize(new Dimension(200,100));

        JButton selectorButton = new JButton("Choose Type");
        selectorButton.setMaximumSize(new Dimension(250,70));
        selectorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPopupMenu popupMenu =  new JPopupMenu();

        for(TreeType type : TreeType.values()){
            TreeType selectedType = type;
            JMenuItem item = new JMenuItem(selectedType.name());
            item.addActionListener(e -> {
                this.selectedType = selectedType;
                System.out.println(selectedType);
                selectorButton.setText(selectedType.name());
            });
            popupMenu.add(item);
        }
        selectorButton.addActionListener(e -> popupMenu.show(selectorButton,0, selectorButton.getHeight()));


        // RecursionSlider

        JSlider recursionSlider = new JSlider(0,10);
        recursionSlider.setMajorTickSpacing(1);
        recursionSlider.setPaintTicks(true);
        recursionSlider.setMaximumSize(new Dimension(250,70));
        recursionSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        recursionSlider.setPaintLabels(true);
        recursionSlider.addChangeListener(e ->{
            recursions = recursionSlider.getValue();
            System.out.println(recursions);
        });

        JLabel recursionSliderLabel = new JLabel("Recursions");
        recursionSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recursionSliderLabel.setForeground(Color.white);
        recursionSliderLabel.setPreferredSize(new Dimension(200,100));

        // generate

        JButton  generatorButton = new JButton("Generate");
        generatorButton.setMaximumSize(new Dimension(200,70));
        generatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generatorButton.addActionListener(e ->{
            if(selectedType != null && recursions != -1){
                System.out.println("Generating");
                new LSystem(selectedType,recursions);
                readLineFromFile();
                customizeDrawPanel();
                repaint();
            }
        });

        // adding panels

        optionsPanel.add(Box.createRigidArea(new Dimension(0,150)));
        optionsPanel.add(selectorLabel);
        optionsPanel.add(selectorButton);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,70)));
        optionsPanel.add(recursionSliderLabel);
        optionsPanel.add(recursionSlider);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,70)));
        optionsPanel.add(generatorButton);

        add(optionsPanel, BorderLayout.WEST);
        setVisible(true);
        JOptionPane.showMessageDialog(this, "A legtöbb fa maximum 5-6 rekurziót bír, ez a határ fölött crash-elhet a program", "Information", JOptionPane.INFORMATION_MESSAGE);

    }
    /**
     * All the customization process for the drawPanel in a function,
     * calculates the coordinates from the lines ArrayList into drawable pixel coordinates,
     * checks if the coordinates fit the drawPanel, and fixes them if they don't fit,
     * sets offset for the drawn tree.
     *
     */
    public void customizeDrawPanel(){
        calculateTreeSizeToPixels();
        checkAndFixTreeSize();
        setOffsetsForDrawing();

        printLines();
        System.out.println("TreeSize: " + lineSize);
        System.out.println("Offset Y: " + lineOffsetY);
        System.out.println("Offset X: " + lineOffsetX);
    }

    /**
     * Reads data from XML file and fills up the lines arraylist with it.
     */
    public void readLineFromFile(){
        lines.clear();
        try{
            BufferedReader file = new BufferedReader(new FileReader("Lines.xml"));
            String currentline;
            double[] start = null;
            double[] end = null;

            while ((currentline = file.readLine()) != null){
                currentline = currentline.trim();
                if(currentline.startsWith("<start>") && currentline.endsWith("</start>")){
                    String values = currentline.substring("<start>".length(), currentline.indexOf("</start>"));
                    String[] parts = values.trim().split("\\s+");
                    start = new double[]{Double.parseDouble(parts[0]),Double.parseDouble(parts[1])};
                }
                if (currentline.startsWith("<end>") && currentline.endsWith("</end>")) {
                    String values = currentline.substring("<end>".length(), currentline.indexOf("</end>"));
                    String[] parts = values.trim().split("\\s+");
                    end = new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])};
                }

                if(start != null && end != null){
                    lines.add(new Line(start,end));
                    start = null;
                    end = null;
                }
            }

            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Calculates the coordinates for the drawn tree from the coordinates in the lines arraylist.
     */
    public void calculateTreeSizeToPixels(){

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for(Line line: lines){
            double [] start = line.getStart();
            double [] end = line.getEnd();

            minX = Math.min(minX, Math.min(start[0], end[0]));
            minY = Math.min(minY, Math.min(start[1], end[1]));
            maxX = Math.max(maxX, Math.max(start[0], end[0]));
            maxY = Math.max(maxY, Math.max(start[1], end[1]));
        }

        double width = maxX - minX;
        double height = maxY - minY;

        drawnHeight = height;
        drawnWidth = width;

        double size = Math.max(width, height);
        lineSize = (HEIGHT - 100)/size;
        this.minY = minY;
        this.minX = minX;
    }
    /**
     * Checks if calculated coordinates fit the drawPanel, fixes them if they don't fit.
     */
    public void checkAndFixTreeSize(){
        if(lineSize < 1){
            for(Line line:lines){
                line.setStart(new double[]{line.getStart()[0] * lineSize, line.getStart()[1]* lineSize});
                line.setEnd(new double[]{line.getEnd()[0] * lineSize, line.getEnd()[1] * lineSize});
            }
            calculateTreeSizeToPixels();
        }
    }
    /**
     * Sets offset for the drawn tree.
     */
    public void setOffsetsForDrawing(){
        lineOffsetX = minX * lineSize - (WIDTH - (drawnWidth * lineSize)) / 2;
        lineOffsetY = minY * lineSize - VERTICAL_PADDING;
    }
    /**
     * Prints all lines from lines arraylist.
     */
    public void printLines(){
        for(Line line: lines){
            System.out.println(Arrays.toString(line.getStart()) + " " + Arrays.toString(line.getEnd()));
        }
    }
    /**
     * DrawPanel on which the tree is drawn.
     */
    class DrawPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            if(lineSize<1) lineSize = 1;

            g.setColor(Color.white);
            int height = getHeight();

            for(Line line : lines){
                g.drawLine((int)(line.getStart()[0]          * lineSize)  - (int)(lineOffsetX),
                           height - (int)(line.getStart()[1] * lineSize) + (int) (lineOffsetY),
                           (int) (line.getEnd()[0]           * lineSize) - (int)(lineOffsetX),
                           height - (int)(line.getEnd()[1]   * lineSize) + (int) (lineOffsetY));
            }
        }
    }
}
