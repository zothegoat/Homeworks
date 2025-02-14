package application;
import java.util.List;
import java.util.ArrayList;
public class Question {

	private int userID;
	private String text;
	private boolean isResolved; //tracks if a question has been solved or not 
	private List<String> feedback;
	
	
	//contructor method 
	public Question(int userID, String text) {
		this.userID = userID;
		this.text = text;
		this.isResolved = false;
		this.feedback = new ArrayList<>();
	}
	
	//geter methods 
	
	public int getID() {return userID;}
	public String getText() {return text;}
	public boolean isResolved() {return isResolved;}
	public List<String> getFeedback() { return feedback; }

	
	//setter methods 
	
	public void setQuestion(String text) {
		this.text = text; 
	}
	
	public void setID(int userID) {this.userID = userID;}
	
	public void resolveQuestion() { this.isResolved = true;}
	
	public void addFeedback(String feedbackMessage) {feedback.add(feedbackMessage);
    }
}
