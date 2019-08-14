/**
 * @author xuyihao
 * @date 2019/08/14
 */

public class BFS {
    public int numIslands(char[][] point) {
        if (point == null || point.length == 0 || point[0].length == 0) {
            return 0;
        }
        int row = point.length;
        int column = point[0].length;
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (point[i][j] == 1) {
                    count++;
                    search(point, i, j);
                }
            }
        }
        return count;
    }

    private static void search(char[][] point, int x, int y) {
        point[x][y] = '2';
        if (x > point.length - 1 && y > point[0].length - 1) {
            return;
        }
        //向右
        if (x < point.length - 1 && point[x + 1][y] == '1') {
            search(point, x + 1, y);
        }
        //向下
        if (y < point[0].length - 1 && point[x][y + 1] == '1') {
            search(point, x, y + 1);
        }
        //向左
        if (x > 0 && point[x - 1][y] == '1') {
            search(point, x - 1, y);
        }
        //向上
        if (y > 0 && point[x][y - 1] == '1') {
            search(point, x, y - 1);
        }
    }
}
