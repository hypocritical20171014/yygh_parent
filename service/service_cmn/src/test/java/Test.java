import java.util.HashMap;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-26-19:37
 */
class Solution {
    public static int solution(String S) {
        // write your code in Java 8 (Java SE 8)
        int n = S.length();
        int x = n % 2 == 0 ? n : n-1;
        int i = 2;
        int Max_n = 0;
        while( i <= x){
            for(int k = 0; k < n-i;++k){
                String sub = "";
                if(k != n-i-1){
                    sub = S.substring(k, k+i);

                }
                else{
                    sub = S.substring(k,k+i-1);
                }
                if(justifyStr(sub)){
                    Max_n = Math.max(Max_n,i);
                };
            }
            i += 2;
        }
        return Max_n;

    }
    public static boolean justifyStr(String sub){
        if(sub.length() == 2){
            if(sub.charAt(0) != sub.charAt(1)){
                return false;
            }
        }
        Map<Character,Integer> map = new HashMap<>();
        char[] cc = sub.toCharArray();
        for (char c : cc) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for(Map.Entry<Character,Integer> entry:map.entrySet()){
            if(entry.getValue() % 2 != 0){
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {
        int a = solution("zthtzh");
        System.out.println(a);
    }

}
