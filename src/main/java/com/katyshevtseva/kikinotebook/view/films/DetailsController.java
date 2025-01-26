package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films.PosterFileManager;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;

import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;

@RequiredArgsConstructor
public class DetailsController implements FxController {
    private final Film film;
    private final NoArgsKnob onUpdateDataListener;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Label detailsLabel;
    @FXML
    private HBox buttonBox;

    @FXML
    private void initialize() {
        imageView.setImage(PosterFileManager.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, 300);
        titleLabel.setText(film.getTitleAndYear());
        detailsLabel.setText(film.getDetailsString());
        detailsLabel.setWrapText(true);
        detailsLabel.setMaxWidth(600);
        FilmOperationsHelper.fillButtonBox(film, buttonBox, () -> {
            onUpdateDataListener.execute();
            FxUtils.closeWindowThatContains(titleLabel);
        });
    }
}
