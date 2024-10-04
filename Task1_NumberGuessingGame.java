import java.util.Random;
import java.util.Scanner;

class NumberGuessingGame {

    public static int playRound(int attemptLimit) {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1; // Random number between 1 and 100
        int attempts = 0;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nI have selected a number between 1 and 100.");
        
        while (attempts < attemptLimit) {
            System.out.print("Attempt " + (attempts + 1) + "/" + attemptLimit + ": Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;
            
            if (guess < 1 || guess > 100) {
                System.out.println("Your guess is out of bounds! Please choose a number between 1 and 100.");
                continue;
            }

            if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else if (guess > numberToGuess) {
                System.out.println("Too high!");
            } else {
                System.out.println("Congratulations! You guessed the correct number " + numberToGuess + " in " + attempts + " attempts.");
                return attempts;
            }
        }
        
        System.out.println("Sorry, you've used all " + attemptLimit + " attempts. The correct number was " + numberToGuess + ".");
        return 0;
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int roundNumber = 0;
        String playAgain = "y";
        
        while (playAgain.equalsIgnoreCase("y")) {
            roundNumber++;
            System.out.println("\n=== Round " + roundNumber + " ===");
            int attempts = playRound(5); // Limit attempts to 5 per round
            if (attempts > 0) {
                score += (100 / attempts); // Higher score for fewer attempts
            }

            System.out.print("Do you want to play another round? (y/n): ");
            playAgain = scanner.next();
        }
        
        System.out.println("\n=== Game Over ===");
        System.out.println("Total rounds played: " + roundNumber);
        System.out.println("Final score: " + score);
    }

    public static void main(String[] args) {
        playGame();
    }
}
