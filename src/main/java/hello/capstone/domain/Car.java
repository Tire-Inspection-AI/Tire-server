package hello.capstone.domain;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("차량 명")
    @Column(name = "name", nullable = false,columnDefinition = "varchar(255)")
    private String name;

    @Comment("제조사")
    @Column(name = "vender", nullable = false,columnDefinition = "varchar(255)")
    private String vender;

    @Comment("차량 번호")
    @Column(name = "registration_number", nullable = false,columnDefinition = "varchar(255)")
    private String registrationNumber;

    @Comment("차대 번호")
    @Column(name = "year", nullable = false,columnDefinition = "int")
    private Integer year;

    @Comment("구동 방식")
    @Column(name = "drive", nullable = false,columnDefinition = "varchar(36)")
    private String drive;

    @Comment("좌석 수")
    @Column(name = "seats", nullable = false,columnDefinition = "int")
    private Integer seats;

    @Comment("연료")
    @Column(name = "fuel", nullable = false,columnDefinition = "varchar(36)")
    private String fuel;

    @Comment("배기량")
    @Column(name = "cc", nullable = false,columnDefinition = "int")
    private Integer cc;

    @Comment("연비")
    @Column(name = "fuel_economy", nullable = false,columnDefinition = "double")
    private double fuelEconomy;

    @Comment("앞 타이어 정보")
    @Column(name = "front_tire", nullable = false,columnDefinition = "varchar(255)")
    private String frontTire;

    @Comment("뒤 타이어 정보")
    @Column(name = "rear_tire", nullable = false,columnDefinition = "varchar(255)")
    private String rearTire;

    @Comment("차량 등록된 날짜")
    @Column(name = "created_at", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tire> tires = new ArrayList<>();


}