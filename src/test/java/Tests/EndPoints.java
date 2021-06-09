package Tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;

public final class EndPoints {
    public static final String User_bifrost = "vmcbtest";
    public static final String Password_bifrost = "Ghjdthrf123!";
    public static final String BasePath_bifrost = "http://vm-cb-bifrost.kld.neolant:8383/rapi2";

    //public static final String User_bifrost = "neolant";
    //public static final String Password_bifrost = "neolant";
    //public static final String BasePath_bifrost = "http://esod-ext.kld.neolant:8084/rapi2"; // Автотесты


    public static final String REST_ContentType = "application/json";
    public static final int REST_HttpStatus_OK = 200;
    public static final int REST_HttpStatus_Send = 201;

    public static final String FileForMEssages = "G:\\1\\Tests\\63276 Кварц периодические операции\\Kvit_deliver3.zip";

    public Long GetFileSize (String FileName) {
        File file = new File(FileName);
        return  file.length();
    }
    /// Подготовка исходящего сообщения c файлом
    public final String GetJsonFromHashMapMessage_WithFileInBody(String FileName) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Task", "Zadacha_1-28");
        map.put("GroupId", null);
        map.put("Title", "тест отправки");
        map.put("Text", "тестовое отправка в фронт через ун рест");
        map.put("Files", Arrays.asList(new LinkedHashMap<String, Object>() {{
                    put("Name", "OKAXBRL1Reporting.zip.enc");
                    put("Encrypted", true);
                    put("SignedFile", null);
                    put("RepositoryType", "http");
                    put("Size", GetFileSize(FileName));
                }})
        );
        map.put("Receivers", Arrays.asList());

        /// Преобразование мапы к виду JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    /// Подготовка исходящего сообщения без файлов
    public final String GetJsonFromHashMapMessage_NotFileInBody(String FileName) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Task", "Zadacha_1-28");
        map.put("GroupId", null);
        map.put("Title", "тест отправки");
        map.put("Text", "тестовое отправка в фронт через ун рест");
         map.put("Files", Arrays.asList());
        map.put("Receivers", Arrays.asList());

        /// Преобразование мапы к виду JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
