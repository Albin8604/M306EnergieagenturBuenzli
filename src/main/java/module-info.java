module ICTSkills {
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.opencsv;

    opens ch.albin.energieagentur.model;
    opens ch.albin.energieagentur.controller to javafx.fxml;

    exports ch.albin.energieagentur to javafx.graphics;
    exports ch.albin.energieagentur.controller to javafx.fxml;

    exports ch.albin.energieagentur.model.tableModel;
    exports ch.albin.energieagentur.model;
    exports ch.albin.energieagentur.util.files;
}