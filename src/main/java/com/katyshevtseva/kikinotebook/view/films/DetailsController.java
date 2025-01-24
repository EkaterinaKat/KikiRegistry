package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films.ToWatchService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;

import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;
import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.TO_WATCH;
import static com.katyshevtseva.kikinotebook.core.films.model.FilmStatus.WATCHED_AND_TO_WATCH;

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
    private Button deleteFromToWatch;

    @FXML
    private void initialize() {
        imageView.setImage(PosterFileManager2.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, 300);
        titleLabel.setText(film.getTitleAndYear());
        detailsLabel.setText(film.getDatesString());
        adjustDeleteButton();
    }

    private void adjustDeleteButton() {
        if (film.getStatus() == TO_WATCH || film.getStatus() == WATCHED_AND_TO_WATCH) {
            deleteFromToWatch.setOnMouseClicked(event ->
                    new StandardDialogBuilder().openQuestionDialog("Delete?", b -> {
                        if (b) {
                            ToWatchService.deleteFromToWatch(film);
                            onUpdateDataListener.execute();
                            FxUtils.closeWindowThatContains(titleLabel);
                        }
                    }));
        } else {
            deleteFromToWatch.setVisible(false);
        }
    }
}
