import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBUtilProperty {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> propertyCollection;

    // Static initializer block to establish a connection
    static {
        String uri = "mongodb+srv://vv200325803:Cr5lNRcWVUUGyQud@cluster0.kzmm9.mongodb.net/";
        mongoClient = MongoClients.create(uri); // Keep the client open
        database = mongoClient.getDatabase("RealEstateDB");
        propertyCollection = database.getCollection("Properties");
    }

    // Get the property collection
    public static MongoCollection<Document> getPropertyCollection() {
        return propertyCollection;
    }

    // Close the MongoDB connection
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
