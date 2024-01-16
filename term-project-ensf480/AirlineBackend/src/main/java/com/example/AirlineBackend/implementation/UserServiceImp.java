package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.model.PremiumUserEntity;
import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.repository.PremiumUserRepository;
import com.example.AirlineBackend.repository.UserRepository;
import com.example.AirlineBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private PremiumUserRepository premiumUserRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PremiumUserRepository premiumUserRepository){
        super();
        this.userRepository = userRepository;
        this.premiumUserRepository = premiumUserRepository;
    }
    
    @Override
    public User saveUser(User user) {
        if ("registered-user".equals(user.getUserRole())) {
            PremiumUserEntity premiumUser = new PremiumUserEntity(user.getName(), user.getEmail(), user.getPassword(), user.getUserRole(), true, false, false, false);
            premiumUser.setBalance(1500);
            return premiumUserRepository.save(premiumUser);
        }
        user.setBalance(1500);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public String manageBalance(String email, double amountOwed) {
        List<User> tmp = userRepository.findAll();
        for (User user2 : tmp) {
            if (Objects.equals(user2.getEmail(), email)) {
                if (amountOwed <= user2.getBalance()) {
                    user2.setBalance(user2.getBalance() - amountOwed);
                    System.out.println(user2.getBalance());
                    return "Successful payment";
                } else if (amountOwed > user2.getBalance()) {
                    return "Failed payment, not enough money";
                }
            }
        }
        return "Cannot compute result";
    }

    @Override
    @Transactional
    public String refundBalance(String email, double refundAmount) {
        System.out.println(email);
        System.out.println(refundAmount);
        System.out.println(email);
        System.out.println(refundAmount);
        System.out.println(email);
        System.out.println(refundAmount);
        List<User> users = userRepository.findAll();
        for(User tmp : users){
            if(Objects.equals(email, tmp.getEmail())){
                tmp.setBalance(tmp.getBalance() + refundAmount);
                return "Successful refund";
            }
        }
        return "Could not refund";
    }

}
