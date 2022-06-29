package application.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import application.model.Message;
import application.model.PrepaidSmsHandy;
import application.model.Provider;
import application.model.SmSHandy;
import application.model.TariffPlanSmsHandy;

public class SmsHandyController {
	@FXML
	private Label providername;
	
	@FXML
	private Label numero;
	
	@FXML
	private Label solde;
	
	@FXML
	private Label freisms;
	
	@FXML
	TableView<SmSHandy> smshandyTable;
	
	@FXML
    private TableColumn<SmSHandy, String> numColumn;
	
	@FXML
    private TableColumn<SmSHandy, String> typeColumn;
	
	public static Main mainApp;
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
    	// Initialize the smshandy table with the columns.
    	numColumn.setCellValueFactory(cellData -> cellData.getValue().NumProperty());
    	typeColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getArtHandy()));
    	
    	showSmshandyDetails(null);
    	smshandyTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSmshandyDetails(newValue));
    }
    
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
    	SmsHandyController.mainApp=mainApp;
        // Add observable list data to the table
    	smshandyTable.setItems(mainApp.getSmshandys());
    }
    
    private void showSmshandyDetails(SmSHandy smshandy) {
        if (smshandy != null) {
        	providername.setText(smshandy.getProvider().NameProperty().get());
        	numero.setText(smshandy.getNumber());
        	
        	if (smshandy instanceof TariffPlanSmsHandy) {
        		freisms.setText(((TariffPlanSmsHandy)smshandy).getRemainingFreeSms());
        		solde.setText("0");
        	}  		
        	else {
        		solde.setText(((PrepaidSmsHandy)smshandy).getBalance());
        		freisms.setText("0");
        	}
        		
        	
        } else {
        	providername.setText("");
        	numero.setText("");
        	solde.setText("");
        	freisms.setText("");
        }
    }
    
    // delete SmsHandy from the table
    @FXML
    private void deleteSmsHandy() {
        int selectedIndex = smshandyTable.getSelectionModel().getSelectedIndex();
        smshandyTable.getItems().remove(selectedIndex);
        
        SmSHandy phone = getSelectedPhone();
        phone.getProvider().delete(phone);
        initialize();
        
        }
    
    @FXML
    private void changeProvider() {
    	SmSHandy selectedHandy = getSelectedPhone();
    	if (selectedHandy != null && mainApp.getProContoller().getSelectedProviderName() != "") {
    		selectedHandy.setProvider(new Provider(mainApp.getProContoller().getSelectedProviderName()));
    		smshandyTable.refresh();
    		showSmshandyDetails(selectedHandy);
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Provider Changed!");
			alert.setContentText("Your Provider has been changed successfully.");
			alert.showAndWait();
    	}
    }
    
    @FXML
    private void addSmsHandy() { 
    	 try {
             FXMLLoader loader = new FXMLLoader();
             loader.setLocation(Main.class.getResource("view/AddSmshandy.fxml"));
             AnchorPane layout = loader.load();

             Stage dialogStage = new Stage();
             dialogStage.setTitle("Add New SMS Handy");
             dialogStage.initModality(Modality.WINDOW_MODAL);
             dialogStage.initOwner(mainApp.getPrimaryStage());
             Scene scene = new Scene(layout);
             scene.getStylesheets().add("application/application.css");
             dialogStage.setScene(scene);
                    
             AddSmsHandyController controller = loader.getController();
             controller.setDialogStage(dialogStage);

             dialogStage.showAndWait();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    @FXML
    private void deposit() {
    	SmSHandy phone = getSelectedPhone();
    	int number = 0;
    	if (phone instanceof PrepaidSmsHandy) {	
    	TextInputDialog dialog = new TextInputDialog("0");
    	dialog.setTitle("Deposit");
    	dialog.setHeaderText("Aufladen des Guthabens eines SmsHandys");
    	dialog.setContentText("Please enter a valid amount:");
    	Optional<String> result = dialog.showAndWait();
    	try {
    		if (result.isPresent()) number = Integer.parseInt(result.get());
    	} catch (NumberFormatException ex){
            ex.printStackTrace();
        }
    	}
    	if (number > 0) {
    		((PrepaidSmsHandy) phone).deposit(number);
    		initialize();
    	}
    	else {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.setContentText("You must select only Prepaid SMS Handy");
                alert.showAndWait();
    		}
        }
    
    public void SetUpTable(TableView<Message> table) {
    	TableColumn<Message, String> col1 = new TableColumn<>("From");
		TableColumn<Message, String> col2 = new TableColumn<>("To");
		TableColumn<Message, String> col3 = new TableColumn<>("Content");
		TableColumn<Message, String> col4 = new TableColumn<>("Date");
		col1.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getFrom()));
		col2.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getTo()));
		col3.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getContent()));
		col4.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDate().toString()));
        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
    }
    
    @FXML
    private void SentMsgs() {
    	SmSHandy phone = getSelectedPhone();
    	if (phone != null) {
    	if (!phone.getSent().isEmpty()) {
    	phone.listSent();
    	TableView<Message> table = new TableView<Message>();
		table.setItems(FXCollections.observableArrayList(phone.getSent()));
		SetUpTable(table);
		VBox root = new VBox();
        root.getChildren().addAll(table);
        Stage stage = new Stage();
		stage.setScene(new Scene(root, 600, 475));
		stage.setTitle("Sent Messages");
		stage.show();
    	} else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("There are no new messages in your inbox");
            alert.showAndWait();
    	}
    	}
    }
    
    @FXML
    private void ReceivedMsgs() {
    	SmSHandy phone = getSelectedPhone();
    	if (phone != null) {
    	if (!phone.getReceived().isEmpty()) {
    	phone.listReceived();
    	TableView<Message> table = new TableView<Message>();
		table.setItems(FXCollections.observableArrayList(phone.getReceived()));
		SetUpTable(table);
		VBox root = new VBox();
        root.getChildren().addAll(table);
        Stage stage = new Stage();
		stage.setScene(new Scene(root, 600, 475));
		stage.setTitle("Received Messages");
		stage.show();
    	} else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("There are no new messages in your inbox");
            alert.showAndWait();
    	}
    	}
    }
    
    @FXML
    private void SendSms() {
    	if (getSelectedPhone()!= null) {
    	try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/SendSms.fxml"));
        AnchorPane layout = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Send SMS");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("application/application.css");
        dialogStage.setScene(scene);
               
        SendSmsController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
    	}
    
    }
    
    public SmSHandy getSelectedPhone() {
		return smshandyTable.getSelectionModel().getSelectedItem();
    }
    }


