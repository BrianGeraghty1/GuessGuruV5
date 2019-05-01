package io.guessguru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guessguru.entities.Prediction;
import io.guessguru.entities.User;
import io.guessguru.repositories.PredictionRepository;

@Service
public class PredictionService {

	@Autowired
	private PredictionRepository predictionRepository;

	public void savePrediction(Prediction prediction) {
		predictionRepository.save(prediction);
	}
	
	public boolean checkPredictions(User user, String fixtureId) {
		boolean exists = false;
		List<Prediction> userPredictions = predictionRepository.findByUser(user);
		for(int i =0; i<userPredictions.size(); i++) {
			if(fixtureId.equalsIgnoreCase(userPredictions.get(i).getFixture().getId())) {
				exists=true;
				return exists;
			}
		}
		return exists;
	}

}
