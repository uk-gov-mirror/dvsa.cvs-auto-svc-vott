package vott.testhistory;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.config.VottConfiguration;
import vott.database.VehicleRepository;
import vott.database.connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

public class RetrieveTestHistoryAndVehicleDataClientCredsTokenTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private String validVINNumber = "";
    private final String validRegMark = "AB15XYZ";

    private String invalidVINNumber = "T123456789";

    private VehicleRepository vehicleRepository;
    vott.models.dao.Vehicle vehicleUpsert = newTestVehicle();

    private List<Integer> deleteOnExit;

    @Before
    public void Setup() {

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleRepository = new VehicleRepository(connectionFactory);

        //Upsert Vehicle
        int vehicleID = vehicleRepository.fullUpsert(vehicleUpsert);
        validVINNumber = vehicleUpsert.getVin();

        deleteOnExit = new ArrayList<>();
        deleteOnExit.add(vehicleID);

        RestAssured.baseURI = VottConfiguration.local().getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/vehicle";
        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            vehicleRepository.delete(primaryKey);
        }
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryTest() {

        System.out.println("Valid access token: " + token);

        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("VehicleRegMark", validRegMark).

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
        givenAuth(token + 1, xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", validVINNumber)
            .queryParam("VehicleRegMark", validRegMark).

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
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", validRegMark).

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
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", validVINNumber).

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
        givenAuth(token)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", validRegMark)
            .queryParam("vinNumber", validVINNumber).

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
        givenAuth(token, xApiKey + "badkey")
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", validRegMark)
            .queryParam("vinNumber", validVINNumber).

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
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", validVINNumber)
            .queryParam("VehicleRegMark", validRegMark).

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
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", invalidVINNumber)
            .queryParam("VehicleRegMark", validRegMark).

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

        //prep request
        givenAuth(token, xApiKey)
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

    private vott.models.dao.Vehicle newTestVehicle() {
        vott.models.dao.Vehicle vehicle = new vott.models.dao.Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("T123456");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }

}
