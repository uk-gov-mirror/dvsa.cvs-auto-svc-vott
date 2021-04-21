/*
 * Test Results Microservice
 * This is the API spec for capturing test results. These test result will be stored in the AWS DynamoDB database. Authorization details will be updated once we have confirmed the security scheme we are using.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: test@test.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.testresults;

import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * DefectAdditionalInformation
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:44:54.508Z[GMT]")
public class DefectAdditionalInformation {
  @SerializedName("location")
  private DefectAdditionalInformationLocation location = null;

  @SerializedName("notes")
  private String notes = null;

  public DefectAdditionalInformation location(DefectAdditionalInformationLocation location) {
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  
  public DefectAdditionalInformationLocation getLocation() {
    return location;
  }

  public void setLocation(DefectAdditionalInformationLocation location) {
    this.location = location;
  }

  public DefectAdditionalInformation notes(String notes) {
    this.notes = notes;
    return this;
  }

   /**
   * Get notes
   * @return notes
  **/
  
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefectAdditionalInformation defectAdditionalInformation = (DefectAdditionalInformation) o;
    return Objects.equals(this.location, defectAdditionalInformation.location) &&
        Objects.equals(this.notes, defectAdditionalInformation.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, notes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DefectAdditionalInformation {\n");
    
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
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
