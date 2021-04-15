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
import java.util.Objects;

/**
 * MetadataAdrDetailsVehicleDetails
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class MetadataAdrDetailsVehicleDetails {
  /**
   * Gets or Sets typeFe
   */
  @JsonAdapter(TypeFeEnum.Adapter.class)
  public enum TypeFeEnum {
    ARTIC_TRACTOR("Artic tractor"),
    RIGID_BOX_BODY("Rigid box body"),
    RIGID_SHEETED_LOAD("Rigid sheeted load"),
    RIGID_TANK("Rigid tank"),
    RIGID_SKELETAL("Rigid skeletal"),
    RIGID_BATTERY("Rigid battery"),
    FULL_DRAWBAR_BOX_BODY("Full drawbar box body"),
    FULL_DRAWBAR_SHEETED_LOAD("Full drawbar sheeted load"),
    FULL_DRAWBAR_TANK("Full drawbar tank"),
    FULL_DRAWBAR_SKELETAL("Full drawbar skeletal"),
    FULL_DRAWBAR_BATTERY("Full drawbar battery"),
    CENTRE_AXLE_BOX_BODY("Centre axle box body"),
    CENTRE_AXLE_SHEETED_LOAD("Centre axle sheeted load"),
    CENTRE_AXLE_TANK("Centre axle tank"),
    CENTRE_AXLE_SKELETAL("Centre axle skeletal"),
    CENTRE_AXLE_BATTERY("Centre axle battery"),
    SEMI_TRAILER_BOX_BODY("Semi trailer box body"),
    SEMI_TRAILER_SHEETED_LOAD("Semi trailer sheeted load"),
    SEMI_TRAILER_TANK("Semi trailer tank"),
    SEMI_TRAILER_SKELETAL("Semi trailer skeletal"),
    SEMI_TRAILER_BATTERY("Semi trailer battery");

    private String value;

    TypeFeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static TypeFeEnum fromValue(String text) {
      for (TypeFeEnum b : TypeFeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<TypeFeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeFeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeFeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return TypeFeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("typeFe")
  private List<TypeFeEnum> typeFe = null;

  public MetadataAdrDetailsVehicleDetails typeFe(List<TypeFeEnum> typeFe) {
    this.typeFe = typeFe;
    return this;
  }

  public MetadataAdrDetailsVehicleDetails addTypeFeItem(TypeFeEnum typeFeItem) {
    if (this.typeFe == null) {
      this.typeFe = new ArrayList<TypeFeEnum>();
    }
    this.typeFe.add(typeFeItem);
    return this;
  }

   /**
   * Get typeFe
   * @return typeFe
  **/
    public List<TypeFeEnum> getTypeFe() {
    return typeFe;
  }

  public void setTypeFe(List<TypeFeEnum> typeFe) {
    this.typeFe = typeFe;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetadataAdrDetailsVehicleDetails metadataAdrDetailsVehicleDetails = (MetadataAdrDetailsVehicleDetails) o;
    return Objects.equals(this.typeFe, metadataAdrDetailsVehicleDetails.typeFe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeFe);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetadataAdrDetailsVehicleDetails {\n");
    
    sb.append("    typeFe: ").append(toIndentedString(typeFe)).append("\n");
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