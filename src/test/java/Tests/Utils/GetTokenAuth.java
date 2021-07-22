package Tests.Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Console;
import java.io.InputStream;

public class GetTokenAuth  {
    public final String GetTokenAuth()
    {
        ReadDataFromJson ReadDataFromJson = new ReadDataFromJson();
        String user = ReadDataFromJson.ReadDataFromJson("bifrost_user");
        String pass= ReadDataFromJson.ReadDataFromJson("bifrost_pass");
        String path= ReadDataFromJson.ReadDataFromJson("bifrost_basePath");
        System.out.println("999999999999999999999999:"+path);
        Response response =
                RestAssured.given()
                        .filter(new RestAssuredRequestFilter())
                        .auth()
                        .preemptive()
                        .basic(user, pass)
                        .header("Accept", "application/json")
                        .contentType("application/json")
                        .and()
                        .body("{\n" +
                                "  Login: \"vmcbtest\",\n" +
                                "  Password: \"Ghjdthrf123!\"\n" +
                                "}")
                        .when()
                        .log().all()
                        .post(path+"/api/auth")
                        .then()
                        .log().all()
                        .extract().response();


        System.out.println(response.contentType());
        System.out.println(response.getBody().asString());

        String auth_token = response.path("authorization_token").toString();
        return auth_token;
    }
}
