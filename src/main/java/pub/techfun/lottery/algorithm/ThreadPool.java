package pub.techfun.lottery.algorithm;

import java.util.concurrent.*;

public class ThreadPool {

    public static final ExecutorService winNumberThreadPool = Executors.newVirtualThreadPerTaskExecutor();

    public static final ExecutorService betThreadPool = Executors.newVirtualThreadPerTaskExecutor();

}
