package Tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

public class RestAssuredRequestFilter implements Filter {
    private static final Log log = LogFactory.getLog(RestAssuredRequestFilter.class);

    @Attachment(value = "Данные : \n")
    public String logRequest(String stream) {
        return stream;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        Prettifier prettifier = new Prettifier();
        String prettyResponseBody = prettifier.getPrettifiedBodyIfPossible(response, response.getBody());
        logRequest("Запрос : \n"+requestSpec.getURI()+"\nBody : \n"+requestSpec.getBody()+"\n"+ "\n\nОтвет : \n Статус :" + response.statusCode()+"\n\nBody: \n"+
                prettyResponseBody);//+
        return response;
    }
}