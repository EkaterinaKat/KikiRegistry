package com.katyshevtseva.kikinotebook.view.films2;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController2;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films2.FilmSearchService2;
import com.katyshevtseva.kikinotebook.core.films2.FilmToWatchService2;
import com.katyshevtseva.kikinotebook.core.films2.web.model.FilmResponse;
import com.katyshevtseva.kikinotebook.core.films2.web.model.PosterResponse;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;

@RequiredArgsConstructor
public class FilmSearchController2 implements FxController {
    private static final int POSTER_WIDTH = 200;
    private static final int GRID_WIDTH = 900;
    private final NoArgsKnob onSelectKnob;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Pane contentPane;

    @FXML
    private void initialize() {
        searchButton.setOnAction(event -> {
            if (!GeneralUtils.isEmpty(searchTextField.getText())) {
                updateContent();
            }
        });
    }

    private void updateContent() {
        contentPane.getChildren().clear();

        try {
            List<FilmResponse> films = FilmSearchService2.search(searchTextField.getText());
            contentPane.getChildren().add(getFilmGridNode(films));
        } catch (Exception e) {
            Label label = new Label(e.getMessage());
            label.setWrapText(true);
            FxUtils.setWidth(label, 800);
            contentPane.getChildren().add(label);
        }
    }

    private Node getFilmGridNode(List<FilmResponse> films) {
        if (films == null || films.isEmpty()) {
            return new Label("Nothing found");
        } else {
            ComponentBuilder.Component<BlockGridController2<FilmResponse>> component =
                    new ComponentBuilder().getBlockGridComponent2(POSTER_WIDTH, GRID_WIDTH, this::getFilmNode);
            component.getController().setContent(films);
            return component.getNode();
        }
    }

    private Node getFilmNode(FilmResponse film, int blockWidth) {
        Label nameLabel = new Label(film.getTitleAndYear());
        FxUtils.setWidth(nameLabel, blockWidth);
        FxUtils.setHeight(nameLabel, 50);
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

        PosterResponse poster = film.getPoster();
        if (poster != null && poster.getUrl() != null) {
            ImageView imageView = new ImageView(new Image(poster.getUrl()));
            setImageWidthPreservingRatio(imageView, blockWidth);
            vBox.getChildren().addAll(imageView, getPaneWithHeight(10));
        }

        HBox hBox = new HBox();
        hBox.getChildren().add(vBox);
        hBox.setOnMouseClicked(event -> {
            FilmToWatchService2.saveToWatchFilm(film);
            onSelectKnob.execute();
            FxUtils.closeWindowThatContains(searchButton);
        });
        return hBox;
    }
}
