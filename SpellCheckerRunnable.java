/**
 * Created by Souleymane Guindo
 * This class defiens the Job given to each thread.
 */
public class SpellCheckerRunnable extends Thread{
    SpellCheckerModel model;

    //A constructor for the Thread Job
    public SpellCheckerRunnable(String name, SpellCheckerModel model){
        super(name);
        this.model = model;
    }

    //Override run method handles the checking and correction of any words which are not spelled correctly
    public void run(){
        Integer indx = this.model.getIndx();
        while(indx != -1){
            System.out.println(this.getName()+" Indx : "+indx);
            String word = this.model.getContent().get(indx);
            if(this.model.checkAgainstDictionary(word)){
                synchronized (this){
                    this.model.updateWordFromUser(this.getName(),word,indx);
                }
            }
            indx = this.model.getIndx();
        }
        System.out.println(this.getName()+" exiting : ");
    }
}
