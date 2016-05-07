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
		double forgetfulness = 0.99;
		learner = new WeightedLeastSquare(forgetfulness);
		maximiser = new Maximiser();
		totalDays = 100 + p_steps;
		currentDay = 100;

		// Add first 100 days of historic data to learner
		for (int i = 1; i <= 100; i++)
			learner.addData(m_platformStub.query(m_type, i));

		// Calculate our initial reaction function
		learner.learnReaction();
	}

	// Each day
	@Override
	public void proceedNewDay(int p_date) throws RemoteException
	{
		Record l_newRecord = m_platformStub.query(m_type, p_date-1);
		// MATHS TO CALCULATE NEW PRICE:
		System.out.println("date " + p_date);
		System.out.println("new leaderprice " + l_newRecord.m_leaderPrice);
		System.out.println("new followerprice "+l_newRecord.m_followerPrice);
		// Add the new data to the learner and learn
		// (May want to use updateReaction here if possible?)
		learner.addData(l_newRecord);
		learner.updateLearn(l_newRecord.m_leaderPrice, l_newRecord.m_followerPrice);

		// Maximise our best outcome with the learned reaction function
		// and use this as our next price
		maximiser = new Maximiser(learner.getReactionFunction());
		float newPrice = maximiser.calculateMaximum();
		m_platformStub.publishPrice(m_type, newPrice);




		currentDay++;

	}

	public static void main(final String[] p_args)
		throws RemoteException, NotBoundException
	{
		new LeaderInThe90s();
	}
}