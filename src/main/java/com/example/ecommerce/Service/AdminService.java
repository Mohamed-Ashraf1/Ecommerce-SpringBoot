package com.example.ecommerce.Service;

import com.example.ecommerce.entity.Admin;
import com.example.ecommerce.repository.AdminRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class AdminService {

//    private AdminDao adminDao;
//    private EntityManager entityManager;
    AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository)
    {
       this.adminRepository = adminRepository;
    }


    public Admin checkAdmin(String email, String password)
    {
        Optional<Admin> admin = adminRepository.findAdminByEmail(email);
        if(admin.isPresent())
        {
            if(password.equals( admin.get().getPassword()) )
                return admin.get();
        }
        return null;
    }

    public void updateAdmin(Admin admin)
    {
        try {
//            entityManager.getTransaction().begin();
//            System.out.println("transaction begin to update admin");
//
//            adminDao.update(admin);
//            System.out.println(" merge admin");
//
//            entityManager.getTransaction().commit();
//            System.out.println(" transaction completed");
            adminRepository.save(admin);

        } catch (RuntimeException e) {

            throw new RuntimeException("Can't update this admin");
        }
    }

//    public Map<Integer, List<Order>> getOrders(int pageNumber,int pageSize,Double minPrice,Double maxPrice,boolean sortByPrice,PAYMENT payment,STATUS status) throws Exception
//    {
//        try {
//            OrderDao orderDao = new OrderDao(entityManager);
//            return orderDao.filterOrders(status, payment, null, null, minPrice, maxPrice, sortByPrice, pageNumber, pageSize);
//        }catch (Exception e)
//        {
//            throw new Exception("Something went wrong");
//        }
//    }
//
//    public Map<Integer, List<User>> getUsers(int pageNumber,int pageSize) throws Exception
//    {
//        try {
//            UserDao userDao = new UserDao(entityManager);
//            return userDao.findAllUsers(pageNumber, pageSize);
//        }catch (Exception e)
//        {
//            throw new Exception("Something went wrong");
//        }
//    }


}
