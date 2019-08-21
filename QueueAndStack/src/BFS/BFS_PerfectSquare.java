package BFS;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author xuyihao
 * @date 2019/08/21
 */

/**
 * ① 我们可以这样想对于当前需要凑的数我们可以依次去尝试减去1的平方、2的平方、3的平方、4的平方...n的平方，而且我们应该知道n应该是小于等于n开根号的，假如凑不出来我们应该继续上面的步骤，但是我们应该将当前的数字减去相应的平方表示我们尝试使用当前的平方数字去凑

② 从上面的分析过程我们可以知道这有点类似与树的结构，没错这就是树的结构，对于树上的每一层我们都是尝试去减去当前的平方数，那么这个就是树的层次遍历了，知道了树的层次遍历之后就比较好办了，可以使用宽度优先搜索来进行解决

③ 宽度优先需要借助队列来进行解决，因为我们需要知道当前的节点和当前目前的值是什么，所以需要维护两个变量，可以使用Java中类的嵌套，即可以使用私有的内部类来封装这两个节点，因为其他的数据结构来存储的话都比较麻烦，使用内部类来封装的话很方便访问当前节点的这两个属性因为他们是绑定在一个类中，我们可以队列中的数据类型声明为Node类型(Node为封装的内部类)

我们在平时编程的过程中也需要养成这样的编程习惯需要熟练掌握编程语言特定数据的封装

④ 首先外面需要一个循环用来判断当前的队列是否为空，并且在最外面的循环我们需要弹出队列中的一个元素，此外还需要一个循环用来用来尝试使用当前的平方数去凑出当前的数字n，在里面的循环中假如我们减去当前的平方数那么直接return，假如不满足将当前数字减去平方数和当前弹出来的节点的深度加上1，并且创建一个节点加入到队列中，这个也是使用宽度优先搜索的套路
 */
public class BFS_PerfectSquare {
    private static int  solve(long n, int sqrt)  {
        long squareNum = 1;
        //使用宽度优先借助队列来完成
        Queue<Node> queue = new LinkedList<Node>();
        int depth = 1;
        long curData;
        //注意一开始传入的是最开始的数据
        queue.add(new Node(n, depth));
        int index = 1;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            curData = node.data;
            depth = node.depth;
            //恢复标志为1
            index = 1;
            squareNum = 1;
            while (curData - squareNum >= 0) {
                if (curData - squareNum == 0) {
                    return depth;
                } else {
                    queue.add(new Node(curData - squareNum, depth + 1));
                }
                squareNum = index * index;
                index++;
            }
        }
        return depth;
    }

    private static class Node {
        private long data;
        private int depth;

        //使用构造器是为了更好进行数据的初始化
        public Node(long data, int depth) {
            super();
            this.data = data;
            this.depth = depth;
        }
    }

    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		int res = solve(n, ((int)Math.sqrt(n)));
		System.out.println(res);
		sc.close();
	}



}
