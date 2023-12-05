package hello.capstone.repository;

import hello.capstone.domain.entity.Tire;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<Tire, Long> {

    public Optional<Tire> findById(Long id);

    @Comment("차량이 가지는 타이어 모두 조회")
    @Query("SELECT t FROM Tire t WHERE t.car.id = :carId ")
    public List<Tire> findByCarId(@Param("carId") Long carId);
}
