package views;

import engine.Game;
import model.characters.Hero;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartGameView extends Application{


	public StartGameView(Stage stage) {
		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox root2=new VBox(20);
		Game.loadHeroes("Heros.csv");
		int i=0;
		while(i<Game.availableHeroes.size()){
			Hero h=Game.availableHeroes.get(i);
			Button hero=new Button(DuringGame.readhero(h));
			hero.setOnAction(e-> {
				try {
					Game.startGame(h);
					new DuringGame(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			root2.getChildren().add(hero);
			i++;
		}
		
		
		
		Scene scene2=new Scene(root2,800,500);
		stage.sizeToScene();
		stage.setScene(scene2);
		
		stage.show();
		
		

	}

}
