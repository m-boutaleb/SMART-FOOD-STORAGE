package model.section.service;


public interface ProductSection<T> {
    void printAllElements();
    boolean add(final T product);
    T getRandomType();
}
