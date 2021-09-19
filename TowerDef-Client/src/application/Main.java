package application;
	
import java.util.Scanner;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

//import javax.imageio.ImageIO;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.ScrollPaneLayout;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;

public class Main extends Application {
	
	public static void SendMessage() throws Exception {
        String url = "https://localhost:5000/commsHub";
        
        HubConnection hubConnection = HubConnectionBuilder.create(url).build();
        hubConnection.start();
        
        hubConnection.send("SendMessage", "Giedrius", "Plyz work m8");
        
//        hubConnection.stop();
    }
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Button btn1 = new Button("Send message");
			Button btn2 = new Button("Empty button");
			
			btn1.setOnAction(value -> {
				try {
					SendMessage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			HBox bottomContent = new HBox();
			bottomContent.getChildren().addAll(btn1, btn2);
			bottomContent.centerShapeProperty();
			
			BorderPane root = new BorderPane();
			root.setBottom(bottomContent);
			
			Scene scene = new Scene(root, 450, 450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
		try {
			TestSignalR();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void ReceiveMessage(String user, String message) {
		String msg = user + ": " + message;
		System.out.println(msg);
	}
	
	
	
	public static void TestSignalR() throws Exception{
        String url = "https://localhost:5000/commsHub";
        
        HubConnection hubConnection = HubConnectionBuilder.create(url).build();
        
        hubConnection.on("ReceiveMessage", (user, message) -> {
            ReceiveMessage(user, message);
        }, String.class, String.class);      
        
        //This is a blocking call
        hubConnection.start().blockingAwait();
        
//        hubConnection.stop();
    }
}
