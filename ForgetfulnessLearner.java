import java.util.*;
import comp34120.ex2.*;

public class ForgetfulnessLearner{
	float minForget = 0.800f;
	float maxForget = 0.999f;
	float step = 0.005f;
	
	public ForgetfulnessLearner(){
		return;
	}

	public float learn(List<Record> records){
		List<Float> errors = new ArrayList<Float>();

		for (float i = minForget; i<=maxForget; i+=step){
			WeightedLeastSquare learner = new WeightedLeastSquare(i);
			learner.addAllData(records);
			learner.learnReaction();
			errors.add((float)learner.calculateError());
		}

		float maxAccuracy = errors.get(0);
		float bestForget = minForget;

		for (int i = 1; i < errors.size(); i++)
			if (errors.get(i) > maxAccuracy){
				maxAccuracy = errors.get(i);
				bestForget = minForget + (i*step);
			}

		return bestForget;
	}
}