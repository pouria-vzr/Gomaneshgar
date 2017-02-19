package telegram.updateHandler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegram.BotConfig;
import telegram.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pouria on 2/7/17.
 */
public class TelegramBot extends TelegramLongPollingBot {


    //list of questions in game
    public static List<Question> history;
    public static Question currentState;
    public static int questionNumber;
    private Helper helper;

    public TelegramBot() {
        history = new ArrayList<Question>();
        questionNumber = 0;
        helper = new Helper();

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getChatId() == Long.parseLong(BotConfig.GROUP_ID)) {
                //20 question game treat
                handelIncomingMessage(update.getMessage());
            } else {
                //ordinary teat
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

    public void handelIncomingMessage(Message message) {

        if (message.getText().equalsIgnoreCase("حدس")) {

            //TODO : guess
            sendMessage("***");

        } else if (message.getText().equalsIgnoreCase("بله") || message.getText().equalsIgnoreCase("خیر")) {
            if (currentState != null){
                currentState.setAnswer(message.getText());
                history.add(currentState);
                questionNumber++;
            }

            if(questionNumber == 20){
                //TODO : guess
                sendMessage("***");
            }

        } else if (helper.isMyTurn(message.getText()) != null) {

            //TODO : send your question
            String myQuestion = "***";
            sendNormalQuestion(myQuestion);
            //TODO : add your Question to history
            Question question = new Question();
            question.setQuestion(myQuestion);
            question.setBotUsername(BotConfig.BOT_USERNAME);
            currentState = question;

        } else if (helper.isQuestion(message.getText())) {

            //question from other bots

        } else if (message.getText().equalsIgnoreCase("پایان")) {

            //TODO : end of the game
            sendMessage("guess");

            history.clear();

        } else if (message.getText().equalsIgnoreCase("آغاز") || message.getText().equalsIgnoreCase("اغاز")) {

            //TODO : introduce yourself if you want
            sendMessage("***");

        }

    }

    //وانهشت
    public void sendAnswerWithoutOrder(String answer) {
        SendMessage message = new SendMessage()
                .setChatId(BotConfig.GROUP_ID)
                .setText(">>> " + answer);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendDirectQuestion(String question) {
        SendMessage message = new SendMessage()
                .setChatId(BotConfig.GROUP_ID)
                .setText("* " + question + " ؟");
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNormalQuestion(String question) {
        SendMessage message = new SendMessage()
                .setChatId(BotConfig.GROUP_ID)
                .setText(question + " ؟");
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //guess
    public void sendMessage(String text) {
        SendMessage message = new SendMessage()
                .setChatId(BotConfig.GROUP_ID)
                .setText(text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
