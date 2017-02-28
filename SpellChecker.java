import java.util.Set;

/**
 * Created by Souleymane Guindo
 */
public class SpellChecker extends Thread{
    SpellCheckerUtil model;
    public SpellChecker(String name, SpellCheckerUtil model){
        super(name);
        this.model = model;
    }

    //Override run method handles the checking and correction of any words which are not spelled correctly
    public void run(){
        Integer indx = this.model.getIndx();
        while(indx != -1){
            System.out.println(this.getName()+" Indx : "+indx);
            String word = this.model.getContent().get(indx);
            if(this.checkAgainstDictionary(word)){
                this.model.updateWordFromUser(this.getName(),word,indx);
            }
            indx = this.model.getIndx();
        }
        System.out.println(this.getName()+" exiting : ");
    }

    //This function checks the input against the dictionary, if the word is incorrect it return true else it returns false
    public boolean checkAgainstDictionary(String theWordToCheck){

        String theWord = theWordToCheck.toLowerCase();
        String plainWord;
        int wordLength = theWord.length();
        Set<String> dictionary = this.model.getDict();
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
