import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Scanner;

public class RealEstateApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Real Estate Application!");
            System.out.println("Do you have an account? (yes/no)");
            String hasAccount = scanner.nextLine().trim().toLowerCase();

            if (hasAccount.equals("yes")) {
                System.out.println("Are you an Agent or a User? (agent/user)");
                String userType = scanner.nextLine().trim().toLowerCase();

                if (userType.equals("agent")) {
                    String agentId = Aauth(scanner);
                    if (agentId != null) {
                        agentMenu(scanner, agentId);  // Enter agent menu on successful login
                    } else {
                        System.out.println("Invalid credentials entered!");
                    }
                } else if (userType.equals("user")) {
                    if (Uauth(scanner)) {
                        customerMenu(scanner);  // Enter customer menu on successful login
                    } else {
                        System.out.println("Invalid credentials entered!");
                    }
                } else {
                    System.out.println("Invalid input! Please enter either 'agent' or 'user'.");
                }
            } else if (hasAccount.equals("no")) {
                System.out.println("Welcome, new customer! Please choose an option:");
                System.out.println("1. Create an Account");
                System.out.println("2. Exit");
                String userType = scanner.nextLine().trim();

                if (userType.equals("1")) {
                    System.out.println("Are you an Agent or a User? (agent/user)");
                    String accountType = scanner.nextLine().trim().toLowerCase();
                    createAccount(accountType);
                } else {
                    running = false;
                }
            } else {
                System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            }
        }

        System.out.println("Thank you for using the Real Estate Application! Goodbye!");
        scanner.close();
    }

    protected static void createAccount(String userType) {
        Scanner scanner = new Scanner(System.in);
        boolean isAgent = userType.equals("agent");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        MongoCollection<Document> collection = MongoDBUtilUser.getUserCollection();
        User newUser = User.createNewUser(username, email, password, isAgent, collection);
        System.out.println("Account created successfully for " + username + "! Your ID is " + newUser.getUserId() + ".");
    }

    private static boolean Uauth(Scanner scanner) {
        System.out.println("Enter your User ID");
        String id = scanner.nextLine();
        System.out.println("Enter your password");
        String pswd = scanner.nextLine();
        MongoCollection<Document> collection = MongoDBUtilUser.getUserCollection();
        return User.authenticateUser(id, pswd, collection);
    }

    private static String Aauth(Scanner scanner) {
        System.out.println("Enter your Agent ID");
        String id = scanner.nextLine();
        System.out.println("Enter your password");
        String pswd = scanner.nextLine();
        MongoCollection<Document> collection = MongoDBUtilUser.getUserCollection();
        boolean isAuthenticated = User.authenticateUser(id, pswd, collection);
        return isAuthenticated ? id : null;
    }

    private static void agentMenu(Scanner scanner, String agentID) {
        boolean running = true;

        while (running) {
            System.out.println("Agent Menu:");
            System.out.println("1. Search for Properties");
            System.out.println("2. Add a Property");
            System.out.println("3. Update a Property");
            System.out.println("4. Delete a Property");
            System.out.println("5. Log Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    PropertyManager.searchProperty();
                    break;
                case "2":
                    PropertyManager.addProperty(scanner, agentID);
                    break;
                case "3":
                    PropertyManager.updateProperty(scanner);
                    break;
                case "4":
                    PropertyManager.deleteProperty(scanner);
                    break;
                case "5":
                    running = false;  // Log out by breaking the loop
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void customerMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("Customer Menu:");
            System.out.println("1. Search for Properties");
            System.out.println("2. View Saved Properties");
            System.out.println("3. Log Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Call the PropertySearcher class to search for properties
                    PropertySearcher.loadPropertyData();
                    PropertySearcher.searchProperty(scanner);
                    break;
                case "2":
                    System.out.println("Viewing saved properties is not yet implemented.");
                    break;
                case "3":
                    running = false;  // Log out by breaking the loop
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}