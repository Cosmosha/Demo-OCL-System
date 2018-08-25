/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complainlogsystem.HODList;

import complainlogsystem.database.DatabaseHandler;
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
public class HODListController implements Initializable {

    @FXML
    private TableColumn<HOD, String> memCol;
    @FXML
    private TableColumn<HOD, String> nameCol;
    @FXML
    private TableColumn<HOD, String> deprtCol;
    @FXML
  
    private TableColumn<HOD, String> phoneCol;
    @FXML
    private TableView<HOD> tableview;
    
    DatabaseHandler handler = DatabaseHandler.getInstance();
    
    ObservableList<HOD> list = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        initCol();
        try {
             
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(HODListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void loadData() throws SQLException{
        
        
        String qu = "SELECT * from HOD";
        ResultSet rs = handler.execQuery(qu);
        
        while(rs.next()){
            String id = rs.getString("id");
            String department = rs.getString("department");
            String name = rs.getString("name");
           
            String phone = rs.getString("phone");
            
            
            list.add(new HOD(id,department,name,phone,phone));
           
            System.out.println(rs);
        }
        tableview.getItems().setAll(list);
    }
    
    private void initCol(){
        memCol.setCellValueFactory(new PropertyValueFactory<>("memb"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deprtCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
    
    public static class HOD{
        private final SimpleStringProperty memb;
        private final SimpleStringProperty name;
        private final SimpleStringProperty department;
        
        private final SimpleStringProperty phone;
        
        HOD(String memb, String name, String department, String status, String phone){
           this.memb = new SimpleStringProperty(memb);
           this.name = new SimpleStringProperty(name);
           this.department = new SimpleStringProperty(department);
       
           this.phone = new SimpleStringProperty(phone);
        }

       public String getMemb() {
            return memb.get();
        }

        public String getName() {
            return name.get();
        }

        public String getDepartment() {
            return department.get();
        }

       

        public String getPhone() {
            return phone.get();
        }
        
        
    }
    
}
