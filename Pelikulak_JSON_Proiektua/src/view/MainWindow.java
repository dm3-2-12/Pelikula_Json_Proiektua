/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controller.GestionEnListaEnMemoria;
import java.io.File;
import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

import model.Pelikula;


/**
 *
 * @author idoia
 */
public class MainWindow extends Application {

    private final TableView<Pelikula> table = new TableView<>();
    ObservableList <Pelikula> pelikulakob= FXCollections.observableArrayList();

    final HBox hb = new HBox();
    
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("JSON Files", "*.json"));
         
        File file = fileChooser.showOpenDialog(stage);

        
        stage.setTitle("Datuen Taula");
        stage.setWidth(625);
        stage.setHeight(510);
        final Label label = new Label("PELIKULAK");
        label.setFont(new Font("Calibri", 20));
        
        table.setEditable(true);
        
        TableColumn<Pelikula, String> PelIzncol =
            new TableColumn<>("Izena");
        PelIzncol.setMinWidth(260);
        PelIzncol.setCellValueFactory(
            new PropertyValueFactory<>("Izena"));
        PelIzncol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        PelIzncol.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setIzena(t.getNewValue());
            });
        
        TableColumn<Pelikula, String> zuzcol =
            new TableColumn<>("Zuzendaria");
        zuzcol.setMinWidth(170);
        zuzcol.setCellValueFactory(
            new PropertyValueFactory<>("Zuzendaria"));
        zuzcol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        zuzcol.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setZuzendaria(t.getNewValue());
            });
        
        TableColumn<Pelikula, String> demCol = new TableColumn<>("durazioa");
        demCol.setMinWidth(50);
        demCol.setCellValueFactory(
        new PropertyValueFactory<>("durazioa"));
        demCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        demCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
                ((Pelikula) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setDurazioa(t.getNewValue());
            });
        
        TableColumn<Pelikula, Integer> Coladina =
            new TableColumn<>("adina");
        Coladina.setMinWidth(20);
        Coladina.setCellValueFactory(
            new PropertyValueFactory<>("adina"));
        Coladina.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter()));
        Coladina.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setAdina(t.getNewValue());
            });
        
        TableColumn<Pelikula, Integer> ColUrtea =
            new TableColumn<>("Urtea");
        ColUrtea.setMinWidth(20);
        ColUrtea.setCellValueFactory(
            new PropertyValueFactory<>("Urtea"));
        ColUrtea.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter()));
        ColUrtea.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setUrtea(t.getNewValue());
            });
        
        pelikulakob = GestionEnListaEnMemoria.jsonPelikulaKargatu(file);
        table.setItems(pelikulakob);
        table.getColumns().addAll(PelIzncol, zuzcol, demCol, Coladina, ColUrtea);
        final TextField addizena = new TextField();
        addizena.setPromptText("Izena");
        addizena.setMaxWidth(PelIzncol.getPrefWidth());
        final TextField addzuz = new TextField();
        addzuz.setMaxWidth(zuzcol.getPrefWidth());
        addzuz.setPromptText("Zuzendari");
        final TextField adddembora = new TextField();
        adddembora.setMaxWidth(demCol.getPrefWidth());
        adddembora.setPromptText("durazioa");
        final TextField addadina = new TextField();
        addadina.setMaxWidth(Coladina.getPrefWidth());
        addadina.setPromptText("Adina");
        final TextField addurtea = new TextField();
        addurtea.setMaxWidth(ColUrtea.getPrefWidth());
        addurtea.setPromptText("Urtea");
       
        final Button addButton = new Button("Gehitu");        
        addButton.setOnAction((ActionEvent e) -> {
            
            Pelikula k = new Pelikula(
                addizena.getText(),
                addzuz.getText(),
                adddembora.getText(),
                Integer.parseInt(addadina.getText()),
                Integer.parseInt(addurtea.getText()));
                
                pelikulakob.add(k);
                table.getItems();
                GestionEnListaEnMemoria.jsonGorde(pelikulakob, file);
            
            addizena.clear();
            addzuz.clear();
            adddembora.clear();
            addadina.clear();
            addurtea.clear();
            
        });
        
        final Button removeButton = new Button("Ezabatu");        
        removeButton.setOnAction((ActionEvent e) -> {
            Pelikula pelikula = table.getSelectionModel().getSelectedItem();    
            table.getItems().remove(pelikula);
            GestionEnListaEnMemoria.jsonGorde(pelikulakob, file);
        });
        
        hb.getChildren().addAll(addizena, addzuz, adddembora,addadina,addurtea, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color:#fff654;");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table, hb);
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        stage.setScene(scene);
        stage.show();   
        stage.setOnCloseRequest((WindowEvent event)-> 
        {
            GestionEnListaEnMemoria.jsonGorde(pelikulakob, file);
        }
        );
        
               
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
