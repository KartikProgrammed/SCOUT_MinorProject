import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnection {
    public static void main(String[] args) {
        String uri = "mongodb+srv://vv200325803:Cr5lNRcWVUUGyQud@cluster0.kzmm9.mongodb.net/";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Connect to a database (this won't create it yet)
            MongoDatabase database = mongoClient.getDatabase("RealEstateDB");

            // Get a collection (this won't create it yet either)
            MongoCollection<Document> collection = database.getCollection("Users");

            // Create a new user (automatically assigns a UserID)
            User newUser = User.createNewUser("John Doe", "john.doe@example.com", "securePassword123", false, collection);
            boolean newUser2=User.authenticateUser("U001","securePassword123",collection);
            System.out.println(newUser2);
            //System.out.println("User created with ID: " + newUser.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
