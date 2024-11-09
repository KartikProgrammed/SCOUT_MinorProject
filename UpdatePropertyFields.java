import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;

public class UpdatePropertyFields {
    public static void main(String[] args) {
        String uri = "mongodb+srv://vv200325803:Cr5lNRcWVUUGyQud@cluster0.kzmm9.mongodb.net/"; // Replace with your MongoDB URI
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("RealEstateDB"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("Properties"); // Replace with your collection name

            for (Document doc : collection.find()) {
                ObjectId id = doc.getObjectId("_id");

                // Convert SquareFeet to Double if it’s a String
                if (doc.get("SquareFeet") instanceof String) {
                    double squareFeet = Double.parseDouble(doc.getString("SquareFeet"));
                    collection.updateOne(Filters.eq("_id", id), new Document("$set", new Document("SquareFeet", squareFeet)));
                }

                // Convert Latitude to Double if it’s a String
                if (doc.get("Latitude") instanceof String) {
                    double latitude = Double.parseDouble(doc.getString("Latitude"));
                    collection.updateOne(Filters.eq("_id", id), new Document("$set", new Document("Latitude", latitude)));
                }

                // Convert Longitude to Double if it’s a String
                if (doc.get("Longitude") instanceof String) {
                    double longitude = Double.parseDouble(doc.getString("Longitude"));
                    collection.updateOne(Filters.eq("_id", id), new Document("$set", new Document("Longitude", longitude)));
                }

                // Convert PriceInLakhs to Double if it’s a String
                if (doc.get("PriceInLakhs") instanceof String) {
                    double priceInLakhs = Double.parseDouble(doc.getString("PriceInLakhs"));
                    collection.updateOne(Filters.eq("_id", id), new Document("$set", new Document("PriceInLakhs", priceInLakhs)));
                }
                if(doc.get("BHKNumber") instanceof String){
                    int BHK=Integer.parseInt(doc.getString("BHKNumber"));
                    collection.updateOne(Filters.eq("_id",id),new Document("$set",new Document("BHKNumber",BHK)));
                }
            }
            System.out.println("Fields converted successfully where needed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
