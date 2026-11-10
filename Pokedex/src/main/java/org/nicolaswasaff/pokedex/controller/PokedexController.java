package org.nicolaswasaff.pokedex.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.nicolaswasaff.pokedex.model.Carta;
//Para el initialize
import javafx.collections.FXCollections;
import org.nicolaswasaff.pokedex.model.CartaPokemon;
import javafx.beans.property.SimpleStringProperty;


public class PokedexController {
    @FXML
    private Label mensajes;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnEliminar;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtEdicion;
    @FXML
    public TextField txtCantidad;
    @FXML
    public TextField txtElemento;
    @FXML
    public CheckBox evolucionActivaCheckBox;
    @FXML
    public CheckBox brillanteCheckBox;
    @FXML
    public TextField txtEnergias;
    @FXML public ComboBox<String> categoriaComboBox;
    private final ObservableList<Carta> cartas = FXCollections.observableArrayList();
    @FXML private TableView  <Carta>         cartasTable;
    @FXML private TableColumn<Carta, String> colCategoria;
    @FXML private TableColumn<Carta, String> colNombre;
    @FXML private TableColumn<Carta, Integer> colAnio;
    @FXML private TableColumn<Carta, Integer> colCantidad;
    @FXML private TableColumn<Carta, String> colValor;
    @FXML protected void onWip() {mensajes.setText("Welcome to JavaFX Application!");}



    @FXML
    public void initialize() {
        //SETEA EL TIPO DE LA CARTA EN EL CHECKBOX
        categoriaComboBox.getItems().addAll("Pokémon", "Energía");
        categoriaComboBox.getSelectionModel().selectFirst();

        cartasTable.setItems(cartas);
        colCategoria.setCellValueFactory(dato ->
                new SimpleStringProperty(dato.getValue().getCategoria()));

        //CARTA DE PRUEBA
        cartas.add(new CartaPokemon("Pikachu", 1998, 3, "Eléctrico", true));
    }
}
