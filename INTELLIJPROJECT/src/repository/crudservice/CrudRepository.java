package repository.crudservice;


public interface CrudRepository<T> {
    void printAllElements();
    boolean add(final T product);
}
