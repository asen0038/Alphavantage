package alphavantage.view;

import alphavantage.model.AlphaVantageFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for handling user display and actions
 */
public class DisplayWindow {

    /* model object that the view will use */
    private AlphaVantageFacade avf;

    /* Stage */
    private Stage mainStage;

    /* Starting page of GUI */
    private Scene yearScene;

    /* Company code entering */
    private Scene homeScene;

    /* Choice page of GUI for database caching */
    private Scene choiceScene;

    /* Data display page of GUI */
    private Scene dataScene;

    /* Pane root for year scene */
    private Pane yearRoot = new Pane();

    /* Pane root for home scene */
    private Pane homeRoot = new Pane();

    /* Pane root for choice scene */
    private Pane choiceRoot = new Pane();

    /* Pane root for data scene */
    private Pane dataRoot = new Pane();

    /* Scroll pane root for choice root */
    private ScrollPane sp = new ScrollPane();

    /* Current company symbol input */
    private String currSym = "";

    /**
     * Display Window constructor
     * @param avf
     * @param stage
     */
    public DisplayWindow(AlphaVantageFacade avf, Stage stage){
        this.avf = avf;
        this.mainStage = stage;

        display();
    }

    /**
     * Getter for home scene
     *
     * @return home scene
     */
    public Scene getHomeScene() {
        return this.homeScene;
    }

    /**
     * Getter for year scene
     *
     * @return year scene
     */
    public Scene getYearScene() {
        return this.yearScene;
    }

    /**
     * Setups all the page and button details and
     * contains the event listeners for all buttons
     * and changes scenes accordingly
     */
    public void display() {

        Label yearPrompt = new Label("Enter a split year between 1900 and 2021 inclusive.");
        yearPrompt.setLayoutX(100);
        yearPrompt.setLayoutY(50);

        Label year = new Label("Split Year");
        year.setLayoutX(150);
        year.setLayoutY(100);

        TextField value = new TextField();
        value.setLayoutX(250);
        value.setLayoutY(100);

        Label errYr = new Label(" ");
        errYr.setLayoutX(250);
        errYr.setLayoutY(300);

        Button yearBtn = new Button("Set split year");
        yearBtn.setLayoutX(300);
        yearBtn.setLayoutY(150);
        yearBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                String y = value.getText();
                if(y == null || y.equals("")){
                    errYr.setText("Error: Please enter a valid input");
                    errYr.setTextFill(Paint.valueOf("red"));

                }else{
                    try {
                        avf.setSplitYear(y);
                        mainStage.setScene(homeScene);

                    }catch (Exception e){
                        errYr.setText("Please enter a valid year");
                        errYr.setTextFill(Paint.valueOf("red"));
                    }
                }
            }
        });

        yearRoot.getChildren().addAll(yearPrompt, year, value, errYr, yearBtn);
        this.yearScene = new Scene(yearRoot,600,400);

        Text data = new Text();
        data.setX(30);
        data.setY(60);
        data.setText(" ");

        Label symbol = new Label("Company Code");
        symbol.setLayoutX(150);
        symbol.setLayoutY(100);

        TextField sym = new TextField();
        sym.setLayoutX(250);
        sym.setLayoutY(100);

        Label errIN = new Label(" ");
        errIN.setLayoutX(250);
        errIN.setLayoutY(300);

        Button btn1 = new Button("Get data");
        btn1.setLayoutX(300);
        btn1.setLayoutY(200);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                currSym = sym.getText();

                if(currSym == null || currSym.equals("")){
                    errIN.setText("Error: Please enter a valid input");
                    errIN.setTextFill(Paint.valueOf("red"));

                }else{
                    errIN.setText("Receiving your data, please wait....");
                    errIN.setTextFill(Paint.valueOf("black"));

                    try {
                        int n = avf.receive(currSym.toUpperCase());
                        if(n == 1){
                            data.setText(avf.getData());
                            mainStage.setScene(dataScene);
                        }else{
                            mainStage.setScene(choiceScene);
                        }

                    }catch (Exception e){
                        errIN.setText("Error: " + e);
                        errIN.setTextFill(Paint.valueOf("red"));
                        e.printStackTrace();
                    }
                }
            }
        });

        homeRoot.getChildren().addAll(symbol, sym, btn1, errIN);
        this.homeScene = new Scene(homeRoot,600,400);

        Label prompt = new Label("Looks like you have searched for this company code before.\n" +
                "Would you like fetch the cached data from the database or receive a fresh copy from the API.");
        prompt.setLayoutX(70);
        prompt.setLayoutY(70);

        //err label
        Label choiceErr = new Label(" ");
        choiceErr.setLayoutX(70);
        choiceErr.setLayoutY(350);

        //database
        Button db = new Button("Fetch from database");
        db.setLayoutX(150);
        db.setLayoutY(200);
        db.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    choiceErr.setText("Fetching your data, please wait...");
                    choiceErr.setTextFill(Paint.valueOf("black"));
                    String d = avf.receiveDB(currSym.toUpperCase());
                    data.setText(d);
                    mainStage.setScene(dataScene);

                }catch (Exception e){
                    choiceErr.setText("Error: " + e);
                    choiceErr.setTextFill(Paint.valueOf("red"));
                    e.printStackTrace();
                }

            }
        });

        //api
        Button api = new Button("Receive from API");
        api.setLayoutX(350);
        api.setLayoutY(200);
        api.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    choiceErr.setText("Receiving your data, please wait....");
                    choiceErr.setTextFill(Paint.valueOf("black"));
                    avf.receiveApi(currSym.toUpperCase());
                    data.setText(avf.getData());
                    mainStage.setScene(dataScene);

                }catch (Exception e){
                    choiceErr.setText("Error: " + e);
                    choiceErr.setTextFill(Paint.valueOf("red"));
                }

            }
        });

        choiceRoot.getChildren().addAll(prompt, choiceErr, db, api);
        this.choiceScene = new Scene(choiceRoot, 600, 400);


        Label errOUT = new Label(" ");
        errOUT.setLayoutX(130);
        errOUT.setLayoutY(10);

        Button send = new Button("Send");
        send.setLayoutX(80);
        send.setLayoutY(10);
        send.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                try {
                    errOUT.setText("Sending your data, please wait...");
                    errOUT.setTextFill(Paint.valueOf("blue"));
                    avf.sendData();
                    errOUT.setText("Data sent");
                    errOUT.setTextFill(Paint.valueOf("blue"));

                }catch (Exception e){
                    errOUT.setText("Error: " + e);
                    errOUT.setTextFill(Paint.valueOf("red"));
                }

            }
        });

        Button btn2 = new Button("Back");
        btn2.setLayoutX(10);
        btn2.setLayoutY(10);
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                errIN.setText(" ");
                errOUT.setText(" ");
                choiceErr.setText(" ");
                data.setText(" ");
                avf.clearData();
                mainStage.setScene(homeScene);
            }
        });

        dataRoot.getChildren().addAll(btn2, data, send, errOUT);
        sp.setContent(dataRoot);
        this.dataScene = new Scene(sp,600,400);
    }

}
