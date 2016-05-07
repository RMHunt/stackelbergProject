import java.util.*;
import comp34120.ex2.*;

public class WindowSizeLearner
{
    private List<float> errors;
    
    public void WindowSizeLearner{
        errors = new ArrayList<float>();
    }
    
    private int learn(ArrayList<Record> records){
        // Find best window size
        for (int windowSize = 1; windowSize <= 99; windowSize ++){
            // Learn function
            MovingWindow learner = new MovingWindow(windowSize);
            learner.addAllData(records);
            learner.learnReaction();
            
            // Test the function
            errors.add(learner.calculateError());                
            }
        }
        
        // find min error 
        float minError = errors.get(0);
        int bestSize = 1;
        for (int i = 1; i < errors.size(); i ++){
            if (minError > error.get(i)){
                minError = error.get(i);
                bestSize = i + 1;
            }
        }
        
        return bestSize;
    }
}