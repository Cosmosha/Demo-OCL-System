/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import complainlogsystem.database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import util.IPAddressing;
import util.Utility;

/**
 * FXML Controller class
 *
 * @author cosmos hagan
 */
public class IpAdressFormController implements Initializable {

    @FXML
    private JFXButton insertButton;
    @FXML
    private JFXTextField ipAddressTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
           
    }    

     private void myConnection(){
        if (DatabaseHandler.connectionStatus==true){
            ipAddressTxt.setDisable(true);
            insertButton.setDisable(true);
        }else{
            JOptionPane.showMessageDialog(null,"Set Database Server IP Address");
          ipAddressTxt.setDisable(false);
            insertButton.setDisable(false);
        }
    }
    
    @FXML
    private void IpAdressButton(ActionEvent event) {
        
      vildate();
        
        Utility.getConnection(ipAddressTxt.getText().trim());
        myConnection();
        new IPAddressing().saveAddress(ipAddressTxt.getText().trim());
    }
    
    
    private void vildate(){
          Boolean flag = (ipAddressTxt.getText().isEmpty());
        if(flag){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please Enter an  IP Address");
                alert.showAndWait();
        }
    }
    
}
