package com.dk.test;

public class Test {

    public static void main(String[] args) {
//        int [] arr = {1,2,0,3,5,5};
        int [] arr = {0,99,100,0,2,3};
        System.out.println(ex(arr));
    }

    /**
     * 如果一个包含n个数据的数组是连续的，那么最大值和最小值之差一定为n-1；如果包含0的话，那么最大值和最小值之差不能超过n-1。
     * @param a
     * @return
     */
    public static boolean ex(int [] a){
        int min = a[0];//最小值
        int minIndex = 0;//最小值索引
        int max = a[0];//最大值
        int maxIndex = 0;//最大值索引
        int d = 1;  // 数组的length-d  计算最大值和最小值之间的差，d默认为1
        for(int i = 1; i < a.length; i++) {
            if(a[i] < min && a[i] !=0 ) {
                min = a[i];
                minIndex = i;
            }
            if(a[i] > max && a[i] != 0) {
                max = a[i];
                maxIndex = i;
            }
            //如果出现两个相同的数d++，则最大值和最小值之间的差不能大于  a.length - d
            if(i!=minIndex && a[i]==min)
                d++;
            if(i!=maxIndex && a[i]==max)
                d++;
        }

        //如果出现了100则需要重新查找最大值和最小值
        int min1 = max;
        if (max == 100) {
            //计算最大值
            for(int i = 0;i<a.length;i++){
                int n = a[i];
                if(max+1 == 100+n){
                    max = max + 1;
                }
            }
            //计算最小值
            for(int i = 0;i<a.length;i++){
                int n = a[i];
               if(100-n<=a.length && n < min1){//n比100小，而且就在100附近的数才进行比较
                   min1 = n;
               }
            }
        }
        if((max - min1) <= a.length-d) {
            return true;
        }
        return false;
    }
}
