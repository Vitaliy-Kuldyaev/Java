package Tests;

import Tests.Utils.BaseTestMetod;
import Tests.Utils.EndPoints;
import Tests.Utils.GetTokenAuth;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;

public class AvatarTests {
    @Test
    @Epic("API Tests")
    @Feature("API Avatar Tests")
    @Story("API Test - AvatarSet")
    @Description("Устанавливает аватар пользователя")
    public void AvatarSet() {
        ///
        GetTokenAuth GetTokenAuth = new GetTokenAuth();
        String TokenAuth =GetTokenAuth.GetTokenAuth();
        System.out.println(TokenAuth);
        BaseTestMetod baseTestMetod = new BaseTestMetod();
        String Test_Path = "/Avatar/Get";
        ////////////////////////////////////////////////////////////////////////
        /// Создаем сообщение
        //Test_Path = "/messages";
       // Response response = baseTestMetod.POST_Method_ReturnResponeAsString(new EndPoints().GetJsonFromHashMapMessage_WithFileInBody(EndPoints.FileForMEssages),EndPoints.User_bifrost,EndPoints.Password_bifrost,EndPoints.REST_ContentType,EndPoints.BasePath_bifrost+ Test_Path);
        //String Token = response.jsonPath().getString("Token");
        //Assertions.assertEquals(EndPoints.REST_HttpStatus_OK , response.getStatusCode(), "Ошибка - Токен не найден");
        //System.out.println(Token);
        //Assertions.assertTrue(Token.length()>10,"Ошибка - Токен не найден");
    }
}
