import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVToMongoDB {

    public static void main(String[] args) {
        String uri = "mongodb+srv://vv200325803:Cr5lNRcWVUUGyQud@cluster0.kzmm9.mongodb.net/";
        String csvFilePath = "C:\\Users\\Kartik Khatri\\Desktop\\RealEstateSearch\\src\\main\\java\\property_full.csv";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("RealEstateDB");
            MongoCollection<Document> collection = database.getCollection("Properties");

            try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                List<String[]> rows = reader.readAll();

                String[] headers = rows.get(0); // First row as headers
                for (int i = 1; i < rows.size(); i++) { // Skip header row
                    Map<String, Object> docData = new HashMap<>();
                    for (int j = 0; j < headers.length; j++) {
                        docData.put(headers[j], rows.get(i)[j]);
                    }
                    Document doc = new Document(docData);
                    collection.insertOne(doc); // Insert each row as a document
                }

                System.out.println("Data imported successfully!");
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }
    }
}