package com.example.golden.heart.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class BotMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String message;
    private LocalDateTime notificationDateTime;

    public BotMenu() {
    }

    public BotMenu(Long chatId, String message, LocalDateTime notificationDateTime) {
        this.chatId = chatId;
        this.message = message;
        this.notificationDateTime = notificationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotMenu botMenu = (BotMenu) o;
        return Objects.equals(id, botMenu.id) && Objects.equals(chatId, botMenu.chatId) && Objects.equals(message, botMenu.message) && Objects.equals(notificationDateTime, botMenu.notificationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, message, notificationDateTime);
    }

    @Override
    public String toString() {
        return "BotMenu{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", message='" + message + '\'' +
                ", notificationDateTime=" + notificationDateTime +
                '}';
    }
}
