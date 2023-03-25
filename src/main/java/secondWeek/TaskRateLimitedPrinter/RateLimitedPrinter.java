package secondWeek.TaskRateLimitedPrinter;

public class RateLimitedPrinter {
    private final int interval;
    private long lastTimeUpdate;

    public RateLimitedPrinter(int interval) {
        checkInterval(interval);
        this.interval = interval;

    }

    public void print(String message) {
        if (lastTimeUpdate == 0) {
            System.out.println(message);
            lastTimeUpdate = System.currentTimeMillis();
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTimeUpdate > interval) {
                System.out.println(message);
                lastTimeUpdate = System.currentTimeMillis();
            }
        }
    }

    private void checkInterval(int interval) {
        if (interval < 0) {
            throw new RuntimeException("Интервал не может быть отрицательным числом");
        }
    }
}
