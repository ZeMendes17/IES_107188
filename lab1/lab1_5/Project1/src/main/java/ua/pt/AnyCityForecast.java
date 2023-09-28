package ua.pt;

import ua.pt.IpmaApiClient;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AnyCityForecast {

    private static final Logger logger = LogManager.getLogger(AnyCityForecast.class);
    private static int count = 1;

    private static int getRandCity(int[] cities) {
        int rnd = new Random().nextInt(cities.length);
        return cities[rnd];
    }

    public static void main(String[] args) {
        // does a periodic forecast request
        logger.info("Starting periodic forecast requests");

        int[] cities = {1010500, 1020500, 1030300, 1030800, 1040200, 1050200, 1060300, 1070500, 1080500, 1080800,
                1081100, 1081505, 1090700, 1090821, 1100900, 1110600, 1121400, 1131200, 1141600, 1151200, 1151300,
                1160900, 1171400, 1182300, 2310300, 2320100, 3410100, 3420300, 3430100, 3440100, 3450200, 3460200,
                3470100, 3480200, 3490100};

        while (true) {
            logger.info("Requesting forecast for a random city");
            Map<String,String> info = IpmaApiClient.getForecastForACity(getRandCity(cities));

            logger.info("Printing results");
            System.out.printf("Predictions for day: %s %n%nMaximum temperature is %4.1f.%nMinimum temperature is %4.1f %n" +
                            "Percentage of precipitation is %s %nWind direction is %s %nWind speed is %s %n",
                    info.get("day"),
                    Double.parseDouble(info.get("tMax")),
                    Double.parseDouble(info.get("tMin")),
                    info.get("precipitaProb"),
                    info.get("predWindDir"),
                    info.get("windSpeed")
            );
            System.out.println("------------------------------------");

            try{
                logger.info("Sleeping for 20 seconds");
                TimeUnit.SECONDS.sleep(20);
                logger.info("Forecast request number " + count++);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted", e);
            }
        }
    }
}
