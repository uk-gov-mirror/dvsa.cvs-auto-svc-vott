package vott.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiKeys extends HashMap<String, String> {

    public String getEnquiryServiceApiKey() {
        return this.get("enquiryService");
    }
}
