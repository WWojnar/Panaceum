package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Main extends Application {
	
	
	Button bLogIn;
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Panaceum");
		
		// VBox to arrange elements vertically
		// Arg in VBox is responsible for distance between elements
		VBox verticalLayout = new VBox(9);
		verticalLayout.setPadding(new Insets(5));
		VBox verticalCentLayout = new VBox(9);
		verticalCentLayout.setPadding(new Insets(5));
		verticalCentLayout.setAlignment(Pos.CENTER);
		verticalLayout.getChildren().add(verticalCentLayout);
		
		
		// Heading text
		Label headingText = new Label();
		headingText.setText("Panaceum");
		headingText.setFont(new Font("Arial", 34));
		// add heading text to VBox
		verticalCentLayout.getChildren().add(headingText);
		headingText.setCenterShape(true);
		
		// For sub heading text
		Label subHeadingText = new Label();
		subHeadingText.setText("Log in");
		subHeadingText.setFont(new Font("Arial", 24));
		verticalCentLayout.getChildren().add(subHeadingText);
		
		// For first text box description 
		Label descriptionText1 = new Label();
		descriptionText1.setText("Username: ");
		descriptionText1.setFont(new Font("Arial", 16));
		verticalLayout.getChildren().add(descriptionText1);
		
		// Text field for user name
		TextField usernameTextField = new TextField();
		// minimum width of the text field
		usernameTextField.setMinWidth(300);
		verticalLayout.getChildren().add(usernameTextField);
		
		// For first text box description 
		Label descriptionText2 = new Label();
		descriptionText2.setText("Password: ");
		descriptionText2.setFont(new Font("Arial", 16));
		verticalLayout.getChildren().add(descriptionText2);
				
		// Password field
		PasswordField passwordField = new PasswordField();
		// minimum width of the text field
		passwordField.setMinWidth(300);
		verticalLayout.getChildren().add(passwordField);
		Label message = new Label();
		verticalLayout.getChildren().add(message);
		
		// Login button
		Button btnLogin = new Button();
		btnLogin.setText("Login");
		btnLogin.setFont(new Font("Arial", 16));
		verticalLayout.getChildren().add(btnLogin);
		
		
		
		
		//            ----------------- ACTIONS ---------------------------------------
		
		// Action for password Field
		
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			
				@Override
				public void handle(ActionEvent e){
			    if (loginVerification.verifyLogin(usernameTextField.getText(), passwordField.getText())) {
			    	message.setText("Your password has been confirmed");
			        message.setTextFill(Color.rgb(21, 117, 84));		    	
			    } else {
			    	message.setText("Your username or password is incorrect!");
			        message.setTextFill(Color.rgb(210, 39, 30));
			    }
			    passwordField.clear();
				}
		});
		
		// -------------------------------- Final Build -----------------------------------
		
		// Set scene
		Scene scene = new Scene(verticalLayout, 400, 300);
		// Set stage title
		primaryStage.setTitle("Panaceum");
		// Set scene
		primaryStage.setScene(scene);
		// Show view
		primaryStage.show();
		
	}
		

	
	public static void main(String[] args) {
		launch(args);
	}
}
