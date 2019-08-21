package BFS;

import java.util.*;

/**
 * @author xuyihao
 * @date 2019/08/14
 * 转盘锁
 */
/*密码锁由四个转轮上的数字组成密码锁的状态，同时我们可以把转动密码锁看成是密码锁状态的改变。一般而言如果一个问题是某事物状态的改变，那么我们可以考虑把问题转换成图搜索的问题。

我们解决图的问题的第一步就是找出问题对应的图（Graph）。由于图是顶点和边的集合，因此找图的关键是找出图的顶点和边。对于这个问题，密码锁的每一个状态都对应着图上的一个顶点。比如状态"0000"是一个顶点，"0001"是另一个顶点。 

如果转动某个转轮一次可以让密码锁从一个状态转移到另一个状态，那么这两个状态之间有一条边相连。例如在状态"0000"分别向上或者向下转动四个转轮中的一个，可以得到如下8个状态："0001"、"0009"、"0010"、"0090"、"0100"、"0900"、"1000"和"9000"。那么图中顶点"0000"就有8条边分别和这8个状态对应的顶点相连。

解决图的问题的第二步是决定用什么顺序来遍历图。通常有两种不同方法遍历图，广度优先搜索和深度优先搜索。由于题目要求的是找出顶点"0000"到目标状态对应的顶点的最短路径，那么我们应该采用广度优先搜索算法。这是因为广度优先搜索是从源点开始首先达到所有距离源点为1的顶点，接着轮到达所有距离源点为2的所有顶点。根据广度优先搜索从源点到达某一顶点，那么一定是途径从源点到达该结点的最短路径。

遍历密码锁对应的图时还要注意的是要避开死锁状态对应的顶点，因此一达到这些顶点之后就不能继续往下搜索了。*/
public class BFS_SwingLock {
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        String init = "0000";
        if (dead.contains(init) || dead.contains(target)) {
            return -1;
        }
        Queue<String> queue1 = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        int steps = 0;
        queue1.offer(init);
        while (!queue1.isEmpty()) {
            String cur = queue1.poll();
            if (cur.equals(target)) {
                return steps;
            }
            List<String> nexts = getNexts(cur);
            for (String next : nexts) {
                if (!dead.contains(next) && !visited.contains(next)) {
                    visited.add(next);
                    queue2.offer(next);
                }
            }

            if (queue1.isEmpty()) {
                steps++;
                queue1 = queue2;
                queue2 = new LinkedList<>();
            }
        }
        return -1;
    }

    /**
     * 上述代码用两个队列实现宽度优先遍历。队列queue1里存的是需要转动n次达到的顶点，queue2是和queue1里的顶点相连同时还没有遍历到的顶点。当queue1的里顶点都删掉之后，接着遍历需要转动n+1次到达的顶点，也就是queue2里的顶点，此时变量steps加1。
     *
     * 下面是辅助函数getNexts的代码，它的作用是根据密码锁的转动规则得到与某一状态相连的8个状态：
     *
     * @param cur
     * @return
     */
    private List<String> getNexts(String cur) {
        List<String> nexts = new LinkedList<>();
        for (int i = 0; i < cur.length(); ++i) {
            char ch = cur.charAt(i);

            char newCh = ch == '0' ? '9' : (char) (ch - 1);
            StringBuilder builder = new StringBuilder(cur);
            builder.setCharAt(i, newCh);
            nexts.add(builder.toString());

            newCh = ch == '9' ? '0' : (char) (ch + 1);
            builder = new StringBuilder(cur);
            builder.setCharAt(i, newCh);
            nexts.add(builder.toString());
        }

        return nexts;

    }

    public static void main(String[] args) {
        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
        String target = "0202";
        BFS_SwingLock bfs_swingLock = new BFS_SwingLock();
        int count = bfs_swingLock.openLock(deadends, target);
        System.out.println("count = " + count);
    }


    /*解法二：双向广度优先搜索

这个问题是单个源点（"0000"）单个目标（输入的target）的广度优先搜索。我们可以想象当我们到达目标节点的时候，我们同时还遍历了求解树中位于同一层的其他节点。尽管在那一层中我们只需要遍历一个节点，我们却实际上遍历很多不必要的节点，因此单向搜索是存在优化空间的。

我们可以把单项搜索改为双向搜索，也就是既从源点出发向着目标搜索，也从目标出发向着源点搜索。如果两个方向搜索最终能够在中间某个位置相遇，那么表明存在从源点到目标的路径。

我们只需要在单向搜索的代码上稍微作些改动，就能实现双向搜索，如下所示：*/
  /*Set<String> dead = new HashSet<>(Arrays.asList(deadends));
    Set<String> visited = new HashSet<>();
    String init = "0000";
    if (dead.contains(init) || dead.contains(target)) {
        return -1;
    }

    if (target.equals(init)) {
        return 0;
    }

    Set<String> set1 = new HashSet<>();
    set1.add(init);
    Set<String> set2 = new HashSet<>();
    set2.add(target);

    int steps = 0;
    while (!set1.isEmpty() && !set2.isEmpty()) {
        if (set1.size() > set2.size()) {
            Set<String> temp = set1;
            set1 = set2;
            set2 = temp;
        }

        Set<String> set3 = new HashSet<>();
        for (String cur : set1) {
            for (String next : getNexts(cur)) {
                if (set2.contains(next)) {
                    return steps + 1;
                }

                if (!dead.contains(next) && !visited.contains(next)) {
                    visited.add(next);
                    set3.add(next);
                }
            }
        }

        steps++;
        set1 = set3;
    }

    return -1;*/

/**
 * 其中一个改动是保存节点的数据结构由队列Queue改成了集合HashSet。这是由于我们需要判断我们某一方向搜索的节点是不是位于另一个方向搜索的集合之中（也就是判断两个方向的搜索是否相遇）。HashSet能够更高效地完成这一要求。另外，我们只要做到遍历求解树的一层之后遍历下一层。同一层的遍历顺序并不重要，因此广度优先搜索算法本身也不是一定要用先进先出的队列实现。

我们一共用了三个集合。集合set1和set2保存两个方向当前遍历层的节点。我们总是优先遍历set1和set2中节点数目较少的那个方向的节点（通过交换set1和set2确保set1中的节点数目总是小于set2的）。集合set3保存的set1的节点的下一层节点。

上述代码改动虽然很小，但性能优化效果却很明显。LeetCode的测试数据表明优化之后的运行时间只有优化之前的运行时间的四分之一到三分之一
 */

}
