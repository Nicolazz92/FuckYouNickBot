package org.velikokhatko.fuckyounickbot.service;

import com.google.common.collect.ImmutableMap;
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

    private static final String ASS_DIRECTION = "Иди в жопу";
    private static final String I_LOVE_YOU = "Люблю";
    private static final String WORK = "Работай";
    private static final String GOAT = "Козёл";
    private static final ImmutableMap<String,String> DATA = new ImmutableMap.Builder<String, String>()
            .put(ASS_DIRECTION, "qqq")
            .put(I_LOVE_YOU, "www")
            .put(WORK, "eee")
            .put(GOAT, "rrr")
            .build();

    private String token;
    private String name;
    private String chatId;

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
        System.out.println(update.getMessage().getText());
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            String message = DATA.getOrDefault(text, "Пшёл нах отседа");
            sendMessage(chatId, message);
        }
    }

    public void sendMessage(String currentChatId, String text) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(currentChatId);
            sendMessage.setText(text);
            ReplyKeyboardMarkup replyKeyboardMarkup = getSettingsKeyboard();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
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
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
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
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
