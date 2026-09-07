package L_System;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Creates a tree, from type and a recursion index, then makes a file containing its coordinates.
 */
public class LSystem {
    /**
     * ArrayList of the branches of the tree.
     */
    private TreeNode tree;
    /**
     * ArrayList of the coordinates of the lines.
     */
    private ArrayList<Line> lines;

    /**
     * Constructor
     * that initializes ArrayLists,
     * Creates the seed string from type and the recursion index,
     * Creates the tree, from the seed string,
     * Converts the tree into coordinates for lines to be drawn,
     * Writes XML file from those lines containing their coordinates.
     * @param type tree type, which contains the axiom and the rules for recursion.
     * @param recursions the number of times the rules get applied to the seed string.
     */
    public LSystem(TreeType type, int recursions){

        lines = new ArrayList<>();

        String string = stringRecursion(type, recursions);
        System.out.println(string);

        createTree(string);
        treeToCoordinates(tree, 0, 0, 90, type.getAngleModifier() , 10,0);
        printLines();
        linesToFile();
    }
    /**
     * Creates the seed string from type, and the recursion index.
     * @param type is the type of the tree, which contains the axiom and the rules for recursion.
     * @param recursions is the number of times the rules get applied to the seed string.
     * @return returns the seed string, from which the tree will be created.
     */
    public String stringRecursion(TreeType type, int recursions){

        char c;
        HashMap<Character, String> rules = type.getRules();
        String string = type.getAxiom();

        for (int recursionIndex = 0; recursionIndex < recursions; recursionIndex++){

            StringBuilder newString = new StringBuilder();

            for (int j = 0; j < string.length(); j++){
                c = string.charAt(j);

                if (rules.containsKey(c)){
                    String expansion = rules.get(c);
                    newString.append(expansion);
                }
                else{
                    newString.append(c);
                }
            }
            string = newString.toString();
        }
        return string;
    }
    /**
     * Creates the main tree from string seed,
     * The creation starts with the main branch,
     * If the current branch has subbranches, it adds them, to the original branch as its children.
     * @param string this is the seed string , from which the tree is created branch by branch.
     * @return returns the current branch.
     * @throws UnBalancedBracketsException if the syntax of the seed is wrong, it throws an UnBalancedBracketsException.
     */
    public TreeNode stringParser(String string) throws UnBalancedBracketsException {

        TreeNode node = new TreeNode();

        int depth = 0;
        StringBuilder itself = new StringBuilder();
        StringBuilder childString = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {

            char c = string.charAt(i);

            if(c == '['){
                if(depth > 0){
                    childString.append(c);
                }
                else{
                    itself.append('B');
                }
                depth += 1;
            }
            else if(c == ']'){
                depth -= 1;
                if(depth > 0){
                    childString.append(c);
                }
                if(depth == 0){
                    TreeNode childNode = stringParser(childString.toString());
                    node.addChild(childNode);
                    childString.delete(0 , childString.length());
                }
                else if(depth < 0){
                    throw new UnBalancedBracketsException("Unbalanced brackets at index " + i);
                }
            }
            else{
                if(depth == 0){
                    itself.append(c);
                }
                else if(depth >= 1){
                    childString.append(c);
                }
            }
        }

        if (depth != 0) {
            throw new UnBalancedBracketsException("Unbalanced brackets, missing closing brackets");
        }

        node.setItself(itself.toString());
        return node;
    }
    /**
     * Exception for unbalanced brackets in the stringParser function.
     */
    public class UnBalancedBracketsException extends Exception{
        public UnBalancedBracketsException(String message){
            super(message);
        }
    }
    /**
     * Calls stringParser to create the tree,
     * mainly here for cleanness,
     * if the brackets are unbalanced it catches the exception.
     * @param string this is the seed string, from which the tree is created.
     */
    public void createTree(String string){
        try{
            tree = (stringParser(string));
        }
        catch(UnBalancedBracketsException e){
            System.out.println("Parser failed: " + e.getMessage());
        }

        if (tree != null) {
            tree.print("    ");
        }
    }
    /**
     * Turns the tree into coordinates, into lines, and stores them in an arraylist.
     * @param node is the tree, starting from the main branch.
     * @param x the x coordinate for line creation.
     * @param y the y coordinate for line creation.
     * @param angle the angle at which the lines continue.
     * @param angleModifier it modifies the angle by a certain amount, every time an angleModification is called.
     * @param size it sets the length of each line with the help of the depth index.
     * @param depth with each branching, the depth index increases, with each increase, the line length shortens.
     */
    public void treeToCoordinates(TreeNode node, double x, double y, double angle, double angleModifier, double size, int depth){
        int count = 0;

        for(char c : node.getItself().toCharArray()){
            if(c == 'B') {
                treeToCoordinates(node.getChildren().get(count), x, y, angle, angleModifier , size,depth + 1);
                count += 1;
            }
            else if(c == 'F'){

                double length = size * Math.pow(0.8, depth);

                double newX = x + (length * Math.cos(Math.toRadians(angle)));
                double newY = y + (length * Math.sin(Math.toRadians(angle)));

                newX = Math.abs(newX) < 1e-10 ? 0.0 : newX;
                newY = Math.abs(newY) < 1e-10 ? 0.0 : newY;

                Line line = new Line(new double[]{x,y},new double[]{newX,newY});
                lines.add(line);

                x = newX;
                y = newY;
            }
            else if(c == '+'){
                angle += angleModifier;
            }
            else if(c == '-'){
                angle -= angleModifier;
            }
        }
    }
    /**
     * Writes XML file from the lines arraylist.
     */
    public void linesToFile(){
        try{
            FileWriter file = new FileWriter("Lines.xml");
            file.write("<lines>\n");

            for(Line line : lines){
                file.write("    <line>\n");
                file.write("        <start>" + line.getStart()[0] + " " + line.getStart()[1] + "</start>\n");
                file.write("        <end>" + line.getEnd()[0] + " " + line.getEnd()[1] + "</end>\n");
                file.write("    </line>\n");
            }
            file.write("</lines>\n");

            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Prints all lines, from lines arraylist.
     */
    public void printLines(){
        for(Line line: lines){
            System.out.println(Arrays.toString(line.getStart()) + " " + Arrays.toString(line.getEnd()));
        }
    }

    /**
     * @return returns lines arraylist.
     */
    public ArrayList<Line> getLines() {
        return lines;
    }
}