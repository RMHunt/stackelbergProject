import java.util.*;
import comp34120.ex2.*;

public class WindowSizeLearner
{
    private List<Float> errors;
    
    public WindowSizeLearner(){
        errors = new ArrayList<Float>();
    }
    
    public int learn(List<Record> records){
        // Find best window size
        for (int windowSize = 1; windowSize <= 99; windowSize ++){
            // Learn function
            MovingWindow learner = new MovingWindow(windowSize);
            learner.addAllData(records);
            learner.learnReaction();
            
            // Test the function
            errors.add((float)learner.calculateError());                
        }
        
        
        // find min error 
        float minError = errors.get(0);
        int bestSize = 1;
        for (int i = 1; i < errors.size(); i ++){
            if (minError > errors.get(i)){
                minError = errors.get(i);
                bestSize = i + 1;
            }
        }
        
        return bestSize;
    }
}
