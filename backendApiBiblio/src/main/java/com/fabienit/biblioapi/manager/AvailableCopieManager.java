package com.fabienit.biblioapi.manager;

import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;

import java.util.List;
import java.util.Optional;

public interface AvailableCopieManager {

    Boolean updateStatusBookCanBeReserved(AvailableCopie availableCopie);
    int updateAvailableQuantity(int currentQuantity, String operationType);
    AvailableCopie relatedAvailableCopieUpdate(int bookId, int libraryId, String operationType);
    AvailableCopie updateReservationCount(int bookId, int libraryId);
    Optional<AvailableCopie> findById(AvailableCopieKey key);
    AvailableCopie save(AvailableCopie availableCopie);
    List<AvailableCopie> findAll();
    Boolean existsById(AvailableCopieKey availableCopieKey);
    void deleteById(AvailableCopieKey availableCopieKey);

}
