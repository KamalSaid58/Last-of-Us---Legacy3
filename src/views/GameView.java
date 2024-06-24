package views;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class GameView extends Application {



	public void start(Stage stage) throws Exception {
		BorderPane root1 = new BorderPane();
		Scene scene1=new Scene(root1,Color.BLACK);
		stage.setTitle("Start Game");
		stage.setWidth(500);
		stage.setHeight(500);
		Label txt=new Label("THE LAST OF US");
		txt.setFont(Font.font("Veranda",40));
		root1.setTop(txt);

		Button start =new Button("START GAME");
		start.setTextFill(Color.BLACK);
		root1.setCenter(start);

		start.setOnAction(e->new StartGameView(stage));



		Image img=new Image("Last_of_us.jpg");
		stage.getIcons().add(img);
		stage.setScene(scene1);
		stage.show();
	} 

	



	public static void main (String[] args) {
		launch(args);

	}

}
