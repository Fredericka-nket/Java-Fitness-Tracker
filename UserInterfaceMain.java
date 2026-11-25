import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// This is the class responsible for  all user interaction. It displays the menu system,
 // validates input, and coordinates the behaviour of the Distance
 //and StepStats classes. Additional features are included to make
 //the program emulate a real fitness tracker, such as greetings,
 //timestamps, an activity log, and motivational messages.
 
public class UserInterfaceMain {

    // This stores the user's personal step goal for the session.
    private static int userStepGoal;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Tracks when the session started
        LocalDateTime sessionBegin = LocalDateTime.now();

        // Formatters for  the timestamps
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Displays day of week and a greeting depending on the time
        LocalDateTime now = LocalDateTime.now();
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome To The Fitness Tracker");
        System.out.println("Today is " + now.format(dateFormat));

        // Time‑based greeting to improve user experience
        int hour = now.getHour();
        if (hour < 12) {
            System.out.println("Hello Good morning... A great time to stay active :)");
        } else if (hour < 18) {
            System.out.println(" Hey there! Good afternoon...Keep your energy levels up.");
        } else {
            System.out.println("Hi Good evening...Well done for checking your activity today.");
        }
        System.out.println("--------------------------------------------------");

        // This asks user for their personal step goal 
        // It has to be a whole number and greater than 0

        userStepGoal = readStepGoal(input);
        System.out.println("You set your personal step goal to: " + userStepGoal + " steps.");
        System.out.println("--------------------------------------------------");

        // An activity log for the session
        List<String> activityLog = new ArrayList<>();

        boolean repeat = true;

        while (repeat) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Pretty print step statistics");
            System.out.println("2. Convert distance (km) into steps");
            System.out.print("Select an option (1 or 2): ");

            int choice = getValidInt(input);

            if (choice == 1) {
                System.out.println("\n--- STEP STATISTICS ---");

                System.out.print("Enter thousands (0 or more): ");
                int thousands = getNonNegativeInt(input);
                System.out.println("You entered: " + thousands);

                System.out.print("Enter hundreds (0 or more): ");
                int hundreds = getNonNegativeInt(input);
                System.out.println("You entered: " + hundreds);

                System.out.print("Enter tens (0 or more): ");
                int tens = getNonNegativeInt(input);
                System.out.println("You entered: " + tens);

                StepStats stats = new StepStats(thousands, hundreds, tens);
                String result = stats.prettyPrint();

                System.out.println("Formatted steps: " + result);

                // Logs the action with an accurate timestamp
                activityLog.add("Step statistics was checked at " + LocalDateTime.now().format(timeFormat));

                // This displays motivational messages to keep the user motivated and active
                System.out.println(getMotivationMessage());

            } else if (choice == 2) {
                System.out.println("\n--- DISTANCE CONVERSION ---");

                System.out.print("Enter distance in kilometres (greater than 0): ");
                double km = getPositiveDouble(input);
                System.out.println("You entered: " + km + " km");

                Distance distance = new Distance(km);
                StepStats stats = distance.convert();

                String result = stats.prettyPrint();
                System.out.println("Equivalent steps: " + result);

                // This also logs the action
                activityLog.add("Converted " + km + " km at " + LocalDateTime.now().format(timeFormat));

                // This compares the personalised step‑goal using the total steps from StepStats
                int totalSteps = stats.getTotalSteps();

                if (totalSteps >= userStepGoal) {
                    System.out.println("AWESOME! You've reached your personal step goal for today!");
                } else {
                    int remaining = userStepGoal - totalSteps;
                    System.out.println("You're " + remaining + " steps away from your goal. You can do this!");
                }

                // Motivational message
                System.out.println(getMotivationMessage());

            } else {
                System.out.println("That option doesn't exist. Kindly choose 1 or 2.");
            }

            // Asks the user if the would like repeat any option
            System.out.print("\nWould you like to perform another calculation? (yes/no): ");
            repeat = askYesNo(input);
        }

        // Session ends here
        LocalDateTime sessionEnd = LocalDateTime.now();
        Duration duration = Duration.between(sessionBegin, sessionEnd);

        System.out.println("\n--------------------------------------------------");
        System.out.println("SESSION SUMMARY");
        System.out.println("The session started at: " + sessionBegin.format(timeFormat));
        System.out.println("The session ended at:   " + sessionEnd.format(timeFormat));
        System.out.println("Total duration:     " + duration.toMinutes() + " minutes and "
                           + (duration.getSeconds() % 60) + " seconds");

        System.out.println("\nActivity Log:");
        if (activityLog.isEmpty()) {
            System.out.println("No activities were recorded.");
        } else {
            for (String entry : activityLog) {
                System.out.println("- " + entry);
            }
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("Thank you for using the Fitness Tracker.See you soon!");
        System.out.println("-----------------------------------------------------");

        input.close();
    }


    /**
     * This reads the user's personal step goal and ensures it is a positive whole number.
     * This prevents decimals, negative numbers, and invalid input.
     * Steps can not be decimals or negative values
     */
    private static int readStepGoal(Scanner input) {
        System.out.print("Please enter your personal step goal for today (e.g., 7000): ");

        while (!input.hasNextInt()) {
            System.out.print("Invalid number. Enter a whole number like 7000: ");
            input.next();
        }

        int goal = input.nextInt();
        input.nextLine(); // clears leftover newline

        while (goal <= 0) {
            System.out.print("Goal must be greater than zero. Please try again: ");
            goal = getValidInt(input);
        }

        return goal;
    }

    //These are validation and helper methods for this program
    //All validation methods prompts the user when invalid input are entered
    // It does not make the program crash due to invalid inputs


    /**
    *The getValidInt method ensures the user enters a valid whole number. 
  * It constantly checks the Scanner input and rejects any non‑integer 
 * inputs such as letters, symbols, or decimals. It also prevents the program from 
 * crashing from invalid input and guarantees that only a valid and 
 * usable integer is returned.
 */


    private static int getValidInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("That does not look like a whole number. Kindly try again: ");
            input.next();
        }
        int value = input.nextInt();
        input.nextLine(); // clears leftover newline
        return value;
    }

    /**
 * This method reads an integer that must be zero or greater. 
 * This method builds on getValidInt() by adding a logical check to prevent 
 *  any negative values, which would not make sense for step quantities. It makes 
 * the program consistent logical and avoids invalid step input.
 */

    private static int getNonNegativeInt(Scanner input) {
        int value = getValidInt(input); // already clears buffer
        while (value < 0) {
            System.out.print("Negative values are not accepted. Please try again: ");
            value = getValidInt(input);
        }
        return value;
    }
    /**
 * This method reads a positive decimal number from the user. 
 * This method is used for distances in kilometres and ensures that the value 
 * is both numeric and greater than zero. It prevents any invalid 
 * distance values from entering the conversion process.
 */

    private static double getPositiveDouble(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.print("That does not look like a valid number. Re-enter a valid number: ");
            input.next();
        }
        double value = input.nextDouble();
        input.nextLine(); //clears leftover newline

        while (value <= 0) {
            System.out.print("Distance must be greater than zero. Please try again: ");
            value = getPositiveDouble(input);
        }
        return value;
    }

    /**
 * This method takes care yes/no questions 
 * The method accepts common variations such as “yes”, “y”, “no”, and “n”, 
 * while rejecting anything else, it prompts the user for the valid input.
 * This avoids unnecessary menu exits or 
 * loops caused by unclear and invalid user input.
 */

    private static boolean askYesNo(Scanner input) {
        String answer = input.next().trim().toLowerCase();
        input.nextLine(); // clears leftover newline

        while (!answer.equals("yes") && !answer.equals("no")
                && !answer.equals("y") && !answer.equals("n")) {
            System.out.print("Please answer with yes or no: ");
            answer = input.next().trim().toLowerCase();
            input.nextLine(); //clears leftover newline
        }
        return answer.equals("yes") || answer.equals("y");
    }

    // This displays motivational messages for the user to keep them going
    //They are displayed randomly to the user
    //All these additions are to make the fitness tracker feel real and improve program logic
    private static String getMotivationMessage() {
        String[] messages = {
            "Every step counts...keep going.",
            "Small progress is still progress.",
            "Consistency beats intensity.",
            "You're doing great! Stay active.",
            "Movement is medicine. Well done."
        };
        int index = (int) (Math.random() * messages.length);
        return messages[index];
    }
}

