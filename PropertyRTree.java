import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import rx.Observable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertyRTree {
    private RTree<Integer, Point> rtree;

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
                int pid = Integer.parseInt(data[0].trim());
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());

                // Insert into R-tree
                rtree = rtree.add(pid, Geometries.point(longitude, latitude));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Query properties within a certain distance of a given point
    public void queryNearbyProperties(double latitude, double longitude, double maxDistance) {
        Observable<Entry<Integer, Point>> results = rtree.search(
                Geometries.circle(longitude, latitude, maxDistance));

        results.forEach(entry -> {
            System.out.println("Found PID: " + entry.value() + " at " +
                    entry.geometry().y() + ", " + entry.geometry().x());
        });
    }

    public static void main(String[] args) {
        PropertyRTree propertyRTree = new PropertyRTree();
        propertyRTree.loadDataFromCSV("C:\\Users\\Kartik Khatri\\Desktop\\RealEstateSearch\\src\\main\\java\\Properties.csv");

        // Query properties near a specific location
        propertyRTree.queryNearbyProperties(72.851441, 19, 0.1);
    }
}
