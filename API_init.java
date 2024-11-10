import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;

public class API_init {

    private static JOpenCageGeocoder jOpenCageGeocoder;

    // Initialize API with the API key
    static {
        jOpenCageGeocoder = new JOpenCageGeocoder("6949a36a3eff442e9f84d446977218ec");
    }

    // Method to get coordinates (latitude, longitude) from address
    public static double[] getCoordinatesFromAddress(String address) {
        try {
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
            request.setRestrictToCountryCode("IN");

            JOpenCageResponse response = jOpenCageGeocoder.forward(request);
            JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result

            if (firstResultLatLng != null) {
                return new double[]{firstResultLatLng.getLat(), firstResultLatLng.getLng()};
            } else {
                System.out.println("Address not found within India.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
