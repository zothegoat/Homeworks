package application;

import java.util.List;
import java.util.ArrayList;

public class Student {
    private List<Integer> trustedReviewers; // List to hold trusted reviewer IDs

    // Constructor
    public Student() {
        this.trustedReviewers = new ArrayList<>();
    }

    // Method to add a trusted reviewer by their ID
    public void addTrustedReviewer(int reviewerID) {
        trustedReviewers.add(reviewerID);
    }

    // Method to check if a reviewer is trusted
    public boolean isTrustedReviewer(int reviewerID) {
        return trustedReviewers.contains(reviewerID);
    }

    // Display all answers and show if any trusted reviewer updated the answer
    public void displayAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            if (answer.hasUpdated() && isTrustedReviewer(answer.getReviewerID())) {
                System.out.println("This answer was recently updated by a trusted reviewer!");
            }
            System.out.println("Answer: " + answer.getAnswerText());
        }
    }
}
