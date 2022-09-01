import java.util.HashMap;

/**
 * @author Hefei
 * @create 2022-08-29-8:51
 */
class Solution5 {
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        //滑动窗口
        int max_count = 0;
        int n = s.length();
        int i = k;

        while(i <= n){
            for(int j = 0; j <= n-i; ++j){
                String ss = "";
                ss = s.substring(j,j+i);
                if(justifySubString(ss,k)){
                    max_count = Math.max(max_count,i);
                    break;
                }
            }
            i++;
        }
        return max_count;
    }
    public static boolean justifySubString(String sub, int k){
        if(sub.length() < k){
            return false;
        }
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0; i < sub.length(); ++i){
            map.put(sub.charAt(i),map.getOrDefault(sub.charAt(i),0)+1);
        }
        if(map.size() >= k){
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        int eceba = lengthOfLongestSubstringKDistinct("nfhiexxjrtvpfhkrxcutexxcodfioburrtjefrgwrnqtyzelvtpvwdvvpsbudwtiryqzzy",
                25);
        System.out.println(eceba);
        String sss = "nfhiexxjrtvpfhkrxcutexxcodfioburrtjefrgwrnqtyzelvtpvwdvvpsbudwtiryqzzy";

        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0; i < sss.length(); ++i){
            map.put(sss.charAt(i),map.getOrDefault(sss.charAt(i),0)+1);
        }
        System.out.println(map);
        System.out.println(map.size());
    }
}
