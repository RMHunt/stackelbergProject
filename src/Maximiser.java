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
     * Calculate maximum for given function. The values are hard coded due to the
     * nature of the coursework.
     */
    private double calculateMaximum()
    {
        // a, b, and c for the quadratic equation
        // These calculations are derived from the given function in the instructions.
        double quadA = 0.3 * reactionFunction.getB() - 1;
        double quadB = 0.3 * reactionFunction.getA() - quadA + 2;
        double quadC = 2 + 0.3 * reactionFunction.getA();
        
        double vertex = (0 - quadB) / 2 * quadA;
        
        // Check if vertex is maximum or minimum
        if((2 * quadA * (vertex + 0.1) + quadB) < 0)
        {
            // if maximum
            return vertex;
        } else {
            return MAX_DOUBLE;
        }
    }
} 