import java.lang.IllegalArgumentException;
import comp34120.ex2.*;
public class WeightedLeastSquare extends AbstractLearner{

	/**
	 *	Inherited:
	 *	List<Record> 		history
	 * 	List<Float> 		followerStrats
	 * 	ReactionFunction 	learnedFunction 
	 */
	private double  forgetfulness;
    private float   adjustFactor;
    private float   p;

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
    	followerStrats.add(prediction);
    }
    
    // Will learn the reaction function from all available data
    @Override
    public void learnReaction(){
        // Calculate our initial P from data so far
        p = 0;
        int numDays = history.size();
        for (int t = 0; t < numDays; t++){
            Record data = history.get(t);
            // p += (Math.pow(forgetfulness, numDays-t) * (1 + 2*data.m_leaderPrice + data.m_leaderPrice*data.m_leaderPrice));
        }

        // Calculate our initial parameters for our learned function
        float a = 0;
        float b = 0;
    }
    
    // Will update the reaction function from just the most recent data
    @Override
    public void updateLearn(){
        return;
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