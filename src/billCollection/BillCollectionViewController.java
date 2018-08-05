package billCollection;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import customerRegistration.data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class BillCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMonth;

    @FXML
    private ComboBox<String> comboYear;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtTotal;
    
    @FXML
    private ImageView imgErr;
    
    @FXML
    private ImageView imgOK;

    @FXML
    private Label lblUpdated;
    
    @FXML
    private Button btnClose;
    
    PreparedStatement pst;
    Connection con;
    
    void doConnection()
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost/milkman", data.uid, data.pwd);
    		System.out.println("Connected");
    		
    	}
    	
    	catch (Exception ex){
    		ex.printStackTrace();
    	}
    }

    @FXML
    void doGetBill(ActionEvent event) {
    	
    	imgOK.setVisible(false);
    	lblUpdated.setVisible(false);
    	
    	try {
			pst = con.prepareStatement("select TotalBill from billhistory where Mobile = ? and Month = ? and Year = ?");
			pst.setString(1, txtMobile.getText());
			pst.setString(2, comboMonth.getSelectionModel().getSelectedItem());
			pst.setString(3, comboYear.getSelectionModel().getSelectedItem());
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				txtTotal.setText(rs.getString("TotalBill"));
			}
    	}
    	
    	catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void doUpdate(ActionEvent event) {
    	
    	try {
			pst = con.prepareStatement("update billhistory set status=1 where Mobile = ? and Month = ? and Year = ?");
			pst.setString(1, txtMobile.getText());
			pst.setString(2, comboMonth.getSelectionModel().getSelectedItem());
			pst.setString(3, comboYear.getSelectionModel().getSelectedItem());
			
			pst.executeUpdate();
    	}
    	
    	catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	imgOK.setVisible(true);
    	lblUpdated.setVisible(true);
    }
    
    @FXML
    void doClose(ActionEvent event) {
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("Closing..");
    	confirm.setContentText("Are You sure?");
    	Optional<ButtonType> res= confirm.showAndWait();
    	if(res.get()==ButtonType.OK)
    	{
    		Scene scene1=(Scene)btnClose.getScene();
			scene1.getWindow().hide();
    	}
    }

    @FXML
    void initialize() {
      doConnection();
      
      ArrayList<String> m = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
 	 comboMonth.getItems().addAll(m);
      
      ArrayList<String> y = new ArrayList<String>(Arrays.asList("2018","2019","2020","2021","2022","2023","2024","2025"));
 	 comboYear.getItems().addAll(y);

    }
}
