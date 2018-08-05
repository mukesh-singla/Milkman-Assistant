package billLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class BillLogViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMonth;

    @FXML
    private ComboBox<String> comboYear;

    @FXML
    private ToggleGroup bill;
    
    @FXML
    private RadioButton radioPaid;

    @FXML
    private RadioButton radioPending;


    @FXML
    private TextField txtMobile;
    
    @FXML
    private Button btnClose;

    @FXML
    private TableView<BillBean> tableview;
    
    ObservableList<BillBean> list;
    
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

    @SuppressWarnings("unchecked")
	@FXML
    void doFetch(ActionEvent event) {
    	
    	radioPaid.setSelected(false);
    	radioPending.setSelected(false);
    	txtMobile.setText("");
    	
    	TableColumn<BillBean, String> MobileCol = new TableColumn<BillBean, String>("Mobile");
    	MobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
    	
    	TableColumn<BillBean, String> CQtyCol = new TableColumn<BillBean, String>("C Qty.");
    	CQtyCol.setCellValueFactory(new PropertyValueFactory<>("CQty"));
    	
    	TableColumn<BillBean, String> BQtyCol = new TableColumn<BillBean, String>("B Qty.");
    	BQtyCol.setCellValueFactory(new PropertyValueFactory<>("BQty"));
    	
    	TableColumn<BillBean, String> CBillCol = new TableColumn<BillBean, String>("C Bill");
    	CBillCol.setCellValueFactory(new PropertyValueFactory<>("CBill"));
    	
    	TableColumn<BillBean, String> BBillCol = new TableColumn<BillBean, String>("B Bill");
    	BBillCol.setCellValueFactory(new PropertyValueFactory<>("BBill"));
    	
    	TableColumn<BillBean, String> TotalBillCol = new TableColumn<BillBean, String>("Total Bill");
    	TotalBillCol.setCellValueFactory(new PropertyValueFactory<>("TotalBill"));
    	
    	TableColumn<BillBean, String> StatusCol = new TableColumn<BillBean, String>("Status");
    	StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(MobileCol, CQtyCol, BQtyCol, CBillCol, BBillCol, TotalBillCol, StatusCol);
    	
    	
    	list = getRecordsFromTableSome(comboMonth.getValue(), comboYear.getValue());
    	tableview.setItems(list);
    }
    
    ObservableList<BillBean> getRecordsFromTableSome(String m, String y)
   	{
   		list = FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from billhistory where Month = ? AND Year = ?");
   			  pst.setString(1, m);
   			  pst.setString(2, y);
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CQty = rs.getString("CQty");
   				String BQty = rs.getString("BQty");
   				String CBill = rs.getString("CBill");
   				String BBill = rs.getString("BBill");
   				String TotalBill = rs.getString("TotalBill");
   				String Status = rs.getString("Status");
   				String Month = rs.getString("Month");
   				String Year = rs.getString("Year");
   				
   				BillBean bean = new BillBean(Mobile, CQty, BQty, CBill, BBill, TotalBill, Status, Month, Year);
   				list.add(bean);
   			}
   			
   			} 
   		catch (SQLException e) 
   		{
   			
   			e.printStackTrace();
   		}
   		
   		return list;
   	}

    @SuppressWarnings("unchecked")
	@FXML
    void doSearch(ActionEvent event) {
    	
    	comboMonth.getSelectionModel().select(null);
    	comboYear.getSelectionModel().select(null);
    	txtMobile.setText("");
    	
    	TableColumn<BillBean, String> MobileCol = new TableColumn<BillBean, String>("Mobile");
    	MobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
    	
    	TableColumn<BillBean, String> CQtyCol = new TableColumn<BillBean, String>("C Qty.");
    	CQtyCol.setCellValueFactory(new PropertyValueFactory<>("CQty"));
    	
    	TableColumn<BillBean, String> BQtyCol = new TableColumn<BillBean, String>("B Qty.");
    	BQtyCol.setCellValueFactory(new PropertyValueFactory<>("BQty"));
    	
    	TableColumn<BillBean, String> CBillCol = new TableColumn<BillBean, String>("C Bill");
    	CBillCol.setCellValueFactory(new PropertyValueFactory<>("CBill"));
    	
    	TableColumn<BillBean, String> BBillCol = new TableColumn<BillBean, String>("B Bill");
    	BBillCol.setCellValueFactory(new PropertyValueFactory<>("BBill"));
    	
    	TableColumn<BillBean, String> TotalBillCol = new TableColumn<BillBean, String>("Total Bill");
    	TotalBillCol.setCellValueFactory(new PropertyValueFactory<>("TotalBill"));
    	
    	TableColumn<BillBean, String> MonthCol = new TableColumn<BillBean, String>("Month");
    	MonthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
    	
    	TableColumn<BillBean, String> YearCol = new TableColumn<BillBean, String>("Year");
    	YearCol.setCellValueFactory(new PropertyValueFactory<>("Year"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(MobileCol, CQtyCol, BQtyCol, CBillCol, BBillCol, TotalBillCol, MonthCol, YearCol);
    	
    	list = getRecordsFromTable();
    	tableview.setItems(list);
    }
    
    ObservableList<BillBean> getRecordsFromTable()
   	{
   		list = FXCollections.observableArrayList();
   		
   		if(radioPaid.isSelected()==true) {
   		
   		try {
   			  pst=con.prepareStatement("select * from billhistory where Status=1");
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CQty = rs.getString("CQty");
   				String BQty = rs.getString("BQty");
   				String CBill = rs.getString("CBill");
   				String BBill = rs.getString("BBill");
   				String TotalBill = rs.getString("TotalBill");
   				String Status = rs.getString("Status");
   				String Month = rs.getString("Month");
   				String Year = rs.getString("Year");
   				
   				BillBean bean = new BillBean(Mobile, CQty, BQty, CBill, BBill, TotalBill, Status, Month, Year);
   				list.add(bean);
   			}
   			
   			} 
   		catch (SQLException e) 
   		{
   			
   			e.printStackTrace();
   		}
   		}
   		
   		if(radioPending.isSelected()==true) {
   			try {
     			  pst=con.prepareStatement("select * from billhistory where Status=0");
     			 
     			ResultSet rs=  pst.executeQuery();
     			while(rs.next())
     			{
     				String Mobile = rs.getString("Mobile");
     				String CQty = rs.getString("CQty");
     				String BQty = rs.getString("BQty");
     				String CBill = rs.getString("CBill");
     				String BBill = rs.getString("BBill");
     				String TotalBill = rs.getString("TotalBill");
     				String Status = rs.getString("Status");
     				String Month = rs.getString("Month");
     				String Year = rs.getString("Year");
     				
     				BillBean bean = new BillBean(Mobile, CQty, BQty, CBill, BBill, TotalBill, Status, Month, Year);
     				list.add(bean);
     			}
     			
     			} 
     		catch (SQLException e) 
     		{
     			
     			e.printStackTrace();
     		}
   		}
   		
   		return list;
   	}

    @SuppressWarnings("unchecked")
	@FXML
    void doTotal(ActionEvent event) {
    	
    	comboMonth.getSelectionModel().select(null);
    	comboYear.getSelectionModel().select(null);
    	radioPaid.setSelected(false);
    	radioPending.setSelected(false);
    
    	TableColumn<BillBean, String> CQtyCol = new TableColumn<BillBean, String>("C Qty.");
    	CQtyCol.setCellValueFactory(new PropertyValueFactory<>("CQty"));
    	
    	TableColumn<BillBean, String> BQtyCol = new TableColumn<BillBean, String>("B Qty.");
    	BQtyCol.setCellValueFactory(new PropertyValueFactory<>("BQty"));
    	
    	TableColumn<BillBean, String> CBillCol = new TableColumn<BillBean, String>("C Bill");
    	CBillCol.setCellValueFactory(new PropertyValueFactory<>("CBill"));
    	
    	TableColumn<BillBean, String> BBillCol = new TableColumn<BillBean, String>("B Bill");
    	BBillCol.setCellValueFactory(new PropertyValueFactory<>("BBill"));
    	
    	TableColumn<BillBean, String> TotalBillCol = new TableColumn<BillBean, String>("Total Bill");
    	TotalBillCol.setCellValueFactory(new PropertyValueFactory<>("TotalBill"));
    	
    	TableColumn<BillBean, String> StatusCol = new TableColumn<BillBean, String>("Status");
    	StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
    	
    	TableColumn<BillBean, String> MonthCol = new TableColumn<BillBean, String>("Month");
    	MonthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
    	
    	TableColumn<BillBean, String> YearCol = new TableColumn<BillBean, String>("Year");
    	YearCol.setCellValueFactory(new PropertyValueFactory<>("Year"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(CQtyCol, BQtyCol, CBillCol, BBillCol, TotalBillCol, StatusCol, MonthCol, YearCol);
    	
    	list = getRecordsFrom(txtMobile.getText());
    	tableview.setItems(list);
    }
    
    ObservableList<BillBean> getRecordsFrom(String mob)
   	{
   		list = FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from billhistory where Mobile = ?");
   			  pst.setString(1, mob);
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CQty = rs.getString("CQty");
   				String BQty = rs.getString("BQty");
   				String CBill = rs.getString("CBill");
   				String BBill = rs.getString("BBill");
   				String TotalBill = rs.getString("TotalBill");
   				String Status = rs.getString("Status");
   				String Month = rs.getString("Month");
   				String Year = rs.getString("Year");
   				
   				BillBean bean = new BillBean(Mobile, CQty, BQty, CBill, BBill, TotalBill, Status, Month, Year);
   				list.add(bean);
   			}
   			
   			} 
   		catch (SQLException e) 
   		{
   			
   			e.printStackTrace();
   		}
   		
   		return list;
   	}
    
    @FXML
    void doExport(ActionEvent event) {
    	try {
			writeExcel();
			txtMobile.setText("Exported to excel..");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
    
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
        	FileChooser chooser=new FileChooser();
	    	
        	chooser.setTitle("Select Path:");
        	
        	chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                    
                );
        	 File file=chooser.showSaveDialog(null);
        	String filePath=file.getAbsolutePath();
        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
        	{
        		txtMobile.setText("file name should have .csv extension");
        		return;
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Mobile,C Qty., B Qty.,C Bill,B Bill,Total Bill,Status,Month,Year\n";
            writer.write(text);
            for (BillBean p : list)
            {
				text = p.getMobile()+ "," + p.getCQty()+ "," + p.getBQty()+ "," + p.getCBill()+ "," + p.getBBill()+ "," + p.getTotalBill()+ "," + p.getStatus()+ "," + p.getMonth()+ "," + p.getYear()+ "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
        	writer.flush();
             writer.close();
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
