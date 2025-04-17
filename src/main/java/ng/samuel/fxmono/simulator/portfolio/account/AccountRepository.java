package ng.samuel.fxmono.simulator.portfolio.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    // create a custom findByEmail method using native SQL
    @Query(value = "SELECT * FROM EMPLOYEE u WHERE u.last_name = ?1",
            nativeQuery = true)
    Optional<Account> findByLastName(String lastName);

    // such as findByColumnName(column data)
    Optional<Account> findByEmail(String email);

    // create a custom getEmployeesByPage method using native SQL
    @Query(value = "SELECT * FROM EMPLOYEE LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Account> getUsersByPage(int limit, int offset);


}
