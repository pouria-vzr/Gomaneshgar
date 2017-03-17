package telegram.updateHandler;

import telegram.model.QResp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pouria on 2/9/17.
 */
public class Helper {

    static Pattern turnPattern = Pattern.compile("([۰-۹]+)\\. @(.+)");
    static Pattern qmsgPattern = Pattern.compile("(.+):\\n(.*؟)");

    static public QResp isTurnMsg(String message) {
        Matcher m = turnPattern.matcher(message);
        if (m.matches()) {
            QResp qrtodo = new QResp();
            qrtodo.setN(parseIntP(m.group(1)));
            qrtodo.setAsker(m.group(2));
            return qrtodo;
        } else {
            return null;
        }
    }

    static public QResp isQMsg(String message) {
        Matcher m = qmsgPattern.matcher(message);
        if (m.matches()) {
            QResp qrongoing = new QResp();
            qrongoing.setAsker(m.group(1));
            qrongoing.setQuestion(m.group(2));
            return qrongoing;
        } else {
            return null;
        }
    }

    static public int parseIntP( String a ){
        a = a.replaceAll("۰","0");
        a = a.replaceAll("۱","1");
        a = a.replaceAll("۲","2");
        a = a.replaceAll("۳","3");
        a = a.replaceAll("۴","4");
        a = a.replaceAll("۵","5");
        a = a.replaceAll("۶","6");
        a = a.replaceAll("۷","7");
        a = a.replaceAll("۸","8");
        a = a.replaceAll("۹","9");
        return Integer.parseInt(a);
    }
}
