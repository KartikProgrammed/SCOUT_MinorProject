import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.Scanner;

public class PropertyManager {

    // Search for a property by ID
    protected static void searchProperty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Property ID to search: ");
        String propertyId = scanner.nextLine();

        MongoCollection<Document> collection = MongoDBUtilProperty.getPropertyCollection();
        Property property = Property.getPropertyById(propertyId, collection);

        if (property != null) {
            System.out.println("Property Found: ");
            System.out.println("ID: " + property.getPropertyId());
            System.out.println("Square Feet: " + property.squareFeet);
            System.out.println("Address: " + property.address);
            System.out.println("BHK: " + property.bhkNumber);
            System.out.println("Latitude: " + property.latitude);
            System.out.println("Longitude: " + property.longitude);
            System.out.println("Price (Lakhs): " + property.priceInLakhs);
        } else {
            System.out.println("Property not found.");
        }
    }

    // Add a new property
    protected static void addProperty(Scanner scanner, String agentID) {
        System.out.print("Enter Square Feet: ");
        int squareFeet = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter BHK Number: ");
        int bhkNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Latitude: ");
        double latitude = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Longitude: ");
        double longitude = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Price (in Lakhs): ");
        double priceInLakhs = Double.parseDouble(scanner.nextLine());

        MongoCollection<Document> collection = MongoDBUtilProperty.getPropertyCollection();
        Property newProperty = Property.createNewProperty(squareFeet, address, bhkNumber, latitude, longitude, priceInLakhs, agentID, collection);

        System.out.println("New Property added successfully with ID: " + newProperty.getPropertyId());
    }

    // Update an existing property
    protected static void updateProperty(Scanner scanner) {
        System.out.print("Enter the Property ID to update: ");
        String propertyId = scanner.nextLine();

        MongoCollection<Document> collection = MongoDBUtilProperty.getPropertyCollection();
        Property property = Property.getPropertyById(propertyId, collection);

        if (property == null) {
            System.out.println("Property not found.");
            return;
        }

        System.out.println("Updating Property with ID: " + propertyId);
        System.out.print("Enter new Square Feet (or press Enter to keep " + property.squareFeet + "): ");
        String squareFeetInput = scanner.nextLine();
        int squareFeet = squareFeetInput.isEmpty() ? property.squareFeet : Integer.parseInt(squareFeetInput);

        System.out.print("Enter new Address (or press Enter to keep current address): ");
        String address = scanner.nextLine();
        address = address.isEmpty() ? property.address : address;

        System.out.print("Enter new BHK Number (or press Enter to keep " + property.bhkNumber + "): ");
        String bhkInput = scanner.nextLine();
        int bhkNumber = bhkInput.isEmpty() ? property.bhkNumber : Integer.parseInt(bhkInput);

        System.out.print("Enter new Latitude (or press Enter to keep " + property.latitude + "): ");
        String latInput = scanner.nextLine();
        double latitude = latInput.isEmpty() ? property.latitude : Double.parseDouble(latInput);

        System.out.print("Enter new Longitude (or press Enter to keep " + property.longitude + "): ");
        String longInput = scanner.nextLine();
        double longitude = longInput.isEmpty() ? property.longitude : Double.parseDouble(longInput);

        System.out.print("Enter new Price in Lakhs (or press Enter to keep " + property.priceInLakhs + "): ");
        String priceInput = scanner.nextLine();
        double priceInLakhs = priceInput.isEmpty() ? property.priceInLakhs : Double.parseDouble(priceInput);

        Document updatedDocument = new Document("PropertyID", propertyId)
                .append("SquareFeet", squareFeet)
                .append("Address", address)
                .append("BHKNumber", bhkNumber)
                .append("Latitude", latitude)
                .append("Longitude", longitude)
                .append("PriceInLakhs", priceInLakhs);

        collection.replaceOne(Filters.eq("PropertyID", propertyId), updatedDocument);
        System.out.println("Property updated successfully.");
    }

    // Delete a property by ID
    protected static void deleteProperty(Scanner scanner) {
        System.out.print("Enter the Property ID to delete: ");
        String propertyId = scanner.nextLine();

        MongoCollection<Document> collection = MongoDBUtilProperty.getPropertyCollection();
        Property.removeProperty(propertyId, collection);
    }
}
