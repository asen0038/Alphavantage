package alphavantage;

import alphavantage.model.AlphaVantageFacade;
import alphavantage.model.AlphaVantageFacadeImpl;
import alphavantage.view.DisplayWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Driver class
 */
public class App extends Application {

    private static String IN;
    private static String OUT;

    @Override
    public void start(Stage primaryStage) throws Exception {

        AlphaVantageFacade avf = new AlphaVantageFacadeImpl(IN, OUT);
        DisplayWindow dw = new DisplayWindow(avf, primaryStage);

        primaryStage.setTitle("Alpha Vantage");
        primaryStage.setScene(dw.getYearScene());
        primaryStage.show();

    }

    /**
     * Driver method
     * @param args
     */
    public static void main(String[] args) {

        //Defensive checks so the application runs

        if(args.length != 2){
            System.out.println("Invalid Command line arguments");
            System.exit(0);
        }

        IN = args[0];
        OUT = args[1];

        if(IN != null && OUT != null){
            if((IN.equals("online") || IN.equals("offline")) &&
                    (OUT.equals("online") || OUT.equals("offline"))){
                launch(args);
            }else{
                System.out.println("Invalid Command line arguments");
                System.exit(0);
            }
        }else{
            System.out.println("Invalid Command line arguments");
            System.exit(0);
        }
    }
}
