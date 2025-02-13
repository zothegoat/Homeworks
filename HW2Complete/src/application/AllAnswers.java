package application;

import java.util.ArrayList;
import java.util.List;

public class AllAnswers {

	private List<Answer> totalAnswers = new ArrayList<>();

    // Getter and Setter for answerList
    public List<Answer> getAnswerList() {
        return totalAnswers;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.totalAnswers = answerList;
    }
	
}
