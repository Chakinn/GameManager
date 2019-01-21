package main;

import data.Backup;
import data.InfoDB;
import data.InsertDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AdminController
{
    @FXML
    TextField mp1;
    @FXML
    TextField mp2;
    @FXML
    TextField mwin;
    @FXML
    TextField fp1;
    @FXML
    TextField fp2;
    @FXML
    TextField ap;
    @FXML
    TextField aa;
    @FXML
    TextField cl;
    @FXML
    TextField cn;
    @FXML
    TextField cu;
    @FXML
    Label errorLabel;

    InfoDB infoDB;
    Communicator communicator;

    public void initData(Communicator com)
    {
        communicator=com;
    }


    @FXML
    public void addMatchRecord()
    {
        hideError();
        int p1=-1;
        int p2=-1;
        try
        {
            p1=Integer.parseInt(mp1.getText());
            p2=Integer.parseInt(mp2.getText());
        }
        catch(NumberFormatException ex)
        {
            showError("Bad input");
        }
        if(p1<0||p2<0||(!mwin.getText().equals("2") && !mwin.getText().equals("1")))
        {
            System.out.print("PARSE ERROR");
            return;
        }

        String s=communicator.sendMatchInsert(p1,p2,mwin.getText());
        if(s==null || s.equals("_ADD_ERROR"))
        {
            showError(s);
        }
    }
    @FXML
    public void addFriend()
    {
        hideError();
        int p1=-1;
        int p2=-1;
        try
        {
            p1=Integer.parseInt(fp1.getText());
            p2=Integer.parseInt(fp2.getText());
        }
        catch(NumberFormatException ex)
        {
            showError("Bad input");
        }

        String s=communicator.sendFriendInsert(p1,p2);
        if(s==null || s.equals("_ADD_ERROR"))
        {
            showError(s);
        }
    }
    @FXML
    public void addAchievement()
    {
        hideError();
        int p1=-1;
        int p2=-1;
        try
        {
            p1=Integer.parseInt(ap.getText());
            p2=Integer.parseInt(aa.getText());
        }
        catch(NumberFormatException ex)
        {
            showError("Bad input");
        }

        String s=communicator.sendAchievementInsert(p1,p2);
        if(s==null || s.equals("_ADD_ERROR"))
        {
            showError(s);
        }
    }
@FXML
    public void addClan()
    {
        hideError();
        int p1=-1;
        try
        {
            p1=Integer.parseInt(cl.getText());
        }
        catch(NumberFormatException ex)
        {
            showError("Bad input");
        }

        String s=communicator.sendClanInsert(p1,cu.getText(),cn.getText());
        if(s==null || s.equals("_ADD_ERROR"))
        {
            showError(s);
        }
    }

    @FXML
    private void logout()
    {
        communicator.exit();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        try {
            Scene scene = new Scene((Pane) loader.load());
            Stage stage = (Stage) aa.getScene().getWindow();
            LoginController lc = loader.<LoginController>getController();
            stage.setScene(scene);
        } catch (Exception ex) {

        }
    }
    @FXML
    private void backup()
    {
        Backup.backup();
    }
    @FXML
    private void loadDB()
    {
        FileChooser fileChooser = new FileChooser();
        File f=fileChooser.showOpenDialog(aa.getScene().getWindow());
        FileInputStream fileInputStream=null;
        try
        {
            fileInputStream = new FileInputStream(f);
            Backup.importSQL(infoDB,fileInputStream);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void showError(String error)
    {
        errorLabel.setText(error);
    }
    private void hideError()
    {
        errorLabel.setText("");
    }

}
