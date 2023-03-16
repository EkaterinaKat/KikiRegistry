package com.katyshevtseva.kikinotebook.view.books;

import com.katyshevtseva.image.ImageContainer;
import com.katyshevtseva.image.ImageContainerCache;
import com.katyshevtseva.kikinotebook.core.BooksService;
import com.katyshevtseva.kikinotebook.core.model.Author;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AuthorImageUtils {
    private static final String AUTHOR_IMAGE_LOCATION = "D:\\onedrive\\central_image_storage\\authors\\";
    private static final ImageContainerCache icc = ImageContainerCache.getInstance();


    public static ImageContainer getImageContainer(Author author) {
        return icc.getImageContainer(author.getImageName(), AUTHOR_IMAGE_LOCATION, 400);
    }

    public static List<ImageContainer> getFreeImagesForAuthorCreation() {
        List<ImageContainer> freeImages = new ArrayList<>();
        List<Author> existingAuthors = BooksService.getAllAuthors();

        for (File file : getAllImageFiles()) {
            boolean imageIsFree = true;
            for (Author author : existingAuthors) {
                if (author.getImageName() != null && author.getImageName().equals(file.getName())) {
                    imageIsFree = false;
                }
            }
            if (imageIsFree) {
                freeImages.add(icc.getImageContainer(file.getName(), AUTHOR_IMAGE_LOCATION, 400));
            }
        }
        return freeImages;
    }


    private static List<File> getAllImageFiles() {
        return Arrays.asList(Objects.requireNonNull(new File(AUTHOR_IMAGE_LOCATION).listFiles()));
    }
}
