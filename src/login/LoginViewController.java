package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField pass;

    @FXML
    private ImageView btnProceed;
    
    @FXML
    private ImageView imgErr;

    @FXML
    private Label lblErr;

    @FXML
    void btnEnter(MouseEvent event) {
    	btnProceed.setOpacity(0.5);
    }

    @FXML
    void btnExit(MouseEvent event) {
    	btnProceed.setOpacity(1);
    }

    @FXML
    void doForgot(ActionEvent event) {
    	
    	String m = "Your Admin Key is 'realjava'";
    	
    	String resp = SST_SMS.bceSunSoftSend("8699819346", m);
    	System.out.println(resp);
    	
    	if(resp.indexOf("Exception")!=-1)
    		System.out.println("Check Internet Connection");
    	
    	else
    		if(resp.indexOf("successfully")!=-1)
        		System.out.println("Sent");
    		else
    		System.out.println( "Invalid Number");
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");
    	alert.setHeaderText("Information Alert");
    	alert.setContentText("Admin Key has been successfully sent to your registered mobile number.");
    	alert.show();

    }

    @FXML
    void doProceed(MouseEvent event) {
    	if(pass.getText().equals("realjava"))
    	{
    		imgErr.setVisible(false);
        	lblErr.setVisible(false);
    		try{
        		
    			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/DashboardView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
    			
    			Scene scene1=(Scene)btnProceed.getScene();
    			scene1.getWindow().hide();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		imgErr.setVisible(true);
        	lblErr.setVisible(true);
    	}
    		

    }

    @FXML
    void initialize() {
   

    }
}
