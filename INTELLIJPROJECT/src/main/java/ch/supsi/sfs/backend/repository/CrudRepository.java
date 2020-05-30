package ch.supsi.sfs.backend.repository;


public interface CrudRepository<T> {
    void printAllElements();
    boolean add(final T product);
    int[] checkStatus();
    boolean remove(final String barcode);
}
