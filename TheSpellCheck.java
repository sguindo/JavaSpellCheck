/**
 * Created by Souleymane Guindo
 * This is the main that will execute the entire program
 *
 ·         Provide the ability to load a dictionary (a file that contains a list of words)
 ·         Provide the ability to load a file that contains the text to check the spelling
 ·         Provide the ability to check if each word of the loaded text is spelled properly (if the word is in the dictionary or not)
 ·         Provide the ability to an operator through a graphical interface to propose a new spelling for each word of the text that is not spelled properly (word that is not in the dictionary).
 ·         Provide the ability to save the corrected text on disk with all the operator modifications
 *
 *************************************Design USED for the Application**********************************
 *      MVC for learning purposes(Model View Controller)
 *      Java Swing for the Graphical Interface
 *      Multithreading for the spell check of words against the dictionary
 ******************************************************************************************************
 *
 * I Attest that is wors was full done by me, Souleymane Guindo
 * If you have any questions please contact by email and I will be more than happy to answer them.
 * ENJOY Your Spell Check!!!!
 */
public class TheSpellCheck {

    public static void main (String [] args){

        SpellCheckerModel theModal = new SpellCheckerModel();

        SpellCheckerView theView = new SpellCheckerView();

        SpellCheckerController theController = new SpellCheckerController(theView, theModal);

    }
}
