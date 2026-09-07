package L_System;

import javax.swing.SwingUtilities;
import java.io.File;

/**
 * Main class.
 * Deletes the previously generated XML file and opens the application window.
 */
public class Main {
    public static void main(String[] args) {
        File file = new File("Lines.xml");
        if (file.exists()) {
            file.delete();
        }

        SwingUtilities.invokeLater(MyFrame::new);
    }
}
