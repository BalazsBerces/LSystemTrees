package L_System;

import java.util.ArrayList;
/**
 * Branch of the tree,
 * has a string of the branch itself,
 * has an arraylist of its children that form in the branching process.
 */
public class TreeNode {
    /**
     * String of itself.
     */
    private String itself;
    /**
     * Arraylist of its children, which are also branches.
     */
    private ArrayList<TreeNode> children = new ArrayList<>();
    /**
     * Basic setItself.
     * @param itself Sets itself.
     */
    public void setItself(String itself) {
        this.itself = itself;
    }
    /**
     * Basic setChildren.
     * @param  children Sets its children.
     */
    public void setChildren(ArrayList<TreeNode> children){
        this.children = children;
    }
    /** Basic addChild.
     * @param child Adds a child to the children arraylist.
     */
    public void addChild(TreeNode child){
        children.add(child);
    }
    /**
     * Basic getChildren.
     * @return Returns the children arraylist.
     */
    public ArrayList<TreeNode> getChildren(){
        return children;
    }
    /**
     * Basic getItslef.
     * @return Retruns the string "itself".
     */
    public String getItself(){
        return itself;
    }
    /**
     * Prints out the tree in a readable way.
     */
    public void print(String prefix) {
        System.out.println(prefix + itself);
        for (TreeNode child : children) {
            child.print(prefix + "                      ");
        }
    }
}
