/**
 * A reaction function. A reaction function contains 2 variables A and B, and
 * the operation done to the input incorporating those variables.
 * @author Ivan
 */
class ReactionFunction
{
    private float variableA;
    private float variableB;
    
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
    public ReactionFunction(float a, float b)
    {
        variableA = a;
        variableB = b;
    }
    
    /**
     * Method to calculate result of reaction given a leader strategy.
     */
    public float calculateReactionValue(float leaderStrat)
    {
        float reactionValue = variableA + variableB * leaderStrat;
        return reactionValue;
    }
    
    /**
     * Getters.
     */
    public float getA()
    {
        return variableA;
    }
    
    public float getB()
    {
        return variableB;
    }
}