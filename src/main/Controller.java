package main;

import data.InfoDB;
import data.UserFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;


public class Controller
{

    @FXML
    Button friendButton;
    @FXML
    AnchorPane anchor;
    @FXML
    GridPane grid;
    @FXML
    GridPane titlegrid;
    @FXML
    TextField searchInput;
    @FXML
    TextField memberInput;
    @FXML
    Label listLabel;
    @FXML
    AnchorPane resultList;

    private String loggedUser;
    private String loggedNick;
    private String loggedPass;
    InfoDB infoDB;
    private UserFacade userFacade;
    private String previousShow;
    private String search="%";

    Communicator communicator;

    void initData(String user,String nick, String pass, Communicator com)
    {
        loggedUser=user;
        loggedNick=nick;
        loggedPass=pass;
        infoDB = new InfoDB("jdbc:mysql://localhost:3306?useLegacyDatetimeCode=false&serverTimezone=UTC",loggedUser,loggedPass,"com.mysql.cj.jdbc.Driver");
        userFacade = new UserFacade(infoDB);
        communicator=com;
    }

    @FXML
    void logout()
    {
        communicator.exit();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        try {
            Scene scene = new Scene((Pane) loader.load());
            Stage stage = (Stage) friendButton.getScene().getWindow();
            LoginController lc = loader.<LoginController>getController();
            stage.setScene(scene);
        } catch (Exception ex) {

        }
    }
    @FXML
    void showAchievements()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getAchievements(loggedNick,search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                }else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                }
                i++;
            }
            previousShow="a";
        }
        listLabel.setText("Your Achievements");
    }
    @FXML
    void showAllAchievements()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getAllAchievements(search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                }else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                }

                i++;
            }
            previousShow="a";
        }
        listLabel.setText("Achievements");
        previousShow="aa";
    }


    @FXML
    void showClans()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getClans(search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                    titlegrid.add(new Label(temp[1]), 1, i);
                }else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                    grid.add(new Label(temp[1]), 1, i);
                }
                i++;
            }
        }
        listLabel.setText("Clans");
        previousShow="c";
    }
    @FXML
    void showClanMembers()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getClanMembers(loggedNick,search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                }else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                }
                i++;
            }
        }
        listLabel.setText("Clan Members");
        previousShow="cm";

    }
    @FXML
    void showFriends()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getFriends(loggedNick,search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if (i == 0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                } else{
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                }
                i++;
            }
        }
        listLabel.setText("Friends");
        previousShow="f";
    }

    @FXML
    void showHistory()
    {
        clear();
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        List<String[]> list = userFacade.getHistory(loggedNick,search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                    titlegrid.add(new Label(temp[1]), 1, i);
                    titlegrid.add(new Label(temp[2]), 2, i);
                    titlegrid.add(new Label(temp[3]), 3, i);
                }else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                    grid.add(new Label(temp[1]), 1, i);
                    grid.add(new Label(temp[2]), 2, i);
                    grid.add(new Label(temp[3]), 3, i);
                }
                i++;
            }
        }
        listLabel.setText("History");
        previousShow="h";
    }
    @FXML
    void showLeaderboard()
    {
        clear();
        List<String[]> list = userFacade.getLeaderboard(search);
        if(list!=null)
        {
            int i = 0;
            for (String[] temp : list)
            {
                if(i==0)
                {
                    titlegrid.add(new Label(temp[0]), 0, i);
                    titlegrid.add(new Label(temp[1]), 1, i);
                    titlegrid.add(new Label(temp[2]), 2, i);
                    titlegrid.add(new Label(temp[3]), 3, i);
                }
                else
                {
                    adjustSize();
                    grid.add(new Label(temp[0]), 0, i);
                    grid.add(new Label(temp[1]), 1, i);
                    grid.add(new Label(temp[2]), 2, i);
                    grid.add(new Label(temp[3]), 3, i);
                }
                i++;

            }
        }
        listLabel.setText("Leaderboard");
        previousShow="l";
    }

    @FXML
    void search()
    {
        search=searchInput.getText();
        if (search.isEmpty())
        {
            search="%";
        }
        switch(previousShow)
        {
            case "a":
            {
                showAchievements();
                break;
            }
            case "aa":
            {
                showAllAchievements();
            }
            case "c":
            {
                showClans();
                break;
            }
            case "cm":
            {
                showClanMembers();
                break;
            }
            case "f":
            {
                showFriends();
                break;
            }
            case "h":
            {
                showHistory();
                break;
            }

            case "l":
            {
                showLeaderboard();
                break;
            }
        }
        search="%";
        searchInput.setText("");
    }

    @FXML
    void addClanMember()
    {
        userFacade.addClanMember(memberInput.getText(),loggedNick);
    }
    @FXML
    void removeClanMember()
    {
        userFacade.removeClanMember(memberInput.getText(),loggedNick);
    }
    @FXML
    void passLeadership()
    {
        userFacade.passLeadership(memberInput.getText(),loggedNick);
    }

    void adjustSize()
    {
        resultList.setPrefHeight(resultList.getPrefHeight()+20.0);
    }
    void clear()
    {
        grid.getChildren().clear();
        titlegrid.getChildren().clear();
        resultList.setPrefHeight(35);
    }


    @FXML
    void communicate()
    {
        communicator.sendID();
    }
}
