    import com.mongodb.client.MongoCollection;
    import org.bson.Document;
    import com.mongodb.client.model.Filters;

    public class User {
        private String userId;
        private String userName;
        private String userEmail;
        private String userPassword;

        public User(String userId, String userName, String userEmail, String userPassword) {
            this.userId = userId;
            this.userName = userName;
            this.userEmail = userEmail;
            this.userPassword = userPassword;
        }

        public String getUserId() {
            return userId;
        }

        public Document toDocument() {
            return new Document("UserID", userId)
                    .append("UserName", userName)
                    .append("UserEmail", userEmail)
                    .append("UserPassword", userPassword);
        }

        // Method to create a new user with auto-assigned ID and display the ID to the user
        public static User createNewUser(String userName, String userEmail, String userPassword, boolean isAgent, MongoCollection<Document> collection) {
            String userId = generateUserId(isAgent, collection);
            User newUser = new User(userId, userName, userEmail, userPassword);
            collection.insertOne(newUser.toDocument());

            // Display the generated user ID to the user
            System.out.println("New user created with ID: " + userId);

            return newUser;
        }

        // Generate a unique User ID based on role (Agent or Customer)
        private static String generateUserId(boolean isAgent, MongoCollection<Document> collection) {
            String prefix = isAgent ? "A" : "U";
            int idNumber = 1;

            while (true) {
                String newUserId = prefix + String.format("%03d", idNumber); // e.g., A001, U002
                if (collection.find(Filters.eq("UserID", newUserId)).first() == null) {
                    return newUserId;
                }
                idNumber++;
            }
        }

        // Remove user by ID
        public static void removeUser(String userId, MongoCollection<Document> collection) {
            Document result = collection.findOneAndDelete(Filters.eq("UserID", userId));
            if (result != null) {
                System.out.println("User with ID " + userId + " has been removed.");
            } else {
                System.out.println("User not found.");
            }
        }

        // Authenticate user
        public static boolean authenticateUser(String userId, String password, MongoCollection<Document> collection) {
            Document doc = collection.find(Filters.and(Filters.eq("UserID", userId), Filters.eq("UserPassword", password))).first();
            return doc != null;
        }
    }
