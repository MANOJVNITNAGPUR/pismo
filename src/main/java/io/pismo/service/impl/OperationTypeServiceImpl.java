package io.pismo.service.impl;

import io.pismo.exceptions.OperationTypeNotFoundException;
import io.pismo.repo.OperationTypeRepository;
import io.pismo.service.OperationTypeService;
import io.pismo.service.dto.OperationTypeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationTypeServiceImpl implements OperationTypeService {

    @Autowired
    private OperationTypeRepository operationTypeRepository;


    @Override
    public OperationTypeDto getOperationTypeDetails(int operationTypeId) {
        return operationTypeRepository.findByOperationTypeId(operationTypeId).map(dao ->{
            OperationTypeDto dto = new OperationTypeDto();
            BeanUtils.copyProperties(dao,dto);
            return dto;
        }).orElseThrow(OperationTypeNotFoundException::new);
    }
}
