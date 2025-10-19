
package net.mboudnag.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception für nicht gefundene Ressourcen.
 *
 * Diese benutzerdefinierte Exception wird geworfen, wenn eine angeforderte
 * Ressource (z.B. eine Aufgabe mit einer bestimmten ID) nicht in der
 * Datenbank gefunden werden kann.
 *
 * Die {@code @ResponseStatus}-Annotation sorgt dafür, dass beim Werfen
 * dieser Exception automatisch ein HTTP-Status 404 (NOT_FOUND) an den
 * Client zurückgegeben wird.
 *
 * @author Mboudnag
 * @version 2.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Konstruktor mit Fehlermeldung.
     *
     * Erstellt eine neue ResourceNotFoundException mit einer
     * aussagekräftigen Fehlermeldung.
     *
     * @param message die detaillierte Fehlermeldung, die beschreibt,
     *                welche Ressource nicht gefunden wurde
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
