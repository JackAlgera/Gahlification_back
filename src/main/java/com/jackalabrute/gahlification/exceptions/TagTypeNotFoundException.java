package com.jackalabrute.gahlification.exceptions;

public class TagTypeNotFoundException extends RuntimeException {

    public TagTypeNotFoundException(String tagType) {
        super(String.format("Tag type with name %s does not exist.", tagType));
    }
}
