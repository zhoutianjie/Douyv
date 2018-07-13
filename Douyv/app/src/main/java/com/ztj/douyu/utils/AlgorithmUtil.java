package com.ztj.douyu.utils;

/**
 * Created by zhoutianjie on 2018/7/13.
 */

public class AlgorithmUtil {

    /**
     * 堆排序
     */
    private static void swap(int[] arr,int a,int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /**
     *
     * @param arry
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] arry,int i,int length){
        int tmp = arry[i];
        for(int k=2*i+1;k<length;k=k*2+1){
            if(k+1<length && arry[k]<arry[k+1]){
                k++;
            }
            if(arry[k]>tmp){
                arry[i] = arry[k];
                i=k;
            }else{
                break;
            }
        }
        arry[i] = tmp;
    }

    public static void heapSort(int[] arr){
        //构建大顶堆
        for(int i=arr.length/2-1;i>=0;i--){
            adjustHeap(arr,i,arr.length);
        }
        for(int j=arr.length-1;j>0;j--){
            swap(arr,0,j);
            adjustHeap(arr,0,j);
        }
    }

    /**
     * 快排
     */
    private static int partition(int[] arr,int left,int right){
        int key = arr[left];
        while (left<right){
            while (left<right && arr[right]>=key){
                right--;
            }
            arr[left] = arr[right];
            while (left<right && arr[left]<key){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = key;
        return left;
    }

    public static void quickSort(int[] arr,int left,int right){
        if(left<right){
            int result = partition(arr,left,right);
            quickSort(arr,left,result-1);
            quickSort(arr,result+1,right);
        }
    }
}
