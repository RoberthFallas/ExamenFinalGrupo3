module una.examengrupo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires gson;
    requires java.net.http;
    requires java.sql;
    requires javafx.graphics;
    exports una.examengrupo3 to javafx.graphics;
    exports una.examengrupo3.model;
    opens una.examengrupo3 to javafx.fxml;
    exports una.examengrupo3.controller;
}
