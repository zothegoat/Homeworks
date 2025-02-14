package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AllAnswers {

	private List<Answer> totalAnswers = new ArrayList<>();

    // Getter and Setter for answerList
    public List<Answer> getAnswerList() {
        return totalAnswers;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.totalAnswers = answerList;
    }
	
    
    public void addAnswerToAnswerList(Answer answer) {
    	
    	totalAnswers.add(answer);
    }
    
    
    //helper method to get trusted answers
    
    public List<Answer> getCuratedTrustedAnswers(Question question) {
        List<Answer> trustedAnswers = new ArrayList<>(); //store trusted answers

        // List of trusted sources, like "staff" or "instructor"
        List<String> trustedSources = Arrays.asList("reviewer");

        // Loop through all answers and filter by trusted sources
        for (Answer answer : getAnswerList()) {
            if (answer.getAssoiatedID() == question.getID()) { // Match answers to the given question
                if (trustedSources.contains(answer.getSource())) { // Check if answer is from trusted source
                    answer.setTrusted(true); // Mark answer as trusted
                    trustedAnswers.add(answer); // Add to curated list
                }
            }
        }

        return trustedAnswers; // Return the curated list of trusted answers
    }
}
