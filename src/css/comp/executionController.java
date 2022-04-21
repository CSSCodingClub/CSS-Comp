/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package css.comp;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author justinfleming
 */
public class executionController implements Initializable {

    @FXML
    private TextArea userCode;
    @FXML
    private Button runButton;
    @FXML
    private Button javaButton;
    @FXML
    private Label label;
    
    @FXML
    private ComboBox languageSelect;
    @FXML
    private Button insertCodeButton;
    
    //Create an array of languages
    private final String[] LANGUAGES = {"Python", "Java"};
    
    //Observable list to store in combobox
    private final ObservableList<String> LANGUAGE_LIST = FXCollections.observableArrayList(LANGUAGES);
    
    private final String JAVA_START_CODE = "public class CompetitionCode { \n\n\tpublic static void main(String[] args) {\n\t\t\n\t}\n}";
            
    private final String PYTHON_START_CODE = "I don't know what to put for Python lol";

    @FXML
    private void runCode(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        
        String code = userCode.getText();
    
        String selectedLanguage = (String) languageSelect.getSelectionModel().getSelectedItem();
        
        BufferedReader in = null;
        
        if(selectedLanguage.equalsIgnoreCase("Python"))
        {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("./DB/Testcode.py"), "utf-8"))) {
            writer.write(code);
            }
            Process p = Runtime.getRuntime().exec("python3 ./DB/Testcode.py");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
        }
        else if(selectedLanguage.equalsIgnoreCase("Java"))
        {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("./DB/Testcode.java"), "utf-8"))) {
                writer.write(code);
            }

            Process p = Runtime.getRuntime().exec("java ./DB/Testcode.java");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        }
        
        Stream ret = in.lines();
            Object[] array = ret.toArray();

            for(Object val : array){
                System.out.println(val.toString());
            }
        
        
    }
    
    @FXML
    private void runJava(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        
        String code = userCode.getText();
    
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("./DB/Testcode.java"), "utf-8"))) {
        writer.write(code);
        }
        
        Process p = Runtime.getRuntime().exec("java ./DB/Testcode.java");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        Stream ret = in.lines();
        Object[] array = ret.toArray();
            
        for(Object val : array){
            System.out.println(val.toString());
        }
            
        //String ret = in.readLine();
        //System.out.println("value is : "+ret);
        //label.setText(array[0].toString());
        
        
        
    }
    
    @FXML
    private void insertCode(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        String selectedLanguage = (String) languageSelect.getSelectionModel().getSelectedItem();
        
        
        if(selectedLanguage.equalsIgnoreCase("Python"))
        {
            userCode.setText(PYTHON_START_CODE);
        }
        else if(selectedLanguage.equalsIgnoreCase("Java"))
        {
            userCode.setText(JAVA_START_CODE);
        }
    }
    
  
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set the values in the combobox
        languageSelect.setItems(LANGUAGE_LIST);
         

    }    
    
}
