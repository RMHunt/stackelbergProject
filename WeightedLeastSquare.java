import java.lang.IllegalArgumentException;
import comp34120.ex2.*;
import java.util.List;
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

        // Setup initial adjustment matrix and parameters:
        p = Matrix2D.getIdentity(2).divide((float)0.01);
        theta = new Matrix2D(2,1);
        
	}

	// Adds one item of data to the history & calculate updated parameters
	@Override
    public void addData(Record record){
    	// Don't need to remember history
        // history.add(record);

        // Matrix2D x = makePhi(record.m_leaderPrice);
        // float y = record.m_followerPrice;

        Matrix2D x = makePhi(record.m_date);
        float y = record.m_date*3 + 1;

        // Calculate adjustment matrix p
        Matrix2D det = x.transpose().multiply(p).multiply(x).add((float)forgetfulness);
        Matrix2D p_adjust = p.multiply(x).multiply(x.transpose()).multiply(p).divide(det);
        p = p.subtract(p_adjust).divide((float)forgetfulness);

        // Calculate how much we should adjust the parameters, based on the difference between the actual and modelled values
        float w_adjust = y - theta.transpose().multiply(x).get();
        theta = theta.add( p.multiply(x.multiply(w_adjust)));

    }

    // Adds all available data to the history
    @Override
    public void addAllData(List<Record> records){
        for (Record r:records)
            addData(r);
    }

    // Will learn the reaction function from all available data
    @Override
    public void learnReaction(){
        thetaToReactionFunction();
    }
    
    // Will update the reaction function from just the most recent data
    // Should we pass the new data via parameter? or just check most recent history 
    @Override
    public void updateLearn(float leaderVal, float followerVal){
        thetaToReactionFunction();
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
        // System.out.println(learnedFunction.getA() + " " + learnedFunction.getB());
    }

    // Helper code to make phi arrays
    private Matrix2D makePhi(float value){
        float[] phif = new float[2];
        phif[0] = 1;
        phif[1] = value;
        return new Matrix2D(phif).transpose(); // (Phi is COLUMN matrix [1, x(t)])
    }

}

// LEGACY CODE:

// // Will learn the reaction function from all available data
//     @Override
//     public void learnReaction(){
//         // // Calculate our initial P from data so far
//         // p = new Matrix2D(2,2);
//         // Matrix2D q = new Matrix2D(2,1);
//         // int numDays = history.size();

//         // for (int t = 0; t < numDays; t++){
//         //     Record data = history.get(t);
//         //     Matrix2D phi = makePhi(data.m_leaderPrice);

//         //     float weight = (float)Math.pow(forgetfulness,numDays-t-1);
//         //     p = p.add(phi.multiply(weight)
//         //                  .multiply(phi.transpose()));
//         //     q = q.add(phi.multiply(weight)
//         //                  .multiply(data.m_followerPrice));
//         // }

//         // // Calculate our initial parameters for our learned function using p and q
//         // theta = p.invert().multiply(q);
//         thetaToReactionFunction();
//     }
    
//     // Will update the reaction function from just the most recent data
//     // Should we pass the new data via parameter? or just check most recent history 
//     @Override
//     public void updateLearn(float leaderVal, float followerVal){
//         // // Updated = Old + Adjust(actual - estimate from model)
//         // // theta+1 = theta + L+1 * [y+1 - phi^transposed(x+1)*theta]
//         // // Where
//         // // L+1 = (p * phi(x+1)) / (forget + phi^transposed(x+1) * p * phi(x+1))
//         // Matrix2D phi = makePhi(leaderVal);
//         // Matrix2D det = phi.transpose().multiply(p).multiply(phi).add((float)forgetfulness);
        
//         // Matrix2D nextAdjust = (p.multiply(phi)).divide(det);
//         //                                 // System.out.println("####");
//         //                                 // System.out.println(nextAdjust);

//         //                                 // System.out.println("follower val " + followerVal);
//         //                                 // System.out.println(phi.transpose());
//         //                                 // System.out.println(theta);
//         //                                 // System.out.println("calc val " + (followerVal - phi.transpose().multiply(theta).get()));
//         // theta = theta.add( nextAdjust.multiply( followerVal - phi.transpose().multiply(theta).get()));
//         //                                 // System.out.println(theta);
//         // // Then calculate the next p
//         // // p+1 = (1/forget)(p - ( p * phi(x+1) * phi^transposed(x+1) * phi)
//         // //                      / (forget + phi^transposed(x+1) * p * phi(x+1))
//         // System.out.println(p);
//         // p = (p.subtract( p.multiply(phi).multiply(phi.transpose()).multiply(p)
//         //                   .divide(det))
//         //       .divide((float)forgetfulness));
//         // System.out.println(p);

//         thetaToReactionFunction();
//     }