package UiUtils;// UIUtils.java
import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

// I love this class but Please change its folder structure

public class UIUtils {

    // Method to set the frame icon from a file path
    public static void setFrameIcon(JFrame frame, String filePath) {
        try {
            URL iconURL = new File(filePath).toURI().toURL();
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        } catch (MalformedURLException e) {
            System.err.println("Invalid file path: " + filePath);
            e.printStackTrace();
        }
    }

    // Method to set the look and feel
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
