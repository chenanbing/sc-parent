package com.cab.sccommom.utils.descart;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by by chenanbing on 2018/9/4 15:22.
 */
public class DescartesUtils {

    /**
     * 笛卡尔乘积算法
     * 把一个List{[1,2],[3,4],[a,b]}转化成List{[1,3,a],[1,3,b],[1,4
     * ,a],[1,4,b],[2,3,a],[2,3,b],[2,4,a],[2,4,b]}数组输出
     *
     * @param layer   中间参数
     * @param curList 中间参数
     */
    private static void descartes(List<List<String>> dimvalue,
                                  List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                DescartesUtils.descartes(dimvalue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    DescartesUtils.descartes(dimvalue, result, layer + 1, list);
                }
            }
        } else if (layer == dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    public static List<List<String>> get(List<String>... ids) {
        List<List<String>> list = new ArrayList<List<String>>();
        for (List<String> idList : ids) {
            list.add(idList);
        }
        List<List<String>> result = new ArrayList<List<String>>();
        descartes(list, result, 0, new ArrayList<String>());
        return result;
    }

//    public static void main(String[] args) {
//        List<String> listSub1 = new ArrayList<String>();
//        List<String> listSub4 = new ArrayList<String>();
//        listSub1.add("100");
//        listSub1.add("101");
//        listSub1.add("199");
//        listSub4.add("200");
//        listSub4.add("201");
//        listSub4.add("202");
//        List<List<String>> result = get(listSub1, listSub4);
//        System.out.println(result);
//    }
}
