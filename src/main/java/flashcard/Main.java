package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Flashcard сургалтын програмын үндсэн гүйцэтгэгч класс.
 * Командын мөрөөр ажиллах CLI интерфейсийг дэмждэг.
 */
public class Main {
    public static void main(String[] args) {
        // Тусламж мэдээлэл өгөх нөхцөл шалгах
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            System.out.println("\uD83D\uDCDA Ашиглах заавар: flashcard <cards-file> [options]");
            System.out.println("--help               Тусламж харуулах");
            System.out.println("--order <type>       Дараалал: random, worst-first, recent-mistakes-first (default: random)");
            System.out.println("--repetitions <num>  Карт бүрийг хэдэн удаа зөв хариулах ёстой (default: 1)");
            System.out.println("--invertCards        Асуулт, хариултыг солих (default: false)");
            return;
        }

        // CLI параметрүүдийн анхны утгууд
        String filePath = args[0];
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;

        // CLI аргументуудыг шалгах, тохируулга хийх
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 < args.length) {
                        order = args[++i];
                    }
                    break;
                case "--repetitions":
                    if (i + 1 < args.length) {
                        repetitions = Integer.parseInt(args[++i]);
                    }
                    break;
                case "--invertCards":
                    invertCards = true;
                    break;
            }
        }

        // Картуудын жагсаалт үүсгэх
        List<Card> cards = new ArrayList<>();

        // Картуудыг файлнаас унших
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    cards.add(new Card(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Файлыг уншиж чадсангүй: " + e.getMessage());
            return;
        }

        System.out.println("\u2705 Нийт " + cards.size() + " карт ачааллаа!");

        // Картуудыг сонгосон төрлөөр эрэмбэлэх
        if (order.equals("random")) {
            Collections.shuffle(cards);
        } else if (order.equals("recent-mistakes-first")) {
            new RecentMistakesFirstSorter().organize(cards);
        } else if (order.equals("worst-first")) {
            cards.sort((a, b) -> Integer.compare(b.getIncorrectCount(), a.getIncorrectCount()));
        }

        // Хэрэглэгчтэй харилцан асуулт тавих хэсэг
        Scanner scanner = new Scanner(System.in);

        for (Card card : cards) {
            int correctCount = 0;
            while (correctCount < repetitions) {
                String question = invertCards ? card.getAnswer() : card.getQuestion();
                String answer = invertCards ? card.getQuestion() : card.getAnswer();

                System.out.println("\n\uD83D\uDD39 Асуулт: " + question);
                System.out.print("Таны хариулт: ");
                String userAnswer = scanner.nextLine().trim();

                if (userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("\u2705 Зөв!");
                    card.markCorrect();
                    card.increaseStreak(); // 👈 Шинээр нэмэгдсэн
                    correctCount++;
                } else {
                    System.out.println("❌ Буруу. Зөв хариулт: " + answer);
                    card.markIncorrect();
                    card.resetStreak(); // 👈 Зөв хариулт тасарсан
                }
                
            }
        }

        scanner.close();

        // Амжилт шалгах хэсэг
        boolean allCorrect = true;
        boolean hasRepeat = false;
        boolean hasConfident = false;
       
        for (Card card : cards) {
            if (card.getIncorrectCount() > 0) allCorrect = false;
            if (card.getTotalAttempts() > 5) hasRepeat = true;
            if (card.getStreak() >= 3) hasConfident = true;
        }
        

        System.out.println("\n\uD83C\uDFC5 Амжилт:");
        if (allCorrect) System.out.println("\uD83C\uDFC6 CORRECT – Бүх картыг зөв хариулсан!");
        if (hasRepeat) System.out.println("🔁 REPEAT – Нэг картад 5-аас олон удаа хариулсан!");
        if (hasConfident) System.out.println("\uD83D\uDCAA CONFIDENT – Нэг картад 3-аас дээш удаа зөв хариулсан!");
    }
}
