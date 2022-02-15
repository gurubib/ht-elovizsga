package hu.gurubib.ht.elovizsga.task2.dao.repositories;

import hu.gurubib.ht.elovizsga.task2.dao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
