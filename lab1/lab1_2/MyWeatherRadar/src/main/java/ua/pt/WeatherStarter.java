package ua.pt;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.pt.IpmaCityForecast; //may need to adapt package name
import ua.pt.IpmaService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    //private static final int CITY_ID_AVEIRO = 1010500;
    private static Logger logger = LogManager.getLogger(WeatherStarter.class);

    public static void  main(String[] args ) {

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        int city_id = 0;
        try {
            city_id = Integer.parseInt(args[0]);
            logger.info("City id given as argument: " + city_id);
        } catch (NumberFormatException e) {
            logger.error("Incorrect argument given, please give a valid city id as argument");
            System.exit(1);
        }
        // prepare the call to remote endpoint
        logger.info("Preparing call to api");
        Call<IpmaCityForecast> callSync = service.getForecastForACity(city_id); // passed by the user

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();

                logger.info("Printing results for city: " + forecast.getGlobalIdLocal());
                System.out.printf("Predictions for day: %s %n%nMaximum temperature is %4.1f.%nMinimum temperature is %4.1f %n" +
                                "Percentage of precipitation is %s %nWind direction is %s %nWind speed is %s %n",
                        firstDay.getForecastDate(),
                        Double.parseDouble(firstDay.getTMax()),
                        Double.parseDouble(firstDay.getTMin()),
                        firstDay.getPrecipitaProb(),
                        firstDay.getPredWindDir(),
                        firstDay.getClassWindSpeed()
                );
            } else {
                logger.error("No results for this request!");
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.info("Done.");
    }
}