package eu.opencore.framework.language;

public enum Key {

    PERMISSION_ERROR("message.permission_error"),

    PLAYER_JOIN_MESSAGE("message.player_join"),
    PLAYER_QUIT_MESSAGE("message.player_leave"),

    COMMAND_USAGE_NOT_FOUND("message.command_usage_not_found"),
    CORRECT_COMMAND_USAGE("message.correct_command_usage"),

    ;


    private String key;

    Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}


