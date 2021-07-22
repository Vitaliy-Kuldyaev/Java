package Tests.Utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.Locale;

public class ReadDataFromJson {
    public String ReadDataFromJson(String field)
    {
        if (field.trim().isEmpty() || field == null) {
            throw new NullPointerException("Empty field :" + field);
        }

        String resourceName = "/DataFile.json";
        InputStream filestream = ReadDataFromJson.class.getResourceAsStream(resourceName);
        if (filestream == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }
        JSONTokener tokener = new JSONTokener(filestream);
        JSONObject object = new JSONObject(tokener);
        return object.get(field).toString();
    }
}
