package customerFinder;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import customerFinder.CustomerBean;

public class CustomerFinderViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<CustomerBean> tableview;
    
    ObservableList<CustomerBean> list;
    
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

    @SuppressWarnings("unchecked")
	@FXML
    void DoAllRecords(ActionEvent event) {
    	
    	comboArea.getSelectionModel().select(null);
    	
    	TableColumn<CustomerBean, String> MobileCol = new TableColumn<CustomerBean, String>("Mobile");
    	MobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
    	
    	TableColumn<CustomerBean, String> CustomerNameCol = new TableColumn<CustomerBean, String>("Customer Name");
    	CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
    	
    	TableColumn<CustomerBean, String> AddressCol = new TableColumn<CustomerBean, String>("Address");
    	AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));

    	TableColumn<CustomerBean, String> AreaCol = new TableColumn<CustomerBean, String>("Area");
    	AreaCol.setCellValueFactory(new PropertyValueFactory<>("Area"));
    	
    	TableColumn<CustomerBean, String> CityCol = new TableColumn<CustomerBean, String>("City");
    	CityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
    	
    	TableColumn<CustomerBean, String> CQCol = new TableColumn<CustomerBean, String>("CQ");
    	CQCol.setCellValueFactory(new PropertyValueFactory<>("CQ"));
    	
    	TableColumn<CustomerBean, String> CPCol = new TableColumn<CustomerBean, String>("CP");
    	CPCol.setCellValueFactory(new PropertyValueFactory<>("CP"));
    	
    	TableColumn<CustomerBean, String> BQCol = new TableColumn<CustomerBean, String>("BQ");
    	BQCol.setCellValueFactory(new PropertyValueFactory<>("BQ"));
    	
    	TableColumn<CustomerBean, String> BPCol = new TableColumn<CustomerBean, String>("BP");
    	BPCol.setCellValueFactory(new PropertyValueFactory<>("BP"));
    	
    	TableColumn<CustomerBean, String> DateCol = new TableColumn<CustomerBean, String>("Date Of Start");
    	DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(MobileCol,CustomerNameCol,AddressCol,AreaCol,CityCol,CQCol,CPCol,BQCol,BPCol,DateCol);
    	
    	
    	list = FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from registration");
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CustomerName = rs.getString("CustomerName");
   				String Address = rs.getString("Address");
   				String Area = rs.getString("Area");
   				String City = rs.getString("City");
   				String CQ = rs.getString("CQ");
   				String CP = rs.getString("CP");
   				String BQ = rs.getString("BQ");
   				String BP = rs.getString("BP");
   				String Date = rs.getString("Date");
   				
   				CustomerBean bean = new CustomerBean(Mobile, CustomerName, Address, Area, City, CQ, CP, BQ, BP, Date);
   				list.add(bean);
   			}
   			
   			} 
   		catch (SQLException e) 
   		{
   			
   			e.printStackTrace();
   		}
   		
   		tableview.setItems(list);
   	}
    
    @FXML
    void doExport(ActionEvent event) {
    	try {
			writeExcel();
			txtName.setText("Exported to excel..");
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
        		txtName.setText("file name should have .csv extension");
        		return;
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Mobile,Customer Name,Address,Area,City,CQ,CP,BQ,BP,Date Of Start\n";
            writer.write(text);
            for (CustomerBean p : list)
            {
				text = p.getMobile()+ "," + p.getCustomerName()+ "," + p.getAddress()+ "," + p.getArea()+ "," + p.getCity()+ "," + p.getCQ()+ "," + p.getCP()+ "," + p.getBQ()+ "," + p.getBP()+ "," + p.getDate()+"\n";
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
    	
    	TableColumn<CustomerBean, String> MobileCol = new TableColumn<CustomerBean, String>("Mobile");
    	MobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
    	
    	TableColumn<CustomerBean, String> CustomerNameCol = new TableColumn<CustomerBean, String>("Customer Name");
    	CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
    	
    	TableColumn<CustomerBean, String> AddressCol = new TableColumn<CustomerBean, String>("Address");
    	AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));

    	TableColumn<CustomerBean, String> AreaCol = new TableColumn<CustomerBean, String>("Area");
    	AreaCol.setCellValueFactory(new PropertyValueFactory<>("Area"));
    	
    	TableColumn<CustomerBean, String> CityCol = new TableColumn<CustomerBean, String>("City");
    	CityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
    	
    	TableColumn<CustomerBean, String> CQCol = new TableColumn<CustomerBean, String>("CQ");
    	CQCol.setCellValueFactory(new PropertyValueFactory<>("CQ"));
    	
    	TableColumn<CustomerBean, String> CPCol = new TableColumn<CustomerBean, String>("CP");
    	CPCol.setCellValueFactory(new PropertyValueFactory<>("CP"));
    	
    	TableColumn<CustomerBean, String> BQCol = new TableColumn<CustomerBean, String>("BQ");
    	BQCol.setCellValueFactory(new PropertyValueFactory<>("BQ"));
    	
    	TableColumn<CustomerBean, String> BPCol = new TableColumn<CustomerBean, String>("BP");
    	BPCol.setCellValueFactory(new PropertyValueFactory<>("BP"));
    	
    	TableColumn<CustomerBean, String> DateCol = new TableColumn<CustomerBean, String>("Date Of Start");
    	DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(MobileCol,CustomerNameCol,AddressCol,AreaCol,CityCol,CQCol,CPCol,BQCol,BPCol,DateCol);
    	
    	
    	list = getRecordsFromTableSome(comboArea.getValue());
    	tableview.setItems(list);
    }
    
    ObservableList<CustomerBean> getRecordsFromTableSome(String Ar)
   	{
   		list = FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from registration where Area=?");
   			  pst.setString(1, Ar);
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CustomerName = rs.getString("CustomerName");
   				String Address = rs.getString("Address");
   				String Area = rs.getString("Area");
   				String City = rs.getString("City");
   				String CQ = rs.getString("CQ");
   				String CP = rs.getString("CP");
   				String BQ = rs.getString("BQ");
   				String BP = rs.getString("BP");
   				String Date = rs.getString("Date");
   				
   				CustomerBean bean = new CustomerBean(Mobile, CustomerName, Address, Area, City, CQ, CP, BQ, BP, Date);
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
    	
    	TableColumn<CustomerBean, String> MobileCol = new TableColumn<CustomerBean, String>("Mobile");
    	MobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
    	
    	TableColumn<CustomerBean, String> CustomerNameCol = new TableColumn<CustomerBean, String>("Customer Name");
    	CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
    	
    	TableColumn<CustomerBean, String> AddressCol = new TableColumn<CustomerBean, String>("Address");
    	AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));

    	TableColumn<CustomerBean, String> AreaCol = new TableColumn<CustomerBean, String>("Area");
    	AreaCol.setCellValueFactory(new PropertyValueFactory<>("Area"));
    	
    	TableColumn<CustomerBean, String> CityCol = new TableColumn<CustomerBean, String>("City");
    	CityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
    	
    	TableColumn<CustomerBean, String> CQCol = new TableColumn<CustomerBean, String>("CQ");
    	CQCol.setCellValueFactory(new PropertyValueFactory<>("CQ"));
    	
    	TableColumn<CustomerBean, String> CPCol = new TableColumn<CustomerBean, String>("CP");
    	CPCol.setCellValueFactory(new PropertyValueFactory<>("CP"));
    	
    	TableColumn<CustomerBean, String> BQCol = new TableColumn<CustomerBean, String>("BQ");
    	BQCol.setCellValueFactory(new PropertyValueFactory<>("BQ"));
    	
    	TableColumn<CustomerBean, String> BPCol = new TableColumn<CustomerBean, String>("BP");
    	BPCol.setCellValueFactory(new PropertyValueFactory<>("BP"));
    	
    	TableColumn<CustomerBean, String> DateCol = new TableColumn<CustomerBean, String>("Date Of Start");
    	DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	
    	tableview.getColumns().clear();
    	tableview.getColumns().addAll(MobileCol,CustomerNameCol,AddressCol,AreaCol,CityCol,CQCol,CPCol,BQCol,BPCol,DateCol);
    	
    	
    	list = getRecords(txtName.getText());
    	tableview.setItems(list);
    }
    
    ObservableList<CustomerBean> getRecords(String nam)
   	{
   		list = FXCollections.observableArrayList();
   		
   		try {
   			
   			  pst=con.prepareStatement("select * from registration where CustomerName LIKE ?");
   			  pst.setString(1, "%" + txtName.getText() + "%");
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CustomerName = rs.getString("CustomerName");
   				String Address = rs.getString("Address");
   				String Area = rs.getString("Area");
   				String City = rs.getString("City");
   				String CQ = rs.getString("CQ");
   				String CP = rs.getString("CP");
   				String BQ = rs.getString("BQ");
   				String BP = rs.getString("BP");
   				String Date = rs.getString("Date");
   				
   				CustomerBean bean = new CustomerBean(Mobile, CustomerName, Address, Area, City, CQ, CP, BQ, BP, Date);
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
    void initialize() {
      doConnection();
      
      try {
			pst = con.prepareStatement("select distinct Area from registration");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				comboArea.getItems().add(rs.getString("Area"));
			}
		} 
  	
  	catch (SQLException e) 
  	{
			e.printStackTrace();
		}
      
    }
}
