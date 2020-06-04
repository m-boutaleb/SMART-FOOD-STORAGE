package ch.supsi.sfs.backend.repository.property;


public interface CrudRepository<T> {
    boolean add(final T product);
    int[] checkStatus();
    boolean remove(final String barcode);
}
