package com.example.covid_personlimiter.data;

import android.util.Log;

import com.example.covid_personlimiter.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        HttpURLConnection urlConnection = null;

        try {
            Log.d("RESPONSE", "HOLAAAAA");
            URL mUrl = new URL( "https://so-unlam.net.ar/api/api/login");
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            Log.d("RESPONSE", "ASDDDDDDDDDD");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            Log.d("RESPONSE", "WAAAAAAAAAA");
            urlConnection.connect();
            Log.d("RESPONSE", "xd");
            int responseCode = urlConnection.getResponseCode();
            Log.d("RESPONSE", "xddd");
            urlConnection.disconnect();
            Log.d("RESPONSE", "xdddddd");
            if (responseCode != HttpURLConnection.HTTP_OK)
                return new Result.Error(new Exception("Error logging in"));

            InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("RESPONSE", "> " + line);   //here u ll get whole response...... :-)

            }

            String res = buffer.toString();
            Log.d("RESPONSE", "> " + res);
            // TODO: handle loggedInUser authentication
            LoggedInUser loggedUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe",
                            "xd",
                            "xd2");
            return new Result.Success<>(loggedUser);
        } catch (Exception e) {
            Log.d("RESPONSE", e.toString());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void refresh_token() {
        // TODO: revoke authentication
    }

    public void logout() {
        // TODO: revoke authentication
    }
}