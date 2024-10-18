package com.example.ecommerce.Service;

import jakarta.persistence.EntityManager;
import org.example.bean.ProductBean;
import org.example.dao.CategoryDao;
import org.example.dao.ProductDao;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private EntityManager em;
    public ProductService(EntityManager entityManager)
    {
        em = entityManager;
        this.productDao = new ProductDao(entityManager);
        this.categoryDao= new CategoryDao(entityManager);
    }
    public Product addNewProduct(ProductBean productbean) throws RuntimeException
    {
        ProductMapper productMapper=new ProductMapper(categoryDao,em);
        Product product= productMapper.toEntity(productbean);
        try {
            em.getTransaction().begin();
            productDao.create(product);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
       return product;
    }
    public Product addNewProduct(Product product) throws RuntimeException
    {
        if (product.getQuantity()<1|| product.getPrice() <1)
            throw new RuntimeException("Error, please enter valid Price or quantity");
        try {
            em.getTransaction().begin();
            productDao.create(product);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error, Product name duplicated");
        }
        return product;
    }

    public List<Category> getSubCategories() throws RuntimeException
    {
        return categoryDao.getSubCategories();

    }

    private boolean checkData(String name, String description,String quantity,String price,String image){
        if (name != null && !name.trim().isEmpty() &&
                description != null && !description.trim().isEmpty() &&
                quantity != null && !quantity.trim().isEmpty() &&
                price != null && !price.trim().isEmpty() &&
                image != null && !image.trim().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public Product findProductById(int id)
    {
        Optional<Product> product = productDao.findById(id);

        if(product.isPresent())
        {
            return product.get();
        }
        else{
            throw new RuntimeException("Product not found");
        }
    }

    public void deleteproduct(int id)
    {
        Optional<Product> product = productDao.findById(id);

        if(product.isPresent())
        {
            em.getTransaction().begin();
            product.get().setDeleted(true);
            productDao.update(product.get());
            em.getTransaction().commit();
        }
        else{
            throw new RuntimeException("Product not found");
        }
    }


    public Map<Integer,List<Product>> findAllProducts(List<String> subCategories, Double minPrice, Double maxPrice, boolean sortByPrice, boolean sortByQuantity, int pageNumber, int pageSize,String searchText)
    {

        if(subCategories == null)
            return productDao.filterProducts(null, minPrice,  maxPrice,  sortByPrice,  sortByQuantity, pageNumber,  pageSize,searchText);
        else
        {
            List<Category> subs = null;
            try {
                    subs = subCategories.stream().map(x -> {
                    return categoryDao.findCategoryByName(x).get();
                }).collect(Collectors.toList());
            }catch (Exception e)
            {
                throw new RuntimeException("SubCategory not found");
            }

            return productDao.filterProducts(subs, minPrice,  maxPrice,  sortByPrice,  sortByQuantity, pageNumber,  pageSize,searchText);

        }

    }


    public List<Product> findProductsBySubcategory(int subCategoryId)
    {
        return productDao.findProductsBySubCategory(subCategoryId);
    }

    public List<Product> getProductsByPage(int pageNumber) {
        int pageSize = 9; // Set the number of products per page
        return productDao.getProducts(pageNumber, pageSize);
    }

    public Integer getTotalProductCount() {
        return productDao.getProductCount();
    }

    public void updateProduct(int id, String name, String description, int quantity, double price,String category){
        em.getTransaction().begin();
        Product product = findProductById(id);
        product.setProductName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setPrice(price);
        Optional<Category> category1=categoryDao.findCategoryByName(category);
        product.setCategory(category1.get());
        productDao.update(product);
        em.getTransaction().commit();

    }
}
