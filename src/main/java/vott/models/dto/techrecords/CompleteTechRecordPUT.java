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

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * CompleteTechRecordPUT
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class CompleteTechRecordPUT {
  @SerializedName("systemNumber")
  private String systemNumber = null;

  @SerializedName("vrms")
  private Vrms vrms = null;

  @SerializedName("vin")
  private String vin = null;

  @SerializedName("trailerId")
  private String trailerId = null;

  @SerializedName("techRecord")
  private TechRecords techRecord = null;

  public CompleteTechRecordPUT systemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
    return this;
  }

   /**
   * It defines the composed primary key, in combination with \&quot;vin\&quot;.
   * @return systemNumber
  **/
    public String getSystemNumber() {
    return systemNumber;
  }

  public void setSystemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
  }

  public CompleteTechRecordPUT vrms(Vrms vrms) {
    this.vrms = vrms;
    return this;
  }

   /**
   * Get vrms
   * @return vrms
  **/
    public Vrms getVrms() {
    return vrms;
  }

  public void setVrms(Vrms vrms) {
    this.vrms = vrms;
  }

  public CompleteTechRecordPUT vin(String vin) {
    this.vin = vin;
    return this;
  }

   /**
   * Used for all vehicle types - PSV, HGV, TRL, car, lgv, motorcycle
   * @return vin
  **/
    public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public CompleteTechRecordPUT trailerId(String trailerId) {
    this.trailerId = trailerId;
    return this;
  }

   /**
   * Used only for TRL
   * @return trailerId
  **/
    public String getTrailerId() {
    return trailerId;
  }

  public void setTrailerId(String trailerId) {
    this.trailerId = trailerId;
  }

  public CompleteTechRecordPUT techRecord(TechRecords techRecord) {
    this.techRecord = techRecord;
    return this;
  }

   /**
   * Get techRecord
   * @return techRecord
  **/
    public TechRecords getTechRecord() {
    return techRecord;
  }

  public void setTechRecord(TechRecords techRecord) {
    this.techRecord = techRecord;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompleteTechRecordPUT completeTechRecordPUT = (CompleteTechRecordPUT) o;
    return Objects.equals(this.systemNumber, completeTechRecordPUT.systemNumber) &&
        Objects.equals(this.vrms, completeTechRecordPUT.vrms) &&
        Objects.equals(this.vin, completeTechRecordPUT.vin) &&
        Objects.equals(this.trailerId, completeTechRecordPUT.trailerId) &&
        Objects.equals(this.techRecord, completeTechRecordPUT.techRecord);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemNumber, vrms, vin, trailerId, techRecord);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompleteTechRecordPUT {\n");
    
    sb.append("    systemNumber: ").append(toIndentedString(systemNumber)).append("\n");
    sb.append("    vrms: ").append(toIndentedString(vrms)).append("\n");
    sb.append("    vin: ").append(toIndentedString(vin)).append("\n");
    sb.append("    trailerId: ").append(toIndentedString(trailerId)).append("\n");
    sb.append("    techRecord: ").append(toIndentedString(techRecord)).append("\n");
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
