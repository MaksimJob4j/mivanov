package ru.job4j.threads;

public class TimeLimit {
    public class CountChar implements Runnable {
        String string;
        int countChar = 0;

        CountChar(String string) {
            this.string = string;
        }

        @Override
        public void run() {
            try {
                for (Character character : string.toCharArray()) {
                    Thread.sleep(0);
                    countChar++;
                }
                System.out.printf("Все %s символы посчитаны.\n", countChar);
            } catch (InterruptedException e) {

            }
        }
    }

    public class Time implements Runnable {
        private long timeLimitMS;
        Time(long timeLimitMS) {
            this.timeLimitMS = timeLimitMS;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeLimitMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(long timeLimitMS, String string) {
        
        Time time = new Time(timeLimitMS);
        Thread timeTread = new Thread(time);
        
        CountChar countChar = new CountChar(string);
        Thread countCharTread = new Thread(countChar);
        
        timeTread.start();
        countCharTread.start();


        try {
            timeTread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        countCharTread.interrupt();
        System.out.printf("За %sмс посчитано %s символов из %s.\n", timeLimitMS, countChar.countChar, string.length());

    }

    public static void main(String[] args) {
        String string = CalcWords.generateString(10000000);
        TimeLimit timeLimit = new TimeLimit();
        timeLimit.start(100, string);

    }
}
