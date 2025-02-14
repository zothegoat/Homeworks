package application;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TestCasesHW2 {

	
	public static void main(String[] args) {
		
		AllQuestions allQuestions = new AllQuestions();
        AllAnswers allAnswers = new AllAnswers();
		System.out.println("These are my positve test cases ");
		
		//Positve Case 1: adding a question
		//the queeston we are looking to see if it passes
		
/*
 * 
 Question question1 = new Question(1, "What is Java?");
System.out.println("Before adding, question count: " + allQuestions.getQuestionList().size());
allQuestions.getQuestionList().add(question1);
System.out.println("After adding, question count: " + allQuestions.getQuestionList().size());
 * */
		Question testQuestion1 = new Question(1, "How close is the Sun?");
		System.out.println("Before adding, question count: " + allQuestions.getQuestionList().size());
		allQuestions.getQuestionList().add(testQuestion1);
		System.out.println("After adding, question count: " + allQuestions.getQuestionList().size());
		//int questionCount = allQuestions.getQuestionList().size();
		if(allQuestions.getQuestionList().contains(testQuestion1)) { //check to see if the question is added by checking for an expaned array size
			System.out.println("Successfully added a question!");
		}
		else {System.out.println("Test failed. No question added.");}
		
		
		//Positive Case 2: adding a valid answer 
		//new answer we are see if it passes or not 
		Answer testAnswer1 = new Answer(1,1,"The sun is 91.808 million miles away from earth!",101);
		System.out.println("Before adding, answer count: " + allAnswers.getAnswerList().size());
		allAnswers.getAnswerList().add(testAnswer1); // Add answer to the list
		System.out.println("After adding, answer count: " + allAnswers.getAnswerList().size());
		//int answerCount =allAnswers.getAnswerList().size();
		if(allAnswers.getAnswerList().contains(testAnswer1)) {//check to if list increases
		System.out.println("Succesfully added an Answer");
		}
		else {System.out.println("Test Failed: no answer was passed in the program");}
		
		
		//Positive case 3: MAking sure a user can get an answer for a valid question
		if(!allAnswers.getAnswerList().isEmpty()) {//checks to see if the answer list is empty
			System.out.println("Successfully retrieved answer!");
		}
		else {System.out.println("Test Failed: the liist is empty threrefore ccant retrieve answers");}
		
		//Positive Case 4: Make sure an answer can be updated 
		testAnswer1.setAnswertext("The sun is actaually 100 million miles away!"); //updated the previously created answer 
		if("The sun is actaually 100 million miles away!".equals(testAnswer1.getAnswerText())) {
			System.out.println("Succesfully updated!");
		}else {System.out.println("Test Case failed.");}
		
		//Positive Test case 5: Mare an answer as resolve
		testAnswer1.markAnswerResolved(); //mark a answer as resolved
		if(testAnswer1.isResolved()) { //use condition to check its resolved 
			System.out.println("Successfully marked as resolved!");
		}else {System.out.println("Test Case failed");}
		
		
		/*
		 These next test cases are for negative test! All of these should fail 
		 * */
		System.out.println("=========Negative Test Case===========");
		//Negative case 1: should report errors for empty strings
		Question testQuestion2 = new Question(2,"");
		boolean emptyStringQuestion = allQuestions.getQuestionList().add(testQuestion2);
		if(emptyStringQuestion || !testQuestion2.getText().isEmpty()) {
			System.out.println("Test Failed");
		}else {System.out.println("Test Passed");}
		
		//Negative case 2: adding an empty answer
		Answer testAnswer2 = new Answer(2, 1, "", 102);
		boolean emptyStringAnswer = allAnswers.getAnswerList().add(testAnswer2);
		if(emptyStringAnswer || !testAnswer2.getAnswerText().isEmpty()) {
			System.out.println("Test failed");
		} else {System.out.println("Test passed");}
		
		//Negative test case 3: duplicate answers 
		//create two duplicate answers
		Answer originalAnswer = new Answer(4, 2, "A dog barks", 150);
		Answer duplicateAnswer = new Answer(4, 2, "A dog barks", 150);
		
		// Checking for duplicates
		boolean duplicateFound = false;
		for (int i = 0; i < allAnswers.getAnswerList().size(); i++) {
		    for (int j = i + 1; j < allAnswers.getAnswerList().size(); j++) {
		        Answer a1 = allAnswers.getAnswerList().get(i);
		        Answer a2 = allAnswers.getAnswerList().get(j);

		        if (a1.getAnswerText().equals(a2.getAnswerText()) && a1.getAnswerID() == a2.getAnswerID()) {
		            duplicateFound = true;
		            System.out.println("Test Failed: Duplicate answer detected!");
		            break;
		        }
		    }
		    if (duplicateFound) break;
		}

		if (!duplicateFound) {
		    System.out.println("Test Passed: No duplicate answers found.");
		}
		//Negative test case 4: Simialirty - two strings with nothing in common should not be considered simialr aka check my similairty method
		
		
	String answer1 = "What is the captial of Japan?";
	String answer2 = "When did Earth form?";
		double similarityScore = questionSimilarity(answer1, answer2);

        if (similarityScore < 0.3) { // Threshold for similarity (you can adjust it)
            System.out.println("The answers are not similar enough. Similarity score: " + similarityScore);
        } else {
            System.out.println("The answers are similar enough. Similarity score: " + similarityScore);
        }
        
      //Negative test case 5: submitting an answer for a question that doesn't exists
    	
    	int notRealQuestionID = 999999;
    	Answer invaildAnswer1 = new Answer(999, notRealQuestionID, "Whatever text goes here", 105);
    	
    	boolean found = false;
    	for (Question question : allQuestions.getQuestionList()) {
    	    if(question.getID() == notRealQuestionID) {
    	    	found = true;
    	    	break;
    	    	}
    	    }
    	
    	if (!found) {
    	    System.out.println("Test Failed: Question ID " + notRealQuestionID + " does not exist. Cannot add answer.");
    	} else {
    	    allAnswers.getAnswerList().add(invaildAnswer1);
    	    System.out.println("Test Passed: Answer added to the list.");
    	}
    	}
	
	
	
		
		
	
	//bring over my similaity method from my QASystemclass
	public static double questionSimilarity(String questionAsked, String potentialQuestions) {
	       //converted to lower case to prevent char issues
			//splits the questions into indivual words into an array of words 
			String[] question1 = questionAsked.toLowerCase().split("\\s+");
	        String[] question2 = potentialQuestions.toLowerCase().split("\\s+");
	//I created sets here to store the unquie words from each set, sets handle duplicates making it easier to only add unquie( non-duplicates)
	        
	        Set<String> set1 = new HashSet<>();
	        Set<String> set2 = new HashSet<>();

	        //It loops through the newly made word arrays adding each word into its respective set 
	        for (String word : question1) {
	            set1.add(word);
	        }
	        for (String word : question2) {  // Fixed error: you were adding words from `question1` again.
	            set2.add(word);
	        }
	//calculates the intersection for Jaccard index
	        Set<String> intersection = new HashSet<>(set1);
	        intersection.retainAll(set2);
	//calculatesthe union for Jaccard Index
	        Set<String> union = new HashSet<>(set1);
	        union.addAll(set2);

	        //divides the two sets to find the simialrity score 
	        return (double) intersection.size() / union.size();
	    }
}
