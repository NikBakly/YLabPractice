package io.ylab.intensive.lesson03.datedMap;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("1", "2");
        datedMap.put("3", "4");
        datedMap.put("5", "6");

        System.out.println("Check method get:");
        System.out.println(datedMap.get("1")); // 2
        System.out.println(datedMap.get("3")); // 4
        System.out.println(datedMap.get("5")); // 6
        System.out.println(datedMap.get("2")); // null

        System.out.println("Check method containsKey:");
        System.out.println(datedMap.containsKey("5")); // true
        System.out.println(datedMap.containsKey("10")); // false

        System.out.println("Check method remove:");
        System.out.println(datedMap.get("5")); // 6
        datedMap.remove("5");
        System.out.println(datedMap.get("5")); // null

        System.out.println("Check method keySet:");
        System.out.println(datedMap.keySet()); // [1, 3] or [3, 1]


        System.out.println("Check method getKeyLastInsertionDate:");
        System.out.println(datedMap.getKeyLastInsertionDate("10")); // null
        System.out.println(datedMap.getKeyLastInsertionDate("1")); // get date
    }
}
