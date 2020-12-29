package concurrent.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Selves
 * @Date 2020/12/24
 */
@Data
@AllArgsConstructor
public class SumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 100;
    private static AtomicInteger depth = new AtomicInteger(0);
    private long[] array;
    private int start;
    private int end;

    @Override
    protected Long compute() {
        int curDepth = depth.incrementAndGet();
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            System.out.println(String.format("depth:{%d} computing %d~%d = %d", curDepth, start, end, sum));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(String.format("depth:{%d} compute %d~%d = %d", curDepth, start, end, sum));
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        //invokeAll的N个任务中，其中N-1个任务会使用fork()交给其它线程执行，但是，它还会留一个任务自己执行，这样，就充分利用了线程池，保证没有空闲的不干活的线程。
//        invokeAll(subtask1, subtask2);
        //以下写法是错误的
        //相当于把任务1又重新分割一次，这是因为执行compute()方法的线程本身也是一个Worker线程，当对两个子任务调用fork()时，
        // 这个Worker线程就会把任务分配给另外两个Worker，但是它自己却停下来等待不干活了！
        // 这样就白白浪费了Fork/Join线程池中的一个Worker线程，导致了4个子任务至少需要7个线程才能并发执行。
        // https://www.liaoxuefeng.com/article/1146802219354112
        //用两次fork()在join的时候，需要用这样的顺序：a.fork(); b.fork(); b.join(); a.join();这个要求在JDK官方文档里有说明。
        subtask1.fork();
        subtask2.fork();
        Long subResult1 = subtask1.join();
        Long subResult2 = subtask2.join();
        Long result = subResult1 + subResult2;
        System.out.println("result = " + subResult1 + " + " + subResult2 + " ==> " + result);
        return result;
    }
}
