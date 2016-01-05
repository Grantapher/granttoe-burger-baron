public class MyStack<T> {
    private Node<T> head;
    private int count;

    public MyStack() {
        head = null;
        count = 0;
    }

    public void push(T item) {
        ++count;
        Node<T> newNode = new Node<>(item);
        newNode.setNext(head);
        head = newNode;
    }

    public T pop() {
        --count;
        Node<T> deadNode = head;
        head = head.getNext();
        return deadNode.getData();
    }

    public T peek() {
        return head.getData();
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        MyStack<T> holdingStack = new MyStack<>();
        while (!isEmpty()) {
            T current = pop();
            sb.append(current.toString()).append(", ");
            holdingStack.push(current);
        }

        while (!holdingStack.isEmpty()) push(holdingStack.pop());

        sb.replace(sb.length() - 2, sb.length(), "");
        sb.append(']');

        return sb.toString();
    }

    private class Node<E> {
        private E data;
        private Node<E> next;

        private Node(E data) {
            this.data = data;
        }

        private Node<E> getNext() {
            return next;
        }

        private void setNext(Node<E> next) {
            this.next = next;
        }

        private E getData() {
            return data;
        }
    }

}
