package hello.capstone.repository;

import hello.capstone.domain.Tire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TireRepository extends JpaRepository<Tire,Long> {


}
