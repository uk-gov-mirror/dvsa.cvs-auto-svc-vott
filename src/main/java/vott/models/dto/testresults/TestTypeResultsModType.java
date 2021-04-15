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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * Used only for LEC tests. p &#x3D; particulate trap, m &#x3D; modification or change of engine, g &#x3D; gas engine.
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:44:54.508Z[GMT]")
public class TestTypeResultsModType {
  /**
   * Gets or Sets code
   */
  @JsonAdapter(CodeEnum.Adapter.class)
  public enum CodeEnum {
    P("p"),
    M("m"),
    G("g");

    private String value;

    CodeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static CodeEnum fromValue(String text) {
      for (CodeEnum b : CodeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<CodeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CodeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CodeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return CodeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("code")
  private CodeEnum code = null;

  /**
   * Gets or Sets description
   */
  @JsonAdapter(DescriptionEnum.Adapter.class)
  public enum DescriptionEnum {
    PARTICULATE_TRAP("particulate trap"),
    MODIFICATION_OR_CHANGE_OF_ENGINE("modification or change of engine"),
    GAS_ENGINE("gas engine");

    private String value;

    DescriptionEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static DescriptionEnum fromValue(String text) {
      for (DescriptionEnum b : DescriptionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<DescriptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DescriptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DescriptionEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return DescriptionEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("description")
  private DescriptionEnum description = null;

  public TestTypeResultsModType code(CodeEnum code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  
  public CodeEnum getCode() {
    return code;
  }

  public void setCode(CodeEnum code) {
    this.code = code;
  }

  public TestTypeResultsModType description(DescriptionEnum description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  
  public DescriptionEnum getDescription() {
    return description;
  }

  public void setDescription(DescriptionEnum description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestTypeResultsModType testTypeResultsModType = (TestTypeResultsModType) o;
    return Objects.equals(this.code, testTypeResultsModType.code) &&
        Objects.equals(this.description, testTypeResultsModType.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestTypeResultsModType {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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