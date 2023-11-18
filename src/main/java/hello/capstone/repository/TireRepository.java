package hello.capstone.repository;

import hello.capstone.domain.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<Tire,Long> {

    public Optional<Tire> findById(Long id);
}
