package hello.capstone.domain;

import hello.capstone.util.CarTypeEnum;
import hello.capstone.util.TireStatusEnum;
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
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("차량 종류 ex(경형, 소형, 중형, 대형, SUV")
    @Column(name = "type", nullable = false)
    @Enumerated
    private CarTypeEnum type;

    @Comment("차량 모델 ex(그랜져)")
    @Column(name = "model", nullable = true)
    private String model;

    @Comment("차량 번호 4자리 (숫자 여야 함.)")
    @Column(name = "fourth_digit_license_plate", nullable = false)
    private Integer fourth_digit_license_plate;

    @Comment("마지막 타이어 교체 시기")
    @Column(name = "recent_change_date", nullable = false)
    private LocalDate recentChangeDate;

    @Comment("왼쪽 앞 바퀴")
    @Column(name = "left_front_tire", nullable = false)
    @Enumerated
    private TireStatusEnum LeftFrontTire;

    @Comment("왼쪽 뒷 바퀴")
    @Column(name = "left_back_tire", nullable = false)
    @Enumerated
    private TireStatusEnum LeftBackTire;

    @Comment("오른쪽 앞 바퀴")
    @Column(name = "right_front_tire", nullable = false)
    @Enumerated
    private TireStatusEnum RightFrontTire;

    @Comment("오른쪽 뒤 바퀴")
    @Column(name = "right_back_tire", nullable = false)
    @Enumerated
    private TireStatusEnum RightBackTire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}