import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.Filters;

public class Property {
    protected String propertyId;
    protected double squareFeet;
    protected String address;
    protected int bhkNumber;
    protected double latitude;
    protected double longitude;
    protected double priceInLakhs;
    protected String agentId;

    public Property(String propertyId, double squareFeet, String address, int bhkNumber, double latitude, double longitude, double priceInLakhs, String agentId) {
        this.propertyId = propertyId;
        this.squareFeet = squareFeet;
        this.address = address;
        this.bhkNumber = bhkNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.priceInLakhs = priceInLakhs;
        this.agentId = agentId; // Assigning the agent's ID
    }

    // Getter methods
    public String getPropertyId() {
        return propertyId;
    }

    // Convert property details to MongoDB document format
    public Document toDocument() {
        return new Document("PropertyID", propertyId)
                .append("SquareFeet", squareFeet)
                .append("Address", address)
                .append("BHKNumber", bhkNumber)
                .append("Latitude", latitude)
                .append("Longitude", longitude)
                .append("PriceInLakhs", priceInLakhs)
                .append("AgentID", agentId);
    }

    // Method to create a new property with auto-assigned ID and display the ID to the user
    public static Property createNewProperty(double squareFeet, String address, int bhkNumber, double latitude, double longitude, double priceInLakhs, String agentId, MongoCollection<Document> collection) {
        String propertyId = generatePropertyId(collection);
        Property newProperty = new Property(propertyId, squareFeet, address, bhkNumber, latitude, longitude, priceInLakhs, agentId);
        collection.insertOne(newProperty.toDocument());
        System.out.println("New property created with ID: " + propertyId + " by Agent ID: " + agentId);

        return newProperty;
    }

    // Generate a unique Property ID
    private static String generatePropertyId(MongoCollection<Document> collection) {
        String prefix = "P";
        int idNumber = 2740;
        while (true) {
            String newPropertyId = prefix + String.format("%03d", idNumber); // e.g., P001, P002
            if (collection.find(Filters.eq("PropertyID", newPropertyId)).first() == null) {
                return newPropertyId;
            }
            idNumber++;
        }
    }

    // Remove property by ID
    public static void removeProperty(String propertyId, MongoCollection<Document> collection) {
        Document result = collection.findOneAndDelete(Filters.eq("PropertyID", propertyId));
        if (result != null) {
            System.out.println("Property with ID " + propertyId + " has been removed.");
        } else {
            System.out.println("Property not found.");
        }
    }

    // Retrieve a property by its ID
    public static Property getPropertyById(String propertyId, MongoCollection<Document> collection) {
        Document doc = collection.find(Filters.eq("PropertyID", propertyId)).first();
        if (doc != null) {
            return new Property(
                    doc.getString("PropertyID"),
                    doc.getDouble("SquareFeet"),
                    doc.getString("Address"),
                    doc.getInteger("BHKNumber"),
                    doc.getDouble("Latitude"),
                    doc.getDouble("Longitude"),
                    doc.getDouble("PriceInLakhs"),
                    doc.getString("AgentID")
            );
        }
        System.out.println("Property not found.");
        return null;
    }
}
