package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.kikinotebook.core.films.StatusService;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films2.PosterFileManager2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private Button deleteFromToWatchButton;
    @FXML
    private Button wantToWatchButton;

    @FXML
    private void initialize() {
        imageView.setImage(PosterFileManager2.getPoster(film).getImage());
        setImageWidthPreservingRatio(imageView, 300);
        titleLabel.setText(film.getTitleAndYear());
        detailsLabel.setText(film.getDetailsString());
        detailsLabel.setWrapText(true);
        detailsLabel.setMaxWidth(600);
        adjustDeleteButton();
        wantToWatchButton.setOnMouseClicked(event -> StatusService.wantToWatchFilm(film));
    }

    private void adjustDeleteButton() {
        if (StatusService.isToWatch(film)) {
            deleteFromToWatchButton.setOnMouseClicked(event ->
                    new StandardDialogBuilder().openQuestionDialog("Delete?", b -> {
                        if (b) {
                            StatusService.deleteFromToWatch(film);
                            onUpdateDataListener.execute();
                            FxUtils.closeWindowThatContains(titleLabel);
                        }
                    }));
        } else {
            deleteFromToWatchButton.setVisible(false);
        }
    }
}
