package ailabexam.csp;
public class nQueens {
    static int count = 0;
    public static void main(String[] args) {
        char a[][] = new char[8][8];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = 'X';
            }
        }
        nquees(a, 0);
        System.out.println(count);
    }

    public static void printBoard(char[][] a) {
        System.out.println("---------");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void nquees(char[][]a,int row){
        if(row == a.length){
            count++;
            printBoard(a);
            return;
        }
        for (int i = 0; i < a.length; i++){
           if(isSafe(a,row,i)) {
            a[row][i] = 'Q';
            nquees(a, row+1);
            a[row][i] = 'X';
           }
            
        }
    }
    public static boolean isSafe(char[][]a,int row,int col){
        // vertical
        int i = row;
        int j = col;
        while(i >= 0){
            if(a[i][j]== 'Q'){
                return false;
            }else{
                i--;
            }
        }
        i = row;
        j = col;
        while(i >= 0 && j>= 0){
            if(a[i][j]== 'Q'){
                return false;
            }else{
                i--;
                j--;
            }
        }
        i = row;
        j = col;
        while(i>= 0 && j<a[0].length){
            if(a[i][j]== 'Q'){
                return false;
            }else{
                i--;
                j++;
            }
        }
        return true;
    }
}
