package BFS;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * @author xuyihao
 * @date 2019/08/14
 */

public class BFS_SwingLock {
    public int openLock(String[] deadends, String target) {
        Set<String> vis = new HashSet<>();
        for (String str : deadends) {
            vis.add(str);
        }
        if (vis.contains("0000")) {
            return -1;
        }
        vis.add("0000");
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("0000");
        int[] way = {-1, 1};
        int sum = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String now = queue.poll();
                if (now.equals(target)) {
                    return sum;
                }
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 2; k++) {
                        char[] c = now.toCharArray();
                        int digit = (c[j] - '0' + way[k] + 10) % 10;
                        c[j] = (char) ('0' + digit);
                        String nxt = new String(c);
                        if (!vis.contains(nxt)) {
                            vis.add(nxt);
                            queue.offer(nxt);
                        }
                    }
                }
            }
            sum++;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] deadends = new String[]{"0201", "0101", "0102", "1212", "2002"};
        String target = "0202";
        BFS_SwingLock bfs_swingLock = new BFS_SwingLock();
        int count = bfs_swingLock.openLock(deadends, target);
        System.out.println("count = " + count);
    }
}
