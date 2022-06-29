package application;

import java.io.IOException;

import application.controller.ProviderController;
import application.controller.SmsHandyController;
import application.model.PrepaidSmsHandy;
import application.model.Provider;
import application.model.SmSHandy;
import application.model.TariffPlanSmsHandy;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	/**
	 * The data as an observable list of providers.
	 */
	public ObservableList<Provider> providers = FXCollections.observableArrayList();
	public ObservableList<SmSHandy> smshandys = FXCollections.observableArrayList();
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	ProviderController pc;
	SmsHandyController sc;
	
	/**
	 * Constructor
	 */
	public Main() {
		providers.add(new Provider("T-Mobile"));
		providers.add(new Provider("AT&T"));
		providers.add(new Provider("Verizon Wireless"));
		providers.add(new Provider("Google Fi"));
		
		smshandys.add(new PrepaidSmsHandy("+123456",providers.get(0)));
		smshandys.add(new PrepaidSmsHandy("+484848",providers.get(0)));
		smshandys.add(new PrepaidSmsHandy("+499988",providers.get(1)));
		
		smshandys.add(new TariffPlanSmsHandy("+7777752242",providers.get(0)));
		smshandys.add(new TariffPlanSmsHandy("+7277277277",providers.get(3)));
		smshandys.add(new TariffPlanSmsHandy("+0121457577",providers.get(2)));
	}
	
   
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SMS Handy App");
        initRootLayout();
        showProvider();
        showSmsHandy();
    }
    



	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add("application/application.css");
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProvider() {
		// TODO Auto-generated method stub
        try {
        	// Load interface from fxml file.
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProviderOverview.fxml"));
            AnchorPane providerOverview = loader.load();
            
            rootLayout.setBottom(providerOverview);
            
            ProviderController controller = loader.getController();
            pc = controller;
            controller.setMainApp(this);
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    private void showSmsHandy() {
		// TODO Auto-generated method stub
        try {
        	// Load interface from fxml file.
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SmsHandyOverview.fxml"));
            AnchorPane smshandyOverview = loader.load();
            
            rootLayout.setTop(smshandyOverview);
            
            SmsHandyController controller = loader.getController();
            sc = controller;
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    
    public ObservableList<Provider> getProviders() {
		return providers;
	}
    public ObservableList<SmSHandy> getSmshandys() {
		return smshandys;
	}
    public ProviderController getProContoller() {
    	return pc;
    }
    public SmsHandyController getSmsContoller() {
    	return sc;
    }
}
