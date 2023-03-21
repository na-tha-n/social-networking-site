
import java.util.Iterator;

/**
 *
 * @author Antou
 * @param <T>
 */
public class Array<T extends Object> implements Iterable<T>{
    private int size = 0;
    final private int capacity;
    T arr[];

    public Array(int capacity) {
        this.capacity = capacity;
        this.arr = (T[])(new Object[capacity]);
    }

    public Array() {
        this.capacity = 1024;
        this.arr = (T[])(new Object[capacity]);
    }
    
    int getSize(){
        return size;
    }
    
    T getElement(int index){
        return arr[index];
    }
    
    boolean addElement(T elm){
        if(size == capacity){
            return false;
        }
        arr[size++] = elm;
        return true;
    }
    
    void removeElement(int index){
        for(int i = index;i < size-1;i++){
            arr[i]=arr[i+1];
        }
        size--;
    }
    
    boolean removeElement(T elem){
        for(int i = 0;i<size;i++){
            if(arr[i].equals(elem)){
                removeElement(i);
                return true;
            }
        }
        return false;
    }
    
    int findElement(T elem){
        for(int i = 0;i<size;i++){
            if(arr[i].equals(elem)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index  = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }
}
