import java.lang.Math.*;
import comp34120.ex2.*;
import java.lang.Float;

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
    public float calculateMaximum()
    {
        // a, b, and c for the quadratic equation
        // These calculations are derived from the given function in the instructions.
        float quadA = (float)0.3 * reactionFunction.getB() - 1;
        float quadB = (float)0.3 * reactionFunction.getA() - quadA + 2;
        float quadC = 2 + (float)0.3 * reactionFunction.getA();
        
        float vertex = (0 - quadB) / 2 * quadA;
        
        // Check if vertex is maximum or minimum
        if((2 * quadA * (vertex + 0.1) + quadB) < 0)
        {
            // if maximum
            return vertex;
        } else {
            return Float.MAX_VALUE;
        }
    }
} 