/**
 * Created by Souleymane Guindo
 * This is SpellCheckerController class which handles all operations
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;


public class SpellCheckerController extends Thread{

    private SpellCheckerView theView;
    private SpellCheckerModel theModel;
    private String dictPath = "";
    private String inputFilePath = "";
    private String userInput = "";

    public SpellCheckerController(SpellCheckerView theView, SpellCheckerModel theModel){
        this.theView = theView;
        this.theModel = theModel;

        //Tell theView when the upload button is clicked to get execute the actionPerfomed
        this.theView.uploadFileListener(new SpellCheckListner());
        this.theView.spellCheckListener(new SpellCheckListner());
    }

    class SpellCheckListner implements ActionListener {

        //Set and List DS to store the dictionary and the input file given by the user
        Set<String> dictionary;
        List<String> fileContent;

        //ActionListener for the different action triggered by the JFrame
        public void actionPerformed(ActionEvent e) {

            //If the action is from the uploadFile button then also the user to upload a dictionary
            if (e.getSource() == theView.uploadFileBtn) {

                int returnVal = theView.fileChooser.showOpenDialog(theView);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = theView.fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    if (theView.uploadFileBtn.getName().equals("uploadDict")) {
                        dictPath = selectedFile.getAbsolutePath();
                        theView.uploadFileBtn.setName("uploadInput");
                        theView.displayLogMessage("DICTIONARY NAME: " + selectedFile.getName());
                        theView.uploadFileBtn.setText("UPLOAD INPUT FILE");

                    } else {
                        theView.spellCheckBtn.setEnabled(true);
                        inputFilePath = selectedFile.getAbsolutePath();
                        theView.displayLogMessage("\nINPUT-FILE NAME: " + selectedFile.getName());
                    }
                }

                //If the event source is from the spell check button then perform the spell Check
            } else if (e.getSource() == theView.spellCheckBtn) {

                try {
                    //Read the DictionaryFile file path and get the content using the fileUtil
                    dictionary = FileUtils.readFromFile(dictPath);
                    System.out.println(dictPath);

                    //Read the Input File file path and get the content using the fileUtil
                    fileContent = new ArrayList<>(FileUtils.readFromFile(inputFilePath));

                    SpellCheckerModel theSpellCheck = new SpellCheckerModel(fileContent, dictionary);

                    //Create the threads for the spell check to take place in parallel and give them theJOB
                    SpellCheckerRunnable threadOne = new SpellCheckerRunnable("Thread One", theSpellCheck);
                    SpellCheckerRunnable threadTwo = new SpellCheckerRunnable("Thread Two", theSpellCheck);
                    SpellCheckerRunnable threadThree = new SpellCheckerRunnable("Thread Three", theSpellCheck);

                    //Start the threads and run the spell check in parallel
                    threadOne.start();
                    threadTwo.start();
                    threadThree.start();

                    //Wait for all the threads to terminate before continuing
                    try {
                        threadOne.join();
                        threadTwo.join();
                        threadThree.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    //Unable the button to save the file
                    theView.displayLogMessage("*************Correction of the file done*************");

                    //Correct the Words
                    Hashtable<Integer, String> errorWords = theSpellCheck.getErrorMAp();

                    System.out.println(errorWords);
                    for (Integer key : errorWords.keySet()) {
                        int indx = Integer.valueOf(key);
                        String wrongWord = errorWords.get(indx);
                        userInput = JOptionPane.showInputDialog("Word not correct!!! \"" + wrongWord + "\"\nPlease Enter Correction");

                        theView.displayLogMessage("Word Corrected:   From: \"" + wrongWord + "\" To: \""+ userInput +"\"");
                        this.fileContent.set(indx,userInput);
                    }


                    //Display log and message for the user
                    String saveFile = "SpellCheckCorrectedFile.txt";
                    JOptionPane.showMessageDialog(null,"Spell Check Completed. You can now save the work: Please Choose Directory");

                    FileUtils.writeToFileSave(fileContent, "SpellCheckCorrectedFile.txt");

                    JOptionPane.showMessageDialog(null,"Thank you for Using the Spell Check your file is saved as: \"" + saveFile + "\"");

                    theView.displayLogMessage("Spell Check Completed.");


                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    System.out.println("The file does not exist! ");
                } catch (IOException ex) {
                    System.out.println("IOException Occured! ");
                    ex.printStackTrace();
                }
            }
        }
    }

}

