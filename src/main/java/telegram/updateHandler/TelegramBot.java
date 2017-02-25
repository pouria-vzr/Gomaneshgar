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
    //current question in game
    public static Question currentState;
    //number of question
    public static int questionNumber;
    private Helper helper;
    //this shows that a question is asked or not
    private boolean isQuestionExist;

    public TelegramBot() {
        history = new ArrayList<Question>();
        questionNumber = 0;
        helper = new Helper();
        isQuestionExist = false;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getChatId());
            System.out.println(update.getMessage().getText());
            if (update.getMessage().getChatId() == Long.parseLong(BotConfig.GROUP_ID)) {
                //20 question game treat
                handelIncomingMessage(update.getMessage());
            } else {
                //ordinary treat
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

        if (message.getText().equals("حدس")) {

            //TODO : guess
            sendMessage("***");

        } else if (message.getText().equals("بله") || message.getText().equals("خیر") || message.getText().equals("نمی دانم")) {
            if (currentState != null && isQuestionExist == true) {
                currentState.setAnswer(message.getText());
                history.add(currentState);
                questionNumber++;
//                String test = currentState.getBotUsername() + "\n" + "Q = " + currentState.getQuestion() + "\n" + "A = " + currentState.getAnswer() + "\n" + "Q num = " + questionNumber;
//                sendMessage(test);
                isQuestionExist = false;
            }

            if (questionNumber % 20 == 0 && questionNumber != 0) {
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
            isQuestionExist = true;

        } else if (helper.isQuestion(message.getText())) {

            isQuestionExist = true;
            //question from other bots

        } else if (message.getText().equals("پایان")) {

            //TODO : end of the game
            sendMessage("guess");

            history.clear();
            questionNumber = 0;
            isQuestionExist = false;

        } else if (message.getText().equals("آغاز") || message.getText().equals("اغاز")) {

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
