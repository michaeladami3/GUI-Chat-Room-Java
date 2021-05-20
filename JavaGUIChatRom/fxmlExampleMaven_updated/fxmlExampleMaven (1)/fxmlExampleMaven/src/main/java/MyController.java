

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MyController implements Initializable {
	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private VBox root;
	TextField t1;
	@FXML
	private BorderPane root2;
	@FXML
    private BorderPane root3;
	@FXML
	public ListView<String> listViewServer;
	@FXML
	private ListView<String> listViewClients;
    @FXML
    private TextField textField; //The Message textfield
	Server serverConnection; //Instance
	static Client clientConnection; //Instance
    @FXML
    private TextField putText; //Already here
	static int clientCount = 0; //Current count of the clients
	@FXML
	private TextField textField1; //The text field for the receiver
	@FXML
	private TextField textField2; //Text field for the option
	
	static String msg = "";  //The message intialized
//	List<Integer> receiver = new ArrayList<>();
	static String receiver = ""; //The person initialized

	static int option = -58; //1,2,3
    //static so each instance of controller can access to update 
    private static String textEntered = "";
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		listViewServer.setItems(list);
	}

	public void helloMethod(ActionEvent e) throws IOException {
        System.out.println(textEntered);
        
        //get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml2.fxml"));
        Parent root2 = loader.load(); //load view into parent
        MyController myctr = loader.getController();//get controller created by FXMLLoader
//        myctr.setText();//use MyLoader class method for setText()
        root.setPrefWidth(300);
        root.setPrefHeight(200);
        root2.getStylesheets().add("/styles/style2.css");//set style
        root.getScene().setRoot(root2);//update scene graph

		serverConnection = new Server(data -> { //Set as new server
			Platform.runLater(()->{ //Same as before
				myctr.listViewServer.getItems().add(data.toString());
			});
		});
	}
	public void helloMethod1(ActionEvent e) throws IOException {
      System.out.println(textEntered); 
      //get instance of the loader class
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml3.fxml"));
      Parent root3 = loader.load(); //load view into parent
      MyController myctr = loader.getController();//get controller created by FXMLLoader
//      myctr.setText();//use MyLoader class method for setText()
      
      root3.getStylesheets().add("/styles/style2.css");//set style
      
      root.getScene().setRoot(root3);//update scene graph
      
		clientConnection = new Client(data->{
			Platform.runLater(()->{
//				listViewClients.getItems().addAll(serverConnection.clientName);
				myctr.listViewClients.getItems().add(data.toString());
			});},msg,receiver,option); //Send all
		clientCount++;
		clientConnection.start(); //Start them
	}
	
	public void b1Method(ActionEvent e) throws IOException{
        textEntered = putText.getText(); //Grab the text from this
        System.out.println(textEntered); //Show it
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml.fxml"));
        Parent root = loader.load();
        MyController myctr = loader.getController();
        root2.getScene().setRoot(root);
	}
	//Message TF
	public void handle1(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) { //Once user presses enter
			msg=textField.getText(); //get the text
			textField.clear(); //clear it
			System.out.println(clientCount);
			System.out.println(msg); //print on console
		}
	}
	//Option TF
	public void handle2(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) { //Once user presses enter 
			option=Integer.valueOf(textField2.getText()); //get the text
			textField2.clear(); //clear
			System.out.println(option); //print
		}
	}
	//Receiver TF
	public void handle3(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {//Once user presses enter
			receiver=textField1.getText(); //get the text
			textField1.clear(); //clear
			System.out.println(receiver); //print
		}
	}
	boolean contains(ArrayList<String> clientName,String name) {
		for(String i: clientName) {
			if(i==name)
				return true;
		}
		return false;
	}
	public void sendButton(ActionEvent e) throws IOException{
       //get instance of the loader class
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml3.fxml"));
       Parent root3 = loader.load(); //load view into parent
       MyController myctr = loader.getController();//get controller created by FXMLLoader
        //get instance of the loader class
       if(msg=="")
		msg=textField.getText();
       if(option==-58 && textField.getText()!="")
		option=Integer.valueOf(textField2.getText());
       if(receiver=="")
		receiver=textField1.getText();
		if(msg!="" && receiver!="" && option!=-58 && 1<=option&&option<=3) {//0<=Integer.valueOf(receiver)&& Integer.valueOf(receiver)<=clientCount ){
			textField.clear();
			textField1.clear();
			textField2.clear();
			clientConnection.msgData = new msgInfo(msg, receiver, option);
			clientConnection.send(clientConnection.msgData);
			msg="";
			receiver="";
			option=-58;		
		}
		else {
			//ERROR messages
			if(msg=="") {
				myctr.listViewClients.getItems().add("Error:Missing a message string or you need to press enter on it.");
			}
			if(receiver=="") {
				myctr.listViewClients.getItems().add("Error:Missing a message string or you need to press enter on it.");
			}
			else if(Integer.valueOf(receiver)>clientCount) {
				myctr.listViewClients.getItems().add("Error:Receiver is out of range.");
			}
			else if(Integer.valueOf(receiver)<0) {
				myctr.listViewClients.getItems().add("Error:Receiver is out of range.");
			}
			if(option==-58) {
				myctr.listViewClients.getItems().add("Error:Missing a message string or you need to press enter on it.");
			}
			else if(option<1) {
				myctr.listViewClients.getItems().add("Error:Option is invalid due to the input.");
			}
			else if(option>3) {
				myctr.listViewClients.getItems().add("Error:Option is invalid due to the input.");
			}
		}
	}
	
}
