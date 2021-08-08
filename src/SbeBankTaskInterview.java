import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SbeBankTaskInterview {
    public static void main(String[] args) {
        Facture facture1 = new Facture("1", BigDecimal.TEN);
        Facture facture2 = new Facture("1", BigDecimal.TEN);
        Facture facture3 = new Facture("2", BigDecimal.TEN);
        Facture facture4 = new Facture("2", BigDecimal.ONE);

        List<Facture> factures = Arrays.asList(facture1, facture2, facture3, facture4);

        // 0. Общая сумма
        BigDecimal sum = factures.stream()
                .map(Facture::getSum)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sum);

        // 1. Сгруппировать счета с Facture
        Map<String, List<Facture>> accountMap = new HashMap<>();

        factures.stream()
                .map(Facture::getAccount)
                .collect(Collectors.toSet())
                .forEach(account -> factures.stream()
                        .filter(it -> account.equals(it.account))
                        .forEach(facture -> {
                            if (!accountMap.containsKey(account)) {
                                accountMap.put(account, new ArrayList<>(Collections.singletonList(facture)));
                            } else {
                                accountMap.get(account).add(facture);
                            }
                        }));

        System.out.println(accountMap);

        // 2. Суммировать сумму по аккаунтам
        Map<String, BigDecimal> mapSum = new HashMap<>();

        accountMap.forEach((k, v) -> {
            BigDecimal sumByAccount = v.stream()
                    .map(Facture::getSum)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            mapSum.put(k, sumByAccount);
        });

        System.out.println(mapSum);
    }

    static class Facture {
        private String account;
        private BigDecimal sum;

        public Facture(String account, BigDecimal sum) {
            this.account = account;
            this.sum = sum;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public BigDecimal getSum() {
            return sum;
        }

        public void setSum(BigDecimal sum) {
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "Facture{" +
                    "account='" + account + '\'' +
                    ", sum=" + sum +
                    '}';
        }
    }
}
