package main;

import data.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginController
{

    @FXML
    TextField nickInput;
    @FXML
    TextField passInput;
    @FXML
    TextField userInput;
    @FXML
    Label errorLabel;



    String nickname;
    Communicator communicator = new Communicator(4444);

    public LoginController()
    {
        communicator.sendID();
    }
    @FXML
    private void login()
    {
        hideError();
        String s=communicator.sendLoginRequest(userInput.getText(),passInput.getText());
        nickname=s;

        if(s!=null&&s!="_LOGIN_ERROR")
        {
            changeView(userInput.getText(),nickname,passInput.getText());
        }
        else
        {
            showError(s);
        }

    }

    @FXML
    private void register()
    {
        hideError();
        if(userInput.getText().equals("")||nickInput.getText().equals("")||passInput.getText().equals(""))
        {
            showError("Fill in fields");
            return;
        }
        //int i= serverFacade.registerPlayer(userInput.getText(),nickInput.getText(),passInput.getText());
        String s=communicator.sendRegisterRequest(userInput.getText(),nickInput.getText(),passInput.getText());
        System.out.print(s);

        if(s!=null&&s!="_REGISTER_ERROR")
        {
            showError(s);
        }
        else
        {
            showError(s);
        }

    }

    private void changeView(String user,String nick,String pass) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("base.fxml"));
        try {
            Scene scene = new Scene((Pane) loader.load());
            Stage stage = (Stage) nickInput.getScene().getWindow();
            Controller c = loader.<Controller>getController();
            c.initData(user,nick,pass,communicator);
            stage.setScene(scene);
        } catch (Exception ex) {

        }
    }
    @FXML
    private void switchAdmin()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        try {
            Scene scene = new Scene((Pane) loader.load());
            Stage stage = (Stage) nickInput.getScene().getWindow();
            AdminController ac = loader.<AdminController>getController();
            ac.initData(communicator);
            stage.setScene(scene);
        } catch (Exception ex) {

        }
    }
    @FXML
    private void exit()
    {
        communicator.exit();
        Platform.exit();
        System.exit(0);
    }

    private void showError(String error)
    {
        errorLabel.setText(error);
    }
    private void hideError()
    {
        errorLabel.setText("");
    }


    private void branchTest() {
        System.out.print("nof");
    }

}
