package client;

import server.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Leet on 03.06.2017.
 */
public class BotClient extends Client {

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int randomId = (int) (Math.random() * 100);
        return "date_bot_" + randomId;
    }

    public class BotSocketThread extends Client.SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Hi there. I'm bot. Know such commands: date, day, month, year, time, hour, minutes, seconds.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] data = message.split(": ");
            SimpleDateFormat dateFormat = null;
            if (data.length == 2) {
                String userQuestion = data[1];
                switch (userQuestion) {
                    case "date":
                        dateFormat = new SimpleDateFormat("d.MM.YYYY");
                        break;
                    case "day":
                        dateFormat = new SimpleDateFormat("d");
                        break;
                    case "month":
                        dateFormat = new SimpleDateFormat("MMMM");
                        break;
                    case "year":
                        dateFormat = new SimpleDateFormat("YYYY");
                        break;
                    case "time":
                        dateFormat = new SimpleDateFormat("H:mm:ss");
                        break;
                    case "hour":
                        dateFormat = new SimpleDateFormat("H");
                        break;
                    case "minutes":
                        dateFormat = new SimpleDateFormat("m");
                        break;
                    case "seconds":
                        dateFormat = new SimpleDateFormat("s");
                        break;
                }
            }
            if (dateFormat != null) {
                String dateString = dateFormat.format(Calendar.getInstance().getTime());
                String botAnswer = String.format("Information for %s: %s", data[0], dateString);
                sendTextMessage(botAnswer);
            }
        }
    }
}
