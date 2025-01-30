package com.katyshevtseva.kikinotebook.core.films.web;

import com.katyshevtseva.kikinotebook.core.films.ActorFileManager;
import com.katyshevtseva.kikinotebook.core.films.model.Actor;
import com.katyshevtseva.web.ImageDownloader;

import static com.katyshevtseva.kikinotebook.core.films.ActorFileManager.ACTORS_IMAGE_LOCATION;
import static com.katyshevtseva.kikinotebook.core.films.ActorFileManager.formImageFileName;

public class ActorPhotoLoader {

    public static void loadActorPhoto(Actor actor) {

        if (ActorFileManager.actorHasPhoto(actor)) {
            System.out.println("-");
            return;
        }

        try {
            ImageDownloader.download(ACTORS_IMAGE_LOCATION, formImageFileName(actor), actor.getPhotoUrl());
            System.out.println("Успешная загрузка " + actor.getNameNonNull());
        } catch (Exception e) {
            System.out.println("Неуспешная загрузка " + actor.getNameNonNull());
            throw new RuntimeException(e);
        }

    }
}
