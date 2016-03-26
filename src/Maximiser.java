import java.lang.Math.*

/**
 * A maximiser. Given a quadratic function, it should find
 * the maximum value along the graph.
 * @author Ivan
 */
class Maximiser
{
    private ReactionFunction reactionFunction;
    
    /**
     * Empty constructor.
     */
    public Maximiser()
    {
        reactionFunction = new ReactionFunction();
    }
    
    /**
     * Construct maximiser for a certain reaction function.
     */
    public Maximiser(ReactionFunction inputFunction)
    {
        reactionFunction = inputFunction;
    }
    
    /**
     * Calculate maximum for given function;
     */
} 