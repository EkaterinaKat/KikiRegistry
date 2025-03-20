package com.katyshevtseva.kikinotebook.view.books;

import com.katyshevtseva.fx.FxUtils;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.fx.Styler;
import com.katyshevtseva.fx.component.ComponentBuilder;
import com.katyshevtseva.fx.component.controller.BlockGridController;
import com.katyshevtseva.fx.dialogconstructor.DcComboBox;
import com.katyshevtseva.fx.dialogconstructor.DcDatePicker;
import com.katyshevtseva.fx.dialogconstructor.DcTextField;
import com.katyshevtseva.fx.dialogconstructor.DialogConstructor;
import com.katyshevtseva.fx.switchcontroller.SectionController;
import com.katyshevtseva.fx.windowbuilder.WindowBuilder;
import com.katyshevtseva.general.GeneralUtils;
import com.katyshevtseva.kikinotebook.core.books.AuthorService;
import com.katyshevtseva.kikinotebook.core.books.BookService;
import com.katyshevtseva.kikinotebook.core.books.model.Author;
import com.katyshevtseva.kikinotebook.core.books.model.Book;
import com.katyshevtseva.kikinotebook.core.books.model.BookAction;
import com.katyshevtseva.kikinotebook.core.books.model.BookGrade;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtseva.fx.FxUtils.getPaneWithHeight;
import static com.katyshevtseva.fx.FxUtils.getPaneWithWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.placeImageInSquare;
import static com.katyshevtseva.fx.Styler.ThingToColor.BACKGROUND;
import static com.katyshevtseva.fx.Styler.getColorfullStyle;
import static com.katyshevtseva.fx.Styler.setHoverStyle;
import static com.katyshevtseva.kikinotebook.view.books.AuthorImageUtils.getImageContainer;
import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.AUTHOR_DIALOG;

public class MainBooksController implements SectionController {
    private static final Size GRID_SIZE = new Size(850, 1300);
    private static final int BLOCK_WIDTH = 370;
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
    }

    @Override
    public void update() {
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

        if (hasImage(author)) {
            vBox.getChildren().addAll(
                    placeImageInSquare(new ImageView(getImageContainer(author).getImage()), blockWidth),
                    getPaneWithHeight(10));
        }

        for (Book book : BookService.find(author, getStringToSearchBook(author))) {
            Label label = new Label(book.getListInfo());

            label.setStyle(label.getStyle() + Styler.getColorfullStyle(BACKGROUND, book.getGrade().getColor()));
            label.setWrapText(true);
            label.setMaxWidth(blockWidth);

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

    private String getStringToSearchBook(Author author) {
        String ss = searchTextField.getText();

        if (GeneralUtils.isEmpty(ss))
            return null;

        boolean authorMatchesSearchString = author.getFullName().toUpperCase().contains(ss.toUpperCase());
        //ситуация когда автор подходит поисковому запросу тогда нужно показать все его книги
        if (authorMatchesSearchString)
            return null;
        //ситуация когда автор не подходит запросу тогда есть смысл показывать только книги подходящие запросу
        else
            return ss;
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

        MenuItem idItem = new MenuItem("Copy Id");
        idItem.setOnAction(event1 -> GeneralUtils.saveToClipBoard(author.getId().toString()));
        menu.getItems().add(idItem);

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
        DcComboBox<BookGrade> gradeDcComboBox = new DcComboBox<>(true, newBook ? null :
                book.getGrade(), Arrays.asList(BookGrade.values()));

        DialogConstructor.constructDialog(() -> {
            BookService.save(book, nameField.getValue(), author, actionBox.getValue(), gradeDcComboBox.getValue(), datePicker.getValue());
            updateContent();
        }, 330, nameField, actionBox, gradeDcComboBox, datePicker);

    }

    private void updateContent() {
        List<Author> authors = AuthorService.getAll(searchTextField.getText())
                .stream()
                .sorted(Comparator.comparing(Author::getSortingString))
                .sorted(Comparator.comparing(this::hasImage).reversed())

                .collect(Collectors.toList());

        authorGridController.setContent(authors);
    }

    private boolean hasImage(Author author) {
        return !getImageContainer(author).getImage().isError();
    }
}
