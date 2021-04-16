package vott.e2e;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.com.google.common.reflect.TypeToken;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.TestResultRepository;
import vott.database.VehicleRepository;
import vott.database.connection.ConnectionFactory;
import vott.json.GsonInstance;
import vott.models.dao.Vehicle;
import vott.models.dto.enquiry.TestResult;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

public class E2eTest {

    private VottConfiguration configuration;

    private Gson gson;

    private FieldGenerator fieldGenerator;

    private TokenService v1ImplicitTokens;

    private VehicleRepository vehicleRepository;
    private TestResultRepository testResultRepository;

    @Before
    public void setUp() throws Exception {
        configuration = VottConfiguration.local();

        gson = GsonInstance.get();

        fieldGenerator = new FieldGenerator();

        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);

        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

        vehicleRepository = new VehicleRepository(connectionFactory);

        testResultRepository = new TestResultRepository(connectionFactory);
    }

    @Test
    public void e2eTestHgv() {
        TechRecordPOST hgvTechRecord = hgvTechRecord();
        CompleteTestResults hgvTestResult = hgvTestResult(hgvTechRecord);

        e2eTest(hgvTechRecord, hgvTestResult);
    }

    @Test
    public void e2eTestPsv() {
        TechRecordPOST psvTechRecord = psvTechRecord();
        CompleteTestResults psvTestResult = psvTestResult(psvTechRecord);

        e2eTest(psvTechRecord, psvTestResult);
    }

    @Test
    public void e2eTestTrl() {
        TechRecordPOST trlTechRecord = trlTechRecord();
        CompleteTestResults trlTestResult = trlTestResult(trlTechRecord);

        e2eTest(trlTechRecord, trlTestResult);
    }

    private void e2eTest(TechRecordPOST techRecord, CompleteTestResults testResult) {
        postTechRecord(techRecord);
        postTestResult(testResult);

        String vin = testResult.getVin();

        with().timeout(Duration.ofSeconds(30)).await().until(vehicleIsPresentInDatabase(vin));
        with().timeout(Duration.ofSeconds(30)).await().until(testResultIsPresentInDatabase(vin));

        vott.models.dto.enquiry.Vehicle actualVehicle = retrieveVehicle(vin);
        List<TestResult> actualTestResults = retrieveTestResults(vin);

        assertNotNull(actualVehicle);
        assertEquals(vin, actualVehicle.getVin());

        assertNotNull(actualTestResults);
        assertThat(actualTestResults).hasSize(1);

        // match on arbitrary field tester name, which we have previously set to a UUID in matchKeys
        assertEquals(testResult.getTesterName(), actualTestResults.get(0).getTester().getName());
    }

    private TechRecordPOST hgvTechRecord() {
        return randomizeKeys(readTechRecord("src/test/resources/technical-records_hgv.json"));
    }

    private TechRecordPOST psvTechRecord() {
        return randomizeKeys(readTechRecord("src/test/resources/technical-records_psv.json"));
    }

    private TechRecordPOST trlTechRecord() {
        return randomizeKeys(readTechRecord("src/test/resources/technical-records_trl.json"));
    }

    private CompleteTestResults hgvTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/test/resources/test-results_hgv.json"));
    }

    private CompleteTestResults psvTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/test/resources/test-results_psv.json"));
    }

    private CompleteTestResults trlTestResult(TechRecordPOST techRecord) {
        return matchKeys(techRecord, readTestResult("src/test/resources/test-results_trl.json"));
    }

    @SneakyThrows(IOException.class)
    private TechRecordPOST readTechRecord(String path) {
        return gson.fromJson(
            Files.newBufferedReader(Paths.get(path)),
            TechRecordPOST.class
        );
    }

    @SneakyThrows(IOException.class)
    private CompleteTestResults readTestResult(String path) {
        return gson.fromJson(
            Files.newBufferedReader(Paths.get(path)),
            CompleteTestResults.class
        );
    }

    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();

        techRecord.setVin(vin);

        return techRecord;
    }

    private CompleteTestResults matchKeys(TechRecordPOST techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());

        // test result ID is not kept on enquiry-service retrievals: need a way to uniquely identify within test suite
        testResult.setTesterName(UUID.randomUUID().toString());

        testResult.setVin(techRecord.getVin());

        return testResult;
    }

    private void postTechRecord(TechRecordPOST techRecord) {
        String techRecordJson = gson.toJson(techRecord);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(v1ImplicitTokens.getBearerToken())
                .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                .body(techRecordJson)
                .post("/vehicles")
                .thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);

        assertThat(response.statusCode()).isBetween(200, 300);
    }

    private void postTestResult(CompleteTestResults testResult) {
        String testResultJson = gson.toJson(testResult);

        Response response;
        int statusCode;

        int tries = 0;
        int maxRetries = 3;
        do {
            response = givenAuth(v1ImplicitTokens.getBearerToken())
                .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
                .body(testResultJson)
                .post("/test-results")
                .thenReturn();
            statusCode = response.statusCode();
            tries++;
        } while (statusCode >= 500 && tries < maxRetries);

        assertThat(response.statusCode()).isBetween(200, 300);
    }

    private vott.models.dto.enquiry.Vehicle retrieveVehicle(String vinNumber) {
        String bearerToken = v1ImplicitTokens.getBearerToken();

        Response response = givenAuth(bearerToken, configuration.getApiKeys().getEnquiryServiceApiKey())
            .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
            .accept(ContentType.JSON)
            .queryParam("vinNumber", vinNumber)
            .get("v1/enquiry/vehicle")
            .thenReturn();

        assertThat(response.statusCode()).isBetween(200, 300);

        return gson.fromJson(response.asString(), vott.models.dto.enquiry.Vehicle.class);
    }

    private List<TestResult> retrieveTestResults(String vinNumber) {
        String bearerToken = v1ImplicitTokens.getBearerToken();

        Response response = givenAuth(bearerToken, configuration.getApiKeys().getEnquiryServiceApiKey())
            .baseUri(configuration.getApiProperties().getBranchSpecificUrl())
            .accept(ContentType.JSON)
            .queryParam("vinNumber", vinNumber)
            .get("v1/enquiry/testResults")
            .thenReturn();

        assertThat(response.statusCode()).isBetween(200, 300);

        return gson.fromJson(response.asString(), new TypeToken<List<TestResult>>(){}.getType());
    }


    private Callable<Boolean> vehicleIsPresentInDatabase(String vin) {
        return () -> {
            List<Vehicle> vehicles = vehicleRepository.select(String.format("SELECT * FROM `vehicle` WHERE `vin` = '%s'", vin));
            return !vehicles.isEmpty();
        };
    }

    private Callable<Boolean> testResultIsPresentInDatabase(String vin) {
        return () -> {
            List<vott.models.dao.TestResult> testResults = testResultRepository.select(String.format(
                "SELECT `test_result`.*\n"
                + "FROM `vehicle`\n"
                + "JOIN `test_result`\n"
                + "ON `test_result`.`vehicle_id` = `vehicle`.`id`\n"
                + "WHERE `vehicle`.`vin` = '%s'", vin
            ));
            return !testResults.isEmpty();
        };
    }
}
