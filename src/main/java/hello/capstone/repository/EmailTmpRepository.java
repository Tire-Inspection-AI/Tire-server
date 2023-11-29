package hello.capstone.repository;

import hello.capstone.domain.entity.EmailTmp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTmpRepository extends JpaRepository<EmailTmp, Long> {

    public void deleteByCreatedAtGreaterThanEqual(LocalDateTime time);

    public Optional<EmailTmp> findByUserEmail(String userEmail);

    void delete(EmailTmp findByUserEmail);

}