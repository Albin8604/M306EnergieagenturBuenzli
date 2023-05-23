module ICTSkills {
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.opencsv;
    requires java.xml.bind;

    opens ch.gruppe.d.energieagentur.model;
    opens ch.gruppe.d.energieagentur.controller to javafx.fxml;
    opens ch.gruppe.d.energieagentur.util.files.xml.model.esl to java.xml.bind;

    exports ch.gruppe.d.energieagentur to javafx.graphics;
    exports ch.gruppe.d.energieagentur.controller to javafx.fxml;

    exports ch.gruppe.d.energieagentur.model.uiModel;
    exports ch.gruppe.d.energieagentur.model;
    exports ch.gruppe.d.energieagentur.util.files;
    exports ch.gruppe.d.energieagentur.util.files.xml.model.esl;
    exports ch.gruppe.d.energieagentur.util.files.xml.model.sdat;
}