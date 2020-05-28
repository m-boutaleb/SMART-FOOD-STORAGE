package model.section.service;


import model.product.Product;

public interface ProductSection {
    void printAllElements();
    boolean add(final Product product)throws Exception;
}
