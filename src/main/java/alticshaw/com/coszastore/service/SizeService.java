package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.SizeEntity;
import alticshaw.com.coszastore.exception.*;
import alticshaw.com.coszastore.payload.response.SizeResponse;
import alticshaw.com.coszastore.repository.SizeRepository;
import alticshaw.com.coszastore.service.imp.SizeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeService implements SizeServiceImp {
    private final SizeRepository sizeRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public void add(String name) {
        if (!(name == null || name.trim().isEmpty())) {
            sizeRepository.findByName(name).ifPresent(existingColor -> {
                throw new ColorAlreadyExistException(name + " already exist!");
            });
            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setName(name);
            sizeRepository.save(sizeEntity);
        } else {
            throw new CustomIllegalArgumentException("Illegal name: " + name);
        }
    }

    @Override
    public List<SizeResponse> getAllSizes() {
        SizeResponse sizeResponse = new SizeResponse();
        return sizeRepository.findAll().stream()
                .map(sizeResponse::mapTagEntityToSizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void edit(Integer id, String name) {
        if (!name.trim().isEmpty()) {
            SizeEntity sizeEntity = sizeRepository.findById(id).orElseThrow(() ->
                    new SizeNotFoundException("Size not found with id: " + id)
            );
            if (!sizeEntity.getName().equals(name)) {
                sizeRepository.findByName(name).ifPresent(existingSize -> {
                    throw new SizeAlreadyExistException("Duplicating size name: " + name);
                });
                sizeEntity.setName(name);
                sizeRepository.save(sizeEntity);
            }
        } else {
            throw new CustomIllegalArgumentException("Illegal name: " + name);
        }
    }

    @Override
    public void delete(Integer id) {
        SizeEntity sizeEntity = sizeRepository.findById(id).orElseThrow(() ->
                new SizeNotFoundException("Size not found with id: " + id)
        );
        sizeRepository.delete(sizeEntity);
    }
}
