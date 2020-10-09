package com.manas.a2_p2;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button getWeatherBtn;
    TextView resultWeatherText;
    private Spinner citySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citySpinner = findViewById(R.id.citySpinner);
        String url = getUrl(getCityNamefromSpinner());
        resultWeatherText = findViewById(R.id.weather_result);
        getWeatherBtn = findViewById(R.id.get_weather_data_btn);

        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String city = getCityNamefromSpinner();
                String url = getUrl(city);

                Log.e("data", city);
                Log.e("data", url);
                JsonObjectRequest jsonObjectRequest = new
                        JsonObjectRequest(Request.Method.GET, url,
                        new JSONObject(),
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String server_data = response.toString();
                                    JSONObject json_outer_obj = new JSONObject(server_data);
                                    JSONObject currentValue = json_outer_obj.getJSONObject("current");
                                    JSONObject json_inner_current_obj = new JSONObject(currentValue.toString());
                                    String currentTemp = json_inner_current_obj.getString("temp");
                                    String currentPressure = json_inner_current_obj.getString("pressure");
                                    String currentWindSpeed = json_inner_current_obj.getString("wind_speed");
                                    String humidity = json_inner_current_obj.getString("humidity");
                                    String windDirection = json_inner_current_obj.getString("wind_deg");
                                    Double tempInCelcius = Double.valueOf(currentTemp) - 273.15;

                                    String result = "Temperature: " + tempInCelcius + "\n" +
                                            "Pressure: " + currentPressure + "\n" +
                                            "Humidity: " + humidity + "\n" +
                                            "Wind Direction: " + windDirection + "\n" +
                                            "Wind Speed: " + currentWindSpeed;

                                    resultWeatherText.setText(result);
                                    Log.e("data", currentTemp);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error8", error.toString());
                    }
                });
                queue.add(jsonObjectRequest);

            }
        });

    }

    String getCityNamefromSpinner() {
        return String.valueOf(citySpinner.getSelectedItem());
    }

    String getUrl(String city) {
        String url = "";
        switch (city) {
            case "Auckland":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-36.8483&lon=174.7625&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            case "Wellington":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-41.2769&lon=174.7731&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            case "Christchurch":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-43.5320&lon=172.6397&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            case "Hamilton":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-37.7891&lon=175.2597&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            case "Tauranga":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-37.6763&lon=176.2226&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            case "Dunedin":
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-45.8668&lon=170.4911&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
                break;
            default:
                url = "https://api.openweathermap.org/data/2.5/onecall?lat=-36.8483&lon=174.7625&appid=ab57f83f7f35c8c4355d9fb49b2d0f96";
        }

        return url;
    }
}
