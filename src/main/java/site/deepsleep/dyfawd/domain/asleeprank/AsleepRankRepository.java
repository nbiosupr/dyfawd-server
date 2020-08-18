package site.deepsleep.dyfawd.domain.asleeprank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsleepRankRepository extends JpaRepository<AsleepRank, Long> {
    Optional<AsleepRank> findTopByOrderByCreatedAtDesc();
}
