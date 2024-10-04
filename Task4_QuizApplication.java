import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String questionText;
    String[] options;
    int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Display the question with options
    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    // Check if the selected answer is correct
    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}

class QuizApplication {
    private Question[] questions;
    private int score;
    private int totalQuestions;
    private int timeLimitPerQuestion = 10; // Time limit in seconds

    public QuizApplication(Question[] questions) {
        this.questions = questions;
        this.totalQuestions = questions.length;
    }

    // Start the quiz
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        score = 0;
        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("\nQuestion " + (i + 1) + " of " + totalQuestions + ":");

            // Start a timer for each question
            Timer timer = new Timer();
            Question currentQuestion = questions[i];
            currentQuestion.displayQuestion();

            final boolean[] answered = {false}; // To track if the user answered in time
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (!answered[0]) {
                        System.out.println("\nTime's up! Moving to the next question.");
                        answered[0] = true;
                    }
                }
            };

            timer.schedule(task, timeLimitPerQuestion * 1000); // Timer for the question

            // Get user's answer
            while (!answered[0]) {
                System.out.print("Enter your choice (1-4): ");
                if (scanner.hasNextInt()) {
                    int userAnswer = scanner.nextInt();
                    if (userAnswer >= 1 && userAnswer <= 4) {
                        answered[0] = true;
                        timer.cancel(); // Cancel the timer as user has answered

                        // Check if the answer is correct
                        if (currentQuestion.isCorrect(userAnswer)) {
                            System.out.println("Correct!");
                            score++;
                        } else {
                            System.out.println("Incorrect. The correct answer was option " + currentQuestion.correctAnswer);
                        }
                    } else {
                        System.out.println("Invalid input. Please select an option between 1 and 4.");
                    }
                } else {
                    scanner.next(); // Clear invalid input
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                }
            }
        }
        scanner.close();
        displayResults();
    }

    // Display the final score and summary
    public void displayResults() {
        System.out.println("\n=== Quiz Completed ===");
        System.out.println("Your Score: " + score + " out of " + totalQuestions);
    }
}

class QuizAppWithTimer {
    public static void main(String[] args) {
        // Define the questions and their options
        Question[] questions = new Question[]{
            new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 3),
            new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2),
            new Question("Who wrote 'Hamlet'?", new String[]{"Charles Dickens", "Mark Twain", "William Shakespeare", "J.K. Rowling"}, 3),
            new Question("Which element has the chemical symbol 'O'?", new String[]{"Gold", "Oxygen", "Iron", "Hydrogen"}, 2)
        };

        // Create a QuizApplication instance and start the quiz
        QuizApplication quizApp = new QuizApplication(questions);
        quizApp.startQuiz();
    }
}
