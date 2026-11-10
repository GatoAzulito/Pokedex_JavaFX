module org.nicolaswasaff.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens org.nicolaswasaff.pokedex.model to com.google.gson;
    opens org.nicolaswasaff.pokedex to javafx.fxml;
    opens org.nicolaswasaff.pokedex.controller to javafx.fxml;
    exports org.nicolaswasaff.pokedex;
}