package uonsupportdesk.module.component.ticket;

public class MessageFormatter {

    public static String formatMessage(String message) {
        String formattedSmiles = message.replace(":)", ":smile:");
        String formattedSad = formattedSmiles.replace(":(", ":frowning_face:");
        String formattedCry = formattedSad.replace(":'(", ":cry:");
        String formattedShocked = formattedCry.replaceAll("(?i):O", ":scream:");
        String formattedGrin = formattedShocked.replaceAll("(?i):D", ":grin:");
        String formattedWink = formattedGrin.replaceAll("(?i):D", ":wink:");
        String formattedTongue = formattedWink.replaceAll("(?i):P", ":stuck_out_tongue:");

        return formattedTongue.replace(":/", ":confused:");
    }
}
