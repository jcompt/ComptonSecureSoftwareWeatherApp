//API Wrapper class for Hardened Weather App

package WeatherApp;

import com.google.gson.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class APIWrapperWeatherApp {

    //Declare class variables
    private boolean valid = false;
    private boolean connected = false;
    private HttpURLConnection connection;
    private String apiData = "";

    //Constructor for API
    APIWrapperWeatherApp(String input){
        //Validate input
        if(ValidateWeatherApp.validateInput(input)){
            valid = true;

            //Declare URL variable to hold the URL to query the API
            try {
                URL weatherURL = new URL(SecretWeatherApp.connectionString(input).toString());

                //Create connection to API
                 connection = (HttpURLConnection) weatherURL.openConnection();

                //Specify type of API request.  GET returns data from the server
                connection.setRequestMethod("GET");

                //Open connection to the API
                connection.connect();

                //Set connected to true
                connected = true;

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Connection unsuccessful.");
        }

    }

    //Allows access to the value of valid
    public boolean isValid() {
        return valid;
    }


    //Allows access to the value of connected
    public boolean isConnected() {
        return connected;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }


    public void getUnformattedResponse(){
        //Store the response code generated by the connection
        try {
            int responseCode = connection.getResponseCode();
            //Create a stringbuilder
            StringBuilder allTheData = new StringBuilder();

            Scanner scanner;

            //Check if the connection to the API is successful.  200 means success.  Any other code is an error.
            if (responseCode == 200) {
                //The connection was successful.  Read the information from the API and save to variable
                scanner = new Scanner(connection.getInputStream());
            } else if (responseCode == 400) {
                scanner = new Scanner(connection.getErrorStream());
            } else {
                throw new RuntimeException("The connection was NOT successful: " + responseCode);
            }

            while (scanner.hasNext()) {
                allTheData.append(scanner.nextLine());
            }
            //Close the scanner when done reading
            scanner.close();

            //Put the api data into a string
            apiData = allTheData.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getFormattedGson(){
        //Convert the stringbuilder text to a JSON object that is formatted nicely
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parseThis = new JsonParser();
        JsonObject root = parseThis.parse(apiData).getAsJsonObject();

        //Check to make sure that the proper data was returned by using if else. The else will run all the code for good data
        if(root.has("error")){
            System.out.println("Error: " + root.getAsJsonObject("error").get("message").getAsString());
        }else{
            //Get the weather data out of the JSON data starting at the root object
            JsonObject locationObj = root.getAsJsonObject("location");
            JsonElement nameElement = locationObj.get("name");
            JsonElement regionElement = locationObj.get("region");
            JsonObject forecastObj = root.getAsJsonObject("forecast");
            JsonArray forecastDayArr = forecastObj.getAsJsonArray("forecastday");

            //Stringbuiler to display the weather information
            StringBuilder weather = new StringBuilder();
            weather.append("3 Day Weather Forecast for: " + nameElement.getAsString() + ", " + regionElement.getAsString());
            weather.append("\n");

            //Display first day's weather data
            for (JsonElement forecastDay : forecastDayArr) {
                //Make a temporary json object to convert the day element to an object
                JsonObject tempForecastDay = forecastDay.getAsJsonObject();
                weather.append("Date: " + tempForecastDay.get("date").getAsString());
                JsonObject dayObj = tempForecastDay.getAsJsonObject("day");

                //Check temperatures to make sure they are in valid temperature range
                if((dayObj.get("maxtemp_f").getAsDouble() < 134.0) && (dayObj.get("mintemp_f").getAsDouble() > -129.0)){

                    weather.append("\n\t" + "Conditions:       " + dayObj.getAsJsonObject("condition").get("text").getAsString() + "\n");
                    weather.append("\t" + "High Temperature: " + dayObj.get("maxtemp_f").getAsString() + "\u00b0F \n");
                    weather.append("\t" + "Low Temperature:  " + dayObj.get("mintemp_f").getAsString() + "\u00b0F \n");
                    weather.append("\n");
                } else {
                    //One or more of the temperatures are outside the expected temperature range for that day
                    weather.append("\t API did not return the data expected. \n");
                    weather.append("\n");
                }

            }
            System.out.println(weather);
        }
    }
}