package application;

public class Answer {

	
	private int answerId;
    private int associatedId;  // Associated Question ID
    private String text;
    private boolean hasBeenRead; //tracks if a anwer has been read
    private boolean isResolved; 
    private boolean isTrusted;
    private String source;
    private int reviewerID;
    private boolean updated;
    private int studentID;
    
    public Answer(int answerId, int associatedId, String text, int studentID) {
        this.answerId = answerId;
        this.associatedId = associatedId;
        this.text = text;
        this.hasBeenRead = false;
        this.isResolved = false;
        this.isTrusted = false;
        //this.source =source;
        this.updated = false;
        //this.reviewerID = reviewerID;
        this.studentID = studentID;
    }
    
    //getter methods 
    public int getAnswerID() {return answerId;}
    public int getAssoiatedID() {return associatedId;}
    public String getAnswerText() {return text;}
    public boolean hasBeenRead() {return hasBeenRead;}
    public boolean isResolved() {return isResolved;}
    public boolean isTrsusted() { return this.isTrusted;}
    public String getSource() { return source; }
    public int getReviewerID() {return reviewerID;}
    public boolean hasUpdated() {return updated;}
    public int getStudentID() { return studentID; }
    	
    
    
    //setter methods 
    
    public void setAnswerID(int answerId) { this.answerId = answerId;}
    public void setAssoiatedID(int associatedId) { this.associatedId = associatedId;}
    public void setAnswertext(String text) {this.text = text; this.updated = true;}
    public void markHasBeenRead() {this.hasBeenRead = true;}
    public void markAnswerResolved() {this.isResolved = true;}
    public void setTrusted(boolean isTrusted) {this.isTrusted = isTrusted;}
    
    
    //helper method which updates an answer
    public void updateReview(String updatedText, int reviewerID) {
    	
    	if(this.reviewerID == reviewerID) {
    		setAnswertext(updatedText);
    }
    
}
}
