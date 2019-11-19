package eu.opencore.framework.language;

public enum Key {

    PLAYER_ONLY("message.player_only"),
    PERMISSION_ERROR("message.permission_error"),
    INPUT_MISMATCH("message.input_mismatch"),


    PLAYER_JOIN_MESSAGE("message.player_join"),
    PLAYER_QUIT_MESSAGE("message.player_leave"),

    COMMAND_USAGE_NOT_FOUND("message.command_usage_not_found"),
    CORRECT_COMMAND_USAGE("message.correct_command_usage"),

    GAMEMODE_SET_SENDER("message.gamemode_set_sender"),
    GAMEMODE_SET_TARGET("message.gamemode_set_target"),

    INVENTORY_SELECT_LANGUAGE_TITLE("message.inventory_select_language_title"),
    INVENTORY_SELECT_LANGUAGE_SUCCESS("message.inventory_select_language_success"),
    INVENTORY_SELECT_LANGUAGE_SAME("message.inventory_select_language_same"),

    PLAYER_LOADED("message.player_loaded"),

    ;


    private String key;

    Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}


