import java.util.*;

public class PropertyUtils {

    // Method to sort properties by price
    public static void sortPropertiesByPrice(List<Property> properties, Scanner scanner) {
        System.out.println("Would you like to sort by price in ascending or descending order?");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            properties.sort(Comparator.comparingDouble(Property::getPriceInLakhs));
        } else if (choice.equals("2")) {
            properties.sort(Comparator.comparingDouble(Property::getPriceInLakhs).reversed());
        } else {
            System.out.println("Invalid option! Sorting by price was not performed.");
        }

        // Display the sorted properties (for demonstration purposes)
        displayProperties(properties);
    }

    // Method to sort properties by BHK
    public static void sortPropertiesByBHK(List<Property> properties, Scanner scanner) {
        System.out.println("Would you like to sort by BHK in ascending or descending order?");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            properties.sort(Comparator.comparingInt(Property::getBhkNumber));
        } else if (choice.equals("2")) {
            properties.sort(Comparator.comparingInt(Property::getBhkNumber).reversed());
        } else {
            System.out.println("Invalid option! Sorting by BHK was not performed.");
        }

        // Display the sorted properties (for demonstration purposes)
        displayProperties(properties);
    }

    // Helper method to display properties
    public static void displayProperties(List<Property> properties) {
        if (properties.isEmpty()) {
            System.out.println("No properties to display.");
        } else {
            for (Property property : properties) {
                System.out.println(PropertySearcher.propertyDetailsToString(property));
            }
        }
    }
}
