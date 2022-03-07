package com.jackalabrute.gahlification.database.daos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DefaultDAO <T> {

    T create(T item);
    void delete(UUID id);
    Optional<T> findById(UUID id);
    List<T> findAll();
    T update(T item);
}
