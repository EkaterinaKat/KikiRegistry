package com.katyshevtseva.kikinotebook.view.books;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.kikinotebook.core.BooksService;
import com.katyshevtseva.kikinotebook.core.model.Author;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static com.katyshevtseva.fx.Styler.getColorfullStyle;
import static com.katyshevtseva.fx.Styler.setHoverStyle;
import static com.katyshevtseva.kikinotebook.view.books.AuthorImageUtils.getImageContainer;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookDialogInfo.AUTHOR_DIALOG;

public class BooksController implements FxController {
    private static final Size GRID_SIZE = new Size(800, 1200);
    private static final int BLOCK_WIDTH = 200;
    private BlockGridController<Author> authorGridController;
    @FXML
    private Button newAuthorButton;
    @FXML
    private Pane authorsPane;

    @FXML
    private void initialize() {
        newAuthorButton.setOnAction(event -> WindowBuilder.openDialog(AUTHOR_DIALOG,
                new AuthorDialogController(null, this::updateContent)));

        adjustBlockListController();
        updateContent();
    }

    private void adjustBlockListController() {
        ComponentBuilder.Component<BlockGridController<Author>> todoComponent =
                new ComponentBuilder().setSize(GRID_SIZE).getBlockGridComponent(BLOCK_WIDTH,
                        null, null, this::getAuthorNode);
        authorsPane.getChildren().add(todoComponent.getNode());
        authorGridController = todoComponent.getController();
    }

    private Node getAuthorNode(Author author, int blockWidth) {
        Label nameLabel = new Label(author.getFullName());
        FxUtils.setWidth(nameLabel, blockWidth);
        nameLabel.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10),
                placeImageInSquare(new ImageView(getImageContainer(author).getImage()), blockWidth),
                getPaneWithHeight(10));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle());
        setHoverStyle(hBox, getColorfullStyle(BACKGROUND, "#EF47FF"));
        return hBox;
    }

    private void updateContent() {
        authorGridController.setContent(BooksService.getAllAuthors());
    }
}
