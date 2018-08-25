/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.complainForm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import complainlogsystem.database.DatabaseHandler;
import fieldValidation.validation;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import util.IPAddressing;
import util.Utility;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class ComplainFormController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private Pane hsider;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXComboBox<String> problemCb;
    @FXML
    private JFXTextArea commentTxt;
    @FXML
    private JFXComboBox<String> departmentCb;
    @FXML
    private JFXRadioButton now;
    @FXML
    private ToggleGroup group1;
    @FXML
    private JFXRadioButton vital;
    @FXML
    private JFXRadioButton critical;
    @FXML
    private JFXTextField reportedByTxt;
    @FXML
    private JFXComboBox<String> complaintDeprtCb;
    @FXML
    private JFXButton sendButton;
    @FXML
    private JFXButton cancelButton;
    
     ObservableList<String>prolist = FXCollections.observableArrayList("Power Off", "Electrical Problem","File Not Sent", "Internet Connection");
    ObservableList<String>list = FXCollections.observableArrayList();
   
        String message;
        String personnel= null ;
    
        DatabaseHandler handler;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           Utility.getConnection(new IPAddressing().getAddress());
           if(Utility.connectionStatus==false){
             loadWindows("/complainlogsystem/main/ipAdressForm.fxml", "IP Address");
           }
          
           
           handler = DatabaseHandler.getInstance();
         menu();
        RadioButton();
      
        try {
            clearAction();
            
            combo();
            
        } catch (SQLException ex) {
            Logger.getLogger(ComplainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void sendButton(ActionEvent event) {
        sendAction();
    }

     void sendAction(){
         
     boolean isProblmCbEmpty = validation.isComboBoxEmpty(problemCb, "problem Required");
     boolean isReportedByEmpty = validation.isTextfieldNotEmpty(reportedByTxt, "ReportedBy is Required");
     boolean isDeprtCbEmpty = validation.isComboBoxEmpty(departmentCb, "Department is Required");
     boolean isComplaintCbEmpty = validation.isComboBoxEmpty(complaintDeprtCb, "Complaint Department Required");
     boolean isCommentEmpty = validation.isAreaTextNotEmpty(commentTxt, "Comment is Required");
     
  if(isProblmCbEmpty && isReportedByEmpty && isDeprtCbEmpty && isComplaintCbEmpty && isCommentEmpty){}
           
         String problmCb = problemCb.getValue();
        String comment = commentTxt.getText();
        String deprtCb = departmentCb.getValue();
        String rButton = group1.getSelectedToggle().getUserData().toString();
        String reportedBy = reportedByTxt.getText();
        String complaintCb = complaintDeprtCb.getValue();
        
        
      Boolean flag = (problmCb.isEmpty()|| comment.isEmpty()|| rButton.isEmpty() || deprtCb.isEmpty() || reportedBy.isEmpty() || complaintCb.isEmpty());
      
      if(flag){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setHeaderText(null);
          alert.setContentText("Please Enter in all fields");
          alert.showAndWait();
          return;
      }
      
      smsApi();
       
      String  qu= "INSERT INTO COMPLAIN (reportedBy,rptbyDepartment,problem,comment,status,personnel,pDepartment,Txtmessage) VALUES ("+
              "'" +reportedBy+"'," +
              "'" +complaintCb+ "'," +
              "'" +problmCb+ "'," +
              "'" +comment+ "'," +
              "'" +rButton+ "'," +
              "'" +personnel+ "'," +
              "'" +deprtCb+ "'," +
             
              "'" +message+ "'" +
              ")";
      
      System.out.println(qu);
      if(handler.execAction(qu))
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
    
  
    @FXML
    private void clearButton(ActionEvent event) {
        clearAction();
    }
   
    void menu(){
     
        try {
            VBox box = FXMLLoader.load(getClass().getResource("sider.fxml"));
            drawer.setSidePane(box);
            
            for (Node node : box.getChildren()){
                if(node.getAccessibleText()!= null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                        switch(node.getAccessibleText()){
                            case "sentLog" : 
                        {
                            loadWindow("/complainlogsystem/sentlog/sentlog.fxml", "Complain Form");
                        }
                            break;
                            case "Login":
                        {
                            node.getScene().getWindow().hide();
                            loadWindow("/complainlogsystem/loginSystem.fxml", "Complain Form");
                        }
                                
                            break;
                            case "Exit" : 
                            System.exit(0);
                        }
                    });
                }
            }
            
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask2.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                burgerTask2.setRate(burgerTask2.getRate()* -1);
                burgerTask2.play();
                
                if(drawer.isShown()){
                    drawer.close();
                }else{
                    drawer.open();
                }
            });     } catch (IOException ex) {
            Logger.getLogger(ComplainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void loadWindow(String loc, String title) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            Image icon = new Image(getClass().getResourceAsStream("/complainlogsystem/images/hand.png"));
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ComplainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     void loadWindows(String loc, String title) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            Image icon = new Image(getClass().getResourceAsStream("/complainlogsystem/images/hand.png"));
            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ComplainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void combo()throws SQLException{
       getDepartment();
      problemCb.setItems(prolist);
    }
     
    void getDepartment()throws SQLException{
        String qu = "SELECT deprt_Name FROM DEPARTMENT";
         ResultSet rs = handler.execQuery(qu);
        
        while (rs.next()){
            String dprt = rs.getString("deprt_Name");
          
            list.add(dprt);
            
        }
        departmentCb.setItems(list);
        complaintDeprtCb.setItems(list);
        
        
    }
    
    void RadioButton(){

        now.setToggleGroup(group1);
        now.setUserData("now");
        vital.setToggleGroup(group1);
        vital.setUserData("vital");
        critical.setToggleGroup(group1);
        critical.setUserData("critical");
        
        now.setSelectedColor(Color.RED);
        vital.setSelectedColor(Color.YELLOWGREEN);
        critical.setSelectedColor(Color.GREEN);
    }
    
      
    void smsApi(){
        try {
            String qu = "SELECT name FROM HOD WHERE department = '"+departmentCb.getValue()+"'";
            ResultSet rs = handler.execQuery(qu);
            
            if (rs.next())
            {
                personnel  = rs.getString("name");
                
            }
            System.out.println(personnel);
            
            String nu = "SELECT phone FROM HOD WHERE name = '"+personnel+"'";
            ResultSet ph = handler.execQuery(nu);
            String numb= null ;
            if (ph.next())
            {
                numb  = ph.getString("phone");
                
            }
            System.out.println(numb); 
            
            
            try {
                String recipient = "+233"+numb+"";
                message = "Hello "+personnel+", Please your service is need at "+complaintDeprtCb.getValue()+". Reported By "+reportedByTxt.getText()+"";
                String username = "cosmosha";
                String password = "costic";
                String sender = "OCL System";
                String requestUrl  = "http://121.241.242.114:8080/sendsms?" +
                        "username=" + URLEncoder.encode(username, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8") +
                        "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
                        "&messagetype=SMS:TEXT" +
                        "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
                        "&originator=" + URLEncoder.encode(sender, "UTF-8") +
                        "&serviceprovider=GSMModem1" +
                        "&responseformat=html";
                URL url = new URL(requestUrl);
                HttpURLConnection uc = (HttpURLConnection)url.openConnection();
                System.out.println(uc.getResponseMessage());
                uc.disconnect();
            } catch(Exception ex) {
                System.out.println(ex.getMessage()
                        
                );
                String get = ex.getMessage();
                System.out.println(get);
                
                if("OK".equals(get))
                {
                    NotificationType nt = NotificationType.SUCCESS;
                    
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("SMS DELIVERY SUCCESSFUL");
                    tray.setMessage("Complain SUCCESSFUL sent to "+departmentCb.getValue()+" Department");
                    tray.showAndDismiss(Duration.millis(800));
                    tray.setNotificationType(nt);
                }
                else
                {
                    NotificationType nt = NotificationType.ERROR;
                    
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("SMS  DELIVERY FAILED");
                    tray.setMessage("Check Internet Connection to sent SMS..");
                    tray.showAndDismiss(Duration.millis(800));
                    tray.setNotificationType(nt);
                    
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(ComplainFormController.class.getName()).log(Level.SEVERE, null, ex);
  }
 }
    
   
    void clearAction(){
      problemCb.setValue("");
       commentTxt.setText("");
        departmentCb.setValue("");
      
      reportedByTxt.setText("");
    complaintDeprtCb.setValue("");
         
    
}
        
  }   
