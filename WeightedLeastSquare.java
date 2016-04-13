import java.lang.IllegalArgumentException;
import comp34120.ex2.*;
public class WeightedLeastSquare extends AbstractLearner{

	/**
	 *	Inherited:
	 *	List<Record> 		history
	 * 	List<Float> 		followerStrats
	 * 	ReactionFunction 	learnedFunction 
	 */
	private double     forgetfulness;
    private float      adjustFactor;
    private Matrix2D   p;

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
        p = new Matrix2D(2,2);
        Matrix2D q = new Matrix2D(2,1);
        int numDays = history.size();

        for (int t = 0; t < numDays; t++){
            float[] phif = new float[2];
            phif[0] = 1;
            phif[1] = history.get(t).m_leaderPrice;

            Matrix2D phi = new Matrix2D(phif).transpose(); // (Phi is COLUMN matrix [1, x(t)])

            float weight = (float)Math.pow(forgetfulness,numDays-t);
            p = p.add(phi.multiply(phi.transpose())
                         .multiply(weight));
            q = q.add(phi.multiply(history.get(t).m_followerPrice)
                         .multiply(weight));
        }

        // Calculate our initial parameters for our learned function using p and q
        Matrix2D initParameters = p.invert().multiply(q);
        learnedFunction = new ReactionFunction(initParameters.get(0,0), initParameters.get(1,0));
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