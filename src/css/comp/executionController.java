/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package css.comp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Label label;
    
    
    @FXML
    private void runcode(ActionEvent event) throws MalformedURLException, ProtocolException, IOException {
        
        String code = userCode.getText();
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("./DB/Testcode.py"), "utf-8"))) {
        writer.write(code);
}
       
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
