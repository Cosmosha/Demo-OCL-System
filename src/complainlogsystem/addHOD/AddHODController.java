/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.addHOD;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import complainlogsystem.database.DatabaseHandler;
import fieldValidation.validation;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class AddHODController implements Initializable {

    @FXML
    private JFXTextField staffIDTxt;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXComboBox<String> DeprtCbx;
    @FXML
    private JFXComboBox<String> DesignCbx;
    @FXML
    private JFXTextField userNameTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXTextField phoneTxt;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton clearButton;

    DatabaseHandler handler = DatabaseHandler.getInstance();
    ObservableList<String>list = FXCollections.observableArrayList();
      ObservableList<String>DesignCbxlist = FXCollections.observableArrayList("Head Of Department", "Personnel");
    
    
    /**
     * 
     * Initializes the controller class.
     */
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        try {
            combo();
          
        } catch (SQLException ex) {
            Logger.getLogger(AddHODController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void combo()throws SQLException{
       getDepartment();
       DesignCbx.setItems(DesignCbxlist);
    }

    @FXML
    private void AddButton(ActionEvent event) {
        addAction();
    }

    @FXML
    private void ClearButton(ActionEvent event) {
        clearAction(); 
    }
    
    void clearAction(){
        staffIDTxt.setText(null);
        nameTxt.setText(null);
        DeprtCbx.setValue("");
        DesignCbx.setValue("");
        userNameTxt.setText(null);
        passwordTxt.setText(null);
         phoneTxt.setText(null);
    }
    
    void addAction(){
      boolean isIDEmpty = validation.isTextfieldNotEmpty(staffIDTxt,"ID is Required..");
      boolean isNameEmpty = validation.isTextfieldNotEmpty(nameTxt, "Name is Required..");
      boolean isUserNameEmpty = validation.isTextfieldNotEmpty(userNameTxt, "Username is Required..");
      boolean isPasswordEmpty = validation.isPasswordFieldNotEmpty(passwordTxt, "Name is Required..");
      boolean isPhoneTpypeNumb = validation.textFieldTypeNumber(phoneTxt, "Phone must be number");
      boolean isDepartCombEmpty = validation.isComboBoxEmpty(DeprtCbx, "Select Department");
      boolean isDesignCombEmpty = validation.isComboBoxEmpty(DesignCbx, "Select Deisgnation");
      
      if(isIDEmpty && isNameEmpty && isUserNameEmpty && isDepartCombEmpty && isDesignCombEmpty && isPasswordEmpty && isPhoneTpypeNumb){
      
        String sID = staffIDTxt.getText();
        String name = nameTxt.getText();
        String dprt = DeprtCbx.getValue();
        String designation = DesignCbx.getValue();
        String username = userNameTxt.getText();
        String password = passwordTxt.getText();
        String phone = phoneTxt.getText();
        
        
      Boolean flag = (sID.isEmpty()|| name.isEmpty() || dprt.isEmpty() || designation.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty());
       if(flag){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setHeaderText(null);
          alert.setContentText("Please Enter in all fields");
          alert.showAndWait();
          return;
      }
       
      String  st= "INSERT INTO HOD VALUES ("+
              "'" +sID+"'," +
              "'" +name+ "'," +
              "'" +dprt+ "'," +
              "'" +designation+ "'," +
              "'" +username+ "'," +
              "'" +password+ "'," +
              "'" +phone+ "'" +
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
    
    
    void getDepartment()throws SQLException{
        String qu = "SELECT deprt_Name FROM DEPARTMENT";
          DatabaseHandler handler = DatabaseHandler.getInstance();
        
        ResultSet rs = handler.execQuery(qu);
        
        while (rs.next()){
            String dprt = rs.getString("deprt_Name");
          
            list.add(dprt);
            
        }
        DeprtCbx.setItems(list);
        
    }
    
}
