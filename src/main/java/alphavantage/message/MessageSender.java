package alphavantage.message;

import alphavantage.company.Company;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MessageSender implements Message{

    /* twilio account sid */
    private String sid;

    /* twilio api key */
    private String key;

    /* To phone number */
    private String to;

    /* From phone number */
    private String from;

    /* Message body text */
    private String body;

    @Override
    public void setDetails() throws IllegalStateException {

        JSONParser parser = new JSONParser();

        try {

            Reader reader = new FileReader("configure/credentials.json");
            JSONObject object = (JSONObject) parser.parse(reader);

            this.sid = (String) object.get("sid");
            this.key = (String) object.get("out_key");
            this.to = (String) object.get("to");
            this.from = (String) object.get("from");

            if(sid.equals("") || key.equals("") || to.equals("") || from.equals("")){
                throw new IllegalStateException();
            }

        }catch (ParseException e) {

        } catch (IOException e) {

        }

    }

    @Override
    public void configureMessage(Company c){

        String data = "";

        if(c.isRecentlySplit()){
            data += "*";
        }

        for(int i = 0; i < 3; i++){
            String combine = c.getOverView().getElement().get(i) +
                    ": " + c.getOverView().getValue().get(i);
            data += combine;
            data += '\n';
        }

        for(int i = 0; i < 3; i++){
            String combine = c.getCashFlow().getReports().get(0).getElement().get(i) +
                    ": " + c.getCashFlow().getReports().get(0).getValue().get(i);
            data += combine;
            data += '\n';
        }

        body = data;

    }

    @Override
    public void sendMessage(String type) throws IllegalStateException{

        if(type.equals("online")){
            String authenticate = String.format("https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json", sid);

            try {
                URL url = new URL(authenticate);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setDoOutput(true);
                String bearer = sid + ":" + key;

                //Convert authentication token to base 64 to be set as curl request property
                String auth = new String(Base64.getEncoder().encode(bearer.getBytes()));

                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Authorization", "Basic " + auth);

                String data = String.format("From=%s&Body=%s&To=%s", from, body, to);

                //Data must be sent as UTF_8 byte arraay as the api's request property uses curl commands
                byte[] out = data.getBytes(StandardCharsets.UTF_8);

                OutputStream stream = con.getOutputStream();
                stream.write(out);

                System.out.println(con.getResponseCode());
                System.out.println(con.getResponseMessage());

                con.disconnect();

            }catch (Exception e){
                throw new IllegalStateException();
            }
        }else{
            System.out.println("Created");
        }

    }

}
