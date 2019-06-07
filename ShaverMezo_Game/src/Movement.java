import java.util.concurrent.TimeUnit;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
public class Movement {
	
	private static final int KEYBOARD_MOVEMENT_DELTA = 20; //Speed of Circle Movement
	
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25); //Sets duration of transitions
	
	public static int counter = 10; //amount of bullets remaining for sniper
	
	public static int Spaces = 0; //counts the number of times the spacebar has been pressed
	
	/**
	 * Used to access the counter's current state in GameDriver
	 * @return counter
	 */
	public static int getCount() { 
		return counter;
	}
	
	/**
	 * Sets up the transition animations for Circle
	 * @param circle
	 * @return amount circle needs to translate
	 */
	  public static TranslateTransition createTranslateTransition(final Circle circle) {
		    final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
		    transition.setOnFinished(new EventHandler<ActionEvent>() {
		      @Override public void handle(ActionEvent t) {
		        circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
		        circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
		        circle.setTranslateX(0);
		        circle.setTranslateY(0);
		     
		      }
		    });
		    return transition;
		  }

	  	/**
	  	 * Detects keyboard input 
	  	 * Translates the location of the circle according to the keys pressed
	  	 * Generates array of instructions which can be scrolled through with the spacenar
	  	 * @param scene
	  	 * @param circle
	  	 * @param lblIntro
	  	 */
	  
		  public static void moveCircleOnKeyPress(Scene scene, final Circle circle, Label lblIntro) {
		    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    	
		    	
		      @Override public void handle(KeyEvent event) {
		    	  
		        switch (event.getCode()) {
		          case UP:    circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;
		          case RIGHT: circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
		          case DOWN:  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
		          case LEFT:  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
		          case SPACE: Spaces ++;
		          String[] Intructions = {"Press Space for Intructions", 
		        		  "This is a two player game about the battle between a Sniper and an Alien Invader", 
		        		  "The Invader controlls their ship using the keyboard. They can move in any direction.",
		        		  "The Invader can pilot their ship through the top and sides of the screen - they will respawn on the opposite side.",
		        		  "The Sniper controlls the mouse. They must shoot down the Invader in order to protect planet Earth.", 
		        		  "When your crosshair is on top of the Invader, left click the mouse to shoot.", 
		        		  "If the green bullet makes contacts with the grey alien ship, the Sniper wins.",
		        		  "The Sniper only has 10 bullets. Your ammo is represented in the top right corner.",
		        		  "If the Sniper runs out of ammo before shooting down the alien ship, the Invader wins.", 
		        		  "PRESS SPACE TO BEGIN", ""};
		          
		          	lblIntro.setTranslateY(750);
		          	lblIntro.setTranslateX(30);
		          	lblIntro.setFont(Font.font("Arial", 20));
					lblIntro.setText(Intructions[Spaces]); 
		        }
		        
		        if (circle.getCenterX() == GameDriver.Width) {
					  circle.setCenterX(0);
					  }
		        else if (circle.getCenterX() == 0) {
					  circle.setCenterX(GameDriver.Width);
					  }
		        else if (circle.getCenterY() == GameDriver.Length) {
					  circle.setCenterY(0);
					  }
		        else if (circle.getCenterY() == 0) {
					  circle.setCenterY(GameDriver.Length);
					  }  
		      }    
		    });
		  }

		  /**
		   * Spawns a bullet to the location of the player's crosshair
		   * Detects if circle intersects with the pointer location
		   * Win conditions 
		   * @param scene
		   * @param circle
		   * @param circle2
		   * @param transition
		   * @param lblScore
		   */
		  public static void moveCircleOnMousePress(Scene scene, final Circle circle, final Circle circle2, final TranslateTransition transition, Label lblScore) {
			  
		  
		    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
		    	
		      @Override public void handle(MouseEvent event) {
		        if (!event.isControlDown()) {
		          circle2.setCenterX(event.getSceneX());
		          circle2.setCenterY(event.getSceneY());
		          
		          counter=counter-1;
		          lblScore.setText("Ammo "  + Movement.getCount());
		          lblScore.setFont(Font.font("Arial", 20));
		          
		        } 
		        
		        //setting up mouse tracking & collisions
		        double mouseX = event.getSceneX();
		        double mouseY = event.getSceneY();
		        
		        double X = (mouseX - circle.getCenterX());
		        double Y = (mouseY - circle.getCenterY());
		        
		        double Xsquared = Math.pow(X, 2);
		        double Ysquared = Math.pow(Y, 2);
		        
		        //If the sniper runs out of bullets & is unable to shoot the invader
		        if (counter <= 0) {
		        	
		        	lblScore.setFont(Font.font("Cambria", 80));
		    	  	lblScore.setTranslateY(350);
				  	lblScore.setTranslateX(325);
				  	lblScore.setText("INVADER WINS");
				  	

		      }
		        //If the sniper's bullet intersects with the invader
		        if (((Xsquared + Ysquared) <= 225)) {
		        	
				  	  circle.setCenterX(60000);
				  	  lblScore.setFont(Font.font("Cambria", 80));
				  	  lblScore.setTranslateY(350);
				  	  lblScore.setTranslateX(375);
				  	  lblScore.setText("SNIPER WINS");
		        	}
		      	}
		      });
		  }
}
