package pub.techfun.lottery.algorithm;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import pub.techfun.lottery.algorithm.consts.DefaultConfig;
import pub.techfun.lottery.algorithm.consts.EnumMode;
import pub.techfun.lottery.algorithm.key.LotteryCalculatorKey;
import pub.techfun.lottery.algorithm.param.ConfigParam;
import pub.techfun.lottery.base.result.WinResultGroup;
import pub.techfun.lottery.base.winnumber.WinNumberGroup;
import pub.techfun.lottery.client.LotteryJudgeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AlgorithmCalculator {

    private static final Random random = new Random();
    private List<AlgorithmResult> resultList = Collections.synchronizedList(new ArrayList<>());
    private final EnumMode mode;
    private final double sampleRate;

    public AlgorithmCalculator(Integer countryCode, Integer lotteryTypeCode, ConfigParam config) {
        if (config.getMode() == null) {
            this.mode = DefaultConfig.mode;
        } else {
            this.mode = config.getMode();
        }
        if (config.getSampleRate() > DefaultConfig.maxSampleRate || config.getSampleRate() < DefaultConfig.minSampleRate) {
            this.sampleRate = DefaultConfig.sampleRate;
        } else {
            this.sampleRate = config.getSampleRate();
        }
        init(WinNumberPool.getWinNumberList(countryCode, lotteryTypeCode, mode.getCount()));
    }

    public synchronized void calculate(LotteryCalculatorKey key, String bet) {
        if (this.mode == EnumMode.MANUAL || resultList.isEmpty()) {
            log.info("stop calculate, mode={}, resultList size={}", mode, resultList.size());
            return;
        }
        List<CompletableFuture<WinResultGroup>> futures = new ArrayList<>();
        for (AlgorithmResult result : resultList) {
            futures.add(CompletableFuture.supplyAsync(() ->
                            winJudge(key, result.getWinNumber(), bet),
                    ThreadPool.winNumberThreadPool));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            List<WinResultGroup> winResultList = futures.stream().map(CompletableFuture::join).toList();
            for (int i = 0; i < resultList.size(); i++) {
                resultHandle(winResultList.get(i), resultList.get(i));
            }
        }).join();
    }

    public synchronized void calculate(LotteryCalculatorKey key, List<String> betList) {
        if (this.mode == EnumMode.MANUAL || resultList.isEmpty()) {
            log.info("stop calculate, mode={}, resultList size={}", mode, resultList.size());
            return;
        }
        List<CompletableFuture<List<WinResultGroup>>> futures = new ArrayList<>();
        for (AlgorithmResult result : resultList) {
            futures.add(CompletableFuture.supplyAsync(() ->
                            winJudge(key, result.getWinNumber(), betList),
                    ThreadPool.winNumberThreadPool));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            List<List<WinResultGroup>> list = futures.stream().map(CompletableFuture::join).toList();
            for (int i = 0; i < resultList.size(); i++) {
                List<WinResultGroup> winResultList = list.get(i);
                for (WinResultGroup winResult : winResultList) {
                    resultHandle(winResult, resultList.get(i));
                }
            }
        }).join();
    }

    private static void resultHandle(WinResultGroup winResult, AlgorithmResult algorithmResult) {
        if (!winResult.valid()) {
            log.error("win judge error, winResult={}", JSON.toJSONString(winResult));
            return;
        }
        algorithmResult.betAmount += winResult.count();
        winResult.getWinResultList().forEach(item -> {
            if (item.win()) {
                algorithmResult.winAmount += item.winCount() * item.odds();
            }
        });
    }

    private static WinResultGroup winJudge(LotteryCalculatorKey key, WinNumberGroup winNumber, String bet) {
        return LotteryJudgeManager.winJudge(key.getCountryCode(), key.getLotteryTypeCode(),
                key.getMethodCode(), winNumber.toString(), bet);
    }

    private static List<WinResultGroup> winJudge(LotteryCalculatorKey key, WinNumberGroup winNumber, List<String> betList) {
        List<CompletableFuture<WinResultGroup>> futures = new ArrayList<>();
        for (String bet : betList) {
            futures.add(CompletableFuture.supplyAsync(() ->
                            winJudge(key, winNumber, bet),
                    ThreadPool.betThreadPool));
        }
        List<WinResultGroup> resultList = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            resultList.addAll(futures.stream().map(CompletableFuture::join).toList());
        }).join();
        return resultList;
    }

    public synchronized AlgorithmResult getResult() {
        if (resultList.isEmpty()) {
            log.error("algorithmResultList is empty, mode={}", this.mode);
            return null;
        }
        if (random.nextDouble(1) > this.sampleRate) {
            Collections.shuffle(resultList);
            return getAndClear(resultList.getFirst());
        }
        Collections.sort(resultList);
        switch (mode) {
            case AUTO, STRONG, WEAK, SAVAGE -> {
                return getAndClear(resultList.getFirst());
            }
            case MANUAL -> {
                return null;
            }
            case RELEASE -> {
                return getAndClear(resultList.getLast());
            }
        }
        return getAndClear(resultList.getFirst());
    }

    private AlgorithmResult getAndClear(AlgorithmResult result) {
        resultList = null;
        return result;
    }

    private void init(List<WinNumberGroup> winNumberList) {
        winNumberList.forEach(winNumber -> {
            resultList.add(new AlgorithmResult(winNumber));
        });
    }

}
