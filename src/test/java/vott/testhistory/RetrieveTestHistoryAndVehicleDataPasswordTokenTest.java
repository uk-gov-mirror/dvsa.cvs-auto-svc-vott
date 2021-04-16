package vott.testhistory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.*;
import vott.models.dto.enquiry.TechnicalRecord;
import vott.models.dto.enquiry.Vehicle;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

public class RetrieveTestHistoryAndVehicleDataPasswordTokenTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private  String validVINNumber = "";
    private final String validVehicleRegMark = "AB15XYZ";

    private final String invalidVINNumber = "T12765431";
    private final String invalidVehicleRegMark = "W01A00229";

    private VehicleRepository vehicleRepository;
    private TechnicalRecordRepository technicalRecordRepository;
    private MakeModelRepository makeModelRepository;
    private VehicleClassRepository vehicleClassRepository;
    private PSVBrakesRepository psvBrakesRepository;
    private AxlesRepository axlesRepository;
    private TyreRepository tyreRepository;

    vott.models.dao.Vehicle vehicleUpsert = newTestVehicle();
    MakeModel mm = newTestMakeModel();
    VehicleClass vc = newTestVehicleClass();
    vott.models.dao.TechnicalRecord tr = newTestTechnicalRecord();
    PSVBrakes psv = newTestPSVBrakes();
    Axles axles = newTestAxles();
    Tyre tyre = newTestTyre();


    @Before
    public void Setup() {
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/vehicle";
        this.token = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT).getBearerToken();

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleRepository = new VehicleRepository(connectionFactory);
        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);
        makeModelRepository = new MakeModelRepository(connectionFactory);
        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        psvBrakesRepository = new PSVBrakesRepository(connectionFactory);
        axlesRepository = new AxlesRepository(connectionFactory);

        //Upsert Vehicle
        int vehicleID = vehicleRepository.fullUpsert(vehicleUpsert);
        validVINNumber = vehicleUpsert.getVin();

        //Upsert MakeModel
        int mmId = makeModelRepository.partialUpsert(mm);

        //Upsert Vehicle Class
        int vcId = vehicleClassRepository.partialUpsert(vc);

        //upsert Tech Record
        tr.setVehicleID(String.valueOf(vehicleID));
        tr.setMakeModelID(String.valueOf(mmId));
        tr.setVehicleClassID(String.valueOf(vcId));
        int trId = technicalRecordRepository.fullUpsert(tr);

        //PSV Brakes
        psv.setTechnicalRecordID(String.valueOf(trId));
        psvBrakesRepository.fullUpsert(psv);

        //Upsert Tyre
        int tyreId = tyreRepository.partialUpsert(tyre);

        //Upsert Axles
        axles.setTechnicalRecordID(String.valueOf(trId));
        axles.setTyreID(String.valueOf(tyreId));
        axlesRepository.fullUpsert(axles);
    }


    @Test
    public void RetrieveVehicleDataAndTestHistoryUsingVinTest() {

        System.out.println("Valid access token: " + token);

        String response =
                givenAuth(token, xApiKey)
                        .header("content-type", "application/json")
                        .queryParam("vinNumber", validVINNumber).

                //send request
                when().//log().all().
                        get().

                //verification
                then().//log().all().
                        statusCode(200).
                        extract().response().asString();

        System.out.println(response);

        Gson gson = new GsonBuilder().create();

        Vehicle vehicle = gson.fromJson(response, Vehicle.class);

        TechnicalRecord technicalRecord1 = vehicle.getTechnicalrecords().get(0);

        assertThat(vehicle.getVin()).isEqualTo(vehicleUpsert.getVin());
        assertThat(vehicle.getVrmTrm()).isEqualTo(vehicleUpsert.getVrm_trm());
        assertThat(vehicle.getTrailerId()).isEqualTo(vehicleUpsert.getTrailerID());
        assertThat(vehicle.getSystemNumber()).isEqualTo(vehicleUpsert.getSystemNumber());


        assertThat(technicalRecord1.getNotes()).isEqualTo(tr.getNotes());
        assertThat(technicalRecord1.getWidth()).isEqualTo(tr.getWidth());
        assertThat(technicalRecord1.getHeight()).isEqualTo(tr.getHeight());
        assertThat(technicalRecord1.getLength()).isEqualTo(tr.getLength());
        assertThat(technicalRecord1.isOffRoad()).isEqualTo(tr.getOffRoad());
        assertThat(technicalRecord1.getRemarks()).isEqualTo(tr.getRemarks());
        assertThat(technicalRecord1.getCoifDate()).isEqualTo(tr.getCoifDate());
        assertThat(technicalRecord1.getRegnDate()).isEqualTo(tr.getRegnDate());
        assertThat(technicalRecord1.getBrakeCode()).isEqualTo(tr.getBrakeCode());
        assertThat(technicalRecord1.getCreatedAt()).isEqualTo(tr.getCreatedAt());
        assertThat(technicalRecord1.getDtpNumber()).isEqualTo(tr.getBrakes_dtpNumber());
        assertThat(technicalRecord1.getMakeModel().getMake()).isEqualTo("Isuzu");
        assertThat(technicalRecord1.getMakeModel().getModel()).isEqualTo("F06");
//        assertThat(technicalRecord1.getMakeModel().get(0).getDtpCode()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord1.getMakeModel().getBodyMake()).isEqualTo(null);
        assertThat(technicalRecord1.getMakeModel().getBodyModel()).isEqualTo(null);
        assertThat(technicalRecord1.getMakeModel().getChassisMake()).isEqualTo(null);
        assertThat(technicalRecord1.getMakeModel().getBodyTypeCode()).isEqualTo("x");
        assertThat(technicalRecord1.getMakeModel().getChassisModel()).isEqualTo(null);
        assertThat(technicalRecord1.getMakeModel().getModelLiteral()).isEqualTo(null);
        assertThat(technicalRecord1.getMakeModel().getBodyTypeDescription()).isEqualTo(mm.getBodyTypeDescription());
        assertThat(technicalRecord1.getMakeModel().getFuelPropulsionSystem()).isEqualTo(mm.getFuelPropulsionSystem());
        assertThat(technicalRecord1.getNoOfAxles()).isEqualTo(tr.getNoOfAxles());
        assertThat(technicalRecord1.getNtaNumber()).isEqualTo(tr.getNtaNumber());
        assertThat(technicalRecord1.getStatusCode()).isEqualTo(tr.getStatusCode());
//        assertThat(technicalRecord1.getUpdateType()).isEqualTo("techRecordUpdate"); // todo not asserting against this as not in scope
        assertThat(technicalRecord1.getTyreUseCode()).isEqualTo(tr.getTyreUseCode());
        assertThat(technicalRecord1.getApprovalType()).isEqualTo(tr.getApprovalType());
        assertThat(technicalRecord1.getCouplingType()).isEqualTo(tr.getCouplingType());
        assertThat(technicalRecord1.getEuroStandard()).isEqualTo(tr.getEuroStandard());
        assertThat(technicalRecord1.getFirstUseDate()).isEqualTo(tr.getFirstUseDate());
        assertThat(technicalRecord1.getFunctionCode()).isEqualTo(tr.getFunctionCode());
        assertThat(technicalRecord1.isRoadFriendly()).isEqualTo(tr.getRoadFriendly());
        assertThat(technicalRecord1.getVehicleClass().getCode()).isEqualTo("t");
        assertThat(technicalRecord1.getVehicleClass().getDescription()).isEqualTo("trailer");
        assertThat(technicalRecord1.getVehicleClass().getVehicleSize()).isEqualTo(null);
        assertThat(technicalRecord1.getVehicleClass().getVehicleType()).isEqualTo("trl");
        assertThat(technicalRecord1.getVehicleClass().getEuVehicleCategory()).isEqualTo(null);
        assertThat(technicalRecord1.getVehicleClass().getVehicleConfiguration()).isEqualTo("articulated");
        assertThat(technicalRecord1.getDispensations()).isEqualTo(tr.getDispensations());
        assertThat(technicalRecord1.getGrossGbWeight()).isEqualTo(tr.getGrossGbWeight());
        assertThat(technicalRecord1.getLastUpdatedAt()).isEqualTo(tr.getLastUpdatedAt());
        assertThat(technicalRecord1.getTrainGbWeight()).isEqualTo(tr.getTrainGbWeight());
        assertThat(technicalRecord1.getUnladenWeight()).isEqualTo(tr.getUnladenWeight());
        assertThat(technicalRecord1.getVariantNumber()).isEqualTo(tr.getVariantNumber());
        assertThat(technicalRecord1.getEmissionsLimit()).isEqualTo(tr.getEmissionsLimit());
        assertThat(technicalRecord1.getGrossEecWeight()).isEqualTo(tr.getGrossEecWeight());
        assertThat(technicalRecord1.getPurchaserNotes()).isEqualTo(tr.getPurchaserNotes());
        assertThat(technicalRecord1.getSeatsLowerDeck()).isEqualTo(tr.getSeatsLowerDeck());
        assertThat(technicalRecord1.getSeatsUpperDeck()).isEqualTo(tr.getSeatsUpperDeck());
        assertThat(technicalRecord1.getSuspensionType()).isEqualTo(tr.getSuspensionType());
        assertThat(technicalRecord1.isTachoExemptMrk()).isEqualTo(tr.getTachoExemptMrk());
        assertThat(technicalRecord1.getTrainEecWeight()).isEqualTo(tr.getTrainEecWeight());
        assertThat(technicalRecord1.getConversionRefNo()).isEqualTo(tr.getConversionRefNo());
        assertThat(technicalRecord1.getGrossKerbWeight()).isEqualTo(tr.getGrossKerbWeight());
        assertThat(technicalRecord1.getManufactureYear()).isEqualTo(tr.getManufactureYear());
        assertThat(technicalRecord1.isSpeedLimiterMrk()).isEqualTo(tr.getSpeedLimiterMrk());
        assertThat(technicalRecord1.isAlterationMarker()).isEqualTo(tr.getAlterationMarker());
        assertThat(technicalRecord1.getCoifSerialNumber()).isEqualTo(tr.getCoifSerialNumber());
        assertThat(technicalRecord1.getFrameDescription()).isEqualTo(tr.getFrameDescription());
        assertThat(technicalRecord1.getGrossLadenWeight()).isEqualTo(tr.getGrossLadenWeight());
        assertThat(technicalRecord1.isLoadSensingValve()).isEqualTo(tr.getBrakes_loadSensingValve());
        assertThat(technicalRecord1.getMaxTrainGbWeight()).isEqualTo(tr.getMaxTrainGbWeight());
        assertThat(technicalRecord1.getSpeedRestriction()).isEqualTo(tr.getSpeedRestriction());
        assertThat(technicalRecord1.getStandingCapacity()).isEqualTo(tr.getStandingCapacity());
        assertThat(technicalRecord1.getCoifCertifierName()).isEqualTo(tr.getCoifCertifierName());
        assertThat(technicalRecord1.getGrossDesignWeight()).isEqualTo(tr.getGrossDesignWeight());
        assertThat(technicalRecord1.getManufacturerNotes()).isEqualTo(tr.getManufacturerNotes());
        assertThat(technicalRecord1.getMaxLoadOnCoupling()).isEqualTo(tr.getMaxLoadOnCoupling());
        assertThat(technicalRecord1.getMaxTrainEecWeight()).isEqualTo(tr.getMaxTrainEecWeight());
        assertThat(technicalRecord1.getNumberOfSeatbelts()).isEqualTo(tr.getNumberOfSeatbelts());
        assertThat(technicalRecord1.getRearAxleToRearTrl()).isEqualTo(tr.getRearAxleToRearTrl());
        assertThat(technicalRecord1.getReasonForCreation()).isEqualTo(tr.getReasonForCreation());
        assertThat(technicalRecord1.getTrainDesignWeight()).isEqualTo(tr.getTrainDesignWeight());
        assertThat(technicalRecord1.getApprovalTypeNumber()).isEqualTo(tr.getApprovalTypeNumber());
//        assertThat(technicalRecord1.getRecordCompleteness()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord1.getFrontAxleToRearAxle()).isEqualTo(tr.getFrontAxleToRearAxle());
        assertThat(technicalRecord1.getMaxTrainDesignWeight()).isEqualTo(tr.getMaxTrainDesignWeight());
        assertThat(technicalRecord1.getNumberOfWheelsDriven()).isEqualTo(tr.getNumberOfWheelsDriven());
        assertThat(technicalRecord1.getVariantVersionNumber()).isEqualTo(tr.getVariantVersionNumber());
        assertThat(technicalRecord1.isAntilockBrakingSystem()).isEqualTo(tr.getBrakes_antilockBrakingSystem());
        assertThat(technicalRecord1.isDrawbarCouplingFitted()).isEqualTo(tr.getDrawbarCouplingFitted());
        assertThat(technicalRecord1.getFrontAxleTo5thWheelMax()).isEqualTo(tr.getFrontAxleTo5thWheelMax());
        assertThat(technicalRecord1.getFrontAxleTo5thWheelMin()).isEqualTo(tr.getFrontAxleTo5thWheelMin());
        assertThat(technicalRecord1.isDepartmentalVehicleMarker()).isEqualTo(tr.getDepartmentalVehicleMarker());
        assertThat(technicalRecord1.getCouplingCentreToRearTrlMax()).isEqualTo(tr.getCouplingCenterToRearTrlMax());
        assertThat(technicalRecord1.getCouplingCentreToRearTrlMin()).isEqualTo(tr.getCouplingCenterToRearTrlMin());
        assertThat(technicalRecord1.getCouplingCentreToRearAxleMax()).isEqualTo(tr.getCouplingCenterToRearAxleMax());
        assertThat(technicalRecord1.getCouplingCentreToRearAxleMin()).isEqualTo(tr.getCouplingCenterToRearAxleMin());
        assertThat(technicalRecord1.getFrontAxleTo5thWheelCouplingMax()).isEqualTo(tr.getFrontAxleTo5thWheelCouplingMax());
        assertThat(technicalRecord1.getFrontAxleTo5thWheelCouplingMin()).isEqualTo(tr.getFrontAxleTo5thWheelCouplingMin());
        assertThat(technicalRecord1.getCentreOfRearmostAxleToRearOfTrl()).isEqualTo(tr.getCentreOfRearmostAxleToRearOfTrl());
        assertThat(technicalRecord1.getSeatbeltInstallationApprovalDate()).isEqualTo(tr.getSeatbeltInstallationApprovalDate());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getBrakeCode()).isEqualTo(tr.getBrakeCode());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getDataTrBrakeOne()).isEqualTo(psv.getDataTrBrakeOne());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getDataTrBrakeTwo()).isEqualTo(psv.getDataTrBrakeTwo());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getDataTrBrakeThree()).isEqualTo(psv.getDataTrBrakeThree());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getRetarderBrakeOne()).isEqualTo(psv.getRetarderBrakeOne());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getRetarderBrakeTwo()).isEqualTo(psv.getRetarderBrakeTwo());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getBrakeCodeOriginal()).isEqualTo(psv.getBrakeCodeOriginal());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getParkingBrakeForceA()).isEqualTo(psv.getParkingBrakeForceA());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getParkingBrakeForceB()).isEqualTo(psv.getParkingBrakeForceB());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getServiceBrakeForceA()).isEqualTo(psv.getParkingBrakeForceA());
        assertThat(technicalRecord1.getPsvBrakes().get(0).getServiceBrakeForceB()).isEqualTo(null);
        assertThat(technicalRecord1.getPsvBrakes().get(0).getSecondaryBrakeForceA()).isEqualTo(null);
        assertThat(technicalRecord1.getPsvBrakes().get(0).getSecondaryBrakeForceB()).isEqualTo(null);
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getTyreCode()).isEqualTo(1234); // todo not reaching the correct nested field
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getTyreSize()).isEqualTo("9.23648E+11");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getPlyRating()).isEqualTo("AB");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getDataTrAxles()).isEqualTo("345");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getFitmentCode()).isEqualTo("single");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(0).getSpeedCategorySymbol()).isEqualTo("a7"); // todo end
        assertThat(technicalRecord1.getAxles().get(0).getGbWeight()).isEqualTo("1400"); // todo not matching type returned
//        assertThat(technicalRecord1.getAxles().get(0).getEecWeight()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(0).getAxleNumber()).isEqualTo("1");
//        assertThat(technicalRecord1.getAxles().get(0).getKerbWeight()).isEqualTo(null);
//        assertThat(technicalRecord1.getAxles().get(0).getLadenWeight()).isEqualTo(null);
//        assertThat(technicalRecord1.getAxles().get(0).getLeverLength()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(0).getDesignWeight()).isEqualTo("1800");
//        assertThat(technicalRecord1.getAxles().get(0).getBrakeActuator()).isEqualTo(null); // todo end
        assertThat(technicalRecord1.getAxles().get(0).isParkingBrakeMrk()).isEqualTo(true);
        assertThat(technicalRecord1.getAxles().get(0).isSpringBrakeParking()).isEqualTo(true);
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getTyreCode()).isEqualTo("5678"); // todo not reaching the correct nested field
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getTyreSize()).isEqualTo("9.23648E+11");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getPlyRating()).isEqualTo("AB");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getDataTrAxles()).isEqualTo("345");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getFitmentCode()).isEqualTo("single");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(1).getSpeedCategorySymbol()).isEqualTo("a7"); // todo end
        assertThat(technicalRecord1.getAxles().get(1).getGbWeight()).isEqualTo("1600");
//        assertThat(technicalRecord1.getAxles().get(1).getEecWeight()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(1).getAxleNumber()).isEqualTo("2");
//        assertThat(technicalRecord1.getAxles().get(1).getKerbWeight()).isEqualTo(null);
//        assertThat(technicalRecord1.getAxles().get(1).getLadenWeight()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(1).getLeverLength()).isEqualTo();
        assertThat(technicalRecord1.getAxles().get(1).getDesignWeight()).isEqualTo("1900");
        assertThat(technicalRecord1.getAxles().get(1).getBrakeActuator()).isEqualTo("113");
        assertThat(technicalRecord1.getAxles().get(1).isParkingBrakeMrk()).isEqualTo(true);
        assertThat(technicalRecord1.getAxles().get(1).isSpringBrakeParking()).isEqualTo(true);
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getTyreCode()).isEqualTo(5678);// todo not reaching the correct nested field
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getTyreSize()).isEqualTo("9.23648E+11");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getPlyRating()).isEqualTo("AB");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getDataTrAxles()).isEqualTo("345");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getFitmentCode()).isEqualTo("single");
//        assertThat(technicalRecord1.getAxles().get(0).getTyres().get(2).getSpeedCategorySymbol()).isEqualTo("a7"); // todo end
//        assertThat(technicalRecord1.getAxles().get(2).getGbWeight()).isEqualTo("1600"); //todo not matching type returned
//        assertThat(technicalRecord1.getAxles().get(2).getEecWeight()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(2).getAxleNumber()).isEqualTo("3");
//        assertThat(technicalRecord1.getAxles().get(2).getKerbWeight()).isEqualTo(null);
//        assertThat(technicalRecord1.getAxles().get(2).getLadenWeight()).isEqualTo(null);
        assertThat(technicalRecord1.getAxles().get(2).getLeverLength()).isEqualTo("125");
        assertThat(technicalRecord1.getAxles().get(2).getDesignWeight()).isEqualTo("1900");
        assertThat(technicalRecord1.getAxles().get(2).getBrakeActuator()).isEqualTo("113"); // todo end
//        assertThat(technicalRecord1.getAxles().get(2).getParkingBrakeMrk()).isEqualTo(true);
//        assertThat(technicalRecord1.getAxles().get(2).getSpringBrakeParking()).isEqualTo(1);
//        assertThat(technicalRecord1.getAxles().get(0).getAxlespacing().get(0).getAxles()).isEqualTo("1-2");
//        assertThat(technicalRecord1.getAxles().get(0).getAxlespacing().get(0).getValue()).isEqualTo(1200);
//        assertThat(technicalRecord1.getAxles().get(0).getPlates().get(0)).isEqualTo(null);

        System.out.println(vehicle);

    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryUsingVrmTest() {

        System.out.println("Valid access token: " + token);

        String response =
                givenAuth(token, xApiKey)
                        .header("content-type", "application/json")
                        .queryParam("VehicleRegMark", validVehicleRegMark).

                //send request
                when().//log().all().
                        get().

                //verification
                then().log().all().
                        statusCode(200).//time(lessThan(100L)).
                        extract().response().asString();

        System.out.println(response);

        JsonPath js2 = new JsonPath(response);
        String vrmTrmReturnedInApiResponse = js2.getString("vrm_trm");
        System.out.println(vrmTrmReturnedInApiResponse);
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryBadJwtTokenTest() {

        System.out.println("Using invalid token: " + token);

        //prep request
        givenAuth(token + 1, xApiKey)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", validVehicleRegMark).

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(403).
            body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNoParamsTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
            .header("content-type", "application/json").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(400).
            body(equalTo("No parameter defined"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryBothVinAndVrmTest() {

        System.out.println("Valid access token: " + token);

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("VehicleRegMark", validVehicleRegMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Too many parameters defined"));
    }

    @Test
    public void RetrieveVehicleDataAndTestHistoryNoAPIKeyTest() {

        System.out.println("Valid access token " + token);

        //prep request
        givenAuth(token)
            .header("content-type", "application/json")
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
            .queryParam("VehicleRegMark", "AB15XYZ").

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
            .queryParam("VehicleRegMark", "AB15XYZ"). //todo send a vrm that doesn't exist in DB

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
            .queryParam("vinNumber", invalidVINNumber). //todo send a vin that doesn't exist in DB

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
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("testNumber", validVehicleRegMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(404).
                body(equalTo("NoSuchKey"));
    }

    private vott.models.dao.TechnicalRecord newTestTechnicalRecord() {
        vott.models.dao.TechnicalRecord tr = new vott.models.dao.TechnicalRecord();

        tr.setVehicleID("1");
        tr.setRecordCompleteness("Complete");
        tr.setCreatedAt("2021-01-01 00:00:00");
        tr.setLastUpdatedAt("2021-01-01 00:00:00");
        tr.setMakeModelID("1");
        tr.setFunctionCode("A");
        tr.setOffRoad("1");
        tr.setNumberOfWheelsDriven("4");
        tr.setEmissionsLimit("Test Emission Limit");
        tr.setDepartmentalVehicleMarker("1");
        tr.setAlterationMarker("1");
        tr.setVehicleClassID("1");
        tr.setVariantVersionNumber("Test Variant Number");
        tr.setGrossEecWeight("1200");
        tr.setTrainEecWeight("1400");
        tr.setMaxTrainEecWeight("1400");
        tr.setApplicantDetailID("1");
        tr.setPurchaserDetailID("1");
        tr.setManufacturerDetailID("1");
        tr.setManufactureYear("2021");
        tr.setRegnDate("2021-01-01");
        tr.setFirstUseDate("2021-01-01");
        tr.setCoifDate("2021-01-01");
        tr.setNtaNumber("NTA Number");
        tr.setCoifSerialNumber("55555");
        tr.setCoifCertifierName("88888");
        tr.setApprovalType("111");
        tr.setApprovalTypeNumber("ABC11111");
        tr.setVariantNumber("Test Variant");
        tr.setConversionRefNo("10");
        tr.setSeatsLowerDeck("2");
        tr.setSeatsUpperDeck("3");
        tr.setStandingCapacity("15");
        tr.setSpeedRestriction("60");
        tr.setSpeedLimiterMrk("1");
        tr.setTachoExemptMrk("1");
        tr.setDispensations("Test Dispensations");
        tr.setRemarks("Automation Test Remarks");
        tr.setReasonForCreation("Automation Test ");
        tr.setStatusCode("B987");
        tr.setUnladenWeight("1400");
        tr.setGrossKerbWeight("1400");
        tr.setGrossLadenWeight("1400");
        tr.setGrossGbWeight("1400");
        tr.setGrossDesignWeight("1400");
        tr.setTrainGbWeight("1400");
        tr.setTrainDesignWeight("1400");
        tr.setMaxTrainGbWeight("1400");
        tr.setMaxTrainDesignWeight("1400");
        tr.setMaxLoadOnCoupling("1400");
        tr.setFrameDescription("Test Automation");
        tr.setTyreUseCode("A1");
        tr.setRoadFriendly("1");
        tr.setDrawbarCouplingFitted("1");
        tr.setEuroStandard("Y555");
        tr.setSuspensionType("Y");
        tr.setCouplingType("B");
        tr.setLength("100");
        tr.setHeight("50");
        tr.setWidth("50");
        tr.setFrontAxleTo5thWheelCouplingMin("55");
        tr.setFrontAxleTo5thWheelCouplingMax("65");
        tr.setFrontAxleTo5thWheelCouplingMin("45");
        tr.setFrontAxleTo5thWheelCouplingMax("65");
        tr.setFrontAxleToRearAxle("15");
        tr.setRearAxleToRearTrl("25");
        tr.setCouplingCenterToRearAxleMin("25");
        tr.setCouplingCenterToRearAxleMax("85");
        tr.setCouplingCenterToRearAxleMin("25");
        tr.setCouplingCenterToRearAxleMax("85");
        tr.setCentreOfRearmostAxleToRearOfTrl("25");
        tr.setNotes("Test Notes");
        tr.setPurchaserNotes("Purchaser Notes");
        tr.setManufacturerNotes("Manufactuer Notes");
        tr.setNoOfAxles("3");
        tr.setBrakeCode("XXXXX");
        tr.setBrakes_dtpNumber("DTP111");
        tr.setBrakes_loadSensingValve("1");
        tr.setBrakes_antilockBrakingSystem("1");
        tr.setCreatedByID("1");
        tr.setLastUpdatedByID("1");
        tr.setUpdateType("AutoTest");
        tr.setNumberOfSeatbelts("3");
        tr.setSeatbeltInstallationApprovalDate("2021-01-01");

        return tr;
    }

    private vott.models.dao.Vehicle newTestVehicle() {
        vott.models.dao.Vehicle vehicle = new vott.models.dao.Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("T123456");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }

    private MakeModel newTestMakeModel() {
        MakeModel mm = new MakeModel();

        mm.setMake("Test Make");
        mm.setModel("Test Model");
        mm.setChassisMake("Test Chassis Make");
        mm.setChassisModel("Test Chassis Model");
        mm.setBodyMake("Test Body Make");
        mm.setBodyModel("Test Body Model");
        mm.setModelLiteral("Test Model Literal");
        mm.setBodyTypeCode("1");
        mm.setBodyTypeDescription("Test Description");
        mm.setFuelPropulsionSystem("Test Fuel");
        mm.setDtpCode("888888");

        return mm;
    }

    private VehicleClass newTestVehicleClass() {
        VehicleClass vc = new VehicleClass();

        vc.setCode("1");
        vc.setDescription("Test Description");
        vc.setVehicleType("Test Type");
        vc.setVehicleSize("55555");
        vc.setVehicleConfiguration("Test Configuration");
        vc.setEuVehicleCategory("ABC");

        return vc;
    }

    private PSVBrakes newTestPSVBrakes() {
        PSVBrakes psv = new PSVBrakes();

        psv.setTechnicalRecordID("1");
        psv.setBrakeCodeOriginal("222");
        psv.setBrakeCode("Test");
        psv.setDataTrBrakeOne("Test Data");
        psv.setDataTrBrakeTwo("Test Data");
        psv.setDataTrBrakeThree("Test Data");
        psv.setRetarderBrakeOne("Test Data");
        psv.setRetarderBrakeTwo("Test Data");
        psv.setServiceBrakeForceA("11");
        psv.setSecondaryBrakeForceA("22");
        psv.setParkingBrakeForceA("33");
        psv.setServiceBrakeForceB("44");
        psv.setSecondaryBrakeForceB("55");
        psv.setParkingBrakeForceB("66");

        return psv;
    }

    private Axles newTestAxles() {
        Axles axles = new Axles();

        axles.setTechnicalRecordID("1");
        axles.setTyreID("1");
        axles.setAxleNumber("222");
        axles.setParkingBrakeMrk("5");
        axles.setKerbWeight("1200");
        axles.setLadenWeight("1500");
        axles.setGbWeight("1200");
        axles.setEecWeight("1500");
        axles.setDesignWeight("1200");
        axles.setBrakeActuator("10");
        axles.setLeverLength("10");
        axles.setSpringBrakeParking("123");

        return axles;
    }

    private Tyre newTestTyre() {
        Tyre tyre = new Tyre();

        tyre.setTyreSize("456");
        tyre.setPlyRating("10");
        tyre.setFitmentCode("55555");
        tyre.setDataTrAxles("Test Data");
        tyre.setSpeedCategorySymbol("1");
        tyre.setTyreCode("88888");

        return tyre;
    }

}


