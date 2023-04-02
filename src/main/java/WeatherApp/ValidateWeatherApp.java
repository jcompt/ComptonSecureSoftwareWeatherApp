//Validate class for Hardened Weather App

package WeatherApp;

public class ValidateWeatherApp {

    //Declare class variables
    private static Boolean isGood = false;
    private static int numDigits = 0;
    private static String userZip;


    public static Boolean validateInput (String args){

        //Make sure that input has been entered
        if(args.length() >0){
            userZip = args;
        }else{
           return isGood;
        }

        //Check to make sure the user's input matches the format of a U.S. zip code
        if(userZip.length() == 5){
            //Do check for digits
            for(int i = 0; i < userZip.length(); i++){
                if(Character.isDigit(userZip.charAt(i))){
                    numDigits +=1;
                }

            } if(numDigits == 5){
                isGood = true;
            }else{
                System.out.println("Your input is not valid!");
            }
        }else
            System.out.println("Your input is not valid!");

        return isGood;
    }

    //Getter for user zip
    public static String getUserZip(){
        return userZip;
    }


}
