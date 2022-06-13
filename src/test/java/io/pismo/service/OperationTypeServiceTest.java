package io.pismo.service;


import io.pismo.dao.OperationTypeDao;
import io.pismo.exceptions.OperationTypeNotFoundException;
import io.pismo.repo.OperationTypeRepository;
import io.pismo.service.dto.OperationTypeDto;
import io.pismo.service.impl.OperationTypeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OperationTypeServiceTest {
    @InjectMocks
    private OperationTypeServiceImpl operationTypeService;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = OperationTypeNotFoundException.class)
    public void invalidOperationTypeIdTest(){
        Mockito.when(operationTypeRepository.findByOperationTypeId(Mockito.eq(1))).thenReturn(Optional.empty());
        operationTypeService.getOperationTypeDetails(1);
    }

    @Test
    public void getOperationTypeDetailsTest(){
        OperationTypeDao operationTypeDao = OperationTypeDao.builder().operationTypeId(1).description("test").operationValue(1).build();
        Mockito.when(operationTypeRepository.findByOperationTypeId(Mockito.eq(1))).thenReturn(Optional.of(operationTypeDao));
        OperationTypeDto operationTypeDto = operationTypeService.getOperationTypeDetails(1);
        Assert.assertEquals(1,operationTypeDto.getOperationTypeId());
    }

}
