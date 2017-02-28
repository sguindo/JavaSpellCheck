/**
 * Created by Souleymane Guindo
 * This is TheSpellChecker class which handles all operations
 */

import java.io.*;
import java.util.*;


public class TheSpellChecker extends Thread{

    public static void main(String []args){

        //The path for the dictionary to be used for the spellCheck of against the inputFile
        String dictPath = "/Users/sguindo/Documents/JavaProjects/TheFiles/dictionarySmall.txt";

        //The Path for the inputFile
        String inputFilePath = "/Users/sguindo/Documents/JavaProjects/TheFiles/inputSmall.txt";

        Set<String> dictionary;
        List<String> fileContent;

        //The Name of the file to be saved
        String saveFileName = "correctedFile.txt";

        try {
            //Read the inputFile file
            dictionary = FileUtils.readFromFile(dictPath);
            //Read the inputFile file
            fileContent = new ArrayList<>(FileUtils.readFromFile(inputFilePath));

            SpellCheckerUtil theSpellCheck = new SpellCheckerUtil(fileContent, dictionary);

            //Create the threads for the spell check to take place in parallel
            SpellChecker threadOne = new SpellChecker("Thread One",theSpellCheck);
            SpellChecker threadTwo = new SpellChecker("Thread Two",theSpellCheck);
            SpellChecker threadThree = new SpellChecker("Thread Three",theSpellCheck);

            //Start the threads and run the spell check in parallel
            threadOne.start();
            threadTwo.start();
            threadThree.start();

            try{
                threadOne.join();
                threadTwo.join();
                threadThree.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            //Once the execution is compeleted I save the content of the input array to file
            FileUtils.writeToFile(fileContent, saveFileName);

        }
        catch (FileNotFoundException e) {
            System.out.println("The file does not exist! ");
        }catch (IOException ex) {
            System.out.println("IOException Occured! ");
            ex.printStackTrace();
        } finally {

        }

    }

}
