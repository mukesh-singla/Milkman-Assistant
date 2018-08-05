package routineRegister;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class RoutineRegisterViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMobile;

    @FXML
    private ComboBox<String> comboMonth;

    @FXML
    private ComboBox<String> comboYear;

    @FXML
    private TableView<RoutineBean> tblview;

    @FXML
    private Label lblExported;
    
    @FXML
    private Button btnClose;
    
    ObservableList<RoutineBean> list;
    
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

    @FXML
    void doExport(ActionEvent event) {
    	
    	try {
			writeExcel();
			lblExported.setVisible(true);
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
            String text="Date, C Qty., B Qty.\n";
            writer.write(text);
            for (RoutineBean p : list)
            {
				text = p.getDate()+ "," + p.getCQ()+ "," + p.getBQ()+"\n";
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

    @SuppressWarnings("unchecked")
	@FXML
    void doShow(ActionEvent event) {
    	
    	lblExported.setVisible(false);
    	
    	TableColumn<RoutineBean, String> DateCol = new TableColumn<RoutineBean, String>("Date");
    	DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	
    	TableColumn<RoutineBean, String> CQCol = new TableColumn<RoutineBean, String>("C Qty.");
    	CQCol.setCellValueFactory(new PropertyValueFactory<>("CQ"));
    	
    	TableColumn<RoutineBean, String> BQCol = new TableColumn<RoutineBean, String>("B Qty.");
    	BQCol.setCellValueFactory(new PropertyValueFactory<>("BQ"));
    	
    	tblview.getColumns().clear();
    	tblview.getColumns().addAll(DateCol,CQCol,BQCol);
    	
    	list = getRecordsFromTableSome(txtMobile.getText(), comboMonth.getSelectionModel().getSelectedItem(), comboYear.getSelectionModel().getSelectedItem());
    	tblview.setItems(list);
    }
    
    ObservableList<RoutineBean> getRecordsFromTableSome(String Mob, String m, String y)
   	{
   		list = FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from routine where Mobile=? and month=? and year=?");
   			  pst.setString(1, Mob);
   			  pst.setString(2, m);
   			  pst.setString(3, y);
   			 
   			ResultSet rs=  pst.executeQuery();
   			while(rs.next())
   			{
   				String Mobile = rs.getString("Mobile");
   				String CQ = rs.getString("CQ");
   				String BQ = rs.getString("BQ");
   				String Date = rs.getString("Month");
   				String Month = rs.getString("Year");
   				String Year = rs.getString("Date");
   				
   				RoutineBean bean = new RoutineBean(Mobile, CQ, BQ, Month, Year, Date);
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
 	
    	 ArrayList<String> m = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
     	 comboMonth.getItems().addAll(m);
          
          ArrayList<String> y = new ArrayList<String>(Arrays.asList("2018","2019","2020","2021","2022","2023","2024","2025"));
     	 comboYear.getItems().addAll(y);
    }
}
