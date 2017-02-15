package telegram.updateHandler;

import telegram.BotConfig;
import telegram.model.Question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pouria on 2/9/17.
 */
public class Helper {

    String questionPattern;
    String turnPattern;
    Pattern question;
    Pattern turn;

    public Helper() {
        questionPattern = "@(.*)bot :(\\n+)([^\\n]*)";
        turnPattern = "([۱۲۳۴۵۶۷۸۹۰]+). @" + BotConfig.BOT_USERNAME;
        question = Pattern.compile(questionPattern);
        turn = Pattern.compile(turnPattern);
    }

    public String isMyTurn(String message) {
        Matcher m = turn.matcher(message);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    public boolean isQuestion(String message) {
        Matcher m = question.matcher(message);
        if (m.find()) {
            Question question = new Question();
            question.setQuestion(m.group(m.groupCount()));
            question.setBotUsername(m.group(1) + "bot");
            TelegramBot.history.add(question);
            return true;
        } else {
            return false;
        }

    }

}
