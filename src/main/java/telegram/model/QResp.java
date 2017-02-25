package telegram.model;

/**
 * Created by pouria on 2/10/17.
 */
public class QResp {
    private int n;
    private String asker;
    private String question;
    private String answer;

    public QResp() {
        n = -1;
        asker = "";
        question = "";
        answer = "";
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QResp{" +
                "n=" + n +
                ", asker='" + asker + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
