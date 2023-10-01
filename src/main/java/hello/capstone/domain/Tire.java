package hello.capstone.domain;


import hello.capstone.util.TirePositionEnum;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
