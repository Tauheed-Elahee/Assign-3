
package assign.pkg3;

/**
 * Simple example of a Java program used to query an API.
 * @license http://data.gc.ca/eng/open-government-licence-canada
 *
 * Maven Dependency:
 *   <dependency>
 *     <groupId>org.json</groupId>
 *     <artifactId>json</artifactId>
 *     <version>20131018</version>
 *   </dependency>
 */
import java.io.*;
import java.net.*;
import java.util.Set;
import org.json.*;
 
public class Assign3a 
{
    public static void main( String[] args )
    {
        try {
            // Build Connection
            //URL api_url = new URL("http://donnees.ec.gc.ca/data/species/developplans/critical-habitat-for-species-at-risk-british-columbia/critical-habitat-for-species-at-risk-british-columbia-williamson-s-sapsucker-sphyrapicus-thyroideus/CH_869_Sphyrapicus_thyroideus.json");
            URL api_url = new URL("http://donnees.ec.gc.ca/data/species/developplans/critical-habitat-for-species-at-risk-british-columbia/critical-habitat-for-species-at-risk-british-columbia-rocky-mountain-tailed-frog-ascaphus-montanus/CH_632_Ascaphus_montanus.json");
            
            //URL api_url = new URL("http://donnees.ec.gc.ca/data/species/developplans/critical-habitat-for-species-at-risk-british-columbia/critical-habitat-for-species-at-risk-british-columbia-marbled-murrelet-brachyramphus-marmoratus/CH_39_Brachyramphus_marmoratus.json");
           
            URLConnection api = api_url.openConnection();
 
            // Set HTTP Headers
            api.setRequestProperty("Accept", "application/json");
            api.setRequestProperty("Accept-Language", "en");
 
            // Get Response
            JSONTokener tokener = new JSONTokener(api.getInputStream());
            JSONObject jsondata = new JSONObject(tokener);
            
          
            // JSON Metadata: There are two top-level fields: type and features
            // We are interested in the features. Each one will be a row in our CSV,
            // or an entry in our database
            JSONArray fields = jsondata.names();   
            JSONArray features = jsondata.getJSONArray("features");  
            
            int numFeatures = features.length();
            System.out.println("Number of features " + numFeatures );
            
            // JSON Metadata: Under features, there are three fields: geometry, type and properties
            //       We will focus on the use of "properties".
            //       "properties" correspond to the column headings of our CSV file.
            Set elements = ((JSONObject)features.get(0)).keySet();
            // I will demonstrate with SciName and UTMZone
            // *********************************************************
            // TBD: You must add all other fields highlighted in the assignment 
            //      (All the yellow-highlighted fields in the assignment description).
            // **************************************************************
        
            for (int i=0; i<numFeatures; i++) {
            // for (int i=0; i<3; i++) {   // For testing, to avoid getting overwhelmed with data, just print out the first 3
                JSONObject feature = (JSONObject)features.get(i);
                JSONObject properties = feature.getJSONObject("properties");
                        
                String sciName = properties.get("SciName").toString();
                String commName = properties.get("CommName_E").toString();
                int speciesID = new Integer(properties.get("SpeciesID").toString()).intValue();
                String provTerr = properties.get("ProvTerr").toString();
                int utmZone = new Integer (properties.get("UTMZone").toString()).intValue();
                double latitude = new Double(properties.get("Latitude").toString()).doubleValue();
                double longitude = new Double(properties.get("Longitude").toString()).doubleValue();
                String chStatus = properties.get("CHStatus").toString();
                double shapeArea = new Double(properties.get("Shape_Area").toString()).doubleValue();
            
                System.out.println(sciName + "," + commName + "," + speciesID + "," + provTerr + "," + utmZone + "," + latitude + "," + longitude + "," + chStatus + "," + shapeArea);
            }
          
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        } catch (IOException e) {
            System.out.println("IO Error");
        }
        
 
    }
}