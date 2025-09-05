// Importing necessary Swing and AWT classes
import javax.swing.*;                          // Provides GUI components (JFrame, JMenu, JTextArea, etc.)
import javax.swing.filechooser.FileNameExtensionFilter; // To filter files by extension in FileChooser
import java.awt.*;                             // For layout (BorderLayout, etc.)
import java.io.*;                              // For File I/O (FileReader, FileWriter, etc.)

// Main class extending JFrame (GUI window)
public class SimpleNotepad extends JFrame {

    // JTextArea for editing text
    private JTextArea textArea;

    // JFileChooser to open/save files
    private JFileChooser fileChooser;

    // Constructor to set up the Notepad GUI
    public SimpleNotepad() {
        // Set title of window
        setTitle("Simple Notepad");
        // Set size of window (width, height)
        setSize(600, 500);
        // Center window on screen
        setLocationRelativeTo(null);
        // Exit program when window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // -------------------- TEXT AREA --------------------

        // Create a text area where user can type
        textArea = new JTextArea();

        // Add scroll functionality (so large text can scroll)
        JScrollPane scrollPane = new JScrollPane(textArea);

        // -------------------- FILE CHOOSER --------------------

        // Create a file chooser dialog
        fileChooser = new JFileChooser();

        // Restrict it to only show .txt files
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        // -------------------- MENU BAR --------------------

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create menu items
        JMenuItem openItem = new JMenuItem("Open");
        // Add event listener (lambda function) to call openFile() when clicked
        openItem.addActionListener(e -> openFile());

        JMenuItem saveItem = new JMenuItem("Save");
        // Add event listener to call saveFile() when clicked
        saveItem.addActionListener(e -> saveFile());
        
        JMenuItem exitItem = new JMenuItem("Exit");
        // Exit program when clicked
        exitItem.addActionListener(e -> System.exit(0));

        // Add menu items to File menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // Adds a line separator
        fileMenu.add(exitItem);

        // Add File menu to menu bar
        menuBar.add(fileMenu);

        // Set the menu bar for this frame
        setJMenuBar(menuBar);

        // Add the scrollPane (with text area) to the frame
        // BorderLayout.CENTER → fills entire window
        add(scrollPane, BorderLayout.CENTER);
    }

    // -------------------- OPEN FILE METHOD --------------------
    private void openFile() {
        // Show open dialog and wait for user response
        int option = fileChooser.showOpenDialog(this);

        // If user selects a file (Approve button clicked)
        if (option == JFileChooser.APPROVE_OPTION) {
            // Get selected file
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Read file contents into textArea
                textArea.read(reader, null);
                // Update window title with file name
                setTitle("Simple Notepad - " + file.getName());
            } catch (IOException e) {
                // Show error dialog if file cannot be read
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // -------------------- SAVE FILE METHOD --------------------
    private void saveFile() {
        // Show save dialog
        int option = fileChooser.showSaveDialog(this);

        // If user selects "Save"
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Ensure file has .txt extension
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write textArea content to file
                textArea.write(writer);
                // Update window title with file name
                setTitle("Simple Notepad - " + file.getName());
            } catch (IOException e) {
                // Show error dialog if saving fails
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // -------------------- MAIN METHOD --------------------
    public static void main(String[] args) {
        // Run GUI on Event Dispatch Thread (safe for Swing apps)
        SwingUtilities.invokeLater(() -> {
            SimpleNotepad notepad = new SimpleNotepad();
            notepad.setVisible(true); // Show window
        });
    }
}
