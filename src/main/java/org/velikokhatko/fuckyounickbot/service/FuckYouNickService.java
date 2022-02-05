package org.velikokhatko.fuckyounickbot.service;

import com.vdurmont.emoji.EmojiManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuckYouNickService extends TelegramLongPollingBot {

    private static final String ASS_DIRECTION = EmojiManager.getForAlias("poop").getUnicode() + " Иди в жопу!";
    private static final String I_LOVE_YOU = EmojiManager.getForAlias("in_love_face").getUnicode() + " Я тебя люблю!";
    private static final String WORK = EmojiManager.getForAlias("computer").getUnicode() + " Иди работай!";
    private static final String GOAT = EmojiManager.getForAlias("goat").getUnicode() + "Коля, ты козёл!";
    private static final String ATTENTION = EmojiManager.getForAlias("dancer").getUnicode() + " Хочу внимания!";
    private static final String SEX = EmojiManager.getForAlias("gift_heart").getUnicode() + " Хочу секса!";
    private static final String FOOD = EmojiManager.getForAlias("cake").getUnicode() + " Покорми меня!";
    private static final String CLEAR_KITCHEN = EmojiManager.getForAlias("gloves").getUnicode() + " Помой кухню!";

    private String token;
    private String name;
    private String nickChatId;

    @PostConstruct
    public void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        sendMessage(String.valueOf(update.getMessage().getFrom().getId()), "Доставлено!");
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            sendMessage(nickChatId, text);
        }
    }

    public void sendMessage(String currentChatId, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(currentChatId);
            sendMessage.setText(text);
            if (!currentChatId.equals(nickChatId)) {
                ReplyKeyboardMarkup replyKeyboardMarkup = getSettingsKeyboard();
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static ReplyKeyboardMarkup getSettingsKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(ASS_DIRECTION);
        keyboardFirstRow.add(I_LOVE_YOU);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(WORK);
        keyboardSecondRow.add(GOAT);

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(ATTENTION);
        keyboardThirdRow.add(SEX);

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(FOOD);
        keyboardFourthRow.add(CLEAR_KITCHEN);

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    @Value("${telegram.bot.token}")
    public void setToken(String token) {
        this.token = token;
    }

    @Value("${telegram.bot.name}")
    public void setName(String name) {
        this.name = name;
    }

    @Value("${telegram.bot.nickChatId}")
    public void setNickChatId(String nickChatId) {
        this.nickChatId = nickChatId;
    }
}
