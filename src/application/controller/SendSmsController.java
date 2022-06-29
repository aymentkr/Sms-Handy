package application.controller;

import application.model.SmSHandy;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SendSmsController extends SmsHandyController{
	
	@FXML
    private ComboBox<String> receiver;
	@FXML
	private TextArea msgContent;
	

	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		// TODO Auto-generated method stub
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void initialize(){
		 if (mainApp.smshandys != null)
		 mainApp.smshandys.forEach(p -> receiver.getItems().add(p.getNumber()));
	}
	@FXML
	private void Cancel() {
		dialogStage.close();
	}
	
	@FXML
    private void Send() {
		String content = msgContent.getText();
		SmSHandy selectedHandy = mainApp.getSmsContoller().getSelectedPhone();
		
		if (receiver != null && content != "" && selectedHandy != null && receiver.getValue() != selectedHandy.getNumber()) {
			selectedHandy.sendSms(receiver.getValue(),content);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Message sent!");
			alert.showAndWait();
			initialize();
			Cancel();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("You must select a valid SMS Handy");
            alert.showAndWait();
		}
		
	}

}
