package Tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Epic("API Tests")
@Feature("API Test Receipts")
public class Test_Receipts {

    public final String IDMessage = "A43E9B4A-974A-489A-8957-7D10BC789990";

/*    /// Тестирование  Универсальный Rest
    /// Проверка Данные о всех квитанциях сообщения
    /// Created by : V. Kuldyaev 01.06.2021
    @Test
    public void UnRest_Metod_Get_MessageId() {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        //System.out.println(IDMessage);
        String Test_Path = "";
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        Test_Path = "/messages/"+IDMessage+"/receipts";
        Response response = baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Данные о всех квитанциях");
        //System.out.println(response.body().asString());
        //String res = response.body().asString().replaceAll("^.|.$", "");

        //List<ArrayList<String>> IdFiles =  response.jsonPath().getList("Files.Id");
        //IdFiles.forEach(s -> s.forEach( z -> System.out.println("Id Files :" + z)));
        List<ArrayList<String>> IdFiles =  response.jsonPath().getList("Id");
        Assertions.assertEquals(IdFiles.stream().count(),18);
        //System.out.println(IdFiles.stream().count());
    }*/

    /// Тестирование  Универсальный Rest
    /// Проверка Данные о квитанции сообщения
    /// Created by : V. Kuldyaev 01.06.2021

    @Test
    @Story("/messages/IDMessage/receipts")
    @Description("Получаем квитанции")
    public void UnRest_Metod_Get_RecepiesId() {
        ///
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        //System.out.println(IDMessage);
        String Test_Path;
        ////////////////////////////////////////////////////////////////////////
        /// Получаем все квитанции
        Test_Path = "/messages/"+IDMessage+"/receipts";
        Response response = baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Step 1 : Ошибка - Данные о всех квитанциях");
        List<ArrayList<String>> IdFiles =  response.jsonPath().getList("Id");
        ////////////////////////////////////////////////////////////////////////
        /// Получаем первую квитанцию
        //IdFiles.set(0,"a048c3e5-f6ef-4f3a-bf24-0e65eb5a7b96");
        Test_Path = "/messages/"+IDMessage+"/receipts/a048c3e5-f6ef-4f3a-bf24-0e65eb5a7b96";
        //Test_Path = "/messages/"+IDMessage+"/receipts/"+IdFiles.get(0);
        Response response_kvit = baseTestMetod.GET_Method_ReturnResponeAsString(EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response_kvit.getStatusCode(), "Step 2 : Ошибка - Данные о 1-ой квитанции");
        List<String> IdFilesName =  response_kvit.jsonPath().getList("Files.Name");
        //IdFilesName.forEach(s -> System.out.println("Id Files :" + s));
        ///System.out.println(response_kvit.body().asString());
        List<String> InArray = Arrays.asList("StatusInfo.xml.sig", "MPSO_IES1.xml.sig", "MPSO_IES1.xml", "StatusInfo.xml");
        Assertions.assertTrue(IdFilesName.stream().anyMatch(InArray :: contains), "Step 3 : Ошибка - Данные не сходятся");
        //Allure.addAttachment("Response log",response_kvit.asString());
    }

}
