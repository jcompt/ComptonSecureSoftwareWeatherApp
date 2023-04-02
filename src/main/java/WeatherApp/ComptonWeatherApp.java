//Jake Compton
//Hardened Weather App
//April 3, 2023

package WeatherApp;

public class ComptonWeatherApp {

    public static void main(String[] args) {

        //Create object of the APIWrapperWeatherApp class
        APIWrapperWeatherApp thisAPIWrapper = new APIWrapperWeatherApp(args[0]);

        //Once the input has been verified and the connection made, call the api to get the weather data
        if(thisAPIWrapper.isValid() && thisAPIWrapper.isConnected()) {
            //Get the unformatted API data
            thisAPIWrapper.getUnformattedResponse();
            //Get the formatted data
            thisAPIWrapper.getFormattedGson();
        }

    }

}
