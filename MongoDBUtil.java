import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBUtil {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> userCollection;

    // Static initializer block to establish a connection
    static {
        String uri = "mongodb+srv://vv200325803:Cr5lNRcWVUUGyQud@cluster0.kzmm9.mongodb.net/";
        mongoClient = MongoClients.create(uri); // Keep the client open
        database = mongoClient.getDatabase("RealEstateDB");
        userCollection = database.getCollection("Users");
    }

    public static MongoCollection<Document> getUserCollection() {
        return userCollection;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
