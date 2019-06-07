import java.awt.MouseInfo;
import java.io.FileInputStream;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameDriver extends Application {
 

/**
 * Game Loop
 * Declares & spawns all objects on the scene
 * Uses Movement class to access mouse & keyboard movement
 * Initializes the scene
 * @param args
 */
  public static void main(String[] args) { launch(args); }
  public static int Width = 1200;
  public static int Length = 800;
  
  public void start(Stage stage) throws Exception {
	

    final Circle circle = createCircle();
    final Circle bullet = createBullet();
    final Label lblScore = new Label ("Ammo "  + Movement.getCount());
    final Label lblIntro = new Label ("Press Spacebar for Intructions");
    
    lblScore.setFont(Font.font("Arial", 20));
    lblScore.setTextFill(Color.GHOSTWHITE);
    lblIntro.setTextFill(Color.GHOSTWHITE);
    lblIntro.setFont(Font.font("Arial", 45));
    lblIntro.setTranslateY(400);
    lblIntro.setTranslateX(350);
    
    final Group group = new Group(lblScore, lblIntro, circle, bullet);
    final TranslateTransition transition = Movement.createTranslateTransition(circle);
    
	final Scene scene = new Scene(group, Width, Length, Color.STEELBLUE.darker());
    Movement.moveCircleOnKeyPress(scene, circle, lblIntro);
    Movement.moveCircleOnMousePress(scene, circle, bullet, transition, lblScore);
    scene.setCursor(Cursor.CROSSHAIR);
    
    stage.setScene(scene);
    stage.show();
    
  }
  

/**
 * Creates green bullet 
 * Bullet shape is used in moveCircleOnMousePress
 * @return bullet
 */
  private Circle createBullet() {
	  	
	  	
	    final Circle bullet = new Circle(7000, 7000, 5, Color.LIME); //Initially spawns bullet offscreen
	    bullet.setOpacity(1);
	    return bullet;
  }
	    
 /**
  * Creates white circle (invader) 
  * Circle shape is used in moveCircleOnKeyPress
  * @return circle 
  */
  public Circle createCircle() {
    final Circle circle = new Circle(300, 200, 15, Color.WHITESMOKE);
    circle.setOpacity(1);
    return circle;
  }
  
 
  
}

