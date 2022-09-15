module ru.gb.koval.javafxchat15092022 {
    requires javafx.controls;
    requires javafx.fxml;


    exports ru.gb.koval.javafxchat15092022.client;
    opens ru.gb.koval.javafxchat15092022.client to javafx.fxml;
}