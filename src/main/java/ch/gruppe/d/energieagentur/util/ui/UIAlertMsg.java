package ch.gruppe.d.energieagentur.util.ui;

public enum UIAlertMsg {
    FILE_MISSING("No File selected"),
    ITEM_MISSING("No Item selected"),
    WRONG_USERNAME_OR_PASSWORD("Wrong username or password"),
    ERROR("An error occurred"),
    ERROR_READING_ESL("An error occurred while reading the ESL data"),
    ERROR_READING_SDAT("An error occurred while reading the SDAT data"),
    MSG_NO_DATA_FOUND ("No Data found in the given Timespan");
    ;

    public final String msg;

    UIAlertMsg(String msg) {
        this.msg = msg;
    }
}
