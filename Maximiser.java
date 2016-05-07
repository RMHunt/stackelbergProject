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
        // // a, b, and c for the quadratic equation
        // // These calculations are derived from the given function in the instructions.
        // float quadA = (float)0.3 * reactionFunction.getB() - 1;
        // float quadB = (float)0.3 * reactionFunction.getA() - quadA + 2;
        // float quadC = 2 + (float)0.3 * reactionFunction.getA();
        
        // float vertex = (0 - quadB) / 2 * quadA;
        
        // // Check if vertex is maximum or minimum
        // if((2 * quadA * (vertex + 0.1) + quadB) < 0)
        // {
        //     // if maximum
        //     return vertex;
        // } else {
        //     return Float.MAX_VALUE;
        // }

        float a = reactionFunction.getA();
        float b = reactionFunction.getB();
        // The very least strategy and associated profit
        float minStrat = 1;
        float minProfit = 0;

        // The maximum point computed from the derivation
        float maxStrat = (3*b - 3*a - 30)/(6*b-20);
        // The max strat is only possible if the second derivation is less than zero
        boolean verifyMaxStrat = (6*b - 20) < 0;

        // How much profit this maximum point gives us
        float profitFromMaxStrat = (3f+0.3f*a-0.3f*b)*maxStrat + (0.3f*b-1f)*maxStrat*maxStrat - (2f+0.3f*a);

        // System.out.printf("maxStrat %f\tprofit %f\n",maxStrat, profitFromMaxStrat);
        // If the maximum point is valid, greater than the minimum 1 and gives us a better result than the minimum profit,
        // then we use it, otherwise we default to the minimum strategy
        if (verifyMaxStrat && maxStrat > 1 && profitFromMaxStrat > minProfit)
            return maxStrat;
        else
            return minStrat;
    }
} 