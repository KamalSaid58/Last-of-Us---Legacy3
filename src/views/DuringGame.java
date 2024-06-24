package views;



import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;


public class DuringGame extends Application {
	private GridPane grid;
	private Button[][] buttons;
	ArrayList <Button> remheros;
	Group root = new Group();
	Hero SHero=Game.heroes.get(0);




	public DuringGame(Stage stage) {
		grid = new GridPane();
		buttons = new Button[15][15];
		remheros=new ArrayList<Button>();
		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String readhero(Hero h) {
		int Vacsize=h.getVaccineInventory().size();
		int Supsize=h.getSupplyInventory().size();

		String s="Name: "+h.getName()+" CurrentHp: "+h.getCurrentHp()+" Actions: "+h.getActionsAvailable()+"  AttackDmg: "+h.getAttackDmg()+" Speciality: "+h.isSpecialAction()+"  Vaccines:"+ Vacsize+"  Supplies:"+Supsize;
		if(h instanceof Medic) {
			s+="  MEDIC";
		}
		if(h instanceof Fighter) {
			s+="  FIGHTER";
		}
		if(h instanceof Explorer) {
			s+="  EXPLORER";
		}

		return s;
	}



	public  void  displayRemainingHeros() {
		VBox display = new VBox(10);
		display.setTranslateX(1000.0);
		display.setTranslateY(100);
		display.getChildren().add(new Text("Heroes available: "));
		remheros=new ArrayList<Button>();
		for(int i=0 ; i<Game.heroes.size();i++) {
			Button but=new Button(readhero(Game.heroes.get(i)));
			but.setPrefSize(800,50);
			remheros.add(but);
			display.getChildren().add(but);
		}
		root.getChildren().add(display);

	}
	/*public void createEmptyGrid() {
		for(int i=0;i<15 ; i++) {
			for(int j=0 ;j<15 ; j++) {0000
				Button b = new Button(" ");
				buttons.add(b);
				b.setMinSize(40, 40);
				b.setStyle("-fx-background-color: #808080; ");
				grid.add(b, i, j);
			}
		}
	}*/
	public void chooseTarget() {
		for(int i=0;i<15 ; i++) {
			for(int j=0 ;j<15 ; j++) {
				if(Game.map[i][14-j]instanceof CharacterCell) {
					CharacterCell c = (CharacterCell) Game.map[i][14-j];
					Character chrctr = c.getCharacter();
					buttons[i][j].setOnAction(e-> SHero.setTarget(chrctr));

				}


			}
		}





	}



	public void updateMap() {
		displayRemainingHeros();
		selectHero();
		for(int i=0;i<15 ; i++) {
			for(int j=0 ;j<15 ; j++) {
				if(Game.map[i][14-j] instanceof CharacterCell) {
					CharacterCell c = (CharacterCell) Game.map[i][14-j];
					Character chrctr = c.getCharacter();
					if(chrctr instanceof Hero) {
						Button b = new Button();
						b.setMaxSize(40,40);
						ImageView view =new ImageView("Joel.jpeg");
						view.setFitHeight(20);
						view.setFitWidth(20);
						b.setGraphic(view);
						buttons[i][j]=b;
						Hero h=(Hero)chrctr;
						Game.adjustVisibility(h); 
						grid.add(b,i,j);

					}
					else {
						if(chrctr instanceof Zombie) {
							Button b;
							if(Game.map[i][14-j].isVisible()){
								b= new Button();
								ImageView view =new ImageView("zombie.jpeg");
								view.setFitHeight(20);
								view.setFitWidth(20);
								b.setGraphic(view);

							}
							else{
								b = new Button("");//
							}
							buttons[i][j]=b;

							b.setMaxSize(40, 40);
							grid.add(b, i,j);


						}
						else {
							Button b = new Button(" ");//Empty Cell
							buttons[i][j]=b;

							b.setMinSize(40, 40);
							grid.add(b, i, j);	
						}
					}
				}
				else {
					if(Game.map[i][14-j] instanceof CollectibleCell) {
						CollectibleCell c = (CollectibleCell) Game.map[i][14-j];
						Collectible coll = c.getCollectible();
						if(coll instanceof Vaccine) {
							Button b;
							if(Game.map[i][14-j].isVisible()){
								b=new Button();
								ImageView view =new ImageView("Vaccine.jpeg");
								view.setFitHeight(20);
								view.setFitWidth(20);
								b.setGraphic(view);

							}
							else{
								b=new Button("");
							}
							buttons[i][j]=b;
							b.setMaxSize(40, 40);
							grid.add(b, i, j);
						}
						else {
							if(coll instanceof Supply) {
								Button b;
								if(Game.map[i][14-j].isVisible()){
									b=new Button();
									ImageView view =new ImageView("Supply.jpeg");
									view.setFitHeight(20);
									view.setFitWidth(20);
									b.setGraphic(view);
								}
								else{
									b=new Button(" ");
								}
								buttons[i][j]=b;
								b.setMaxSize(40, 40);
								grid.add(b, i, j);	
							}

						}
					}else
					{
						Button b = new Button("");//TrapCell
						buttons[i][j]=b;
						b.setMaxSize(40, 40);
						grid.add(b, i, j);	
					}

				}
				if(!Game.map[i][14-j].isVisible()) {
					buttons[i][j].setStyle("-fx-background-color: #808080; ");
				}
				else {
					if(SHero.getLocation().getX()==i && SHero.getLocation().getY()==14-j)
						buttons[i][j].setStyle("-fx-background-color: #AC9362; ");
					else
						buttons[i][j].setStyle("-fx-background-color: #ADD8E6; ");
				}


			}
		}
		chooseTarget();
		Button chosenHero=new Button(readhero(SHero));
		chosenHero.setTranslateX(850);
		chosenHero.setTranslateY(600);
		chosenHero.setPrefSize(800,50);
		root.getChildren().add(chosenHero);

	} 


	public void selectHero() {
		for(int i=0;i<Game.heroes.size();i++){ 
			Hero hh=Game.heroes.get(i);
			Button now=remheros.get(i);
			now.setOnAction(e->{
				SHero=hh;
				updateMap();		
			});

		}
	}



	public void controller() {

		Button left=new Button("LEFT");
		Button right=new Button("RIGHT");
		Button down=new Button("UP");
		Button up=new Button("DOWN");
		root.getChildren().addAll(left,right,down,up);
		left.setTranslateX(750);
		left.setTranslateY(50);
		right.setTranslateX(950);
		right.setTranslateY(50);
		up.setTranslateY(90);
		up.setTranslateX(850);
		down.setTranslateY(0);
		down.setTranslateX(850);


		Button attack=new Button("ATTACK");
		attack.setTranslateX(850);
		attack.setTranslateY(200);
		root.getChildren().add(attack);

		Button cure=new Button("CURE");
		cure.setTranslateX(850);
		cure.setTranslateY(300);
		root.getChildren().add(cure);

		Button special=new Button("USE SPECIAL");
		special.setTranslateX(850);
		special.setTranslateY(400);
		root.getChildren().add(special);

		Button endTurn=new Button("END TURN");
		endTurn.setTranslateX(850);
		endTurn.setTranslateY(500);
		root.getChildren().add(endTurn);

		Label label=new Label("Controlled hero:");
		label.setTranslateX(850);
		label.setTranslateY(580);
		root.getChildren().add(label);


		left.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}
			else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						int startHp=SHero.getCurrentHp();
						SHero.move(Direction.DOWN);
						int endHp=SHero.getCurrentHp();
						if(endHp<startHp) 
							AlertBox.display("MESSAGE","You entered a trap");

						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}
						else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});
		right.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						int startHp=SHero.getCurrentHp();
						SHero.move(Direction.UP);
						int endHp=SHero.getCurrentHp();
						if(endHp<startHp) 
							AlertBox.display("MESSAGE","You entered a trap");
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});

		down.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						int startHp=SHero.getCurrentHp();
						SHero.move(Direction.RIGHT);
						int endHp=SHero.getCurrentHp();
						if(endHp<startHp) 
							AlertBox.display("MESSAGE","You entered a trap");
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});

		up.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						int startHp=SHero.getCurrentHp();
						SHero.move(Direction.LEFT);
						int endHp=SHero.getCurrentHp();
						if(endHp<startHp) 
							AlertBox.display("MESSAGE","You entered a trap");
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});
		attack.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{

					try {

						SHero.attack();
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (NotEnoughActionsException e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					} catch (InvalidTargetException e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});

		cure.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						SHero.cure();
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {

						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});
		special.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}else{
					try {
						SHero.useSpecial();
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {

						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});
		endTurn.setOnAction(e->{
			if(Game.checkWin()){
				AlertBox.display("VICTORY","You won the game!!!! exit now");
			}else{
				if(Game.checkGameOver()){
					AlertBox.display("GAME OVER","You lost the game!!!! exit now");
				}
				else{
					try {
						
						Game.endTurn();
						
						updateMap();
						if(Game.checkWin()){
							AlertBox.display("VICTORY","You won the game!!!! exit now");
						}else{
							if(Game.checkGameOver()){
								AlertBox.display("GAME OVER","You lost the game!!!! exit now");
							}
						}
					} catch (Exception e1) {
						AlertBox.display("EXCEPTION",e1.getMessage());
					}
				}
			}

		});








	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("THE LAST OF US");
		stage.setX(50);
		stage.sizeToScene();
		grid.setPadding((new Insets(10, 10, 10, 10)));
		grid.setVgap(5);
		grid.setHgap(5);
		updateMap();
		controller();



		root.getChildren().add(grid);
		Scene scene3 = new Scene(root);
		stage.setScene(scene3);
		stage.show();

	}

}
