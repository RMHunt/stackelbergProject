import java.util.*;
import comp34120.ex2.*;

/**
 * An abstraction of the learner classes. A learner takes historical data and
 * returns its approximation of the reaction function learned by its specific
 * method. Also provides means to test its own accuracy.
 * @author Ivan
 */
abstract class AbstractLearner
{
    // I am honestly not entirely sure how to do this in the java way.
    // I'd usually do it by doing stuff then returning a new
    // reaction function object at the end. whaddya think?
    protected List<Record> history = new ArrayList<Record>();
    protected ReactionFunction learnedFunction;
    protected List<Float> followerStrats = new ArrayList<Float>();
    
    // abstract protected ReactionFunction learner();

    // Adds one item of data to the history
    abstract void addData(Record record);

    // Adds one predicted reaction to the list of responsePredictions
    abstract void addPrediction(float prediction);

    // Will learn the reaction function from all available data
    abstract void learnReaction();

    // Will update the reaction function from just the most recent data
    abstract void updateLearn();

    // Returns the predicted reaction for a particular date
    abstract float getReaction(float leaderValue);

    // Returns the learned function
    abstract ReactionFunction getReactionFunction();

    // calculate error using R-square method
    // maybe implement more error caluclation methods?
    private double calculateError()
    {
        double sst = 0;
        double sse = 0;
        double meanY = 0;
        
        for (int i = 0; i < followerStrats.size(); i ++)
        {
            meanY += followerStrats.get(i);
        }
        
        meanY = meanY / followerStrats.size();
        
        for (int i = 0; i < followerStrats.size(); i ++)
        {
            sst += Math.pow((followerStrats.get(i) - meanY), 2);
            float leaderStrat = history.get(i).m_leaderPrice;
            sse += Math.pow((followerStrats.get(i) - learnedFunction.calculateReactionValue(leaderStrat)), 2);
        }
        
        double rSquare = 1 - sse / sst;
        return rSquare;
    }
}