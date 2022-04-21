/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package css.comp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * FXML Controller class
 *
 * @author redno
 */
public class FXMLResetPasswordController implements Initializable {

    @FXML
    private Label labelSecQues;
    @FXML
    private Label labelUserFound;
    @FXML
    private Label labelCorrectAnswer;
    @FXML
    private Label labelPasswordUpdated;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldAnswer;
    @FXML
    private PasswordField textFieldNewPassword;
    
    
    private ArrayList<String> securityQuestionsList = new ArrayList();
    
    @FXML
    private void handleFindUser(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        URL url = new URL("https://thk34xed3a.execute-api.us-east-2.amazonaws.com/DEV/get-question");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");
        
        String data = "{\n    \"user\": \"" + textFieldUsername.getText() + "\"\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        
        InputStream inputStream = http.getInputStream();
        InputStreamReader inputStreamReader;
        BufferedReader reader = null;
        String jsonResult = "";

        try
        {
            
            // Call the url and create a Buffered Reader on the result.
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
                
            // Keep reading lines while more still exist.
            // Append the result to a String.  Should use a StringBuilder if we
            // are expecting a lot of lines.
            while (line != null) {
                jsonResult += line;
                line = reader.readLine();
            }
        }
        catch (MalformedURLException malformedURLException) {
            // URL was bad.
            jsonResult = malformedURLException.getMessage();
        }
        catch (IOException ioException) {
            // An error occurred during the reading process.
            jsonResult = ioException.getMessage();
        }
        finally
        {
            // Close the reader and the underlying objects.
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException ioException) {
                jsonResult += ioException.getMessage();
            }
        }
        
        //Convert the json result to just the number
        String result = jsonResult.substring(2, 3);
        
        int questionIndex;
        try{
            //Convert result to an integer
            questionIndex = Integer.parseInt(result);
            
            //Set Label color to green and update text
            labelUserFound.setTextFill(Color.web("#00FF00"));
            labelUserFound.setText("User Found!");
            
            //Set label for security question
            labelSecQues.setText(securityQuestionsList.get(questionIndex));
        }
        catch(NumberFormatException ex)
        {
            //Set security question label to empty
            labelSecQues.setText("");
            
            //Set Label color to red and update text
            labelUserFound.setTextFill(Color.web("#FF0000"));
            labelUserFound.setText("User Not Found");
        }
        
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
    
    @FXML
    private void handleCheckAnswer(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        URL url = new URL("https://thk34xed3a.execute-api.us-east-2.amazonaws.com/DEV/validate-password-reset");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");
        
        String data = "{\n    \"user\": \"" + textFieldUsername.getText() + "\",\n    \"answer\": \"" + textFieldAnswer.getText() + "\"\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        
        InputStream inputStream = http.getInputStream();
        InputStreamReader inputStreamReader;
        BufferedReader reader = null;
        String jsonResult = "";

        try
        {
            // Call the url and create a Buffered Reader on the result.
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
                
            // Keep reading lines while more still exist.
            // Append the result to a String.  Should use a StringBuilder if we
            // are expecting a lot of lines.
            while (line != null) {
                jsonResult += line;
                line = reader.readLine();
            }
        }
        catch (MalformedURLException malformedURLException) {
            // URL was bad.
            jsonResult = malformedURLException.getMessage();
        }
        catch (IOException ioException) {
            // An error occurred during the reading process.
            jsonResult = ioException.getMessage();
        }
        finally
        {
            // Close the reader and the underlying objects.
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException ioException) {
                jsonResult += ioException.getMessage();
            }
        }
        
           System.out.println(jsonResult);
        //Convert the json result to just the text
        String result = jsonResult.substring(1, (jsonResult.length()-1));
        
        System.out.println(result);
        
        if(result.equals("VALID"))
        {
            //Make new password field editable
            textFieldNewPassword.setEditable(true);
            
            //Set Label color to green and update text
            labelCorrectAnswer.setTextFill(Color.web("#00FF00"));
            labelCorrectAnswer.setText("Correct Answer! Please input new password.");
        }
        else
        {
            //Set Label color to red and udpate text
            labelCorrectAnswer.setTextFill(Color.web("#FF0000"));
            labelCorrectAnswer.setText("Incorrect Answer... Please try again.");
        }
        
        //Convert result to an integer
        //int questionIndex = Integer.parseInt(result);
        
        //Set label for security question
        //labelSecQues.setText(securityQuestionsList.get(questionIndex));
        
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
    
    @FXML
    private void handleNewPassword(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        URL url = new URL("https://thk34xed3a.execute-api.us-east-2.amazonaws.com/DEV/reset-password");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");
        
        String data = "{\n    \"user\": \"" + textFieldUsername.getText() + "\",\n    \"pass\": \"" + textFieldNewPassword.getText() + "\"\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        
        InputStream inputStream = http.getInputStream();
        InputStreamReader inputStreamReader;
        BufferedReader reader = null;
        String jsonResult = "";

        try
        {
            
            // Call the url and create a Buffered Reader on the result.
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
                
            // Keep reading lines while more still exist.
            // Append the result to a String.  Should use a StringBuilder if we
            // are expecting a lot of lines.
            while (line != null) {
                jsonResult += line;
                line = reader.readLine();
            }
        }
        catch (MalformedURLException malformedURLException) {
            // URL was bad.
            jsonResult = malformedURLException.getMessage();
        }
        catch (IOException ioException) {
            // An error occurred during the reading process.
            jsonResult = ioException.getMessage();
        }
        finally
        {
            // Close the reader and the underlying objects.
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException ioException) {
                jsonResult += ioException.getMessage();
            }
        }
        
        //Convert the json result to just the text
        String result = jsonResult.substring(1, (jsonResult.length()-1));
        
        if(result.equals("PASSWORD CHANGED"))
        {
            //Set Label color to green and update text
            labelPasswordUpdated.setTextFill(Color.web("#00FF00"));
            labelPasswordUpdated.setText("Password Successfully Updated!");
        }
        else
        {
            //Set Label color to red and update text
            labelPasswordUpdated.setTextFill(Color.web("#FF0000"));
            labelPasswordUpdated.setText("An error occured... Please try again.");
        }
        
        
        
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
    
    @FXML
    private void handleReturn(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("css/comp/FXMLDocument.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 600, 450));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        securityQuestionsList.add("In what city were you born?");
        securityQuestionsList.add("What is your mother's maiden name?");
        securityQuestionsList.add("What high school did you attend?");
        securityQuestionsList.add("What is the name of your favorite pet?");
        securityQuestionsList.add("What is the name of your first school?");
        securityQuestionsList.add("What was the make of your first car?");
        securityQuestionsList.add("What was your favorite food as a child?");
    }    
    
}
