package hello.capstone.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TireStatusEnum {
    Replace_Required("Replace_Required"),
    Recommend_Replacement("Recommend_Replacement"),
    Good_Condition("Good_Condition");

    private final String value;

    TireStatusEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TireStatusEnum fromValue(String value) {
        for (TireStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown TireStatusEnum: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
