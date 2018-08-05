package dashboard;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private ImageView imgCR1;

    @FXML
    private ImageView imgCR2;

    @FXML
    private ImageView imgRC1;

    @FXML
    private ImageView imgGB1;

    @FXML
    private ImageView imgCF1;

    @FXML
    private ImageView imgBL1;

    @FXML
    private ImageView imgRC2;

    @FXML
    private ImageView imgGB2;

    @FXML
    private ImageView imgCF2;

    @FXML
    private ImageView imgBL2;
    
    @FXML
    private ImageView imgClose1;

    @FXML
    private ImageView imgClose2;

    @FXML
    private ImageView imgAD1;

    @FXML
    private ImageView imgAD2;

    @FXML
    private ImageView imgAD3;

    @FXML
    private ImageView imgBC1;

    @FXML
    private ImageView imgRR1;

    @FXML
    private ImageView imgBC2;

    @FXML
    private ImageView imgRR2;
    
    @FXML
    void Close(MouseEvent event) {
    	
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("Closing..");
    	confirm.setContentText("Are You sure?");
    	Optional<ButtonType> res= confirm.showAndWait();
    	if(res.get()==ButtonType.OK)
    			System.exit(1);
    }
    
    @FXML
    void openAD(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("developers/DevelopersView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBL(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billLog/BillLogView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openCF(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerFinder/CustomerFinderView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openCR(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerRegistration/CustomerRegistrationView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openGB(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("generateBill/GenerateBillView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openRC(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("routineCustomer/RoutineCustomerView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void openRR(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("routineRegister/RoutineRegisterView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBC(MouseEvent event) {
    	try{
    		
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billCollection/BillCollectionView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void imgADbig(MouseEvent event) {
    	imgAD1.setOpacity(0.5);
    	imgAD2.setOpacity(0.5);
    	imgAD3.setOpacity(0.5);
    	
    }

    @FXML
    void imgADsmall(MouseEvent event) {
    	imgAD1.setOpacity(1);
    	imgAD2.setOpacity(1);
    	imgAD3.setOpacity(1);
    }

    @FXML
    void imgBLbig(MouseEvent event) {
    	imgBL1.setOpacity(0.5);
    	imgBL2.setOpacity(0.5);
    }

    @FXML
    void imgBLsmall(MouseEvent event) {
    	imgBL1.setOpacity(1);
    	imgBL2.setOpacity(1);
    }

    @FXML
    void imgCFbig(MouseEvent event) {
    	imgCF1.setOpacity(0.5);
    	imgCF2.setOpacity(0.5);
    }

    @FXML
    void imgCFsmall(MouseEvent event) {
    	imgCF1.setOpacity(1);
    	imgCF2.setOpacity(1);
    }

    @FXML
    void imgCRbig(MouseEvent event) {
    	imgCR1.setOpacity(0.5);
    	imgCR2.setOpacity(0.5);
    }

    @FXML
    void imgCRsmall(MouseEvent event) {
    	imgCR1.setOpacity(1);
    	imgCR2.setOpacity(1);
    }

    @FXML
    void imgGBbig(MouseEvent event) {
    	imgGB1.setOpacity(0.5);
    	imgGB2.setOpacity(0.5);
    }

    @FXML
    void imgGBsmall(MouseEvent event) {
    	imgGB1.setOpacity(1);
    	imgGB2.setOpacity(1);
    }

    @FXML
    void imgRCbig(MouseEvent event) {
    	imgRC1.setOpacity(0.5);
    	imgRC2.setOpacity(0.5);
    }

    @FXML
    void imgRCsmall(MouseEvent event) {
    	imgRC1.setOpacity(1);
    	imgRC2.setOpacity(1);
    }
    
    @FXML
    void imgClosebig(MouseEvent event) {
    	imgClose1.setOpacity(0.5);
    	imgClose2.setOpacity(0.5);
    }

    @FXML
    void imgClosesmall(MouseEvent event) {
    	imgClose1.setOpacity(1);
    	imgClose2.setOpacity(1);
    }
    
    @FXML
    void imgRRbig(MouseEvent event) {
    	imgRR1.setOpacity(0.5);
    	imgRR2.setOpacity(0.5);
    }

    @FXML
    void imgRRsmall(MouseEvent event) {
    	imgRR1.setOpacity(1);
    	imgRR2.setOpacity(1);
    }
    
    @FXML
    void imgBCbig(MouseEvent event) {
    	imgBC1.setOpacity(0.5);
    	imgBC2.setOpacity(0.5);
    }

    @FXML
    void imgBCsmall(MouseEvent event) {
    	imgBC1.setOpacity(1);
    	imgBC2.setOpacity(1);
    }



    @FXML
    void initialize() {

    }
}
