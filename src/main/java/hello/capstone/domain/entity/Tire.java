package hello.capstone.domain.entity;


import hello.capstone.domain.TirePositionEnum;
import hello.capstone.domain.TireStatusEnum;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tire")
public class Tire {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("최근 타이어 교체 시기")
    @Column(name = "recent_change_date", nullable = false)
    private LocalDate recentChangeDate;

    @Comment("바퀴 구분")
    @Column(name = "tire_position", nullable = false, columnDefinition = "varchar(36)")
    @Enumerated(EnumType.STRING)
    private TirePositionEnum tirePosition;


    @Comment("타이어 상태")
    @Column(name = "tire_status", nullable = false, columnDefinition = "varchar(36)")
    @Enumerated(EnumType.STRING)
    private TireStatusEnum tireStatus;

    @Comment("타이어 마모도")
    @Column(name = "wear", nullable = false)
    private double wear;
    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public static Tire createTire(Car car, TirePositionEnum position, LocalDate recentChangeDate) {
        return Tire.builder()
                .recentChangeDate(recentChangeDate)
                .tirePosition(position)
                .tireStatus(TireStatusEnum.Good_Condition)
                .car(car)
                .build();
    }

}
