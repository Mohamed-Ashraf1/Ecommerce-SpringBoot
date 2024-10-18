package com.example.ecommerce.Service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.STATUS;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderService {
//    EntityManager entityManager;
//    OrderDao orderDao;
//    ProductDao productDao;
//    UserDao userDao;
//    UserProductCartDao userProductCartDao;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository= orderRepository;
        this.userRepository = userRepository;

//        entityManager = em;
//        orderDao = new OrderDao(entityManager);
//        productDao = new ProductDao(entityManager);
//        userProductCartDao = new UserProductCartDao(entityManager);
//        userDao= new UserDao(entityManager);
    }


    public List<Order> getOrdersByUserId(int userId){
        User user = userRepository.findById(userId).get();
        return orderRepository.findByUser(user);
    }
    
    public Order getOrderById(int orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }

    @Transactional
    public Optional<Order> cancelOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(STATUS.CANCELED); // Assuming STATUS is an enum or constant
            orderRepository.save(order); // Save the updated order
            return Optional.of(order);
        }
        return Optional.empty();
    }

//        public Optional<Order> approveOrder(int orderId){
//            entityManager.getTransaction().begin();
//            Order order=orderDao.findById(orderId).get();
//            order.setStatus(STATUS.SHIPPED);
//            orderDao.update(order);
//            entityManager.getTransaction().commit();
//            return Optional.of(order);
//        }

//    public void placeOrder(User user, List<UserProductCart> cart,String paymentType,double bill,double payedFromBalance){
//        if (cart == null || cart.isEmpty()){
//            throw new RuntimeException("cart is empty");
//        }
//        double totalBill = payedFromBalance + bill;
//        PAYMENT payment;
//        if (bill==0){
//            payment=PAYMENT.VISA;
//        }else{
//            payment=paymentType.equals("cash")?PAYMENT.CASH:PAYMENT.VISA;
//        }
//        StringBuilder result = new StringBuilder();
//
//        // compare quantity for each product in the cart with the quantity in the inventory
//        String s=validateAvailabilityOfProductsQuantity(cart);
//        result.append(s);
//        if (result.length()>0){
//            throw new RuntimeException(result.toString());
//        }else{
//            try {
//                entityManager.getTransaction().begin();
//                Order order=new Order(totalBill,LocalDate.now(),STATUS.PENDING,payment,user);
//                orderDao.create(order);
//                for(UserProductCart cartItem:cart){
//                    Product product=productDao.findById(cartItem.getProduct().getId()).get();
//                    product.setQuantity(product.getQuantity()-cartItem.getProductQuantity());
//                    productDao.update(product);
//                    OrderProductList orderProduct=new OrderProductList(cartItem.getProductQuantity(), product.getPrice(),product,order );
//                    entityManager.persist(orderProduct);
//                }
//                double newBalance = Math.round((user.getBalance() - payedFromBalance) * 100.0) / 100.0;
//                user.setBalance(newBalance);
//                userDao.update(user);
//                userProductCartDao.deleteAllProductsFromCartByUserId(user.getId());
//                entityManager.getTransaction().commit();
//            }catch (Exception e){
//                entityManager.getTransaction().rollback();
//                throw new RuntimeException(e.getMessage());
//            }
//
//        }
//    }

//    private String validateAvailabilityOfProductsQuantity(List<UserProductCart> cart){
//        StringBuilder result = new StringBuilder();
//        for (UserProductCart cartItem : cart) {
//            Optional<Product> p=productDao.findById(cartItem.getProduct().getId());
//            if(p.isPresent()){
//                Product product=p.get();
//                if (product.getQuantity()==0){
//                    result.append("product : ").append(cartItem.getProduct().getProductName()).append(" is out of stock\n");
//                }else if (product.getQuantity()<cartItem.getProductQuantity()){
//                    result.append("you want ")
//                            .append(cartItem.getProductQuantity())
//                            .append(" from product ")
//                            .append(product.getProductName())
//                            .append(" and there only ")
//                            .append(product.getQuantity()).append("\n");
//                }
//            }else {
//                result.append("product : ").append(cartItem.getProduct().getProductName()).append(" is not exist\n");
//            }
//        }
//        return result.toString();
//    }


//   // public Optional<Order> getOrderById(int id){
//        return Optional.of(orderDao.findById(id).get());
//    }

}
