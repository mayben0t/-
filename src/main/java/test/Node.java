package test;

public class Node {
    public int index;
    public Node next;

    public Node(int index, Node next) {
        this.index = index;
        this.next = next;
    }


    @Override
    public String toString() {
        return "Node{" +
                "index=" + index +
                ", next=" + (next==null?"null":next.index+"" )+
                '}';
    }
}
