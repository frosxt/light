package com.github.frosxt.builders;

import org.bson.Document;

public class DocumentBuilder {
    private final Document document;

    public DocumentBuilder() {
        this.document = new Document();
    }

    public DocumentBuilder append(final String key, final Object value) {
        document.append(key, value);
        return this;
    }

    public Document build() {
        return document;
    }
}