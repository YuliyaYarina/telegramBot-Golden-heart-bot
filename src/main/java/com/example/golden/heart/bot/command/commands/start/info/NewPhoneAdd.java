//package com.example.golden.heart.bot.command.commands.start.info;
//
//import org.springframework.stereotype.Service;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class NewPhoneAdd {
//    private static final Pattern INCOMING_MESSAGE_PATTERN_PHONE = Pattern.compile("\\+\\d{3}-\\d{2}-\\d{2}");
//
//
//    public static void phone() {
//        String telegramMessage = "Номер телефона: +123 456-78-90";
//
//        String processedPhoneNumber = processPhoneNumber(telegramMessage);
//        if (processedPhoneNumber != null) {
//            System.out.println("Обработанный номер телефона: " + processedPhoneNumber);
//            savePhoneNumberToDB(processedPhoneNumber);
//        } else {
//            System.out.println("Номер телефона не найден в сообщении");
//        }
//    }
//
//    public static String processPhoneNumber(String telegramMessage) {
//
//        Pattern pattern = INCOMING_MESSAGE_PATTERN_PHONE;
//
//        Matcher matcher = pattern.matcher(telegramMessage);
//
//        if (matcher.find()) {
//            String phoneNumber = matcher.group().replaceAll(" ", "").replaceAll("-", "");
//            return phoneNumber;
//        } else {
//            return null;
//        }
//    }
//
//    public static void savePhoneNumberToDB(String phoneNumber) {
//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/golden-heart-bot", "student", "Yuliya")) {
//            String sql = "INSERT INTO bot_user (phone) VALUES (p)";
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, phoneNumber);
//                statement.executeUpdate();
//                System.out.println("Номер телефона успешно сохранен в базу данных");
//            }
//        } catch (SQLException e) {
//            System.out.println("Ошибка при сохранении номера телефона в базу данных: " + e.getMessage());
//        }
//    }
//}
