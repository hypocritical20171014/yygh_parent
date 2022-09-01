import java.util.HashMap;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-26-20:11
 */
class Solution2 {
    public static int solution(int[] A, int M) {
        boolean[] flag = new boolean[A.length];
        HashMap<Integer,Integer> map = new HashMap<>();

        // write your code in Java 8 (Java SE 8)
        int Max_length = 0;
        for(int i = 0; i < A.length-1;++i){
//            if(flag[i]) continue;
            for(int j = i+1; j < A.length; ++j){
//                if(flag[j]) continue;
                if(Math.abs(A[i]-A[j]) % M == 0){
                    flag[i] = true;
                    flag[j] = true;
                    map.put(i,map.getOrDefault(i,0)+1);
                    map.put(j,map.getOrDefault(j,0)+1);
                }
            }
        }
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(entry.getValue() >=2){
                Max_length++;
            }
        }
        return Max_length == 0 ? 1 : Max_length;

    }


    public static void main(String[] args) {
//        int[] a = new int[]{-3,-2,1,0,8,7,1};
//        int b = 3;
//        int solutions = solution(a, b);
        System.out.println(Integer.MAX_VALUE);
    }
}
