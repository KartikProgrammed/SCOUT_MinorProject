import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class RealEstateApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while(running){
            System.out.println("Welcome to the Real Estate Application!");
            System.out.println("Do you have an account? (yes/no)");
            String hasAccount = scanner.nextLine().trim().toLowerCase();
            if (hasAccount.equals("yes")) {
                // If the user has an account
                System.out.println("Are you an Agent or a User? (agent/user)");
                String userType = scanner.nextLine().trim().toLowerCase();

                if (userType.equals("agent")) {
                    if(Aauth(scanner)){
                        agentMenu(scanner);
                    }
                    else{
                        System.out.println("Invalid credentials entered!");
                    }
                }
                else if (userType.equals("user")) {
                    if(Uauth(scanner)){
                        customerMenu(scanner);
                    }
                    else{
                        System.out.println("Invalid credentials entered!");
                    }
                }
                else {
                    System.out.println("Invalid input! Please enter either 'agent' or 'user'.");
                }

            }
            else if (hasAccount.equals("no")) {
                // If the user is new
                System.out.println("Welcome, new customer! Please choose an option:");
                System.out.println("1. Create an Account");
                System.out.println("2. Exit");
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    System.out.println("Are you an Agent or a User? (agent/user)");
                    String userType = scanner.nextLine().trim().toLowerCase();
                    createAccount(userType);
                } else {
                    running = false; // Exit the program
                }

            }
            else {
                System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            }
        }
        System.out.println("Thank you for using the Real Estate Application! Goodbye!");
        scanner.close();
    }
    private static void createAccount(String userType) {
        Scanner scanner = new Scanner(System.in);
        boolean isAgent = userType.equals("agent"); // Use equals for string comparison

        // Simulate account creation
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Access the collection using MongoDBUtil
        MongoCollection<Document> collection = MongoDBUtil.getUserCollection();

        User newUser = User.createNewUser(username, email, password, isAgent, collection);

        System.out.println("Account created successfully for " + username +"your ID is"+newUser.getUserId()+ "!");
    }

    private static boolean Uauth(Scanner scanner) {
        System.out.println("enter your User ID");
        String id= scanner.nextLine();
        System.out.println("enter your password");
        String pswd=scanner.nextLine();
        MongoCollection<Document> collection = MongoDBUtil.getUserCollection();
        boolean ret = User.authenticateUser(id, pswd, collection);
        return ret;
    }
    private static boolean Aauth(Scanner scanner) {
        System.out.println("enter your Agent ID");
        String id= scanner.nextLine();
        System.out.println("enter your password");
        String pswd=scanner.nextLine();
        MongoCollection<Document> collection = MongoDBUtil.getUserCollection();
        return User.authenticateUser(id, pswd, collection);
    }
    private static void agentMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Agent Menu:");
            System.out.println("1. List Properties");
            System.out.println("2. Add a Property");
            System.out.println("3. Update a Property");
            System.out.println("4. Delete a Property");
            System.out.println("5. Log Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            running=false;

//            switch (choice) {
//                case "1":
//                    listProperties();
//                    break;
//                case "2":
//                    addProperty(scanner);
//                    break;
//                case "3":
//                    updateProperty(scanner);
//                    break;
//                case "4":
//                    deleteProperty(scanner);
//                    break;
//                case "5":
//                    running = false; // Log out
//                    break;
//                default:
//                    System.out.println("Invalid option! Please try again.");
//            }
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
            running=false;

//            switch (choice) {
//                case "1":
//                    searchProperties(scanner);
//                    break;
//                case "2":
//                    viewSavedProperties();
//                    break;
//                case "3":
//                    running = false; // Log out
//                    break;
//                default:
//                    System.out.println("Invalid option! Please try again.");
//            }
        }
    }

};

