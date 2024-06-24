package views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	public static void display(String title,String message){
		Stage window=new Stage();
		window.setTitle(title);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(200);
		Label label=new Label();
		label.setText(message);
		VBox layout=new VBox();
		layout.getChildren().add(label);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
