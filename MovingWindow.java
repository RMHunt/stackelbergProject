import java.lang.IllegalArgumentException;
import java.util.*;
import comp34120.ex2.*;
public class MovingWindow extends AbstractLearner{

	/**
	 *	Inherited:
	 *	List<Record> 		history
	 * 	List<Float> 		followerStrats
	 * 	ReactionFunction 	learnedFunction 
	 */
	private int windowSize;
    private int currentDate;
    private List<Record> window = new ArrayList<Record>();
        
    // constructor
    public MovingWindow(int windowSize){
        if(windowSize < 1)
            throw new IllegalArgumentException("Window Size must not be less than 1");
        
        this.windowSize = windowSize;
    }

    // Will learn the reaction function from all available data
    @Override
    public void learnReaction(){
        // Initialise window
        for (int i = 0; i < windowSize; i ++){
            window.add(history.get(i));
        }
        currentDate = windowSize;
        
        // Learn initial estimator
        learnedFunction = regressWindow();
        
        // Update initial estimator using historical data until the end
        while (currentDate < history.size){
            updateLearn(0.1, 0.1);
        }
    }
    
    // Update the reaction function 
    @Override
    public void updateLearn(float leaderVal, float followerVal){
        // Updated = Old + 0.95 * difference between new and old
        // 0.95 simulates the lambda in the notes for modified moving window
        // there should be a mathematical solution to find the best theta for the
        // whole equation, but I have not found it yet.
        // probably too complex if lambda has to be learned too.
        if (currentDate < history.size){
            window.remove(0);
            window.add(history.get(currentDate));
            currentDate ++;
        } else {
            return;
        }
        
        ReactionFunction newFunction = regressWindow();
        float lambda = 0.95;
        currentA = learnedFunction.getA();
        currentB = learnedFunction.getB();
        learnedFunction.setA(currentA + lambda * (newFunction.getA() - currentA));
        learnedFunction.setB(currentB + lambda * (newFunction.getB() - currentB));
    }
    
    // Returns the predicted reaction for a particular date
    @Override
    public float getReaction(float leaderValue){
    	return learnedFunction.calculateReactionValue(leaderValue);
    }
    
    // Returns the learned function
    @Override
    public ReactionFunction getReactionFunction(){
        return learnedFunction;
    }
    
    // Linear regression method
    private ReactionFunction regressWindow(){
        float alpha, beta = 0;
        float cov, var = 0;
        
        // find means
        float leaderMean, followMean = 0;
        for (int i = 0; i < windowSize; i ++){
            leaderMean += window.get(i).m_leaderPrice;
            followMean += window.get(i).m_followerPrice;
        }
        
        // find sums
        for (int i = 0; i < windowSize; i ++){
            float leader = window.get(i).m_leaderPrice;
            float follow = window.get(i).m_followerPrice;
            cov += (follow - followMean) * (leader - leaderMean);
            var += (leader - leaderMean) ^ 2;
        }
        
        // calculate variables
        beta = cov / var;
        alpha = followMean - beta * leaderMean;
        
        // construct function and return
        ReactionFunction reaction = new ReactionFunction(alpha, beta);
        return reaction;
    }
    
    private 
}