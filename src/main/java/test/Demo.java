package test;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Node node = null;
        Node head = null;
        Node head2 = null;
//        Node temp = null;
        for(int i=10;i>0;i--){
            Node node1 = new Node(i, node);
//            node1.next = temp;
//            temp = node1;
            node = node1;
            if(i==1)
                head = node1;
                head2 = node1;
        }

        while (head2!=null){
            System.out.println(head2);
            head2 = head2.next;
        }

        System.out.println("====================");

        int count = 0;
        ArrayList<Node> test = new ArrayList<>();
        Node temp = null;
        while (head !=null){
            count++;
            test.add(head);
            if(head.next == null){
                temp = head;
            }
            head = head.next;

        }
        List<Node> lists = new ArrayList<>();
        for(int j=test.size()-1;j>=0;j--){

            Node node1 = test.get(j);
            if(j==0){
                node1.next = null;
            }else {
                Node node2 = test.get(j - 1);
                node1.next = node2;
            }


        }

        while(temp!=null){
            System.out.println(temp);
            temp = temp.next;
        }



    }
}
