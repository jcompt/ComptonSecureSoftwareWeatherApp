package WeatherApp;

public class ValidateWeatherApp {

    //Declare class variables
    public static String[] args;
    public static Boolean isGood = false;
    public static int numDigits = 0;
    public static String userZip;


    public static Boolean validateInput (String[] args){

        //Make sure that input has been entered
        if(args.length >0){
            userZip = args[0];
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
                passZip(userZip);
            }else{
                System.out.println("The input you entered is NOT in the format of a zip code!");
            }
        }else
            System.out.println("A zip code is 5 digits!");

        return isGood;
    }

    public static String passZip (String thisZip){
        return thisZip;
    }


}
