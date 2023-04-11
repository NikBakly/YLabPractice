package fifthWeek.io.ylab.intensive.lesson05.messagefilter;

public class Test {
    public static void main(String[] args) {

        String test4 = "Привет.СукА ты у меня:сука.";


        System.out.println(result(test4));
    }

    public static String result(String message) {
        StringBuilder result = new StringBuilder(message);
        int leftPointer = 0;
        int rigthPointer = 0;

        while (rigthPointer < message.length()) {
            if (checkCharByPointer(result, rigthPointer) && !checkCharByPointer(result, leftPointer)) {
                leftPointer = rigthPointer;
            } else {
                if (checkCharByPointer(result, leftPointer)) {
                    String world = result.substring(leftPointer, rigthPointer);
                    if (contains(world)) {
                        result.replace(leftPointer + 1, rigthPointer - 1, "*".repeat(rigthPointer - leftPointer - 2));
                    }
                    leftPointer = rigthPointer;
                }
            }
            ++rigthPointer;
        }

        if (checkCharByPointer(result, leftPointer)) {
            String world = result.substring(leftPointer, rigthPointer);
            if (contains(world)) {
                result.replace(leftPointer + 1, rigthPointer - 1, "*".repeat(rigthPointer - leftPointer - 2));
            }
        }

        return result.toString();
    }

    public static boolean checkCharByPointer(StringBuilder result, int pointer) {
        return (result.charAt(pointer) >= 'А' && result.charAt(pointer) <= 'Я')
                || (result.charAt(pointer) >= 'а' && result.charAt(pointer) <= 'я');
    }

    public static boolean contains(String world) {
        return world.equalsIgnoreCase("сука");
    }
}
