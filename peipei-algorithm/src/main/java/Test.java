import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author Selves
 * @Date 2020/12/15
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
//        log.info("result={}", findMedianSortedArrays(
//                new int[]{1,2},
//                new int[]{3,4}));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        double mid = Math.floor((l1 + l2) / 2.0);
        //是否需要累加两个数来计算中位数
        boolean isOdd = mid % 2 != 0;
        int n1, n2;
        int i1 = 0, i2 = 0;
        int scanCount = 0;
        boolean isNum1 = false;
        while (true) {
            n1 = l1 > 0 ? nums1[i1] : 0;
            n2 = l2 > 0 ? nums2[i2] : 0;
            if (scanCount == (int) mid) {
                int m1 = Math.min(n1, n2);
                if (isOdd) {
                    return m1;
                } else {
                    if (l1 == 0) {
                        return n2;
                    }
                    if (l2 == 0) {
                        return n1;
                    }
                    int m2 = isNum1 ? nums1[i1 - 1] : nums2[i2 - 1];
                    return (n1 + n2) / 2.0;
                }
            }
            if (n1 < n2) {
                i1 = i1 + 1 == l1 ? i1 : i1 + 1;
                isNum1 = true;
            } else {
                i2 = i2 + 1 == l2 ? i2 : i2 + 1;
                isNum1 = false;
            }
            scanCount++;
        }
    }

}
