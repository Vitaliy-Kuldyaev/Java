package Tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;

public class RestAssuredRequestFilter implements Filter {
    private static final Log log = LogFactory.getLog(RestAssuredRequestFilter.class);

    @Attachment(value = "Данные по запросу: {zapros} \n")
    public String logRequest(String zapros,String stream) {
        return stream;
    }
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        //Allure.addAttachment("Header",requestSpec.getHeaders().toString());

        Prettifier prettifier = new Prettifier();
        //String prettyResponseBody = prettifier.getPrettifiedBodyIfPossible(response, response.getBody());
        String prettyResponseBody = response.getBody() == null ? "пусто" : prettifier.getPrettifiedBodyIfPossible(response, response.getBody());

        Prettifier prettifierReq = new Prettifier();
        String prettyReqBody = requestSpec.getBody() == null ? "пусто" : prettifierReq.getPrettifiedBodyIfPossible(requestSpec);

        String subStr = requestSpec.getMethod()+"  "+ requestSpec.getURI();
        subStr = subStr.length() > 100 ? subStr.substring(0, 100) + " ..." : subStr;
        logRequest(subStr,"Header :\n"+requestSpec.getHeaders().toString()+"\n\nЗапрос : \n"+requestSpec.getMethod()+"  "+requestSpec.getURI()+"\nBody : \n"+prettyReqBody+"\n"+ "\n\nОтвет : \n Статус :" + response.statusCode()+"\n\nBody: \n"+
                prettyResponseBody);//+
        return response;
    }
}