import java.lang.IllegalArgumentException;
import comp34120.ex2.*;
public class WeightedLeastSquare extends AbstractLearner{

	/**
	 *	Inherited:
	 *	List<Records> 		history
	 * 	List<Float> 		predictions
	 * 	ReactionFunction 	learnedFunction 
	 */
	double forgetfulness;

	public WeightedLeastSquare(double forgetfulness){
		if (forgetfulness < 0 || forgetfulness > 1)
			throw new IllegalArgumentException("Forgetfulness must be between 0 and 1");

		this.forgetfulness = forgetfulness;
	}

	// Adds one item of data to the history
	@Override
    public void addData(Record record){
    	history.add(record);
    }

    // Adds one predicted reaction to the list of responsePredictions
    @Override
    public void addPrediction(float prediction){
    	return;
    }
    
    // Will learn the reaction function from all available data
    @Override
    public void learnReaction(){

    }
    
    // Will update the reaction function from just the most recent data
    @Override
    public void updateLearn(){

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
}