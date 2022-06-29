package application.controller;

import application.model.PrepaidSmsHandy;
import application.model.Provider;
import application.model.SmSHandy;
import application.model.TariffPlanSmsHandy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddSmsHandyController extends SmsHandyController{
	
	@FXML
    private TextField smshandyNummer;
    @FXML
    private ComboBox<String> ArtdesHandys;
    @FXML
    private ComboBox<String> Providers;
	
	private Stage dialogStage;
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void initialize(){
		 ArtdesHandys.setItems(FXCollections.observableArrayList("PrepaidSmsHandy", "TariffPlanSmsHandy"));
		 if (mainApp.providers != null)
		 mainApp.providers.forEach(p -> Providers.getItems().add(p.getName()));
	}
	
	@FXML
	private void Cancel() {
		dialogStage.close();
	}
	
	@FXML
    private void Save() {
		SmSHandy phone;
		Provider provider = null;
		for (Provider p : mainApp.providers) {
			if (p.getName() == Providers.getValue())
				provider = p;
		}
		boolean test = true;
		for (SmSHandy s : mainApp.smshandys) {
			if (s.getNumber().equals(smshandyNummer.getText())) {
				test = false;
			}		
		}
		if (test && provider!=null) {
		if (!smshandyNummer.getText().equals("") && !Providers.getValue().equals("") && !ArtdesHandys.getValue().equals("")) {
        	boolean isNumeric = smshandyNummer.getText().chars().allMatch( Character::isDigit );
        	if (isNumeric) {
        	if (ArtdesHandys.getValue().toString().equalsIgnoreCase("PrepaidSmsHandy") ) {
        		phone = new PrepaidSmsHandy(smshandyNummer.getText(), provider);
        		
            } else {
            	phone = new TariffPlanSmsHandy(smshandyNummer.getText(), provider);
            }
            	mainApp.smshandys.add(phone);
        	Cancel();
        	}
        	
        }
        else {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("You must write in the textfield.");
			alert.showAndWait();
        }}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error!");
			alert.setContentText("SMS Handy already exists.");
			alert.showAndWait();
		}
	}
}
