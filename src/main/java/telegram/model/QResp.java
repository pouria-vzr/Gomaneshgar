package telegram.model;

/**
 * Created by pouria on 2/10/17.
 */
public class QResp {
    public int n;
    public String asker;
    public String question;
    public String answer;

    public QResp() {
        n = -1;
        asker = "";
        question = "";
        answer = "";
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
