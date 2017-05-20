package com.whoshell.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArrayUtil : 数组工具类
 * @author _Fan
 *
 */
public class ArrayUtil {

	private static Log log = LogFactory.getLog(ArrayUtil.class);
	
	/**
	 * 判断字符数组中是否包含某个元素
	 * @param array  目标数组
	 * @param content 包含元素
	 * @return
	 */
	public static<T> boolean  arrayContains(T[] array,T content){
		boolean flag = false;
		for(int i=0;i<array.length;i++){
			if(content.equals(array[i])||content==array[i]){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断当前数组是否包含另外一个数组中的全部元素
	 * @param origArray  大的原始数组
	 * @param targArray  目标数组(包含需要判断内容)
	 * @return
	 */
	public static<T> boolean arrayContainsArray(T[] origArray,T[] targArray){
		boolean flag = true;
			for(int i=0;i<targArray.length;i++){
				if(!Arrays.asList(origArray).contains(targArray[i])){
					flag = false;
					return flag;
				}
		}
		return flag;
	}
	
	/**
	 * 将字符数组转换为整形数组
	 * @param sarray
	 * @return
	 */
	public static Integer[] convertStringArrToIntArr(String[] sarray) {
		Integer[] retNumArr = new Integer[sarray.length];
		for (int i = 0; i < retNumArr.length; i++) {
			retNumArr[i] = Integer.parseInt(sarray[i]);
		}
		return retNumArr;

	}
	
	/**
	 * 将一个list集合转化为由sign符号分割的字符串
	 * @param stringList
	 * @return
	 */
	public static<T> String listToString(List<T> list,String sign){
        if (list==null || list.size()==0) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (T string : list) {
            if (flag) {
                result.append(sign);
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	/**
	 * 去除一个较大数组中与较小数组重复的元素，返回过滤后的数组。
	 * @param minArray
	 * @param maxArray
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] filterRepeatElements(final T[] mixArray,final T[] maxArray){
	    if(mixArray == null || mixArray.length == 0) {
	        return maxArray;
	    }
	    final int len = Array.getLength(maxArray)-Array.getLength(mixArray);
	    final Class<?> type = maxArray.getClass().getComponentType();
	    final Set<T> temSet1 = new HashSet<T>();
	    final Set<T> temSet2 = new HashSet<T>();
	    Collections.addAll(temSet1, mixArray);
	    Collections.addAll(temSet2, maxArray);
	    temSet2.removeAll(temSet1);
	    T[] retArray = (T[]) Array.newInstance(type, len);
	    int i = 0;
	    for(Iterator<T> it = temSet2.iterator();it.hasNext();){
	        retArray[i] = it.next();
	        i++;
	    }
	    return retArray;
	}
	
	/**
	 * convertStringArrToLongList ： 用于将字符创数组转化为Long型的List集合
	 * @param strArr
	 * @return
	 */
	public static List<Long> convertStringArrToLongList(final String[] strArr) {
		if(strArr == null || strArr.length==0){
			return null;
		}
	    List<Long> longList = new ArrayList<Long>();
	    for(int i=0; i< strArr.length; i++) {
	    	if(StringUtils.isNotEmpty(strArr[i])){
	    		 Long lo = Long.valueOf(strArr[i]);
	 	        if(lo != null){
	 	            longList.add(lo);
	 	        }
	    	}
	       
	    }
	    return longList;
	}
	
	/**
	 * minInt : 查找整型数组中最小的数值
	 * @param a
	 * @return
	 */
	public static int minInt(Integer[] target) {
		// 返回数组最小值
		if(target==null || target.length==0){
			return -1;
		}
		int x;
		Integer temp[] = new Integer[target.length];
		System.arraycopy(target, 0, temp, 0, target.length);
		x = temp[0];
		for (int i = 1; i < temp.length; i++) {
			if (temp[i] < x) {
				x = temp[i];
			}
		}
		return x;
	}
}
