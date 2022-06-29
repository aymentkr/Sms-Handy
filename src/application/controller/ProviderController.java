package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import application.Main;
import application.model.Provider;

public class ProviderController {
	
	@FXML
	public TextField providername;
	
	@FXML
	private TableView<Provider> providerTable;
	
	@FXML
    public TableColumn<Provider, String> providerColumn;
	
	public static Main mainApp;

	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
    	// Initialize the provider table with the column.
    	providerColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
    	showProviderName(null);
    	providerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProviderName(newValue));
    }
    
	private void showProviderName(Provider provider) {
		if (provider!=null)
        providername.setText(provider.NameProperty().get());		
	}

	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
    	ProviderController.mainApp=mainApp;
        // Add observable list data to the table
        providerTable.setItems(mainApp.getProviders());
    }
    
    
    // delete provider from the table
    @FXML
    public void deleteProvider() {
        int selectedIndex = providerTable.getSelectionModel().getSelectedIndex();
        providerTable.getItems().remove(selectedIndex);
        
        Provider provider = providerTable.getItems().get(selectedIndex);
      
        Provider.getProviderList().remove(provider);
        mainApp.getSmsContoller().smshandyTable.getItems().removeAll(provider.getSubscriber().values());
        mainApp.getSmsContoller().initialize();
    }
    
    //add provider to the table
    @FXML
    private void addProvider() {
		if (!providername.getText().isBlank() && providername!=null) {
			boolean test = true;
			for (Provider p : mainApp.providers) {
				if (p.getName().equals(providername.getText())) {
					test = false;
				}		
			}
			
			if (test) {
			String providerName = providername.getText().trim();
			mainApp.providers.add(new Provider(providerName));
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error!");
				alert.setContentText("Provider already exists.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("You must write in the textfield.");
			alert.showAndWait();
		}
    }
    public String getSelectedProviderName() {
    	return providername.getText();
    }
}
