
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
    
    private String addPatient(String login, String token, String sex, int age, String bloodType, String pesel, String firstName, String lastName, String phone, String email, String city, String street, String buildingNumber, String flatNumber, String zipCode) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("sex", sex)
                    .put("age", age)
                    .put("bloodType", bloodType)
                    .put("pesel", pesel)
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("phone", phone)
                    .put("email", email)
                    .put("city", city)
                    .put("street", street)
                    .put("buildingNumber", buildingNumber)
                    .put("flatNumber", flatNumber)
                    .put("zipCode", zipCode);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url = "http://localhost:8084/Panaceum/patient/add";
        //String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/patient/add";

        return dataTransfer(json, url);
    }
    
    private String addDoctor(String login, String token, String speciality, String licenceNumber, String pesel, String firstName, String lastName, String phone, String email, String city, String street, String buildingNumber, String flatNumber, String zipCode, String doctorLogin) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("speciality", speciality)
                    .put("licenceNumber", licenceNumber)
                    .put("pesel", pesel)
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("phone", phone)
                    .put("email", email)
                    .put("city", city)
                    .put("street", street)
                    .put("buildingNumber", buildingNumber)
                    .put("flatNumber", flatNumber)
                    .put("zipCode", zipCode)
                    .put("doctorLogin", doctorLogin);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url = "http://localhost:8084/Panaceum/doctor/add";
        //String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/doctor/add";

        return dataTransfer(json, url);
    }
    
    private String addMedicine(String login, String token, String name, String activeSubstance) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("name", name)
                    .put("activeSubstance", activeSubstance);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url = "http://localhost:8084/Panaceum/medicine/add";
        //String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/medicine/add";

        return dataTransfer(json, url);
    }
    
    private String addHospital(String login, String token, String name, String regon, String phone, String city, String street, String buildingNumber, String flatNumber, String zipCode) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("name", name)
                    .put("REGON", regon)
                    .put("phone", phone)
                    .put("city", city)
                    .put("street", street)
                    .put("buildingNumber", buildingNumber)
                    .put("flatNumber", flatNumber)
                    .put("zipCode", zipCode);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url = "http://localhost:8084/Panaceum/hospital/add";
        //String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/hospital/add";

        return dataTransfer(json, url);
    }
    
    private String addHistory(String login, String token, int patientId, int doctorId, int hospitalId,
            String nurseCard, String finalCard, String pressure, String pulse, float temperature, float mass,
            float height, String content, String idc10, boolean firstIllnes, String symptoms, String recognition,
            String treatment, boolean[] factor, String factor5Note, String factor6Note, String factor7Note, String notepad) {
        JSONObject json = null;
        try {System.err.println(factor5Note + "\n" + factor6Note + "\n" + factor7Note);
            json = new JSONObject()
                    .put("login", login)
                    .put("token", token)
                    .put("patientId", patientId)
                    .put("doctorId", doctorId)
                    .put("hospitalId", hospitalId)
                    .put("nurseCard", nurseCard)
                    .put("finalCard", finalCard)
                    .put("pressure", pressure)
                    .put("pulse", pulse)
                    .put("temperature", temperature)
                    .put("mass", mass)
                    .put("height", height)
                    .put("content", content)
                    .put("idc10", idc10)
                    .put("firstIllnes", firstIllnes)
                    .put("symptoms", symptoms)
                    .put("interviewRecognition", recognition)
                    .put("treatment", treatment)
                    .put("factor1", factor[0])
                    .put("factor2", factor[1])
                    .put("factor3", factor[2])
                    .put("factor4", factor[3])
                    .put("factor5", factor[4])
                    .put("factor5Note", factor5Note)
                    .put("factor6", factor[5])
                    .put("factor6Note", factor6Note)
                    .put("factor7", factor[6])
                    .put("factor7Note", factor7Note)
                    .put("factor8", factor[7])
                    .put("factor9", factor[8])
                    .put("factor10", factor[9])
                    .put("factor11", factor[10])
                    .put("factor12", factor[11])
                    .put("factor13", factor[12])
                    .put("factor14", factor[13])
                    .put("factor15", factor[14])
                    .put("factor16", factor[15])
                    .put("factor17", factor[16])
                    .put("factor18", factor[17])
                    .put("factor19", factor[18])
                    .put("factor20", factor[19])
                    .put("factor21", factor[20])
                    .put("factor22", factor[21])
                    .put("factor23", factor[22])
                    .put("factor24", factor[23])
                    .put("factor25", factor[24])
                    .put("factor26", factor[25])
                    .put("factor27", factor[26])
                    .put("factor28", factor[27])
                    .put("factor29", factor[28])
                    .put("factor30", factor[29])
                    .put("notepad", notepad);
            System.err.println(json);
        } catch (JSONException e) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url = "http://localhost:8084/Panaceum/patient/addHistory";
        //String url = "http://panaceum.iiar.pwr.edu.pl:8080/Panaceum/patient/addHistory";

        return dataTransfer(json, url);
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();
        String help = "";

        //help = test.register("kluski", "1234");
        //help = test.login("kluski", "1234");
        //help = test.addPrescription("kluski", "45263ae3d04964189f2477ebabcdb283", "wcinaj pan", "2017-08-15", "Medicine1", 1, 1);
        //help = test.addDoctor("kluski", "1", "spe", "1234567898", "12345678913", "opo", "asd", "65", "em", "ci", "st", "bu", "fl", "11-111", "sexDoctor");
        //help = test.addMedicine("kluski", "1", "testomed", "dużo");
        //help = test.addHospital("testo", "1", "na", "123456789", "ph", "ci", "st", "bu", "fl", "11-111");
        boolean[] factors = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
        help = test.addHistory("kluski", "1", 1, 1, 1, "", "", "", "", 0, 0, 0, "", "abc", true, "", "", "", factors, "", "", "", "");
        
        System.out.println(help);
    }

}
