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
 * TechRecordArchiveAndProvisionalPayload
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class TechRecordArchiveAndProvisionalPayload {
  @SerializedName("msUserDetails")
  private TechRecordArchiveAndProvisionalPayloadMsUserDetails msUserDetails = null;

  @SerializedName("techRecord")
  private TechRecord techRecord = null;

  public TechRecordArchiveAndProvisionalPayload msUserDetails(TechRecordArchiveAndProvisionalPayloadMsUserDetails msUserDetails) {
    this.msUserDetails = msUserDetails;
    return this;
  }

   /**
   * Get msUserDetails
   * @return msUserDetails
  **/
    public TechRecordArchiveAndProvisionalPayloadMsUserDetails getMsUserDetails() {
    return msUserDetails;
  }

  public void setMsUserDetails(TechRecordArchiveAndProvisionalPayloadMsUserDetails msUserDetails) {
    this.msUserDetails = msUserDetails;
  }

  public TechRecordArchiveAndProvisionalPayload techRecord(TechRecord techRecord) {
    this.techRecord = techRecord;
    return this;
  }

   /**
   * Get techRecord
   * @return techRecord
  **/
    public TechRecord getTechRecord() {
    return techRecord;
  }

  public void setTechRecord(TechRecord techRecord) {
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
    TechRecordArchiveAndProvisionalPayload techRecordArchiveAndProvisionalPayload = (TechRecordArchiveAndProvisionalPayload) o;
    return Objects.equals(this.msUserDetails, techRecordArchiveAndProvisionalPayload.msUserDetails) &&
        Objects.equals(this.techRecord, techRecordArchiveAndProvisionalPayload.techRecord);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msUserDetails, techRecord);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TechRecordArchiveAndProvisionalPayload {\n");
    
    sb.append("    msUserDetails: ").append(toIndentedString(msUserDetails)).append("\n");
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
