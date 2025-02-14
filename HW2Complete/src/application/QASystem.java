package application;
import java.lang.foreign.Linker.Option;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class QASystem {
	public static void main(String[] args) {
		
	Scanner scanner = new Scanner(System.in);
	AllQuestions allQuestions = new AllQuestions();
	AllAnswers allAnswers = new AllAnswers();
	
	
	//create the student menu
	
	while (true) {
		
		System.out.println("\nStudent Menu:");
		System.out.println("1. Ask a Question");
		System.out.println("2. View Potentional Answers:");
		System.out.println("3. View Unresolved Questions and Propose Answers(optional):");
		System.out.println("4. Mark Answers which resolve your question:");
		System.out.println("5. View List of resolved answers for any Question:");
		System.out.println("6. Update a Answer:");
		System.out.println("7. Logout");
		System.out.println("Insert which number operation you would like to use: ");
		
		int userOption = scanner.nextInt(); //contains the users option
		scanner.nextLine();
		
		//use a switch case to determine what to do with the user option 
		switch(userOption) {
		
		//For now, do case 1 where they want to enter a option
		//case 1 should handle the submission of students question
		case 1: 
			System.out.print("Submit your Question here: "); 
			String question = scanner.nextLine(); //gather question text
			
			System.out.println("Would like to see if their are any similar questions Please put yes or no?");
			String permissionForRelated = scanner.nextLine();
			
			//check to see if the user asks to see the questions or not 
			if(permissionForRelated.equalsIgnoreCase("yes")) {
				System.out.println("Here are some similar questions!");
				for(Question similarQuestion : allQuestions.getQuestionList()) {
					//checks to see if the user question it 
					double similarity = questionSimilarity(question, similarQuestion.getText()); //calculates the similairity between the two questions
					if(similarity >= 0.3) { //adjust this if statement for how simialr you want the questions to be 0.0-1.0
						System.out.println("Here is a similar question: " + similarQuestion.getText());
					}
				}
				
			}
			System.out.print("Please choose your question ID here(Only numbers): ");
			int userID = scanner.nextInt(); //gather the userID text
			scanner.nextLine();
			
			//create the students question they just asked
			
			Question newQuestion = new Question(userID, question);
			//add the question to the list using my addQuestion helper method 
			allQuestions.addQuestionToList(newQuestion);
			System.out.println("Submitted!");
		break;
		
		 
		
		//this case statement 
		
		case 2:
			System.out.print("Enter the question ID for which potetentional answers you want to see: ");
			int id  = scanner.nextInt(); //gathers the question ID
			scanner.nextLine();
			
			
			boolean matchsFound = false;
			//loop through all the answers and if an associatedID matches a question, mark true as a match
			for (int i = 0; i < allAnswers.getAnswerList().size(); i++) {
			    Answer answer = allAnswers.getAnswerList().get(i);
			    if (answer.getAssoiatedID() == id) {
			    	matchsFound = true;
			        System.out.println("Answer ID: " + answer.getAnswerID());
			        System.out.println("Answer: " + answer.getAnswerText());
			        answer.markHasBeenRead();//marks that the user has seen the answer
			    }
			}
			if(!matchsFound) {System.out.println("No Answers Avaiable!");}
			
		
			
		break;
		
		//case 3 should handle showing unresolved questions and unread answers 
		
		
		case 3: 
			System.out.println("Here are the current unresolved questions: ");
			
			//display all of the unanswered questions
			showUnresolvedQuestions(allQuestions);
			
		    
		    // loop through the list of questions
		    for (Question questionIndexer : allQuestions.getQuestionList()) { 
		        if (!questionIndexer.isResolved()) {
		            int unreadAnswers = 0;
		            for (Answer answerIndexer : allAnswers.getAnswerList()) { // loop through all the potential answers
		                // if the IDs match but the answer is unread, increment the amount of unread answers
		                if (answerIndexer.getAssoiatedID() == questionIndexer.getID() && !answerIndexer.hasBeenRead()) {
		                    unreadAnswers++;
		                }
		            }
		            System.out.println("# of unread answers: " + unreadAnswers);
		            boolean hasAnswers = false;
		            
		            // Next, we need to print out the current list of potential answers
		            for (Answer answerIndexer : allAnswers.getAnswerList()) {
		                if (answerIndexer.getAssoiatedID() == questionIndexer.getID()) { // check if question and answer IDs match
		                    System.out.println("The Associated ID: " + answerIndexer.getAnswerID() + "   Answer: " + answerIndexer.getAnswerText());
		                    hasAnswers = true;
		                }
		            }
		            
		            if (!hasAnswers) {
		                System.out.println("No Answers");
		            }

		            // This next section of case three should consider if the user would like to propose an answer to the question
		            System.out.println("Submit a new answer? (type yes or no)");
		            String answerSubmitChoice = scanner.nextLine();

		            if (answerSubmitChoice.equalsIgnoreCase("yes")) {
		                System.out.println("Submit New Answer:");
		                String newAnswer = scanner.nextLine(); // Declare newAnswer here
		                
		                // Check for duplicates
		                boolean isDupe = false;
		                for (Answer currAnswer : allAnswers.getAnswerList()) { // loop through the entire answer list
		                    if (currAnswer.getAnswerText().equalsIgnoreCase(newAnswer)) { // check if an answer in the list matches an already submitted one
		                        isDupe = true;
		                        break;
		                    }
		                }
		                
		                if (isDupe) { // prompt user if they are submitting a duplicate
		                    System.out.println("This Answer is a Duplicate!");
		                } else {
		                    // if it isn't a duplicate, add the new answer to the list
		                	System.out.println("Enter your studentID:");
		                	int studentID = scanner.nextInt();
		                	scanner.nextLine();
		                    int newAnswerID = allAnswers.getAnswerList().size() + 1;
		                    // create a new answer object for the user
		                    Answer addAnswer = new Answer(newAnswerID, questionIndexer.getID(), newAnswer, studentID);
		                    allAnswers.addAnswerToAnswerList(addAnswer); // assuming you have a method to add answers to the list
		                    System.out.println("Your answer has been submitted!");
		                }
		            }
		            
		           //Allow the student to provide private feedback 
		            System.out.println("Would you like to provide private feedback on this question (yes/no)?");
		            String choice = scanner.nextLine();
		            
		            if (choice.equalsIgnoreCase("yes")) { //if user says yes, proceed
		                System.out.println("Enter your private feedback for the question:");
		                String feedback = scanner.nextLine(); // Capture the feedback text

		                // Add the feedback to the question
		                questionIndexer.addFeedback(feedback);
		                System.out.println("Your feedback has been added!");
		            }
		            
		        
		        }
		    }
		    break;
		    //case 4 should handle when a answer needs to be marked as resolved 
		case 4: 
			System.out.print("Please put the question ID to which the answer belongs: ");
		    int questionId = scanner.nextInt(); // Get the question ID
		    scanner.nextLine();

		    System.out.print("Please put answer ID to mark as resolved: ");
		    int answerId = scanner.nextInt(); // Get the answer ID
		    scanner.nextLine(); 

		    boolean answerRecieved = false;
		    for(Answer answer : allAnswers.getAnswerList()) {
		    	if(answer.getAssoiatedID() == questionId && answer.getAnswerID() == answerId) {
		    		answer.markAnswerResolved(); //marks the answer as resolved
		    		System.out.println("Answer marked!");
		    		answerRecieved = true;
		    		break;
		    	}
		    	
		    	if(!answerRecieved) {
		    		System.out.println("Not found.");
		    	}
		    	
		    }
		    
		    
		    
			
		break;
		
		
		
		// case five should handle the option to see the answers that are resolved
		case 5:
			
		System.out.println("Enter the question ID to the correlated resolved answer: ");
		int IdForResolvedQue = scanner.nextInt();
		
		
		boolean foundResolved = false;
		for (Answer answer : allAnswers.getAnswerList()) {
	        if (answer.getAssoiatedID() == IdForResolvedQue && answer.isResolved()) {
	            System.out.println("Resolved Answer ID: " + answer.getAnswerID());
	            System.out.println("Answer: " + answer.getAnswerText());
	        }
	    }

		
		if (!foundResolved) {System.out.println("No Answer have been made yet");}
			
		break;
		
		case 6:
			System.out.print("Enter the answer ID to update: ");
            int answerID = scanner.nextInt();
            scanner.nextLine();
            
         // Check if the answer exists and belongs to the student
            System.out.print("Enter your student ID: ");
            int studentID = scanner.nextInt();
            scanner.nextLine();
            
            Answer answerToUpdate = null;
            for (Answer answer : allAnswers.getAnswerList()) {
                if (answer.getAnswerID() == answerID && answer.getStudentID() == studentID) {
                    answerToUpdate = answer;
                    break;
                }
            }
            
            
            if(answerToUpdate != null) {
            	
            	System.out.println("Current Answer: " + answerToUpdate.getAnswerText());
                System.out.print("Enter your new answer: ");
                String newAnswerText = scanner.nextLine();
                answerToUpdate.setAnswertext(newAnswerText);
                System.out.println("Your answer has been updated.");
            }
            else {
                System.out.println("Answer update has been cancelled.");
            }
		break;
		
		default: 
			System.out.println("THATS NOT AN OPTION");
		}
		
		
		
		
	}
		
	}
	
	//helper method which should determine smilarity between questions
			//Using the Jaccard Index to scan for similairty 
			//source: https://en.wikipedia.org/wiki/Jaccard_index
	
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
	
	
	// Helper method to display unresolved questions
	// Helper method to display unresolved questions
	public static void showUnresolvedQuestions(AllQuestions allQuestions) {
	    boolean unresolvedFound = false;
	    
	    // Use getQuestionList() method from AllQuestions class
	    for (Question question : allQuestions.getQuestionList()) {
	        if (!question.isResolved()) { // If the question is unresolved
	            unresolvedFound = true;
	            System.out.println("Question ID: " + question.getID());
	            System.out.println("Question Text: " + question.getText());
	            System.out.println("Unresolved: Yes");
	       
	        }
	    }

	    if (!unresolvedFound) {
	        System.out.println("No unresolved questions found.");
	    }
	}
	
}
