package telegram.updateHandler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegram.BotConfig;
import telegram.model.QResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pouria on 2/7/17.
 */
public class TelegramBot extends TelegramLongPollingBot {

    // list of question-responses in game
    public static List<QResp> history;
    // current question-responses in game 
    public static QResp qr;
    // is bot on 20 questions train
    public static boolean isRun;

    public TelegramBot() {
        history = new ArrayList<QResp>(100); // initialize with a capacity for 100 question-responses
        qr = new QResp();
        isRun = false;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();
            long chat_id = msg.getChatId();
            String msgtxt = msg.getText();

            if (chat_id != BotConfig.GROUP_ID) {
                // ordinary behavior
            } else {   // the 20 question game behavior
                if (msgtxt.equals("آغاز")) {
                    isRun = true;
                    sendMessage(myIntro());
                } else if (msgtxt.equals("پایان")) {
                    isRun = false;
                    history.clear();
                } else if (isRun) {
                    if (msgtxt.equals("حدس")) {
                        String guess = make_a_guess();
                        sendMessage(guess);
                    } else if (msgtxt.equals("بله") || msgtxt.equals("خیر") || msgtxt.equals("نمی دانم") || msgtxt.equals("نمیدانم") || msgtxt.equals("نمی‌دانم")) {
                        qr.setAnswer(msgtxt);
                        history.add(qr);

                        if (qr.getN() % 20 == 0) {
                            String guess = make_a_guess();
                            sendMessage(guess);
                        } else if (hasAnyPointForVanehasht()) {
                            String guess = make_a_guess();
                            sendMessage(">>> " + guess);
                        }
                    } else {
                        QResp qrtodo = Helper.isTurnMsg(msgtxt);
                        if (qrtodo != null) {  // A "turn" message received
                            qr = new QResp();
                            qr.setN(qrtodo.getN());
                            qr.setAsker(qrtodo.getAsker());
                            if (qr.getAsker().equals(BotConfig.BOT_USERNAME)) {  // That is my turn.
                                String ques;
                                if (hasAnyPointForDirectQ()) {
                                    String guess = make_a_guess();
                                    ques = "* " + guess + "؟";
                                } else {
                                    ques = choose_a_question();
                                }
                                sendMessage(ques);
                            }
                        } else {
                            QResp qrongoing = Helper.isQMsg(msgtxt);
                            if (qrongoing != null) {  // A "question" received. (copied and resent by admin)
                                qr.setAsker(qrongoing.getAsker());
                                qr.setQuestion(qrongoing.getQuestion());
                                if (hasAnyPointForVanehasht()) {
                                    String guess = make_a_guess();
                                    sendMessage(">>> " + guess);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }

    public void sendMessage(String text) {
        SendMessage msg = new SendMessage()
                .setChatId(BotConfig.GROUP_ID)
                .setText(text);
        try {
            sendMessage(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String myIntro() {
        //TODO : introduce yourself if you want
        return "اینجا می‌توانید هر پیام یا شعار دلخواهی برای معرفی بنویسید.";
    }

    public String choose_a_question() {
        //TODO : It is your turn, make or pick a question
        return "آیا از شاعران است؟";
    }

    public String make_a_guess() {
        //TODO : find the most confident guess word
        return "سعدی";
    }

    public boolean hasAnyPointForDirectQ() {
        //TODO : is there any point to ask a direct question

        // Just for live debugging using Telegram
        if (qr.getN() == 101) return true;

        return false;
    }

    public boolean hasAnyPointForVanehasht() {
        //TODO : is there any point to send a guess and exit

        // Just for live debugging using Telegram
        if (qr.getN() == 999) return true;

        return false;
    }
}