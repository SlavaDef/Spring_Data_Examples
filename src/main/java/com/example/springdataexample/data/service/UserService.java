package com.example.springdataexample.data.service;

import com.example.springdataexample.data.models.User;
import com.example.springdataexample.data.models.dto.UserDto;
import com.example.springdataexample.data.models.dto.UserInfoDto;
import com.example.springdataexample.data.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final EntityManager manager; // також можно використовувати але більш древне
    // спринговий клас який дозволяє прямо виконувати запити
    private final NamedParameterJdbcTemplate template;

    public List<User> findAll() {
        return repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }


    public boolean exist(String email) { // перед видаленням перевіряємо чи є такий юзер
        if (email == null) {
            return false;
        }

        Integer userCount = template.queryForObject("SELECT count(*) FROM \"user\" WHERE email = :email",
                Map.of("email", email), Integer.class);


        // User user = manager.find(User.class, email);
        // return user!= null;

        // return repository.existsById(email);
        return userCount == 1;
    }

    public void deleteByEmail(String email) {

        repository.deleteById(email);
    }

    @Transactional
    public void deleteByIds(List<String> emails) {
        template.update("DELETE FROM \"user\" email IN (:emails)",
                Map.of("emails", emails)
        );

        //  repository.deleteAllByEmails(emails);
    }

    public List<User> search(String query) {
        return repository.search("%" + query + "%");

    }

    public List<User> search2(String query) {
        return repository.findAllById(repository.searchEmail("%" + query + "%"));


        //  return  repository.searchBySql("%"+query +"%");

    }

    // робимо метод для підрахунку н років від н року

    public int countPeopleOlderThen(int age) {
        LocalDate maxBirthday = LocalDate.now().minusYears(age);
        return repository.countOlderThan(maxBirthday);

    /* return (int)  findAll()
                .stream()
                .filter(u -> {

                    int userAge = (int) ChronoUnit.YEARS.between(u.getBirthday(), LocalDate.now());
                            return userAge > age;
                })
                .count();
*/
    }

    public UserInfoDto getUserInfo(String email) {

        User user = repository.findById(email).get();
        List<String> userAddress = template.queryForList("SELECT address FROM user_address " +
                        "WHERE email = :email",
                Map.of("email", email), String.class);
        UserInfoDto resalt = new UserInfoDto();
        resalt.setUserDto(UserDto.fromUser(user));
        resalt.setAdresses(userAddress);

        return resalt;

    }

}
