import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        long startTs = System.currentTimeMillis(); // start time
        List<Thread> threads = new ArrayList<>();
        //    for (int i = 0; i < texts.length; i++) {
        //        texts[i] = generateText("aab", 30_000);
        //    }
        for (int r = 0; r < texts.length; r++) {
            String tex = generateText("aab", 30_000);
            Runnable runnable = () -> {
                int maxSize = 0;
                for (int i = 0; i < tex.length(); i++) {
                    for (int j = 0; j < tex.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (tex.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(tex.substring(0, 100) + " -> " + maxSize);
            };
            Thread thread = new Thread(runnable);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }
        // for (String text : texts) {
        //     int maxSize = 0;
        //     for (int i = 0; i < text.length(); i++) {
        //         for (int j = 0; j < text.length(); j++) {
        //             if (i >= j) {
        //                 continue;
        //             }
        //             boolean bFound = false;
        //             for (int k = i; k < j; k++) {
        //                 if (text.charAt(k) == 'b') {
        //                     bFound = true;
        //                     break;
        //                 }
        //             }
        //             if (!bFound && maxSize < j - i) {
        //                 maxSize = j - i;
        //             }
        //         }
        //     }
        //     System.out.println(text.substring(0, 100) + " -> " + maxSize);
        // }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
