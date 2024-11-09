import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;

public class API_init {
    public static void main(String[] args) {
        try {
            JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("6949a36a3eff442e9f84d446977218ec");
            JOpenCageForwardRequest request = new JOpenCageForwardRequest("bidholi");
            request.setRestrictToCountryCode("IN");

            JOpenCageResponse response = jOpenCageGeocoder.forward(request);
            JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result

            if (firstResultLatLng != null) {
                System.out.println("Latitude: " + firstResultLatLng.getLat() + ", Longitude: " + firstResultLatLng.getLng());
            } else {
                System.out.println("Address not found within India.");
            }
        } catch (NullPointerException e) {
            System.out.println("Address not found within India.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
