package com.example.ecommerce.Service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserProductCart;
import com.example.ecommerce.entity.UserProductId;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserProductCartRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    UserProductCartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public CartService(UserProductCartRepository cartRepository, UserRepository userRepository,ProductRepository productRepository)
    {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public void addProductToCart(int userId,int productId)
    {

        Optional<User> user = userRepository.findById(userId);

        Optional<Product> product = productRepository.findById(productId);

        if(user.isPresent() && product.isPresent())
        {
            try {
//            if(product.get().getQuantity() < 1)
//                throw new RuntimeException("No Enough quantity for the product");
            //no need to decrease product qunatity here, beacuse no action taken to make an order yet

            //check if there is old history before
            Optional<UserProductCart> oldUserProductCart = cartRepository.findById(new UserProductId(userId,productId));
            if(oldUserProductCart.isPresent())
            {
                //update
                oldUserProductCart.get().setProductQuantity(oldUserProductCart.get().getProductQuantity()+1);
                cartRepository.save(oldUserProductCart.get());
            }
            else
            {
                //create new object
                UserProductCart userProductCart = new UserProductCart(user.get(),product.get(),1);
                //create in persistance context
                cartRepository.save(userProductCart);
            }

            }catch (RuntimeException e)
            {
                throw new RuntimeException("Transaction has failed");
            }
        }
        else {
            throw new RuntimeException("Error, Add product or user not valid");
        }
    }
//
    public List<UserProductCart> getUserCartProducts(User user){
        List<UserProductCart> productCartList= cartRepository.getCartByUser(user);
        return productCartList;
    }
//
    public void deleteProductFromCart(int userId,int productId){

         cartRepository.deleteProductFromCart(userId,productId);
    }
    public void updateProductQuantity(int userId,int productId,int quantity){
        try {

            Optional<UserProductCart> oldUserProductCart = cartRepository.findById(new UserProductId(userId,productId));
            oldUserProductCart.get().setProductQuantity(quantity);
            cartRepository.save(oldUserProductCart.get());

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
//    public int cartProductsCount(int userId)
//    {
//        return cartDao.countProductsByUser(userId);
//    }
}
