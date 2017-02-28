import java.io.*;
import java.util.*;

/**
 * Created by Souleymane Guindo
 * This class will read a file and give the content of the file
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
        public static void writeToFile(List<String> content,String fileName) throws IOException {
            BufferedWriter correctedFileOutput = null;
            try{
                correctedFileOutput = new BufferedWriter(new FileWriter(fileName) );
                for(String word : content){
                    correctedFileOutput.write(word + " ");
                }
                correctedFileOutput.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if(correctedFileOutput != null){
                    correctedFileOutput.close();
                }
            }

        }
}
