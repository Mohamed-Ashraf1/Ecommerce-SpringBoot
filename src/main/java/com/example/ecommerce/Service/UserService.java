//package com.example.ecommerce.Service;
//
//import jakarta.persistence.EntityManager;
//import org.apache.commons.validator.routines.EmailValidator;
//import org.example.dao.BalanceLogsDao;
//import org.example.dao.UserDao;
//import org.example.entity.*;
//
//import java.util.Optional;

//import org.springframework.stereotype.Service;

//@Service
//public class UserService {
//    private UserDao userDao;
//    private EntityManager em;
//    BalanceLogsDao balanceLogsDao;
//    public UserService(EntityManager entityManager)
//    {
//        em = entityManager;
//        this.userDao = new UserDao(entityManager);
//        balanceLogsDao = new BalanceLogsDao(entityManager);
//    }
//    public boolean checkIfEmailExist(String email){
//
//        Optional<User> u = userDao.findUserByEmail(email);
//
//        return u.isPresent();
//
//    }
//
//    public User loginCheck(String email, String password) throws RuntimeException {
//        Optional<User> result = userDao.findUserByEmail(email);
//
//        if (result.isPresent()) {
//            if (password.equals(result.get().getPassword())) {
//                return result.get();
//            }
//        }
//        return null;
//    }
//
//    public User registerNewUser(String name, String email, String password
//            , String street, String city, String country, String phone, String dob, String gender) throws RuntimeException {
//        User user = new User();
//        if (checkData(name, email, password, street, city, country, phone, dob, gender)) {
//            System.out.println("username:" + name);
//            if (EmailValidator.getInstance().isValid(email)) {
//
//                if (!userDao.findUserByEmail(email).isPresent()) {
//                    GENDER g = gender.equals("Female") ? GENDER.FEMALE : GENDER.MALE;
//
//                    user = new User(name, email, password, g, 0.0, dob, phone);
//                    Address a = new Address(street, city, country, user);
//                    user.setAddress(a);
//                    try {
//                        em.getTransaction().begin();
//                        userDao.create(user);
//                        em.getTransaction().commit();
//                    } catch (Exception e) {
//                        em.getTransaction().rollback();
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    throw new RuntimeException("Email already in use");
//
//                }
//            } else {
//                throw new RuntimeException("invalid email");
//
//            }
//        } else {
//            throw new RuntimeException("enter all fields please");
//
//        }
//
//        return user;
//
//    }
//
//    private boolean checkData(String name, String email, String password
//            , String street, String city, String country, String phone, String dob, String gender) {
//        if (name != null && !name.trim().isEmpty() &&
//                email != null && !email.trim().isEmpty() &&
//                password != null && !password.trim().isEmpty() &&
//                street != null && !street.trim().isEmpty() &&
//                city != null && !city.trim().isEmpty() &&
//                country != null && !country.trim().isEmpty() &&
//                phone != null && !phone.trim().isEmpty() &&
//                dob != null && !dob.trim().isEmpty() &&
//                gender != null && !gender.trim().isEmpty()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public User findUserById(int id) throws RuntimeException {
//
//        Optional<User> user = userDao.findById(id);
//
//        if (user.isPresent()) {
//            System.out.println("Birthdate: " + user.get().getDateOfBirth());
//            return user.get();
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//
//    public void updateUser(User user) throws RuntimeException {
//        try {
//            em.getTransaction().begin();
//            System.out.println("transatction has begun");
//            userDao.update(user);
//            System.out.println("merge operation applied");
//
//            em.getTransaction().commit();
//            System.out.println("commit done");
//
//        } catch (RuntimeException e) {
//            System.out.println("exception happened, commit is rolled back");
//            throw new RuntimeException("Can't update this user");
//        }
//    }
//    public Optional<User> AddBalanceToUser(int userId, double amount){
//        Optional<User> user = userDao.findById(userId);
//        if (user.isPresent()) {
//            try {
//                em.getTransaction().begin();
//                user.get().setBalance(user.get().getBalance() + amount);
//                BalanceLogs balanceLogs=new BalanceLogs(amount,PAYMENT.VISA,user.get());
//                balanceLogsDao.create(balanceLogs);
//                em.getTransaction().commit();
//                return userDao.findById(userId);
//            }catch (Exception e) {
//                em.getTransaction().rollback();
//                throw new RuntimeException(e.getMessage());
//            }
//
//
//
//
//        }
//        return user;
//    }
//
//}
