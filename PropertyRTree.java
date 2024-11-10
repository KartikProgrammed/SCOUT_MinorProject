import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import rx.Observable;
import rx.functions.Action1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PropertyRTree {
    private RTree<String, Point> rtree;

    public PropertyRTree() {
        // Initialize an R-tree instance
        rtree = RTree.create();
    }

    // Load data from CSV into R-tree
    public void loadDataFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String pid = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());

                // Insert into R-tree with String PID
                rtree = rtree.add(pid, Geometries.point(longitude, latitude));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Query properties within a certain distance of a given point
    public List<String> queryNearbyProperties(double latitude, double longitude, double maxDistance) {
        // Create a list to store the property IDs
        List<String> propertyIds = new ArrayList<>();

        // Perform the search query
        Observable<Entry<String, Point>> results = rtree.search(
                Geometries.circle(longitude, latitude, maxDistance));

        // Use toList() to collect the results into a List
        List<Entry<String, Point>> entries = results.toList().toBlocking().single(); // Block until results are available

        // For each result, add the PID to the propertyIds list
        for (Entry<String, Point> entry : entries) {
            if(propertyIds.size()<10) {
//                System.out.println("Found PID: " + entry.value() + " at " + entry.geometry().y() + ", " + entry.geometry().x());
                propertyIds.add(entry.value());
            }
            else{
                break;
            }
        }

        // Return the collected property IDs
        return propertyIds;
    }
        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PropertyRTree propertyRTree = new PropertyRTree();
        propertyRTree.loadDataFromCSV("C:\\Users\\Kartik Khatri\\Desktop\\RealEstateSearch\\src\\main\\java\\Properties.csv");
//        propertyRTree.searchProperty(scanner);
//            System.out.println(coordinates[1]);
        // Query properties near a specific location
//        propertyRTree.queryNearbyProperties(76.644605, 12.274538, 0.1);
        scanner.close();
    }
}
