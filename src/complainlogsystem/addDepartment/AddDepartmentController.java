 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.addDepartment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import complainlogsystem.database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class AddDepartmentController implements Initializable {

    @FXML
    private JFXTextField DeprtID;
    @FXML
    private JFXTextField DeprtName;
    @FXML
    private JFXTextField Location;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;
    
     DatabaseHandler handler = DatabaseHandler.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        
    }    

    
    
    
    @FXML
    private void addButton(ActionEvent event) {
              actionAdd();
            
    }

    @FXML
    private void cancelButton(ActionEvent event) {
      clearAction();
    }
    
    void actionAdd(){
     boolean isDeprtIdEmpty = fieldValidation.validation.isTextfieldNotEmpty(DeprtID, "Department is Required");
     boolean isDeprtNameEmpty = fieldValidation.validation.isTextfieldNotEmpty(DeprtName, "Department Name is Required");
     boolean isLocationEmpty = fieldValidation.validation.isTextfieldNotEmpty(Location, "Location is Required");
     
     if(isDeprtIdEmpty && isDeprtNameEmpty && isLocationEmpty){
         
     
        String DepartmentID = DeprtID.getText();
        String DepartmentName = DeprtName.getText();
        String DeprtLocation = Location.getText();
        
      Boolean flag = (DepartmentID.isEmpty()|| DepartmentName.isEmpty() || DeprtLocation.isEmpty());
       if(flag){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setHeaderText(null);
          alert.setContentText("Please Enter in all fields");
          alert.showAndWait();
          return;
      }
      String  st= "INSERT INTO department VALUES ("+
              "'" +DepartmentID+"'," +
              "'" +DepartmentName+ "'," +
              "'" +DeprtLocation+ "'" +
              ")";
      
      System.out.println(st);
      if(handler.execAction(st))
      {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setHeaderText(null);
          alert.setContentText("Saved");
          alert.showAndWait();
      }else // Error
      {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setHeaderText(null);
          alert.setContentText("Failed");
          alert.showAndWait();
        
      }
       clearAction();
     
    }
     
    }
    
    void clearAction(){
        DeprtID.setText(null);
        DeprtName.setText(null);
        Location.setText(null);
    }
    
  
   
    
}
