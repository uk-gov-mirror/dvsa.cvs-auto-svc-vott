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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * MetadataAdrDetailsTankTankStatement
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class MetadataAdrDetailsTankTankStatement {
  /**
   * Gets or Sets substancesPermittedFe
   */
  @JsonAdapter(SubstancesPermittedFeEnum.Adapter.class)
  public enum SubstancesPermittedFeEnum {
    PERMITTED_UNDER_THE_TANK_CODE_AND_ANY_SPECIAL_PROVISIONS_SPECIFIED_IN_9_MAY_BE_CARRIED("Substances permitted under the tank code and any special provisions specified in 9 may be carried"),
    _CLASS_UN_NUMBER_AND_IF_NECESSARY_PACKING_GROUP_AND_PROPER_SHIPPING_NAME_MAY_BE_CARRIED("Substances (Class UN number and if necessary packing group and proper shipping name) may be carried");

    private String value;

    SubstancesPermittedFeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static SubstancesPermittedFeEnum fromValue(String text) {
      for (SubstancesPermittedFeEnum b : SubstancesPermittedFeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<SubstancesPermittedFeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SubstancesPermittedFeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SubstancesPermittedFeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return SubstancesPermittedFeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("substancesPermittedFe")
  private List<SubstancesPermittedFeEnum> substancesPermittedFe = null;

  public MetadataAdrDetailsTankTankStatement substancesPermittedFe(List<SubstancesPermittedFeEnum> substancesPermittedFe) {
    this.substancesPermittedFe = substancesPermittedFe;
    return this;
  }

  public MetadataAdrDetailsTankTankStatement addSubstancesPermittedFeItem(SubstancesPermittedFeEnum substancesPermittedFeItem) {
    if (this.substancesPermittedFe == null) {
      this.substancesPermittedFe = new ArrayList<SubstancesPermittedFeEnum>();
    }
    this.substancesPermittedFe.add(substancesPermittedFeItem);
    return this;
  }

   /**
   * Get substancesPermittedFe
   * @return substancesPermittedFe
  **/
    public List<SubstancesPermittedFeEnum> getSubstancesPermittedFe() {
    return substancesPermittedFe;
  }

  public void setSubstancesPermittedFe(List<SubstancesPermittedFeEnum> substancesPermittedFe) {
    this.substancesPermittedFe = substancesPermittedFe;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetadataAdrDetailsTankTankStatement metadataAdrDetailsTankTankStatement = (MetadataAdrDetailsTankTankStatement) o;
    return Objects.equals(this.substancesPermittedFe, metadataAdrDetailsTankTankStatement.substancesPermittedFe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(substancesPermittedFe);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetadataAdrDetailsTankTankStatement {\n");
    
    sb.append("    substancesPermittedFe: ").append(toIndentedString(substancesPermittedFe)).append("\n");
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