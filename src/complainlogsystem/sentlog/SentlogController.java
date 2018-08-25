/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.sentlog;

import complainlogsystem.database.DatabaseHandler;
import complainlogsystem.log.LoggedListController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author cosmo
 */
public class SentlogController implements Initializable {

    @FXML
    private TableColumn<log, String> reptbyCOl;
    @FXML
    private TableColumn<log, String> deprtCol;
    @FXML
    private TableColumn<log, String> problmCol;
    @FXML
    private TableColumn<log, String> commentCol;
    @FXML
    private TableColumn<log, String> condtnCol;
    @FXML
    private TableColumn<log, String> personnelDprtCol;
    @FXML
    private TableColumn<log, String> dateTimeCol;
    @FXML
    private TableView<log> tableview;
    
     DatabaseHandler handler = DatabaseHandler.getInstance();
    
    ObservableList<log>list= FXCollections.observableArrayList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        // TODO
        try {
        
            initCol();
            
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(LoggedListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     private void loadData() throws SQLException{
        
        
       String qu = "SELECT * from COMPLAIN";
        ResultSet rs = handler.execQuery(qu);
        
        while(rs.next()){
            String reportedby = rs.getString("reportedBy");
            String rptbydepartment = rs.getString("rptbyDepartment");
            String problem = rs.getString("problem");
           
            String comment = rs.getString("comment");
            String condition = rs.getString("status");
       
            String pdepartment = rs.getString("pDepartment");
           
          
            String dateTime = rs.getString("dateTime");
            
            list.add(new log(reportedby,rptbydepartment,problem,comment,condition,pdepartment,dateTime));
           
            System.out.println(rs);
        }
        tableview.getItems().setAll(list);
    }
    
    private void initCol(){
        reptbyCOl.setCellValueFactory(new PropertyValueFactory<>("reportedby"));
        deprtCol.setCellValueFactory(new PropertyValueFactory<>("rptbydepartment"));
       problmCol.setCellValueFactory(new PropertyValueFactory<>("problem"));
      commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        condtnCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        
     personnelDprtCol.setCellValueFactory(new PropertyValueFactory<>("pdepartment"));
        
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    public static class log{
        private final SimpleStringProperty reportedby;
        private final SimpleStringProperty rptbydepartment ;
        private final SimpleStringProperty problem;
        private final SimpleStringProperty comment;
        private final SimpleStringProperty condition;
        
        private final SimpleStringProperty pdepartment;
       
        private final SimpleStringProperty date;
        
        log(String reportedby, String rptbydepartment, String problem, String comment, String condition, String pdepartment,  String date){
           this.reportedby = new SimpleStringProperty(reportedby);
           this.rptbydepartment = new SimpleStringProperty(rptbydepartment);
           this.problem = new SimpleStringProperty(problem);
           this.comment = new SimpleStringProperty(comment);
          this.condition = new SimpleStringProperty(condition);
         
           this.pdepartment = new SimpleStringProperty(pdepartment);
           
            this.date = new SimpleStringProperty(date);
        }

        public String getReportedby() {
            return reportedby.get();
        }

        public String getRptbydepartment() {
            return rptbydepartment.get();
        }

        public String getProblem() {
            return problem.get();
        }

        public String getComment() {
            return comment.get();
        }

        public String getCondition() {
            return condition.get();
        }


        public String getPdepartment() {
            return pdepartment.get();
        }


        public String getDate() {
            return date.get();
        }

      
        
        
    }   
    
}
