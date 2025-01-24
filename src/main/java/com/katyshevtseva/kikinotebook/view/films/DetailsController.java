package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;

public class DetailsController implements FxController {
    private final Film film;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Label detailsLabel;

    public DetailsController(Film film) {
        this.film = film;
    }

    @FXML
    private void initialize() {
        imageView.setImage(PosterFileManager2.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, 300);
        titleLabel.setText(film.getTitleAndYear());
        detailsLabel.setText(film.getDatesString());
    }
}
