import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ClientTest {
    
    private String dataTransfer(JSONObject json, String url_address) {
        String print_returned = "";

        try {
            URL url = new URL(url_address);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            while ((input = in.readLine()) != null) {
                print_returned += input + "\n";
            }

            in.close();
        } catch (IOException e) {
            if (e.toString().contains("java.net.ConnectException: Connection refused: connect")) {
                print_returned += "Zapomniales zalaczyc serwer" + "\n";
            } else {
                print_returned += e;
            }
        }

        return print_returned;
    }

    private String register(String login, String passwd) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                            .put("login", login)
                            .put("password", passwd);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url = "http://localhost:8084/Panaceum/user/register";
        String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/user/register";

        return dataTransfer(json, url);
    }
    
    private String login(String login, String passwd) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                            .put("login", login)
                            .put("password", passwd);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url = "http://localhost:8084/Panaceum/user/login";
        String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/user/login";
        
        return dataTransfer(json, url);
    }
	
	private String addPrescription(String login, String token, String dosage, String expiryDate,
            String medicineName, int doctorId, int patientId) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("dosage", dosage)
                    .put("expiryDate", expiryDate)
                    .put("medicineName", medicineName)
                    .put("doctorId", doctorId)
                    .put("patientId", patientId);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url = "http://localhost:8084/Panaceum/prescription/add";
        String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/prescription/add";

        return dataTransfer(json, url);
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();
        String help = "";

        //help = test.register("kluski", "1234");
        help = test.login("kluski", "1234");
		help = test.addPrescription("kluski", "45263ae3d04964189f2477ebabcdb283", "wcinaj pan", "2017-08-15", "Medicine1", 1, 1);
		
        System.out.println(help);
    }
    
}
