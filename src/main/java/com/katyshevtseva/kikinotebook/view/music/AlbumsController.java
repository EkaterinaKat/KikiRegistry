package com.katyshevtseva.kikinotebook.view.music;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.music.MusicService;
import com.katyshevtseva.kikinotebook.core.music.entity.Album;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static com.katyshevtseva.fx.Styler.getColorfullStyle;
import static com.katyshevtseva.kikinotebook.view.music.AlbumImageUtils.getImageContainer;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookDialogInfo.ALBUM_DIALOG;

public class AlbumsController implements SectionController {
    private static final Size GRID_SIZE = new Size(850, 1300);
    private static final int BLOCK_WIDTH = 370;
    private BlockGridController<Album> albumGridController;
    @FXML
    private HBox albumsPane;
    @FXML
    private Button newAlbumButton;

    @FXML
    private void initialize() {
        newAlbumButton.setOnAction(event -> WindowBuilder.openDialog(ALBUM_DIALOG,
                new AlbumDialogController(null, this::updateContent)));

        adjustBlockListController();
        updateContent();
    }

    private void adjustBlockListController() {
        ComponentBuilder.Component<BlockGridController<Album>> component =
                new ComponentBuilder().setSize(GRID_SIZE).getBlockGridComponent(BLOCK_WIDTH,
                        null, null, this::getAlbumNode);
        albumsPane.getChildren().add(component.getNode());
        albumGridController = component.getController();
    }

    private Node getAlbumNode(Album album, int blockWidth) {
        Label titleLabel = new Label(album.getTitle());
        FxUtils.setWidth(titleLabel, blockWidth);
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(getPaneWithHeight(10), titleLabel, getPaneWithHeight(10));

        vBox.getChildren().addAll(
                placeImageInSquare(new ImageView(getImageContainer(album).getImage()), blockWidth),
                getPaneWithHeight(10));

        Label infoLabel = FxUtils.getLabel(album.getFullInfo(), blockWidth);
        vBox.getChildren().addAll(infoLabel, getPaneWithHeight(10));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle() + getColorfullStyle(BACKGROUND, album.getGrade().getColor()));
        hBox.setOnContextMenuRequested(event -> showAlbumContextMenu(hBox, event, album));
        return hBox;
    }

    private void showAlbumContextMenu(Node node, ContextMenuEvent event, Album album) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> WindowBuilder.openDialog(ALBUM_DIALOG,
                new AlbumDialogController(album, this::updateContent)));
        menu.getItems().add(editItem);

        menu.show(node, event.getScreenX(), event.getScreenY());
    }

    private void updateContent() {
        albumGridController.setContent(MusicService.getAlbums());
    }

}
