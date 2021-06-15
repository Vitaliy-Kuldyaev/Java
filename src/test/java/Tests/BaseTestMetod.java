package Tests;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class BaseTestMetod {
    public int ServerRespose=0;
    public String  PostOutStr="";


     public Response POST_Method_ReturnResponeAsString (String bodymessage, String user, String pass, String context, String path) {
         return  RestAssured.given()
                .auth()
                .preemptive()
                .basic(user, pass)
                .header("Accept", "application/json")
                .contentType(context)
                .and()
                .body(bodymessage)
                .when()
                .log().all()
                .post(path)
                .then()
                .log().all()
                .extract().response();
        /// Подготовка Строки для парсинга
        //String res = response.body().asString().replaceAll("^.|.$", ""); /// Убираем скобки в начале и в конце строки
        //String res = response.body().asString().replaceAll("^.|.$", ""); /// Убираем скобки в начале и в конце строки
        //String res = response.body().asString();
        /// Отдаем ответ сервера
        //ServerRespose = response.getStatusCode();
    }

    public Response PUT_Method_ReturnResponeAsString (String FileMessageName, String user, String pass, String context, String path) {
                File rrr = new File(FileMessageName);
                return RestAssured.given()
                .auth()
                .preemptive()
                .basic(user, pass)
                .header("Accept", "*/*")
                .header("Content-type", "application/octet-stream")
                .header("Access-Control-Expose-Headers", "Content-Range")
                .header("Content-Range", "bytes 0-"+(rrr.length()-1)+"/"+ rrr.length())
                //.header("Content-Range", "100")
                .header("Connection", "keep-alive")
                .header("Expect", "100-continue")
                .body(rrr)
                //.log().all()
                .put(path)
                .then()
                //.log().all()
                .extract().response();
        }

    public Response GET_Method_ReturnResponeAsString (String user, String pass, String context, String path) {
        return RestAssured.given()
                //.filter(new AllureRestAssured().setRequestTemplate(“custom-http-request.ftl”).setResponseTemplate(“custom-http-response.ftl”))
                //.config(RestAssuredConfig.config().logConfig(LogConfig.))
                .auth()
                .preemptive()
                .basic(user, pass)
                .header("Accept", "application/json")
                .contentType(context)
                .log().all()
                .get(path)
                .then()
                .log().all()
                .extract().response();
/*
        try {
            Allure.addAttachment("Interface request response log",new FileInputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
*/
    }

    public Response Delete_Method_ReturnResponeAsString (String user, String pass, String context, String path) {
         return RestAssured.given()
                .auth()
                .preemptive()
                .basic(user, pass)
                .header("Accept", "application/json")
                .contentType(context)
                //.log().all()
                .delete(path)
                .then()
                //.log().all()
                .extract().response();

    }

}
