package eu.opencore.framework.language;

public enum Key {

    PLAYER_ONLY("player_only"),
    PERMISSION_ERROR("permission_error"),
    INPUT_MISMATCH("input_mismatch"),


    PLAYER_JOIN_MESSAGE("player_join"),
    PLAYER_QUIT_MESSAGE("player_leave"),

    COMMAND_USAGE_NOT_FOUND("command_usage_not_found"),
    CORRECT_COMMAND_USAGE("correct_command_usage"),

    GAMEMODE_SET_SENDER("gamemode_set_sender"),
    GAMEMODE_SET_TARGET("gamemode_set_target"),

    INVENTORY_SELECT_LANGUAGE_TITLE("inventory_select_language_title"),
    INVENTORY_SELECT_LANGUAGE_SUCCESS("inventory_select_language_success"),
    INVENTORY_SELECT_LANGUAGE_SAME("inventory_select_language_same"),
    INVENTORY_SELECT_LANGUAGE_INVALID("inventory_select_language_invalid"),

    PLAYER_LOADED("player_loaded"),

    ;


    private String key;

    Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}


