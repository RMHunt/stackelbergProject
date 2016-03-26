/**
 * A reaction function. A reaction function contains 2 variables A and B, and
 * the operation done to the input incorporating those variables.
 * @author Ivan
 */
class ReactionFunction
{
    private double variableA;
    private double variableB;
    
    /**
     * Empty constructor.
     */
    public ReactionFunction()
    {
        variableA = 0;
        variableB = 0;
    }
    
    /**
     * Constructor given A and B.
     */
    public ReactionFunction(double a, double b)
    {
        variableA = a;
        variableB = b;
    }
    
    /**
     * Method to calculate result of reaction given a leader strategy.
     */
    private double calculateReactionValue(double leaderStrat)
    {
        double reactionValue = variableA + variableB * leaderStrat;
        return reactionValue;
    }
    
    /**
     * Getters.
     */
    private double getA()
    {
        return variableA;
    }
    
    private double getB()
    {
        return variableB;
    }
}