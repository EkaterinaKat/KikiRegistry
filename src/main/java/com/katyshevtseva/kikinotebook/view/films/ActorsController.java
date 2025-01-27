package com.katyshevtseva.kikinotebook.view.films;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.windowbuilder.FxController;
import com.katyshevtseva.kikinotebook.core.films.ActorService;
import com.katyshevtseva.kikinotebook.core.films.model.Actor;
import com.katyshevtseva.kikinotebook.core.films.model.Film;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;

@RequiredArgsConstructor
public class ActorsController implements FxController {
    private static final Size GRID_SIZE = new Size(900, 960);
    private static final int BLOCK_WIDTH = 190;
    private final Film film;
    private BlockGridController<Actor> actorGridController;
    @FXML
    private Pane contentPane;

    @FXML
    private void initialize() {
        adjustBlockListController();
        updateContent();
    }

    private void adjustBlockListController() {
        ComponentBuilder.Component<BlockGridController<Actor>> component =
                new ComponentBuilder().setSize(GRID_SIZE).getBlockGridComponent(BLOCK_WIDTH,
                        null, null, this::getActorNode);
        contentPane.getChildren().add(component.getNode());
        actorGridController = component.getController();
    }

    private Node getActorNode(Actor actor, int blockWidth) {
        Label nameLabel = new Label(actor.getNameNonNull());
        FxUtils.setWidth(nameLabel, blockWidth);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

//        if (author.getImageName() != null) {
//            vBox.getChildren().addAll(
//                    placeImageInSquare(new ImageView(getImageContainer(author).getImage()), blockWidth),
//                    getPaneWithHeight(10));
//        }

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle());
        return hBox;
    }

    private void updateContent() {
        actorGridController.setContent(ActorService.findActors(film));
    }
}
