import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by Souleymane Guindo
 * This class will read a file and give the content of the file back as a list
 */
public class FileUtils {


    //This method will take a path as an input and extract all the content and returns a set
    public static Set<String> readFromFile(String path) throws IOException {
        Set<String> uniqueWords = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(path));
            uniqueWords = new LinkedHashSet<>();
            while(fileReader.ready()){
                String fileContent = fileReader.readLine();
                //Split the words at whiteSpace character
                String [] content = fileContent.split("\\s");

                for(int i = 0; i < content.length; i++){
                    uniqueWords.add(content[i].toLowerCase());
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("The file does not exist! ");
        }catch (IOException ex) {
            System.out.println("IOException Occured! ");
            ex.printStackTrace();
        } finally {
            if(fileReader != null){
                fileReader.close();
            }
        }
        return uniqueWords;
    }

    //This is a file util helper function which saves to file
    public static void writeToFileSave(List<String> content,String fileName) throws IOException {
        BufferedWriter correctedFileOutput = null;
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showDialog(chooser, "Directory to save");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println(chooser.getSelectedFile());
                correctedFileOutput = new BufferedWriter(new FileWriter(chooser.getSelectedFile()+"\\"+fileName) );
            }
            //Write the content which was corrected to the file
            for(String word : content){
                correctedFileOutput.write(word + " ");
            }
            correctedFileOutput.flush();
        }catch (Exception e){
            System.out.println("File Cant be saved");
            e.printStackTrace();
        }
        finally {
            if(correctedFileOutput != null){
                correctedFileOutput.close();
            }
        }

    }
}
