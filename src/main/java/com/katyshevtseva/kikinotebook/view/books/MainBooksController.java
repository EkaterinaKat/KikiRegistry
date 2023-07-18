package com.katyshevtseva.kikinotebook.view.books;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DcDatePicker;
import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.kikinotebook.core.AuthorService;
import com.katyshevtseva.kikinotebook.core.BookService;
import com.katyshevtseva.kikinotebook.core.model.Author;
import com.katyshevtseva.kikinotebook.core.model.Book;
import com.katyshevtseva.kikinotebook.core.model.BookAction;
import com.katyshevtseva.kikinotebook.core.model.BookGrade;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static com.katyshevtseva.fx.Styler.getColorfullStyle;
import static com.katyshevtseva.fx.Styler.setHoverStyle;
import static com.katyshevtseva.kikinotebook.view.books.AuthorImageUtils.getImageContainer;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookDialogInfo.AUTHOR_DIALOG;

public class MainBooksController implements SectionController {
    private static final Size GRID_SIZE = new Size(850, 1300);
    private static final int BLOCK_WIDTH = 330;
    private BlockGridController<Author> authorGridController;
    @FXML
    private Button newAuthorButton;
    @FXML
    private Pane authorsPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button clearButton;

    @FXML
    private void initialize() {
        newAuthorButton.setOnAction(event -> WindowBuilder.openDialog(AUTHOR_DIALOG,
                new AuthorDialogController(null, this::updateContent)));

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> updateContent());
        clearButton.setOnAction(event -> searchTextField.setText(""));

        adjustBlockListController();
        updateContent();
    }

    private void adjustBlockListController() {
        ComponentBuilder.Component<BlockGridController<Author>> component =
                new ComponentBuilder().setSize(GRID_SIZE).getBlockGridComponent(BLOCK_WIDTH,
                        null, null, this::getAuthorNode);
        authorsPane.getChildren().add(component.getNode());
        authorGridController = component.getController();
    }

    private Node getAuthorNode(Author author, int blockWidth) {
        Label nameLabel = new Label(author.getFullName());
        FxUtils.setWidth(nameLabel, blockWidth);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.BASELINE_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                getPaneWithHeight(10),
                nameLabel,
                getPaneWithHeight(10));

        if (author.getImageName() != null) {
            vBox.getChildren().addAll(
                    placeImageInSquare(new ImageView(getImageContainer(author).getImage()), blockWidth),
                    getPaneWithHeight(10));
        }

        for (Book book : BookService.find(author, searchTextField.getText())) {
            Label label = new Label(book.getListInfo());

            label.setStyle(label.getStyle() + Styler.getColorfullStyle(BACKGROUND, book.getGrade().getColor()));

            label.setContextMenu(getBookContextMenu(book, author));
            vBox.getChildren().addAll(label, getPaneWithHeight(10));
        }

        HBox hBox = new HBox();
        hBox.getChildren().addAll(getPaneWithWidth(10), vBox, getPaneWithWidth(10));
        hBox.setStyle(Styler.getBlackBorderStyle());
        setHoverStyle(hBox, getColorfullStyle(BACKGROUND, "#EF47FF"));
        hBox.setOnContextMenuRequested(event -> showAuthorContextMenu(hBox, event, author));
        return hBox;
    }

    private void showAuthorContextMenu(Node node, ContextMenuEvent event, Author author) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> WindowBuilder.openDialog(AUTHOR_DIALOG,
                new AuthorDialogController(author, this::updateContent)));
        menu.getItems().add(editItem);

        MenuItem addBookItem = new MenuItem("Add book");
        addBookItem.setOnAction(event1 -> openBookEditDialog(author, null));
        menu.getItems().add(addBookItem);

        menu.show(node, event.getScreenX(), event.getScreenY());
    }

    private ContextMenu getBookContextMenu(Book book, Author author) {
        ContextMenu menu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event1 -> openBookEditDialog(author, book));
        menu.getItems().add(editItem);

        return menu;
    }

    private void openBookEditDialog(Author author, Book book) {
        boolean newBook = book == null;
        DcTextField nameField = new DcTextField(true, newBook ? "" : book.getName());
        DcComboBox<BookAction> actionBox = new DcComboBox<>(true,
                newBook ? BookAction.READ_RUS : book.getAction(), Arrays.asList(BookAction.values()));
        DcDatePicker datePicker = new DcDatePicker(false, newBook ? null : book.getFinishDate());
        DcComboBox<BookGrade> gradeDcComboBox = new DcComboBox<>(true, book.getGrade(), Arrays.asList(BookGrade.values()));

        DialogConstructor.constructDialog(() -> {
            BookService.save(book, nameField.getValue(), author, actionBox.getValue(), gradeDcComboBox.getValue(), datePicker.getValue());
            updateContent();
        }, 330, nameField, actionBox, gradeDcComboBox, datePicker);

    }

    private void updateContent() {
        authorGridController.setContent(AuthorService.getAllSorted(searchTextField.getText()));
    }
}
