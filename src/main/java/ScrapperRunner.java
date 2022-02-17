public class ScrapperRunner {
    public static void main(String[] args) {
        Thread t1 = new ScrapperThreader("thread 1");
        Thread t2 = new ScrapperThreader("thread 2");
//        Thread t3 = new ScrapperThreader("thread 3");
//        Thread t4 = new ScrapperThreader("thread 4");

        t1.start();
        t2.start();
//        t3.start();
//        t4.start();

    }
}
