package com.example.springdataexample.data.repo;

import com.example.springdataexample.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // описуємо логіку запиту до бази данних
    @Query("from User u where lower(u.email) like lower(:query) or lower(u.fullName) like lower(:query)")
    List<User> search(@Param("query") String query); // це HQl варіант


    // nativeQuery = true == sql запит
    @Query(nativeQuery = true, value = "SELECT email, full_name AS fullName, birthday, gender " +
            "FROM \"user\"" +
            "WHERE lower(email) LIKE lower(:query) or lower(full_name) LIKE lower(:query)")
    List<User> searchBySql(@Param("query") String query);

    // вибірка всіх ємейлів
    @Query(nativeQuery = true, value = "SELECT email " +
            "FROM \"user\"" +
            "WHERE lower(email) LIKE lower(:query) or lower(full_name) LIKE lower(:query)")
    List<String> searchEmail(@Param("query") String query);

    @Query(nativeQuery = true, value
            = "SELECT count(*) FROM \"user\" WHERE birthday < :maxBirthday")
    int countOlderThan(LocalDate maxBirthday);

    @Modifying// вказуємо спрінгу що модифікуємо змінюємо данні
    @Query(nativeQuery = true, value = "DELETE FROM \"user\" WHERE email IN (:emails)")
    void deleteAllByEmails(@Param("emails") List<String> emails);


}
