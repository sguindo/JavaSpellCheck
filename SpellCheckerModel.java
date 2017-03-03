import java.lang.*;
import java.util.*;

/**
 * Created by Souleymane Guindo
 * This is util class which used for storing the dictionary and the content of the input file
 */
public class SpellCheckerModel {

    private List<String> testFileContent;
    private Set<String> dict;
    private volatile Integer indx = 0;
    private Hashtable<Integer, String> errorMAp = new Hashtable<Integer, String>();

    //This method checks whether the word is spelled correctly or not
    public SpellCheckerModel(){
    }

    public SpellCheckerModel(List<String> testFileContent, Set<String> dict){
        this.dict = dict;
        this.testFileContent = testFileContent;
    }

    public List<String> getContent(){
        return  this.testFileContent;
    }

    public Set<String> getDict(){
        return this.dict;
    }

    public Hashtable<Integer, String> getErrorMAp() {
        return errorMAp;
    }

    public synchronized Integer getIndx(){
        int oldIndx = indx;
        if(oldIndx >= this.testFileContent.size())
            oldIndx = -1;
        else{
            indx ++;
        }
        return Integer.valueOf(oldIndx);
    }

    //This method allows the user to suggest a correction word for the misspelled word and it is thread safe
    public synchronized void updateWordFromUser(String threadName, final String wordToCorrect, final Integer indx){

        System.out.println(indx + " " + wordToCorrect);
        errorMAp.put(indx, wordToCorrect);
    }

    //This function checks the input against the dictionary, if the word is incorrect it return true else it returns false
    public boolean checkAgainstDictionary(String theWordToCheck){

        String theWord = theWordToCheck.toLowerCase();
        String plainWord;
        int wordLength = theWord.length();
        Set<String> dictionary = this.getDict();
        //Case 1: Check if the word is in the dictionary and spelt correctly
        if(dictionary.contains(theWord)){
            return false;
        }
        // Case 2: Check if the Word is in quotes or not eg: word passed as "Java instead of Java
        if(wordLength > 1 && theWord.substring(0,1).equals("\"")){
            plainWord = theWord.substring(1, wordLength);
            if(dictionary.contains(plainWord)){
                return false;
            }
        }

        //Case 3: check the Word for punctuation such as . ! ; , : eg: word passed as Java. instead of Java
        if(wordLength > 1 && (theWord.substring(wordLength - 1).equals(".")  || theWord.substring(wordLength - 1).equals(",") ||  theWord.substring(wordLength - 1).equals("!")
                ||  theWord.substring(wordLength - 1).equals(";") || theWord.substring(wordLength - 1).equals(":")))
        {
            plainWord = theWord.substring(0, wordLength-1);

            if (dictionary.contains(plainWord))
            {
                return false;
            }
        }

        //I will make this the final case for this program
        //Case 4: check theWord if its with quotes and punctuation eg: word passed as Java," instead of Java
        if (wordLength > 2 && (theWord.substring(wordLength-2).equals(",\"")  || theWord.substring(wordLength-2).equals(".\"")
                || theWord.substring(wordLength-2).equals("?\"") || theWord.substring(wordLength-2).equals("!\"") ))
        {
            plainWord = theWord.substring(0, wordLength-2);

            if (dictionary.contains(plainWord))
            {
                return false;
            }
        }
        //Word not correct
        return true;
    }
}
