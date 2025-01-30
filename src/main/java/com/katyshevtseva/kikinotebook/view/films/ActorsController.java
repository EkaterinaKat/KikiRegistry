package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.kikinotebook.core.films.ActorFileManager;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import com.katyshevtseva.kikinotebook.core.films.model.Role;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.setImageWidthPreservingRatio;

@RequiredArgsConstructor
public class ActorsController implements FxController {
    private static final Size GRID_SIZE = new Size(900, 960);
    private static final int BLOCK_WIDTH = 190;
    private final Film film;
    private BlockGridController<Role> actorGridController;
    @FXML
    private Pane contentPane;

    @FXML
    private void initialize() {
        adjustBlockListController();
        updateContent();
    }

    private void adjustBlockListController() {
        ComponentBuilder.Component<BlockGridController<Role>> component =
                new ComponentBuilder().setSize(GRID_SIZE).getBlockGridComponent(BLOCK_WIDTH,
                        null, null, this::getRoleNode);
        contentPane.getChildren().add(component.getNode());
        actorGridController = component.getController();
    }

    private Node getRoleNode(Role role, int blockWidth) {
        Label nameLabel = new Label(role.getNameAndDescNonNull());
        FxUtils.setWidth(nameLabel, blockWidth);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

        if (role.getActor().getHasLoadedPhoto()) {
            ImageView imageView = new ImageView(ActorFileManager.getActorPhoto(role.getActor()).getImage());
            setImageWidthPreservingRatio(imageView, blockWidth);
            vBox.getChildren().addAll(imageView, getPaneWithHeight(10));
        }

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle());
        return hBox;
    }

    private void updateContent() {
        Comparator<Role> comparator = Comparator
                .comparing(Role::descriptionIsEmpty)
                .thenComparing(Role::actorDoesntHavePhoto);

        List<Role> actors = film.getRoles()
                .stream()
                .peek(role -> role.getActor().setHasLoadedPhoto(ActorFileManager.actorHasPhoto(role.getActor())))
                .sorted(comparator)
                .collect(Collectors.toList());

        actorGridController.setContent(actors);
    }
}
