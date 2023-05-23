package ch.gruppe.d.energieagentur.util.ui;

public enum UIAlertMsg {
    FILE_MISSING("No File selected"),
    ITEM_MISSING("No Item selected"),
    WRONG_USERNAME_OR_PASSWORD("Wrong username or password");

    public final String msg;

    UIAlertMsg(String msg) {
        this.msg = msg;
    }
}
