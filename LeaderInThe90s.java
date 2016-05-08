import comp34120.ex2.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class LeaderInThe90s extends PlayerImpl{

	private AbstractLearner weightedLearner;
	private AbstractLearner windowedLearner;
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
		weightedLearner = new WeightedLeastSquare(forgetfulness);
		maximiser = new Maximiser();
		totalDays = 100 + p_steps;
		currentDay = 100;
		
		List<Record> records = new ArrayList<Record>();
		// Add first 100 days of historic data to learner
		for (int i = 1; i <= 100; i++)
			records.add(m_platformStub.query(m_type, i));
			
		// Initialise moving window learner.
		WindowSizeLearner windowSizeLearner = new WindowSizeLearner();
		int windowSize = windowSizeLearner.learn(records);
		windowedLearner = new MovingWindow(windowSize);
			
		weightedLearner.addAllData(records);
		windowedLearner.addAllData(records);

		// Error is between 0 and 1, 1 is less error
		m_platformStub.log(PlayerType.LEADER,"Weighted: "+weightedLearner.calculateError()+" | Window: "+windowedLearner.calculateError());
		if (weightedLearner.calculateError() < windowedLearner.calculateError()){
			m_platformStub.log(PlayerType.LEADER,"Selected MovingWindow");
			learner = windowedLearner;
			System.out.println("Using Moving Window");
		} else {
			m_platformStub.log(PlayerType.LEADER,"Selected WeightedLeastSquare");
			learner = weightedLearner;
			System.out.println("Using Weighted Square");
		}
		System.out.println("Window Error " + windowedLearner.calculateError());
		System.out.println("Weighted Error " + weightedLearner.calculateError());

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
		// Add a random factor in either direction
		// newPrice = (float)((Math.random()-0.5f)*(0.2f*newPrice)+newPrice);
		m_platformStub.publishPrice(m_type, newPrice);

		currentDay++;

	}

	public static void main(final String[] p_args)
		throws RemoteException, NotBoundException
	{
		new LeaderInThe90s();
	}
}