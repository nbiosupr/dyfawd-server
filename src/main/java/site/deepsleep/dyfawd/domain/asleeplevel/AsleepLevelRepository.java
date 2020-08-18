package site.deepsleep.dyfawd.domain.asleeplevel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsleepLevelRepository extends JpaRepository<AsleepLevel, Long> {
    Optional<AsleepLevel> findTopByOrderByCreatedAtDesc();
}
