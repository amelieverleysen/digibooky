package com.switchfully.digibooky.domain.repositories;

import com.switchfully.digibooky.domain.*;
import org.junit.jupiter.api.*;

class LendingRepositoryTest {

    private final LendingRepository lendingRepository = new LendingRepository();

    @Test
    void whenCreatingALendingItem_thenLendingItemEquals() {
        //GIVEN WHEN
        LendItem lendItemToTest = new LendItem("999", "100", "200");
        LendItem savedLendItem = lendingRepository.save(lendItemToTest);

        //THEN
        Assertions.assertEquals(lendItemToTest, savedLendItem);
    }

    @Test
    void whenRemovingALendingItem_thenReturnTrue() {
        //GIVEN WHEN
        LendItem lendItemToTest = new LendItem("999", "100", "200");
        //THEN
        Assertions.assertTrue(lendingRepository.removeLendItem(lendItemToTest));
    }

}