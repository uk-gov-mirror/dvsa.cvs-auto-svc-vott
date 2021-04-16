package vott.testhistory;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.ConfigurationProvider;
import vott.config.VottConfiguration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RetrieveTestHistoryAndVehicleDataClientCredsTokenTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = ConfigurationProvider.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private final String validVINNumber = "T12765432";
    private final String validTestNumber = "W01A00229";

    private final String invalidVINNumber = "T12765431";
    private final String invalidTestNumber = "W01A00222";

    @Before
    public void Setup() {
        RestAssured.baseURI = ConfigurationProvider.local().getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/vehicle";
        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryTest() {

        System.out.println("Valid access token: " + token);

        given()//.log().all()
                .header("authorization", "Bearer " + token)
                .header("x-api-key", xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", "T12765432")
                .queryParam("VehicleRegMark", "AB15XYZ").

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(200);
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryBadJwtTokenTest() {

        System.out.println("Using invalid token: " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token + 1)
            .header("x-api-key", xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", "T12765432")
            .queryParam("VehicleRegMark", "AB15XYZ").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(403).
            body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNoVinNumberTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("x-api-key", xApiKey)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", "AB15XYZ").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(400);
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNoVehicleRegMarkTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("x-api-key", xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", "T12765432").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(400);
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNoAPIKeyTest() {

        System.out.println("Valid access token " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", "AB15XYZ")
            .queryParam("vinNumber", "T12765432").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(403).
            body("message", equalTo("Forbidden"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryInvalidAPIKey() {

        System.out.println("Valid access token " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("x-api-key", xApiKey + "badkey")
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", "AB15XYZ")
            .queryParam("vinNumber", "T12765432").

        //send request
        when().//log().all().
            get().

        //verification
            then().//log().all().
            statusCode(403).
            body("message", equalTo("Forbidden"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryVehicleRegMarkDoesntExistTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("x-api-key", xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", "T12765432")
            .queryParam("VehicleRegMark", "AB15XYZ").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(404).
            body(equalTo("NoSuchKey"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryVinNumberDoesntExistTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        given()//.log().all()
            .header("authorization", "Bearer " + token)
            .header("x-api-key", xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", "T12765431")
            .queryParam("VehicleRegMark", "AB15XYZ").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(404).
            body(equalTo("NoSuchKey"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNonPrintableCharsParamsTest() {

        System.out.println("Valid access token: " + token);

        //TODO add control chars test i.e. ctrl+c etc.

        //prep request
        given()//.log().all()
                .header("authorization", "Bearer " + token)
                .header("x-api-key", xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", "T12765431")
                .queryParam("testNumber", "W01A00229").

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("NoSuchKey"));
    }

}


