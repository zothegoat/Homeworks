package application;

import java.util.ArrayList;
import java.util.List;

public class AllQuestions {

	
	
	    private List<Question> totalQuestion = new ArrayList<>();
	    
	    

	    // Getter and Setter for totalQuestion
	    public List<Question> getQuestionList() {
	        return totalQuestion;
	    }

	    public void setQuestionList(List<Question> questionList) {
	        this.totalQuestion = questionList;
	    }
	    
	    
	    //helper method which should ad a question to this list
	    
	    public void addQuestionToList(Question question) {
	    	
	    	totalQuestion.add(question);
	    }
	    
	    //helper mehod to display most recent Questions
	 
	    public List<Question> getRecentQuestions() {
	        int size = totalQuestion.size();
	        return totalQuestion.subList(0, Math.min(size, 10));  // Returns a sublist of the 10 most recent questions
	    }
}



