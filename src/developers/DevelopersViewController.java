package developers;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DevelopersViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void openRealJava(ActionEvent event) {
    	try 
    	{
            Desktop.getDesktop().browse(new URI("http://www.realJavaOnline.com"));
        } 
    	
    	catch (Exception e1) 
    	{
            e1.printStackTrace();
        } 
    }

    @FXML
    void initialize() {

    }
}
