package BFS;

/**
 * @author xuyihao
 * @date 2019/08/14
 */

public class BFS_Island {
    public static int numIslands(char[][] point) {
        if (point == null || point.length == 0 || point[0].length == 0) {
            return 0;
        }
        int row = point.length;
        int column = point[0].length;
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (point[i][j] == '1') {
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

    public static void main(String[] args){
        char[][] point = new char[10][20];
        int num = 0;
        for(int i = 0; i < point.length; i ++){
            for(int j = 0; j < point[0].length; j ++){
                num = (int)(Math.random()*100);;
                if(num >= 80){
                    point[i][j] = '1';
                }else{
                    point[i][j] = '0';
                }
            }
        }
        System.out.println("---------------------------");
        for(int i = 0; i < point.length; i ++){
            for(int j = 0; j < point[0].length; j ++){
                System.out.print(point[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------");
        System.out.println("岛屿数：" + numIslands(point));
    }
}
