import java.util.*

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
    private ArrayList leaderStrats = new ArrayList();
    private ArrayList followerStrats = new ArrayList();
    private ReactionFunction learnedFunction;
    
    abstract private ReactionFunction learner();
    
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
            sst += (followerStrats.get(i) - meanY) ^ 2;
            sse += (followerStrats.get(i) - learnedFunction(leaderStrats.get(i))) ^ 2;
        }
        
        double rSquare = 1 - sse / sst;
        return rSquare;
    }
}