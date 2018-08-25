 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.main;

import complainlogsystem.database.DatabaseHandler;
import fieldValidation.validation;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class MainController implements Initializable {
    
    ObservableList<Dprt> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Dprt> tableview;
    @FXML
    private TableColumn<Dprt, String> dprtIDCol;
    @FXML
    private TableColumn<Dprt, String> dprtNameCol;
    @FXML
    private TableColumn<Dprt, String> locationCol;
    @FXML
    private TextField searchTxt;
    @FXML
    private Button searchButton;
    @FXML
    private TextField membTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private ComboBox<String> DepartCb;
    @FXML
    private ComboBox<String> statusCb;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addHODButton;
    @FXML
    private ImageView addHoDButton;
    @FXML
    private Button addDeprtButton;
    @FXML
    private Button HODListButton;
    @FXML
    private Button LoggedListButton;
    @FXML
    private Button ComplainFormButton;

  DatabaseHandler handler = DatabaseHandler.getInstance(); 
  
    ObservableList<String>Statuslist = FXCollections.observableArrayList("Head Of Department", "Personnel");
    ObservableList<String>lists = FXCollections.observableArrayList();
    @FXML
    private MenuItem ipAddress;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initCol();
        
        try {
            // TODO
            loadData();
            statusCb.setItems(Statuslist);
            getDepartment();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    
    void getDepartment()throws SQLException{
        String qu = "SELECT deprt_Name FROM DEPARTMENT";
         ResultSet rs = handler.execQuery(qu);
        
        while (rs.next()){
            String dprt = rs.getString("deprt_Name");
          
            lists.add(dprt);
            
        }
        DepartCb.setItems(lists);
     
        
        
    }
    
    private void loadData()throws SQLException{
    
        String qu = "SELECT * FROM DEPARTMENT";
        ResultSet rs = handler.execQuery(qu);
        
        while (rs.next()){
            String dID = rs.getString("id");
            String name = rs.getString("deprt_Name");
            String location = rs.getString("location");
            
            list.add(new Dprt(dID, name, location));
            
            
        }
        tableview.getItems().setAll(list);
    }
    
    private void initCol(){
        dprtIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dprtNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    @FXML
    private void ipAddress(ActionEvent event) {
         loadWindow("/complainlogsystem/main/ipAdressForm.fxml", "IP Address");
    }
    
    
   public static class Dprt{
        private final SimpleStringProperty id;
         private final SimpleStringProperty name;
        private final SimpleStringProperty location;
       
        Dprt(String id, String name, String location){
            this.id = new SimpleStringProperty(id);
           this.name = new SimpleStringProperty(name);
           this.location = new SimpleStringProperty(location);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getLocation() {
            return location.get();
        }
        
        
    }

    @FXML
    private void addHOD(ActionEvent event) throws IOException {
        loadWindow("/complainlogsystem/addHOD/addHOD.fxml", "Add Head Of Department");
        
    }

    @FXML
    private void addDepartment(ActionEvent event) throws IOException {
          loadWindow("/complainlogsystem/addDepartment/addDepartment.fxml", "Add Department");
    }

    @FXML
    private void addHODList(ActionEvent event) throws IOException {
          loadWindow("/complainlogsystem/HODList/HODList.fxml", "Head Of Department List");
    }

    @FXML
    private void LoggedList(ActionEvent event) throws IOException {
          loadWindow("/complainlogsystem/log/loggedList.fxml", "Logged List");
    }

    @FXML
    private void complainForm(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
         getWindow("/complainlogsystem/complainForm/complainForm.fxml", "Complain Form");
    }

    @FXML
    private void updateButton(ActionEvent event) {
         updateAction();
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearAction();
    }
    
    
    void clearAction(){
        membTxt.setText("");
        nameTxt.setText(null);
        DepartCb.setValue(null);
        statusCb.setValue(null);
        usernameTxt.setText("");
        passwordTxt.setText("");
         phoneTxt.setText("");
    }
    
    
    void loadWindow(String loc, String title) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
      void getWindow(String loc, String title) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            Image icon = new Image(getClass().getResourceAsStream("/complainlogsystem/images/hand.png"));
        stage.getIcons().add(icon);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
   
     
     void search(){
         String id = searchTxt.getText();
         String qu = "SELECT * FROM HOD WHERE id = '"+id+"'";
         ResultSet rs = handler.execQuery(qu);
         System.out.println(rs);
         
         Boolean flag = false;
         
        try {
            while (rs.next())
            {
                String Smemb = rs.getString("id");
                String Sname = rs.getString("name");
                String Sdepartment = rs.getString("department");
                String Sdesignation= rs.getString("designation");
                String Susername = rs.getString("username");
                String Spassword = rs.getString("password");
                String Sphone = rs.getString("phone");
              
                
                 membTxt.setText(Smemb);
                 nameTxt.setText(Sname);
                 DepartCb.setValue(Sdepartment);
                 statusCb.setValue(Sdesignation);
                usernameTxt.setText(Susername);
                passwordTxt.setText(Spassword);
                 phoneTxt.setText(Sphone);
                 
                 flag = true;
                
            } 
             if(!flag){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please Enter a Valid Member ID");
                alert.showAndWait();
          
             }
            
           
             } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
     }
     
     void updateAction(){
      boolean isIDEmpty = validation.isTextfieldEmpty(membTxt,"ID is Required..");
      boolean isNameEmpty = validation.isTextfieldEmpty(nameTxt, "Name is Required..");
      boolean isUserNameEmpty = validation.isTextfieldEmpty(usernameTxt, "Username is Required..");
      boolean isPasswordEmpty = validation.isTextfieldEmpty(passwordTxt, "Password is Required..");
      boolean isPhoneTpypeNumb = validation.textFieldNumber(phoneTxt, "Phone must be number");
     boolean isDprtEmpty = validation.isComboBoxEmpty(DepartCb, "Department is Required");
      boolean isDesignationEmpty = validation.isComboBoxEmpty(statusCb, "Designation is Required");
      
      
      if(isIDEmpty && isNameEmpty && isUserNameEmpty && isPasswordEmpty && isDesignationEmpty && isDprtEmpty && isPhoneTpypeNumb){  
      }
      
        String sID = membTxt.getText();
        String name = nameTxt.getText();
        String dprt = DepartCb.getValue();
        String designation =  statusCb.getValue();
        String username = usernameTxt.getText();
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
       
       
       String st = "UPDATE HOD SET `name`= '" +nameTxt.getText()+ "',`department`= '" +DepartCb.getValue()+ "',`designation`= '" +statusCb.getValue()+ "',`username`= '" +usernameTxt.getText()+ "',`password`= '" +passwordTxt.getText()+ "',`phone`= '" +phoneTxt.getText()+ "' WHERE 'id' = '"+sID+"' ";
      
      System.out.println(st);
      if(handler.execAction(st))
      {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setHeaderText(null);
          alert.setContentText("INFO Updated");
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

    @FXML
    private void searchButton(ActionEvent event) {
        search();
    }
    
    
    
   
   
}   
