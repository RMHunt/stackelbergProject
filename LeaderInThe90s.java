import comp34120.ex2.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LeaderInThe90s extends PlayerImpl{

	private AbstractLearner learner;
	private Maximiser maximiser;
	private int totalDays;
	private int currentDay;
	// Inherited Platform 	m_platformStub
	// Inherited PlayerType m_type

	// Constructor
	public LeaderInThe90s() throws RemoteException, NotBoundException{
		super(PlayerType.LEADER, "Running in the 90s Leader");
	}

	// Initialization
	@Override
	public void startSimulation(int p_steps) throws RemoteException{
		// Initialise learner and maximiser.
		double forgetfulness = 0.95;
		learner = new WeightedLeastSquare(forgetfulness);
		maximiser = new Maximiser();
		totalDays = 100 + p_steps;
		currentDay = 100;

		// Add first 100 days of historic data to learner
		for (int i = 0; i < 100; i++)
			learner.addData(m_platformStub.query(m_type, i));

		// Calculate our initial reaction function
		learner.learnReaction();
	}

	// Each day
	@Override
	public void proceedNewDay(int p_date) throws RemoteException
	{

		Record l_newRecord = m_platformStub.query(m_type, p_date);
		// MATHS TO CALCULATE NEW PRICE:

		// Add the new data to the learner and learn
		// (May want to use updateReaction here if possible?)
		learner.addData(l_newRecord);
		learner.learnReaction();

		// Maximise our best outcome with the learned reaction function
		// and use this as our next price
		maximiser = new Maximiser(learner.getReactionFunction());
		float newPrice = maximiser.calculateMaximum();
		m_platformStub.publishPrice(m_type, newPrice);

		currentDay++;

	}
}