package hello.capstone.repository;

import hello.capstone.domain.Car;
import hello.capstone.domain.User;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {

    @Comment("유저가 가지는 차량 모두 조회")
    @Query("SELECT c FROM Car c WHERE c.user.id = :userId ")
    public List<Car> findByUserIdAndName(@Param("userId") Long userId);


    public Optional<Car> findByUserId(Long userId);
}
