package secondWeek.TaskStatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int sumOfNumbers;
    private int countOfNumbers;
    private int minNumber;
    private int maxNumber;


    @Override
    public void add(int value) {
        minNumber = Math.min(value, minNumber);
        maxNumber = Math.max(value, maxNumber);
        ++countOfNumbers;
        sumOfNumbers += value;
    }

    @Override
    public int getMin() {
        return minNumber;
    }

    @Override
    public int getMax() {
        return maxNumber;
    }

    @Override
    public int getCount() {
        return countOfNumbers;
    }

    @Override
    public Double getAvg() {
        if (countOfNumbers == 0) {
            throw new RuntimeException("Не возможно найти среднего значение, так как нету никаких переданных значений");
        }
        return (double) sumOfNumbers / countOfNumbers;
    }
}
