package ua.pt;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class IpmaApiClient {
    public static Map<String, String> getForecastForACity(int cityId) {
        Map<String, String> ret = new HashMap<>();
        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        Call<IpmaCityForecast> callSync = service.getForecastForACity(cityId);
        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();
            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();
                ret.put("tMin", firstDay.getTMin());
                ret.put("tMax", firstDay.getTMax());
                ret.put("precipitaProb", firstDay.getPrecipitaProb());
                ret.put("predWindDir", firstDay.getPredWindDir());
                ret.put("day", firstDay.getForecastDate());
                ret.put("windSpeed", firstDay.getClassWindSpeed() + "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

}
