
/**
 * This is a double linked list class which is responsible for creating a linked
 * list of the type T.
 * @author Antou
 * @param <T> User-specified type to create the linked list to.
 */
public class List<T> implements Iterable<T> {

    /**
     * size of the list.
     */
    private int size = 0;
    /**
     * pointer to the head of the list.
     */
    private Node<T> head = null;
    /**
     * pointer to the tail of the list.
     */
    private Node<T> tail = null;

    void clear() {
        head = null;
    }

    /**
     * Internal node class to represent data.
     * @param <T> The user-specified type of the list
     */
    private static class Node<T> {
        /**
         * the internal data.
         */
        private T data;
        /**
         * pointer to the prev and next of the node.
         */
        private Node<T> prev, next;

        /**
         * Constructor to construct a node of the variables data, prev, next
         * @param data is the data included in the node.
         * @param prev is the pointer to the prev element.
         * @param next is the pointer to the next element.
         */
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    
    /**
     * Returns the size of the linked list.
     * @return the size of the linked list.
     */
    public int size() {
        return size;
    }

    /**
     * Is the linked list empty?
     * @return true if the linked list empty. 
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Add a node to the tail of the linked list, O(1).
     * @param elem the element to be stored in the list.
     */
    public void add(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            tail.next = new Node<>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }


    /**
     * Returns an iterator to traverse the list.
     * @return iterator to traverse the list
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            /**
             * Variable to keep track of the last traversed element.
             */
            private Node<T> trav = head;
            
            /**
             * Returns true if the list has another element to be traversed.
             * @return true if the list has another element to be traversed.
             */
            @Override
            public boolean hasNext() {
                return trav != null;
            }

            /**
             * returns the next element.
             * @return the next element.
             */
            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                size--;
                if(trav == head){
                    head = head.next;
                    if(head != null)
                        head.prev = null;
                    return;
                }
                trav.prev.next = trav.next;
                
                if(trav.next != null)
                    trav.next.prev = trav.prev;
            }
        };
    }
}
