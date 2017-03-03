import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Souleymane Guindo
 * This class is responsible for showing the graphical interface
 */
public class SpellCheckerView extends JFrame{

    JButton uploadFileBtn , spellCheckBtn;
    JFileChooser fileChooser, saveFile;
    JTextArea log;
    JFrame theFrame;

    public SpellCheckerView(){
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //CreateTheFileChooser to choose the dictionary and the inputFile
        fileChooser = new JFileChooser();
        saveFile = new JFileChooser();
        spellCheckBtn = new JButton("Perform Spell Check");
        spellCheckBtn.setEnabled(false);

        uploadFileBtn = new JButton("UPLOAD DICTIONARY");
        uploadFileBtn.setName("uploadDict");


        //Create the button Layout and add to panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(uploadFileBtn);
        buttonPanel.add(spellCheckBtn);

        //Create Frame
        theFrame = new JFrame("The Spell Checker");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the JFrame
        theFrame.add(logScrollPane, BorderLayout.PAGE_START);
        theFrame.add(buttonPanel, BorderLayout.PAGE_END);

        //Display the window.
        theFrame.pack();
        theFrame.setSize(600,400);
        theFrame.setVisible(true);
    }

    //Displays the log message to the user
    public void displayLogMessage(String log){
        this.log.append(log + "\n");
    }

    //If the uploadFileButton is clicked execute method in the controller called actionPerformed
    public void uploadFileListener(ActionListener e) {
        uploadFileBtn.addActionListener(e);
    }

    //If the spellCheckListner is clicked execute method in the controller called actionPerformed
    public void spellCheckListener(ActionListener e) {
        spellCheckBtn.addActionListener(e);
    }

}
