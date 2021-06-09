package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class BaseTestMetod {
    public int ServerRespose=0;
    public String  PostOutStr="";


    static Response POST_Method_ReturnResponeAsString (String bodymessage, String user, String pass, String context, String path) {
        Response response = RestAssured.given()
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
        return response;
    }

    static Response PUT_Method_ReturnResponeAsString (String FileMessageName, String user, String pass, String context, String path) {
                File rrr = new File(FileMessageName);
                Response response = RestAssured.given()
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
        return response;    }

    static Response GET_Method_ReturnResponeAsString (String user, String pass, String context, String path) {
        Response response = RestAssured.given()
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
        return response;
    }

    static Response Delete_Method_ReturnResponeAsString (String user, String pass, String context, String path) {
        Response response = RestAssured.given()
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
        return response;
    }

}
