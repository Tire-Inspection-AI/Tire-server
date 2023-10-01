package hello.capstone.car_api;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarInfo {


    private String drive;

    private String carName;

    private String carVender;

    private String fuel;
    private String frontTire;
    private String rearTire;
    private String seats;
    private String fuelEconomy;

    private String carYear;

    private String cc;

}
