package generateBill;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import customerRegistration.data;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import login.SST_SMS;

public class GenerateBillViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listMobile;

    @FXML
    private ListView<String> listName;

    @FXML
    private ComboBox<Integer> month;

    @FXML
    private ComboBox<Integer> year;

    @FXML
    private TextField txtdays;

    @FXML
    private Label lblCR;

    @FXML
    private Label lblCQ;

    @FXML
    private Label lblBR;

    @FXML
    private Label lblBQ;

    @FXML
    private Label lblCB;

    @FXML
    private Label lblBB;

    @FXML
    private Label lblTotal;
    
    @FXML
    private Button btnTotal;
    
    @FXML
    private Label lblSent;
    
    @FXML
    private Button btnClose;
    
    @FXML
    private Button btnSMS;
    
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
    void doFind(ActionEvent event) {
    	int i = month.getSelectionModel().getSelectedItem();
    	int j = 0;
    	if(i > 0 && i < 8)
    	{
    		if(i == 2)
    		{
    			j = year.getSelectionModel().getSelectedItem();
    			if((j % 4) == 0 || (j % 100) == 0 || (j % 400) == 0)
    			{
    				txtdays.setText("29");
    			}
    			else
    				txtdays.setText("28");
    		}
    		else if(i == 4 || i == 6)
    		{
    			txtdays.setText("30");
    		}
    		else 
    			txtdays.setText("31");
    	}
    	else if(i <= 12 && i > 7)
    	{
    		if((i % 2) == 0)
    			txtdays.setText("31");
    		else
    			txtdays.setText("30");
    	}
    	
    	if(!txtdays.getText().equals(""))
    	{
    		listMobile.setMouseTransparent(false);
    		listName.setMouseTransparent(false);
    	}

    }

    @FXML
    void doTotal(ActionEvent event) {
    	float cr = Float.parseFloat(lblCR.getText());
    	float cq = Float.parseFloat(lblCQ.getText());
    	float br = Float.parseFloat(lblBR.getText());
    	float bq = Float.parseFloat(lblBQ.getText());
    	
    	lblCB.setText(String.valueOf(cr*cq));
    	lblBB.setText(String.valueOf(br*bq));
    	
    	float cb = Float.parseFloat(lblCB.getText());
    	float bb = Float.parseFloat(lblBB.getText());
    	
    	lblTotal.setText(String.valueOf(cb + bb));
    	
    	try {
			pst = con.prepareStatement("insert into billhistory values(?,?,?,?,?,?,0,?,?)");
			pst.setString(1,listMobile.getSelectionModel().getSelectedItem());
			pst.setFloat(2,Float.parseFloat(lblCQ.getText()));
			pst.setFloat(3,Float.parseFloat(lblBQ.getText()));
			pst.setFloat(4,Float.parseFloat(lblCB.getText()));
			pst.setFloat(5,Float.parseFloat(lblBB.getText()));
			pst.setFloat(6,Float.parseFloat(lblTotal.getText()));
			pst.setInt(7,month.getSelectionModel().getSelectedItem());
			pst.setInt(8,year.getSelectionModel().getSelectedItem());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	btnSMS.setDisable(false);
    }

    @FXML
    void dolistMobile(MouseEvent event) {
    	
    	ObservableList<Integer> indx = listMobile.getSelectionModel().getSelectedIndices();
    	for (Integer integer : indx) {
			listName.getSelectionModel().select(integer);
		}
    	
    	lblCR.setText("");
    	lblBR.setText("");
    	lblCQ.setText("");
    	lblBQ.setText("");
    	lblCB.setText("");
    	lblBB.setText("");
    	lblTotal.setText("");
    	btnTotal.setDisable(true);
    	lblSent.setVisible(false);
    	btnSMS.setDisable(true);
    	
    	if(event.getClickCount() == 2)
    	{
    		try {
    			pst = con.prepareStatement("select * from registration where Mobile = ?");
    			pst.setString(1, listMobile.getSelectionModel().getSelectedItem());
    			
    			ResultSet rs = pst.executeQuery();
    			
    			rs.next();
   
    				lblCR.setText(rs.getString("CP"));
    				lblBR.setText(rs.getString("BP"));
    				
        			float cq = rs.getFloat("CQ");
        			float bq = rs.getFloat("BQ");
        			
        			Date date = rs.getDate("Date");
        			LocalDate local = date.toLocalDate();
     
        			int m = local.getMonthValue();
        			int y = local.getYear();
        			int d =local.getDayOfMonth();
        			int end_date = Integer.parseInt(txtdays.getText());
        			int nod = 0;
        			int sel_month = month.getSelectionModel().getSelectedItem();
        			int sel_year = year.getSelectionModel().getSelectedItem();
        			
        			if(m == sel_month && y == sel_year)
        				nod = end_date-d+1;
        			else if(y == sel_year)
        			{
        				if(m < sel_month)
        				nod = end_date;
        			}
        			else if(y < sel_year)
        				nod = end_date;
        			
        			float tdcq = 0,tdbq = 0;
           			
            		tdcq = nod * cq;
        			tdbq = nod * bq;
 
			pst = con.prepareStatement("select sum(CQ),sum(BQ) from routine where Mobile = ?");
			pst.setString(1, listMobile.getSelectionModel().getSelectedItem());

			ResultSet rs1 = pst.executeQuery();
			rs1.next();
			float cvq = 0,bvq = 0;
			if(m == month.getSelectionModel().getSelectedItem() && y == year.getSelectionModel().getSelectedItem())
			{
				cvq = rs1.getFloat("sum(CQ)");
				bvq = rs1.getFloat("sum(BQ)");
			}

			float ncq = 0,nbq = 0;
			nbq = tdbq + bvq;
			ncq = tdcq + cvq;
			
			lblCQ.setText(String.valueOf(ncq));
			lblBQ.setText(String.valueOf(nbq));
			
	}
        	
        	catch (SQLException e) {
    			
    			e.printStackTrace();
    		}
    		
    		btnTotal.setDisable(false);
    	}	
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
    void doSMS(ActionEvent event) {
    	String m = "Your Milk Bill for the month " + month.getSelectionModel().getSelectedItem() + "/" + year.getSelectionModel().getSelectedItem() + " is Rs." + lblTotal.getText();
    	
    	String resp = SST_SMS.bceSunSoftSend(listMobile.getSelectionModel().getSelectedItem(), m);
    	lblSent.setVisible(true);
    	
    	if(resp.indexOf("Exception")!=-1)
    		lblSent.setText("Check Internet Connection !");
    	
    	else
    		if(resp.indexOf("successfully")!=-1)
    			
        		lblSent.setText("SMS Sent Successfully !");
    		else
    			lblSent.setText("Invalid Number !");
    	
    	int indx = listMobile.getSelectionModel().getSelectedIndex();
		listName.getItems().remove(indx);
		listMobile.getItems().remove(indx);
		
		listMobile.getSelectionModel().clearSelection();
		listName.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() {
    	
    	doConnection();
        
    	try {
			pst = con.prepareStatement("select CustomerName,Mobile from registration");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				listName.getItems().add(rs.getString("CustomerName"));
				listMobile.getItems().add(rs.getString("Mobile"));
			}
			
		} 
    	
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	
    	 ArrayList<Integer> m = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    	 month.getItems().addAll(m);
         month.getSelectionModel().select(null);
         
         ArrayList<Integer> y = new ArrayList<Integer>(Arrays.asList(2018,2019,2020,2021,2022,2023,2024,2025));
    	 year.getItems().addAll(y);
         year.getSelectionModel().select(null);
         
        listMobile.setMouseTransparent( true );
        listName.setMouseTransparent( true );
        
        btnTotal.setDisable(true);
        btnSMS.setDisable(true);
    }
}
