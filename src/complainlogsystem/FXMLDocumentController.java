/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import complainlogsystem.database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXPasswordField passwordTxtBox;
    @FXML
    private JFXTextField usernameTxtBox;
    
      DatabaseHandler handler = DatabaseHandler.getInstance();

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    
    @FXML
    private void loginButton(ActionEvent event) throws SQLException {
        String pass = passwordTxtBox.getText();
         String qu = "SELECT password FROM HOD WHERE password = '"+pass+"'";
         ResultSet rs = handler.execQuery(qu);
         System.out.println(rs);
      
         
        
            if (rs.next()){
                String sPass = rs.getString("password");
                if (pass.contentEquals(sPass)){
                    try {
                         
                        NotificationType nt = NotificationType.SUCCESS;
           
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("System Login");
                        tray.setMessage("SUCCESSFUL");
                        tray.showAndDismiss(Duration.millis(600));
                        tray.setNotificationType(nt);
                
                        ((Node)event.getSource()).getScene().getWindow().hide();
                        loadWindow("/complainlogsystem/main/main.fxml", "Main");
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
               }
                return;
            }
            
             Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Wrong Password");
                alert.showAndWait();
                passwordTxtBox.setText("");
                
               NotificationType nt = NotificationType.ERROR;
           
               TrayNotification tray = new TrayNotification();
               tray.setTitle("System Login");
               tray.setMessage("FAILED");
               tray.showAndDismiss(Duration.millis(500));
               tray.setNotificationType(nt);
    }
    
     
     void loadWindow(String loc, String title) throws IOException{
        
        Parent parent = FXMLLoader.load(getClass().getResource(loc));
        Stage stage = new Stage(StageStyle.DECORATED);
        Image icon = new Image(getClass().getResourceAsStream("/complainlogsystem/images/hand.png"));
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
        
        
    }
   
    
}
