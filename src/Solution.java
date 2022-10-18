import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {
    PriorityQueue<String> min_heap=new PriorityQueue<>();
    PriorityQueue<String> max_heap=new PriorityQueue<>();
    int count=0;//记录已获取数字的个数
    public void Insert(String num) {
        if(count%2==0){
            max_heap.offer(num);
            String tmp=max_heap.poll();
            min_heap.offer(tmp);
        }
        else{
            min_heap.offer(num);
            String tmp=min_heap.poll();
            max_heap.offer(tmp);
        }
        count++;
    }

    public Double GetMedian() {
        if(count%2==0){
            return new Double(min_heap.peek()+max_heap.peek())/2;
        }
        else{
            return new Double(min_heap.peek());
        }
    }
}
