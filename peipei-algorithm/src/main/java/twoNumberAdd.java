import lombok.extern.slf4j.Slf4j;
import vo.ListNode;

import java.util.List;

/**
 * @author Selves
 * @Date 2020/12/15
 * 两数之和
 */
@Slf4j
public class twoNumberAdd {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1);
        ListNode tmp = null;
        ListNode[] nodes = new ListNode[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new ListNode(9);
        }
        for (int i = 0; i < nodes.length; i++) {
            if (i == 0) {
                l2 = new ListNode(1);
                tmp = l2;
            } else {
                tmp.next = nodes[i];
                tmp = tmp.next;
            }
        }
//        do{
//            System.out.println(l2.val+"->");
//            l2 = l2.next;
//        }while (l2 != null);
        System.out.println("-----------");

        ListNode l3 = addTwoNumbers(l1, l2);

        do {
            System.out.println(l3.val + "->");
            l3 = l3.next;
        } while (l3 != null);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode();
        l3.val = -1;
        ListNode cursor = l3;
        int val1 = 0, val2 = 0, carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            val1 = l1 == null ? 0 : l1.val;
            val2 = l2 == null ? 0 : l2.val;

            int sum = val1 + val2 + carry;
            int result = sum % 10;
            ListNode next = new ListNode(result);
            if(l3.val == -1){
                l3 = next;
            }else {
                cursor.next = next;
            }
            cursor = next;

            carry = sum / 10;

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return l3;
    }
}
