import java.util.*;
import comp34120.ex2.*;

public class WindowSizeLearner
{
    private List<Double> errors;
    
    public WindowSizeLearner(){
        errors = new ArrayList<Double>();
    }
    
    public int learn(List<Record> records){
        // Find best window size
        for (int windowSize = 1; windowSize <= 99; windowSize ++){
            // Learn function
            MovingWindow learner = new MovingWindow(windowSize);
            learner.addAllData(records);
            learner.learnReaction();
            
            // Test the function
            errors.add(learner.calculateError());               
        }
        
        
        // find min error 
        double minError = errors.get(1);
        int bestSize = 1;
        for (int i = 1; i < errors.size(); i ++){
            // System.out.println("max accuracy " + minError);
            // System.out.println("Current accuracy " + errors.get(i));
            if (minError < errors.get(i)){
                minError = errors.get(i);
                bestSize = i;
                // System.out.println("update" + bestSize);
            }
        }
        
        // System.out.println("Best window size " + bestSize);
        return bestSize;
    }
}
