package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

public class TestApi {
/*
    /// Тестирование  Универсальный Rest
    /// Step1 - POST "/messages"  Создает новое сообщение
    /// Step2 - POST "/messages/:MessageId/files/:FileId/createUploadSession" Создаем сессию загрузки
    /// Step3 - PUT "/messages/:MessageId/files/:FileId" Отправляем файл
    /// Step4 - POST "/messages" Завершаем сессию
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_POST_CreateMessage() throws IOException, InterruptedException {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "";
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        Test_Path = "/messages";
        Response response = baseTestMetod.POST_Method_ReturnResponeAsString(new EndPoints().GetJsonFromHashMapMessage(EndPoints.FileForMEssages),EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Создание сообщения");
        /// Вытаскиваем Id сообщения
        String IdMessages = response.jsonPath().getString("Id");
        /// Вытаскиваем Id файлов
        List<String> IdFiles = response.jsonPath().getList("Files.Id");
        //IdFiles.forEach(s -> System.out.println("Id Files :" + s));
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сессию загрузки
        Test_Path = "/messages/" + IdMessages + "/files/" +IdFiles.get(0) + "/createUploadSession";
        Response response_sess = baseTestMetod.POST_Method_ReturnResponeAsString("",EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_sess.getStatusCode(), "Step 2 : Ошибка - Сессия загрузки");
        ////////////////////////////////////////////////////////////////////////
        /// Отправляем файл {{protocol}}://{{host}}/rapi2/messages/:MessageId/files/:FileId
        Test_Path ="/messages/" + IdMessages + "/files/" +IdFiles.get(0);
        Response response_send = BaseTestMetod.PUT_Method_ReturnResponeAsString(EndPoints.FileForMEssages,EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_Send , response_send.getStatusCode(), "Step 3 : Ошибка - Отправка файла");
        ////////////////////////////////////////////////////////////////////////
        /// Завершаем загрузку
        Test_Path = "/messages/" + IdMessages;
        Response response_endsess = baseTestMetod.POST_Method_ReturnResponeAsString("",EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_endsess.getStatusCode(), "Step 4 : Ошибка - Закрытие сессии");
    }

    /// Тестирование  Универсальный Rest
    /// GET "/dictionaries"  Возвращает список справочников
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_Get_dictionaries() {
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "/dictionaries";
        /// Отправка запроса
        Response response = baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        /// Проверка на формат даты
        /// True = 2020-02-11T10:58:03Z , False = 2018-11-19T14:42:56.2010881
        String res = response.body().asString().replaceAll("^.|.$", ""); /// Убираем скобки в начале и в конце строки
        JSONObject jo = new JSONObject(res); /// Парсим JSON
        String Data_For_Test =jo.get("Date").toString().replaceAll("[0-9]","5");

        /// Валидация
        Assertions.assertEquals(Data_For_Test,"5555-55-55T55:55:55Z", "Формат даты не верен");
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Ответ сервера не 200");
        Assertions.assertTrue(jo.has("Id"), "Отсутствует поле Id");
        Assertions.assertTrue(jo.has("Text"), "Отсутствует поле Text");
        Assertions.assertTrue(jo.has("Date"), "Отсутствует поле Date");
    }

    /// Тестирование  Универсальный Rest
    /// GET "/dictionaries/{dictId}"  Возвращает 100 записей конкретного справочника в виде json-массива
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_Get_dictionaries_dictid() {
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "/dictionaries/e4a12700-1420-4b6c-89c1-00a69878887b";
        /// Отправка запроса
        Response response = baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        JSONObject jo = new JSONObject(response.body().asString()); /// Парсим JSON
        /// Валидация
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Ответ сервера не 200");
    }


    /// Тестирование  Универсальный Rest
    /// Проверка Удаление сообщения
    /// Step1 - POST "/messages"  Создает новое сообщение
    /// Step2 - Delete "/messages/:MessageId Удаляем сообщение
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_Delete_Message() {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "";
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        Test_Path = "/messages";
        Response response = baseTestMetod.POST_Method_ReturnResponeAsString(new EndPoints().GetJsonFromHashMapMessage(EndPoints.FileForMEssages),EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Создание сообщения");
        /// Вытаскиваем Id сообщения
        String IdMessages = response.jsonPath().getString("Id");
        //IdFiles.forEach(s -> System.out.println("Id Files :" + s));
        /// Удаляем Сообщение
        Test_Path = "/messages/" + IdMessages;
        Response response_endsess = baseTestMetod.Delete_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_endsess.getStatusCode(), "Step 2 : Ошибка - Удаление сообщения");


    /// Тестирование  Универсальный Rest
    /// Проверка Удаление файла или сессии загрузки
    /// Step1 - POST "/messages"  Создает новое сообщение
    /// Step2 - POST "/messages/:MessageId/files/:FileId/createUploadSession" Создаем сессию загрузки
    /// Step3 - DELETE "/messages/:MessageId/files/:FileId" Удаление файла или сессии загрузки
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_Delete_Message() {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "";
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        Test_Path = "/messages";
        Response response = baseTestMetod.POST_Method_ReturnResponeAsString(new EndPoints().GetJsonFromHashMapMessage(EndPoints.FileForMEssages),EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Создание сообщения");
        /// Вытаскиваем Id сообщения
        String IdMessages = response.jsonPath().getString("Id");
        /// Вытаскиваем Id файлов
        List<String> IdFiles = response.jsonPath().getList("Files.Id");
        //IdFiles.forEach(s -> System.out.println("Id Files :" + s));
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сессию загрузки
        Test_Path = "/messages/" + IdMessages + "/files/" +IdFiles.get(0) + "/createUploadSession";
        Response response_sess = baseTestMetod.POST_Method_ReturnResponeAsString("",EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_sess.getStatusCode(), "Step 2 : Создание сессии загрузки");
        /// Удаляем Сессию загрузки
        Test_Path = "/messages/" + IdMessages + "/files/" + IdFiles.get(0);
        Response response_del= baseTestMetod.Delete_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_del.getStatusCode(), "Step 3 : Ошибка - Удаление файла или сессии загрузки");
    }*/

    /// Тестирование  Универсальный Rest
    /// Проверка Скачать сообщение
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    @Epic("API Tests")
    @Feature("API Test Messages")
    @Story("/messages")
    @Description("Получаем сообщение")
    public void UnRest_Metod_Get_MessageId() {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path;
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        Test_Path = "/messages";
        Response response = baseTestMetod.POST_Method_ReturnResponeAsString(new EndPoints().GetJsonFromHashMapMessage_WithFileInBody(EndPoints.FileForMEssages),EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Создание сообщения");
        /// Вытаскиваем Id сообщения
        String IdMessages = response.jsonPath().getString("Id");
        /// Вытаскиваем Id файлов
        List<String> IdFiles = response.jsonPath().getList("Files.Id");
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сессию загрузки
        Test_Path = "/messages/" + IdMessages + "/files/" +IdFiles.get(0) + "/createUploadSession";
        Response response_sess = baseTestMetod.POST_Method_ReturnResponeAsString("",EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_sess.getStatusCode(), "Step 2 : Ошибка - Сессия загрузки");
        ////////////////////////////////////////////////////////////////////////
        /// Отправляем файл {{protocol}}://{{host}}/rapi2/messages/:MessageId/files/:FileId
        Test_Path ="/messages/" + IdMessages + "/files/" +IdFiles.get(0);
        Response response_send = baseTestMetod.PUT_Method_ReturnResponeAsString(EndPoints.FileForMEssages,EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_Send , response_send.getStatusCode(), "Step 3 : Ошибка - Отправка файла");

        ////////////////////////////////////////////////////////////////////////
        /// Завершаем Сообщение
        Test_Path = "/messages/" + IdMessages;
        Response response_endsess = baseTestMetod.POST_Method_ReturnResponeAsString("",EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_endsess.getStatusCode(), "Step 4 : Ошибка - Закрытие сессии");

        /// Скачиваем сообщение
        Test_Path = "/messages/" + IdMessages + "/download";
        Response response_download= baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_download.getStatusCode(), "Step 5 : Ошибка - Cкачать сообщение");
        Assertions.assertTrue(response_download.body().asString().contains("OKAXBRL1Reporting.zip.enc"));
    }
}

