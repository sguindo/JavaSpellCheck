import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Created by Souleymane Guindo
 * This is util class which used for storing the dictionary and the content of the input file
 */
public class SpellCheckerUtil {

    private List<String> testFileContent;
    private Set<String> dict;
    private volatile Integer indx = 0;
    //This method checks whether the word is spelled correctly or not
    public SpellCheckerUtil(List<String> testFileContent, Set<String> dict){
        this.dict = dict;
        this.testFileContent = testFileContent;
    }

    public List<String> getContent(){
        return  this.testFileContent;
    }

    public Set<String> getDict(){
        return this.dict;
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

    private void updateContent(String content, int index){
        this.testFileContent.set(index,content);
    }

    //This method allows the user to suggest a correction word for the misspelled word and it is thread safe
    public synchronized void updateWordFromUser(String threadName, String wordToCorrect, Integer indx){
        System.out.println(threadName+" The following word :::>\"" + wordToCorrect + "\"<::: is not spelled correctly \n");
        System.out.println(threadName+":::::::Please provide the correct spelling for the word::::::::::");
        String userInput = "";
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userInput = reader.readLine();
            updateContent(userInput,indx);
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

}
