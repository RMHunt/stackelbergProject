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
    private Matrix2D   theta; // Parameters

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
            Matrix2D phi = makePhi(history.get(t).m_leaderPrice);

            float weight = (float)Math.pow(forgetfulness,numDays-t);
            p = p.add(phi.multiply(phi.transpose())
                         .multiply(weight));
            q = q.add(phi.multiply(history.get(t).m_followerPrice)
                         .multiply(weight));
        }

        // Calculate our initial parameters for our learned function using p and q
        theta = p.invert().multiply(q);
        thetaToReactionFunction();
    }
    
    // Will update the reaction function from just the most recent data
    // Should we pass the new data via parameter? or just check most recent history 
    @Override
    public void updateLearn(float leaderVal, float followerVal){
        // Updated = Old + Adjust(actual - estimate from model)
        // theta+1 = theta + L+1 * [y+1 - phi^transposed(x+1)*theta]
        // Where
        // L+1 = (p * phi(x+1)) / (forget + phi^transposed(x+1) * p * phi(x+1))
        Matrix2D phi = makePhi(leaderVal);
        Matrix2D det = phi.transpose().multiply(p).multiply(phi).add((float)forgetfulness);
        
        Matrix2D nextAdjust = (p.multiply(phi)).divide(det);

        theta = theta.add( nextAdjust.multiply( followerVal - phi.transpose().multiply(theta).get()));
        // Then calculate the next p
        // p+1 = (1/forget)(p - ( p * phi(x+1) * phi^transposed(x+1) * phi)
        //                      / (forget + phi^transposed(x+1) * p * phi(x+1))
        p = (p.subtract( p.multiply(phi).multiply(phi.transpose()).multiply(phi)
                          .divide(det))
              .divide((float)forgetfulness));

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

    // Helper code to create a reaction function from learned parameters.
    private void thetaToReactionFunction(){
        learnedFunction = new ReactionFunction(theta.get(0,0), theta.get(1,0));
    }

    // Helper code to make phi arrays
    private Matrix2D makePhi(float value){
        float[] phif = new float[2];
        phif[0] = 1;
        phif[1] = value;

        return new Matrix2D(phif).transpose(); // (Phi is COLUMN matrix [1, x(t)])
    }

}