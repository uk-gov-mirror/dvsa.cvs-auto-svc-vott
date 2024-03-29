/*
 * Vehicles Microservice
 * This is the API spec for the vehicle microservice. Endpoints and parameters only exist for the operations getVehicle and getTechRecords. Other operations within the microservice are out of scope.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: bpecete@deloittece.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.techrecords;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * Used only for HGV and TRL
 */
@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class AdrDetails {
  @SerializedName("vehicleDetails")
  private AdrDetailsVehicleDetails vehicleDetails = null;

  @SerializedName("listStatementApplicable")
  private Boolean listStatementApplicable = null;

  @SerializedName("batteryListNumber")
  private String batteryListNumber = null;

  @SerializedName("declarationsSeen")
  private Boolean declarationsSeen = null;

  @SerializedName("brakeDeclarationsSeen")
  private Boolean brakeDeclarationsSeen = null;

  @SerializedName("brakeDeclarationIssuer")
  private String brakeDeclarationIssuer = null;

  @SerializedName("brakeEndurance")
  private Boolean brakeEndurance = null;

  @SerializedName("weight")
  private String weight = null;

  @SerializedName("compatibilityGroupJ")
  private Boolean compatibilityGroupJ = null;

  @SerializedName("documents")
  private List<String> documents = null;

  @SerializedName("permittedDangerousGoods")
  private List<String> permittedDangerousGoods = null;

  @SerializedName("additionalExaminerNotes")
  private String additionalExaminerNotes = null;

  @SerializedName("applicantDetails")
  private AdrDetailsApplicantDetails applicantDetails = null;

  @SerializedName("memosApply")
  private List<String> memosApply = null;

  @SerializedName("additionalNotes")
  private AdrDetailsAdditionalNotes additionalNotes = null;

  @SerializedName("adrTypeApprovalNo")
  private String adrTypeApprovalNo = null;

  @SerializedName("adrCertificateNotes")
  private String adrCertificateNotes = null;

  @SerializedName("tank")
  private AdrDetailsTank tank = null;

  public AdrDetails vehicleDetails(AdrDetailsVehicleDetails vehicleDetails) {
    this.vehicleDetails = vehicleDetails;
    return this;
  }

   /**
   * Get vehicleDetails
   * @return vehicleDetails
  **/
    public AdrDetailsVehicleDetails getVehicleDetails() {
    return vehicleDetails;
  }

  public void setVehicleDetails(AdrDetailsVehicleDetails vehicleDetails) {
    this.vehicleDetails = vehicleDetails;
  }

  public AdrDetails listStatementApplicable(Boolean listStatementApplicable) {
    this.listStatementApplicable = listStatementApplicable;
    return this;
  }

   /**
   * Optional. Applicable only if vehicleDetails.type contains the word ‘battery’.
   * @return listStatementApplicable
  **/
    public Boolean isListStatementApplicable() {
    return listStatementApplicable;
  }

  public void setListStatementApplicable(Boolean listStatementApplicable) {
    this.listStatementApplicable = listStatementApplicable;
  }

  public AdrDetails batteryListNumber(String batteryListNumber) {
    this.batteryListNumber = batteryListNumber;
    return this;
  }

   /**
   * Mandatory if listStatementApplicable is true, therefore applicable only if vehicleDetails.type contains the word ‘battery’. Else N/A
   * @return batteryListNumber
  **/
    public String getBatteryListNumber() {
    return batteryListNumber;
  }

  public void setBatteryListNumber(String batteryListNumber) {
    this.batteryListNumber = batteryListNumber;
  }

  public AdrDetails declarationsSeen(Boolean declarationsSeen) {
    this.declarationsSeen = declarationsSeen;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return declarationsSeen
  **/
    public Boolean isDeclarationsSeen() {
    return declarationsSeen;
  }

  public void setDeclarationsSeen(Boolean declarationsSeen) {
    this.declarationsSeen = declarationsSeen;
  }

  public AdrDetails brakeDeclarationsSeen(Boolean brakeDeclarationsSeen) {
    this.brakeDeclarationsSeen = brakeDeclarationsSeen;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return brakeDeclarationsSeen
  **/
    public Boolean isBrakeDeclarationsSeen() {
    return brakeDeclarationsSeen;
  }

  public void setBrakeDeclarationsSeen(Boolean brakeDeclarationsSeen) {
    this.brakeDeclarationsSeen = brakeDeclarationsSeen;
  }

  public AdrDetails brakeDeclarationIssuer(String brakeDeclarationIssuer) {
    this.brakeDeclarationIssuer = brakeDeclarationIssuer;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return brakeDeclarationIssuer
  **/
    public String getBrakeDeclarationIssuer() {
    return brakeDeclarationIssuer;
  }

  public void setBrakeDeclarationIssuer(String brakeDeclarationIssuer) {
    this.brakeDeclarationIssuer = brakeDeclarationIssuer;
  }

  public AdrDetails brakeEndurance(Boolean brakeEndurance) {
    this.brakeEndurance = brakeEndurance;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return brakeEndurance
  **/
    public Boolean isBrakeEndurance() {
    return brakeEndurance;
  }

  public void setBrakeEndurance(Boolean brakeEndurance) {
    this.brakeEndurance = brakeEndurance;
  }

  public AdrDetails weight(String weight) {
    this.weight = weight;
    return this;
  }

   /**
   * Mandatory if brakeEndurance &#x3D; true, else N/A
   * @return weight
  **/
    public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public AdrDetails compatibilityGroupJ(Boolean compatibilityGroupJ) {
    this.compatibilityGroupJ = compatibilityGroupJ;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return compatibilityGroupJ
  **/
    public Boolean isCompatibilityGroupJ() {
    return compatibilityGroupJ;
  }

  public void setCompatibilityGroupJ(Boolean compatibilityGroupJ) {
    this.compatibilityGroupJ = compatibilityGroupJ;
  }

  public AdrDetails documents(List<String> documents) {
    this.documents = documents;
    return this;
  }

  public AdrDetails addDocumentsItem(String documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<String>();
    }
    this.documents.add(documentsItem);
    return this;
  }

   /**
   * Optional. Applicable only if vehicleDetails.type contains the word ‘tank’ or ‘battery’.
   * @return documents
  **/
    public List<String> getDocuments() {
    return documents;
  }

  public void setDocuments(List<String> documents) {
    this.documents = documents;
  }

  public AdrDetails permittedDangerousGoods(List<String> permittedDangerousGoods) {
    this.permittedDangerousGoods = permittedDangerousGoods;
    return this;
  }

  public AdrDetails addPermittedDangerousGoodsItem(String permittedDangerousGoodsItem) {
    if (this.permittedDangerousGoods == null) {
      this.permittedDangerousGoods = new ArrayList<String>();
    }
    this.permittedDangerousGoods.add(permittedDangerousGoodsItem);
    return this;
  }

   /**
   * Mandatory. Users must select, AT LEAST ONE of these. However, users CAN select more than one
   * @return permittedDangerousGoods
  **/
    public List<String> getPermittedDangerousGoods() {
    return permittedDangerousGoods;
  }

  public void setPermittedDangerousGoods(List<String> permittedDangerousGoods) {
    this.permittedDangerousGoods = permittedDangerousGoods;
  }

  public AdrDetails additionalExaminerNotes(String additionalExaminerNotes) {
    this.additionalExaminerNotes = additionalExaminerNotes;
    return this;
  }

   /**
   * Optional for all vehicle types
   * @return additionalExaminerNotes
  **/
    public String getAdditionalExaminerNotes() {
    return additionalExaminerNotes;
  }

  public void setAdditionalExaminerNotes(String additionalExaminerNotes) {
    this.additionalExaminerNotes = additionalExaminerNotes;
  }

  public AdrDetails applicantDetails(AdrDetailsApplicantDetails applicantDetails) {
    this.applicantDetails = applicantDetails;
    return this;
  }

   /**
   * Get applicantDetails
   * @return applicantDetails
  **/
    public AdrDetailsApplicantDetails getApplicantDetails() {
    return applicantDetails;
  }

  public void setApplicantDetails(AdrDetailsApplicantDetails applicantDetails) {
    this.applicantDetails = applicantDetails;
  }

  public AdrDetails memosApply(List<String> memosApply) {
    this.memosApply = memosApply;
    return this;
  }

  public AdrDetails addMemosApplyItem(String memosApplyItem) {
    if (this.memosApply == null) {
      this.memosApply = new ArrayList<String>();
    }
    this.memosApply.add(memosApplyItem);
    return this;
  }

   /**
   * Get memosApply
   * @return memosApply
  **/
    public List<String> getMemosApply() {
    return memosApply;
  }

  public void setMemosApply(List<String> memosApply) {
    this.memosApply = memosApply;
  }

  public AdrDetails additionalNotes(AdrDetailsAdditionalNotes additionalNotes) {
    this.additionalNotes = additionalNotes;
    return this;
  }

   /**
   * Get additionalNotes
   * @return additionalNotes
  **/
    public AdrDetailsAdditionalNotes getAdditionalNotes() {
    return additionalNotes;
  }

  public void setAdditionalNotes(AdrDetailsAdditionalNotes additionalNotes) {
    this.additionalNotes = additionalNotes;
  }

  public AdrDetails adrTypeApprovalNo(String adrTypeApprovalNo) {
    this.adrTypeApprovalNo = adrTypeApprovalNo;
    return this;
  }

   /**
   * Get adrTypeApprovalNo
   * @return adrTypeApprovalNo
  **/
    public String getAdrTypeApprovalNo() {
    return adrTypeApprovalNo;
  }

  public void setAdrTypeApprovalNo(String adrTypeApprovalNo) {
    this.adrTypeApprovalNo = adrTypeApprovalNo;
  }

  public AdrDetails adrCertificateNotes(String adrCertificateNotes) {
    this.adrCertificateNotes = adrCertificateNotes;
    return this;
  }

   /**
   * Get adrCertificateNotes
   * @return adrCertificateNotes
  **/
    public String getAdrCertificateNotes() {
    return adrCertificateNotes;
  }

  public void setAdrCertificateNotes(String adrCertificateNotes) {
    this.adrCertificateNotes = adrCertificateNotes;
  }

  public AdrDetails tank(AdrDetailsTank tank) {
    this.tank = tank;
    return this;
  }

   /**
   * Get tank
   * @return tank
  **/
    public AdrDetailsTank getTank() {
    return tank;
  }

  public void setTank(AdrDetailsTank tank) {
    this.tank = tank;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdrDetails adrDetails = (AdrDetails) o;
    return Objects.equals(this.vehicleDetails, adrDetails.vehicleDetails) &&
        Objects.equals(this.listStatementApplicable, adrDetails.listStatementApplicable) &&
        Objects.equals(this.batteryListNumber, adrDetails.batteryListNumber) &&
        Objects.equals(this.declarationsSeen, adrDetails.declarationsSeen) &&
        Objects.equals(this.brakeDeclarationsSeen, adrDetails.brakeDeclarationsSeen) &&
        Objects.equals(this.brakeDeclarationIssuer, adrDetails.brakeDeclarationIssuer) &&
        Objects.equals(this.brakeEndurance, adrDetails.brakeEndurance) &&
        Objects.equals(this.weight, adrDetails.weight) &&
        Objects.equals(this.compatibilityGroupJ, adrDetails.compatibilityGroupJ) &&
        Objects.equals(this.documents, adrDetails.documents) &&
        Objects.equals(this.permittedDangerousGoods, adrDetails.permittedDangerousGoods) &&
        Objects.equals(this.additionalExaminerNotes, adrDetails.additionalExaminerNotes) &&
        Objects.equals(this.applicantDetails, adrDetails.applicantDetails) &&
        Objects.equals(this.memosApply, adrDetails.memosApply) &&
        Objects.equals(this.additionalNotes, adrDetails.additionalNotes) &&
        Objects.equals(this.adrTypeApprovalNo, adrDetails.adrTypeApprovalNo) &&
        Objects.equals(this.adrCertificateNotes, adrDetails.adrCertificateNotes) &&
        Objects.equals(this.tank, adrDetails.tank);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleDetails, listStatementApplicable, batteryListNumber, declarationsSeen, brakeDeclarationsSeen, brakeDeclarationIssuer, brakeEndurance, weight, compatibilityGroupJ, documents, permittedDangerousGoods, additionalExaminerNotes, applicantDetails, memosApply, additionalNotes, adrTypeApprovalNo, adrCertificateNotes, tank);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdrDetails {\n");
    
    sb.append("    vehicleDetails: ").append(toIndentedString(vehicleDetails)).append("\n");
    sb.append("    listStatementApplicable: ").append(toIndentedString(listStatementApplicable)).append("\n");
    sb.append("    batteryListNumber: ").append(toIndentedString(batteryListNumber)).append("\n");
    sb.append("    declarationsSeen: ").append(toIndentedString(declarationsSeen)).append("\n");
    sb.append("    brakeDeclarationsSeen: ").append(toIndentedString(brakeDeclarationsSeen)).append("\n");
    sb.append("    brakeDeclarationIssuer: ").append(toIndentedString(brakeDeclarationIssuer)).append("\n");
    sb.append("    brakeEndurance: ").append(toIndentedString(brakeEndurance)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
    sb.append("    compatibilityGroupJ: ").append(toIndentedString(compatibilityGroupJ)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
    sb.append("    permittedDangerousGoods: ").append(toIndentedString(permittedDangerousGoods)).append("\n");
    sb.append("    additionalExaminerNotes: ").append(toIndentedString(additionalExaminerNotes)).append("\n");
    sb.append("    applicantDetails: ").append(toIndentedString(applicantDetails)).append("\n");
    sb.append("    memosApply: ").append(toIndentedString(memosApply)).append("\n");
    sb.append("    additionalNotes: ").append(toIndentedString(additionalNotes)).append("\n");
    sb.append("    adrTypeApprovalNo: ").append(toIndentedString(adrTypeApprovalNo)).append("\n");
    sb.append("    adrCertificateNotes: ").append(toIndentedString(adrCertificateNotes)).append("\n");
    sb.append("    tank: ").append(toIndentedString(tank)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
