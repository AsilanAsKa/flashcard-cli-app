package flashcard;

public class Card {
    private final String question;
    private final String answer;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int totalAttempts = 0;
    private int currentStreak = 0;

public void resetStreak() {
    currentStreak = 0;
}

public void increaseStreak() {
    currentStreak++;
}

public int getStreak() {
    return currentStreak;
}


    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }

    public void markCorrect() {
        correctCount++;
        totalAttempts++;
    }

    public void markIncorrect() {
        incorrectCount++;
        totalAttempts++;
    }

    public int getCorrectCount() { return correctCount; }
    public int getIncorrectCount() { return incorrectCount; }
    public int getTotalAttempts() { return totalAttempts; }
}
