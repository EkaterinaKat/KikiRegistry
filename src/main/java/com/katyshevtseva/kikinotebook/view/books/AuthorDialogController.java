package com.katyshevtseva.kikinotebook.view.books;

import com.katyshevtseva.fx.FxImageCreationUtil;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.NoArgsKnob;
import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.kikinotebook.core.AuthorService;
import com.katyshevtseva.kikinotebook.core.model.Author;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.katyshevtseva.fx.FxUtils.associateButtonWithControls;
import static com.katyshevtseva.fx.FxUtils.closeWindowThatContains;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;

class AuthorDialogController implements FxController {
    private ImageContainer image;
    private final Author existing;
    private final NoArgsKnob onSaveListener;
    @FXML
    private Pane imagePane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button saveButton;

    AuthorDialogController(Author existing, NoArgsKnob onSaveListener) {
        this.existing = existing;
        this.onSaveListener = onSaveListener;
    }

    @FXML
    private void initialize() {
        associateButtonWithControls(saveButton, nameTextField);
        saveButton.setOnAction(event -> save());
        showImage(FxImageCreationUtil.getIcon(FxImageCreationUtil.IconPicture.GREY_PLUS));
        setExistingPieceInfo();
    }

    private void setExistingPieceInfo() {
        if (existing != null) {
            if (existing.getImageName() != null) {
                image = AuthorImageUtils.getImageContainer(existing);
                showImage(image.getImage());
            }
            nameTextField.setText(existing.getName());
            surnameTextField.setText(existing.getSurname());
        }
    }

    private void save() {
        AuthorService.save(existing, nameTextField.getText(), surnameTextField.getText(),
                image != null ? image.getFileName() : null);
        onSaveListener.execute();
        closeWindowThatContains(imagePane);
    }

    private void openImageSelectionDialog() {
        new StandardDialogBuilder().openImageSelectionDialog(
                AuthorImageUtils.getFreeImagesForAuthorCreation(),
                imageContainer -> {
                    image = imageContainer;
                    showImage(image.getImage());
                });
    }

    private void showImage(Image image) {
        imagePane.getChildren().clear();
        Node imageNode = placeImageInSquare(new ImageView(image), 400);
        imageNode.setOnMouseClicked(event -> openImageSelectionDialog());
        imagePane.getChildren().add(imageNode);
    }

}
