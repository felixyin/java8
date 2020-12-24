package io.github.biezhi.java8.stream.lesson2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 * 
 * (2) 交易员都在哪些不同的城市工作过？
 * 
 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
 * 
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * 
 * (5) 有没有交易员是在米兰工作的？
 * 
 * (6) 打印生活在剑桥的交易员的所有交易额。
 * 
 * (7) 所有交易中，最高的交易额是多少？
 * 
 * (8) 找到交易额最小的交易。
 *
 * @author biezhi
 * @date 2018/2/12
 */
public class QuizEnd {

        // 交易员
        @Data
        @AllArgsConstructor
        static class Trader {
                // 姓名
                private String name;
                // 城市
                private String city;
        }

        // 交易
        @Data
        @AllArgsConstructor
        static class Transaction {
                private Trader trader;
                // 交易年份
                private int year;
                // 交易额
                private int value;
        }

        public static void main(String[] args) {
                Trader raoul = new Trader("Raoul", "Cambridge");
                Trader mario = new Trader("Mario", "Milan");
                Trader alan = new Trader("Alan", "Cambridge");
                Trader brian = new Trader("Brian", "Cambridge");

                List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                                new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
                                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
                                new Transaction(alan, 2012, 950));

                // (1) 找出2011年发生的所有交易，并按交易额排序（从高到低）。
                transactions.stream().filter(t -> t.year == 2011)
                                .sorted(Comparator.comparingInt(Transaction::getValue).reversed())
                                .forEach(System.out::println);
                System.out.println("---------------------------------------------");

                // (2) 交易员都在哪些不同的城市工作过？
                transactions.stream().map(t -> t.trader).map(t -> t.city).distinct().forEach(System.out::println);
                transactions.stream().map(t -> t.trader.city).distinct().forEach(System.out::println);
                System.out.println("---------------------------------------------");

                // (3) 查找所有来自于剑桥的交易员，并按姓名排序
                transactions.stream().map(Transaction::getTrader).filter(t -> "Cambridge".equals(t.city))
                                .sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
                System.out.println("---------------------------------------------");

                // (4) 返回所有交易员的姓名字符串，按字母顺序排序。
                transactions.stream().map(Transaction::getTrader).map(Trader::getName).sorted(Comparator.naturalOrder())
                                .forEach(System.out::println);
                System.out.println("---------------------------------------------");

                // (5) 有没有交易员是在米兰工作的？
                boolean isAtMilan = transactions.stream().map(Transaction::getTrader)
                                .anyMatch(t -> t.city.equals("Milan"));
                System.out.println(isAtMilan);
                System.out.println("---------------------------------------------");

                // (6) 打印生活在剑桥的交易员的所有交易额。
                List<Integer> collect = transactions.stream().filter(t -> t.trader.city.equals("Cambridge"))
                                .map(Transaction::getValue).collect(Collectors.toList());
                collect.forEach(System.out::println);
                // 交易额之和
                int reduce = transactions.stream().filter(t -> t.trader.city.equals("Cambridge"))
                                .mapToInt(Transaction::getValue).reduce(0, Integer::sum);
                System.out.println(reduce);
                System.out.println("7---------------------------------------------");

                // (7) 所有交易中，最高的交易额是多少？
                OptionalInt integer = transactions.stream().mapToInt(Transaction::getValue).max();
                System.out.println(integer);

                // (8) 找到交易额最小的交易。
                System.out.println("---------------------------------------------");
                transactions.stream().mapToInt(Transaction::getValue).min().ifPresent(System.out::println); // 最小金额
                transactions.stream().reduce((t1, t2) -> t1.value < t2.value ? t1 : t2).ifPresent(System.out::println); // 最小交易数据

        }

}