import java.util.Arrays;

/**
 * @author Hefei
 * @create 2022-08-26-21:10
 */
class Solution3 {
    public static int solution(int[] A, int[] B) {
        // write your code in Java 8 (Java SE 8)
        int Min_val = Integer.MAX_VALUE;
        int n = A.length;
        int[] C = new int[n];
        for(int i = 0; i < n; ++i){
            C[i] = Math.max(A[i],B[i]);
        }
        Arrays.sort(C);
        for(int i = 0; i < n;++i){
            if(C[i] != i+1){
                Min_val = Math.min(Min_val,i+1);
            }
        }
        return Min_val == Integer.MAX_VALUE ? n+1 : Min_val;

    }

    public static void main(String[] args) {
        int[] a = new int[]{3,2,1,6,5};
        int[] b = new int[]{4,2,1,3,3};
        int solution = solution(a, b);
        System.out.println(solution);
    }
}
