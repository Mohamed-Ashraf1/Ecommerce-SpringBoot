package com.example.ecommerce.Service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    OrderProductListRepository orderProductListRepository;
    UserProductCartRepository userProductCartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,ProductRepository productRepository, OrderProductListRepository orderProductListRepository) {
        this.orderRepository= orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderProductListRepository = orderProductListRepository;

    }


    public List<Order> getOrdersByUserId(int userId){
        User user = userRepository.findById(userId).get();
        return orderRepository.findByUser(user);
    }
    
    public Order getOrderById(int orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }

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

        public Optional<Order> approveOrder(int orderId){

            Order order = orderRepository.findById(orderId).get();
            order.setStatus(STATUS.SHIPPED);
            orderRepository.save(order);
            return Optional.of(order);
        }

    public void placeOrder(User user, List<UserProductCart> cart,String paymentType,double bill,double payedFromBalance){
        if (cart == null || cart.isEmpty()){
            throw new RuntimeException("cart is empty");
        }
        double totalBill = payedFromBalance + bill;
        PAYMENT payment;
        if (bill==0){
            payment=PAYMENT.VISA;
        }else{
            payment=paymentType.equals("cash")?PAYMENT.CASH:PAYMENT.VISA;
        }
        StringBuilder result = new StringBuilder();

        // compare quantity for each product in the cart with the quantity in the inventory
        String s=validateAvailabilityOfProductsQuantity(cart);
        result.append(s);
        if (result.length()>0){
            throw new RuntimeException(result.toString());
        }else{
            try {
                Order order=new Order(totalBill,LocalDate.now(),STATUS.PENDING,payment,user);
                orderRepository.save(order);
                for(UserProductCart cartItem:cart){
                    Product product=productRepository.findById(cartItem.getProduct().getId()).get();
                    product.setQuantity(product.getQuantity()-cartItem.getProductQuantity());
                    productRepository.save(product);
                    OrderProductList orderProduct=new OrderProductList(cartItem.getProductQuantity(), product.getPrice(),product,order );
                    orderProductListRepository.save(orderProduct);
                }
                double newBalance = Math.round((user.getBalance() - payedFromBalance) * 100.0) / 100.0;
                user.setBalance(newBalance);
                userRepository.save(user);
                userProductCartRepository.deleteAllProductsFromCartByUser(user);
            }catch (Exception e){

                throw new RuntimeException(e.getMessage());
            }

        }
    }

    private String validateAvailabilityOfProductsQuantity(List<UserProductCart> cart){
        StringBuilder result = new StringBuilder();
        for (UserProductCart cartItem : cart) {
            Optional<Product> p=productRepository.findById(cartItem.getProduct().getId());
            if(p.isPresent()){
                Product product=p.get();
                if (product.getQuantity()==0){
                    result.append("product : ").append(cartItem.getProduct().getProductName()).append(" is out of stock\n");
                }else if (product.getQuantity()<cartItem.getProductQuantity()){
                    result.append("you want ")
                            .append(cartItem.getProductQuantity())
                            .append(" from product ")
                            .append(product.getProductName())
                            .append(" and there only ")
                            .append(product.getQuantity()).append("\n");
                }
            }else {
                result.append("product : ").append(cartItem.getProduct().getProductName()).append(" is not exist\n");
            }
        }
        return result.toString();
    }


}
