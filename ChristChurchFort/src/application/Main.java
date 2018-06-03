package application;
	
import java.io.IOException;

import com.ccf.controller.MenuController;
import com.ccf.util.BalanceUpdator;
import com.ccf.util.HibernateSessionFactory;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static Stage globalStage;
	
	public Stage getPrimaryStage(){
		return globalStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			globalStage = primaryStage ;
			root = FXMLLoader.load(getClass().getResource("/com/ccf/fxml/Template.fxml"));
			primaryStage.setTitle("Christ Church Fort");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              BalanceUpdator balanceUpdator = BalanceUpdator.getInstance();
		              balanceUpdator.updateAllBalances();
		          }
		      });
			MenuController menuController = new MenuController();
			menuController.paySantha();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		HibernateSessionFactory.getSessionFactory();
		launch(args);
	}
}
