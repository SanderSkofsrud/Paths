package edu.ntnu.idatt2001;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Main extends Application {

  StackPane stackPaneA = new StackPane();
  StackPane stackPaneR = new StackPane();
  StackPane root = new StackPane();
  BorderPane borderPane = new BorderPane();
  HBox hBox = new HBox();
  HBox hBoxImages = new HBox();
  VBox vBox = new VBox();
  VBox vBoxA = new VBox();
  VBox vBoxR = new VBox();
  BorderPane borderPane1 = new BorderPane();
  @Override
  public void start(Stage stage) throws FileNotFoundException {
    stage.setTitle("Paths");
    stage.setWidth(500);
    stage.setHeight(500);

    Text text = new Text("You see a troll, what do you do?");
    Text textA = new Text("Are you stupid? You tried to attack the troll? It destroyed you easily. You died");
    Text textR = new Text("You tried to run away from the troll, but you slipped and fell down a cliff. You died");

    Button buttonRun = new Button("Run");
    buttonRun.setOnAction(e -> {
      borderPane.setVisible(false);
      stackPaneR.setVisible(true);
      stackPaneA.setVisible(false);
    });

    Button buttonAttack = new Button("Attack");
    buttonAttack.setOnAction(e -> {
      borderPane.setVisible(false);
      stackPaneR.setVisible(false);
      stackPaneA.setVisible(true);
    });

    Button buttonRestartA = new Button("Restart");
    buttonRestartA.setOnAction(e -> {
      borderPane.setVisible(true);
      stackPaneR.setVisible(false);
      stackPaneA.setVisible(false);
    });

    Button buttonRestartR = new Button("Restart");
    buttonRestartR.setOnAction(e -> {
      borderPane.setVisible(true);
      stackPaneR.setVisible(false);
      stackPaneA.setVisible(false);
    });


    Image troll = new Image(new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("troll.png")).getFile()),150,150,true,true);
    Image steve = new Image(new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("steve.png")).getFile()),150,150,true,true);
    ImageView imageViewTroll = new ImageView(troll);
    ImageView imageViewSteve = new ImageView(steve);
    hBox.getChildren().addAll(buttonRun, buttonAttack);
    hBoxImages.getChildren().addAll(imageViewSteve, imageViewTroll);
    vBox.getChildren().addAll(text, hBoxImages, hBox);
    borderPane1.setCenter(vBox);
    hBox.setAlignment(javafx.geometry.Pos.CENTER);
    hBoxImages.setAlignment(javafx.geometry.Pos.CENTER);
    vBox.setAlignment(javafx.geometry.Pos.CENTER);
    vBoxA.setAlignment(javafx.geometry.Pos.CENTER);
    vBoxR.setAlignment(javafx.geometry.Pos.CENTER);
    borderPane.setCenter(borderPane1);
    vBoxR.getChildren().addAll(textR, buttonRestartR);
    stackPaneR.getChildren().addAll(vBoxR);
    vBoxA.getChildren().addAll(textA, buttonRestartA);
    stackPaneA.getChildren().addAll(vBoxA);
    root.getChildren().addAll(borderPane, stackPaneA, stackPaneR);
    stackPaneA.setVisible(false);
    stackPaneR.setVisible(false);
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
