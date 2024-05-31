package atc_project;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;


class weatherData{
	Double windSpeed;
	Double temperature;
	Long humidity;
	String weather;
	void setWindSpeed(Double windSpeed) {
		
	}
	public weatherData(Double windSpeed,Double temperature,long relativeHumidity,String weather) {
		this.windSpeed = windSpeed;
		this.temperature = temperature;
		this.humidity = relativeHumidity;
		this.weather = weather;
	}
	
}


public class weatherAPI {
	

	public weatherData weatherCalc(String city) {
		try{
        	//String city;
        	//city = "Mumbai";

           // Get location data
            JSONObject cityLocationData = (JSONObject) getLocationData(city);
            double latitude = (double) cityLocationData.get("latitude");
            double longitude = (double) cityLocationData.get("longitude");
            weatherData data =displayWeatherData(latitude, longitude);
            return data;
       

    }catch(Exception e){
        e.printStackTrace();
    }
	return null;
}

public static JSONObject getLocationData(String city){
    city = city.replaceAll(" ", "+");

    String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
            city + "&count=1&language=en&format=json";

    try{
    	HttpURLConnection conn = null;
        try{
          
          URL url = new URL(urlString);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
        
       }catch(IOException e){
          e.printStackTrace();
      }
       HttpURLConnection apiConnection = conn;
        // check for response status
        // 200 - means that the connection was a success
        if(apiConnection.getResponseCode() != 200){
            System.out.println("Error: Could not connect to API");
            return null;
        }

        
        String jsonResponse = readApiResponse(apiConnection);

        
        JSONParser parser = new JSONParser();
        JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);
        JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
        return (JSONObject) locationData.get(0);

    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
	}



	public static weatherData displayWeatherData(double latitude, double longitude){
        try{
            
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code";
            
            HttpURLConnection conn = null;
            try{
              // attempt to create connection
              URL url = new URL(urlString);
               conn = (HttpURLConnection) url.openConnection();
  
              // set request method to get
              conn.setRequestMethod("GET");
              
           }catch(IOException e){
              e.printStackTrace();
          }
  
            HttpURLConnection apiConnection = conn;
          

            // check for response status
            // 200 - means that the connection was a success
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            
            String jsonResponse = readApiResponse(apiConnection);

            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
            String time = (String) currentWeatherJson.get("time");
            //System.out.println("Current Time: " + time);

            double temperature = (double) currentWeatherJson.get("temperature_2m");
            //System.out.println("Current Temperature (C): " + temperature);

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            //System.out.println("Relative Humidity: " + relativeHumidity);

            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            //System.out.println("Wind Speed: " + windSpeed);
            
            Long weathercode = (Long) currentWeatherJson.get("weather_code");
           // System.out.println("Weather Code: " + weathercode);
            
            Map<Long,String> map =  new HashMap<>();
            
            map.put((long) 0,"Clear Sky");    
            map.put((long) 1, "Mainly Clear");
            map.put((long) 2, "Partly Cloudy");
            map.put((long) 3, "Overcast");
            map.put((long) 45, "Fog");
            map.put((long) 48, "Depositing rime Fog");   
            map.put((long) 51, "Drizzle");
            map.put((long) 53, "Moderate Drizzle");
            map.put((long) 55, "Dense Drizzle");
            map.put((long) 56, "Freezing Drizzle: Light");
            map.put((long) 57, "Freezing Drizzle: Intense");
            map.put((long) 61, "Rain: Slight");
            map.put((long) 63, "Rain: Moderate");
            map.put((long) 65, "Rain:Heavy");
            map.put((long) 66, "Freezing Rain: Light");
            map.put((long) 67, "Freezing Rain: Heavy");
            map.put((long) 71, "Snow fall: Slight");
            map.put((long) 73, "Snow fall: Moderate");
            map.put((long) 75, "Snow fall: Heavy");
            map.put((long) 77, "Snow grains");
            map.put((long) 80, "Rain showers: Slight");
            map.put((long) 81, "Rain showers: Moderate");
            map.put((long) 82, "Rain showers: Violent");
            map.put((long) 85, "Snow showers slight");
            map.put((long) 86, "Snow showers Heavy");
            map.put((long) 95, "Thunderstorm: Slight or Moderate");
            map.put((long) 96, "Thunderstorm with Slight Hail");
            map.put((long) 99, "Thunderstorm with Heavy Hail");

            
          String weather = map.get(weathercode);
          //System.out.println("Weather: " + weather);    
          weatherData weatherDataValues =  new weatherData(windSpeed,temperature,relativeHumidity,weather);
      	  return weatherDataValues;
      	  
        }catch(Exception e){
            e.printStackTrace();
        }
		return null;
    }
	
	public static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            
            StringBuilder resultJson = new StringBuilder();

            
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            
            while (scanner.hasNext()) {
                // Read and append the current line to the StringBuilder
                resultJson.append(scanner.nextLine());
            }

            
            scanner.close();

            // Return the JSON data as a String
            return resultJson.toString();

        } catch (IOException e) {
            // Print the exception details in case of an IOException
            e.printStackTrace();
        }

        // Return null if there was an issue reading the response
        return null;
    }


	
}
