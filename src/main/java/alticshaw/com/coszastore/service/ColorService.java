package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.ColorEntity;
import alticshaw.com.coszastore.exception.ColorAlreadyExistException;
import alticshaw.com.coszastore.exception.ColorNotFoundException;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.payload.response.ColorResponse;
import alticshaw.com.coszastore.repository.ColorRepository;
import alticshaw.com.coszastore.service.imp.ColorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService implements ColorServiceImp {
    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void add(String name) {
        if (!(name == null || name.trim().isEmpty())) {
            colorRepository.findByName(name).ifPresent(existingColor -> {
                throw new ColorAlreadyExistException(name + " already exist!");
            });
            ColorEntity color = new ColorEntity();
            color.setName(name);
            colorRepository.save(color);
        } else {
            throw new CustomIllegalArgumentException("Illegal name: " + name);
        }
    }


    @Override
    public List<ColorResponse> getAllColors() {
        ColorResponse colorResponse = new ColorResponse();
        return colorRepository.findAll().stream()
                .map(colorResponse::mapColorEntityToColorResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void edit(Integer id, String name) {
       if (!name.trim().isEmpty()) {
           ColorEntity colorEntity = colorRepository.findById(id).orElseThrow(() ->
                   new ColorNotFoundException("Color not found with id: " + id)
           );
           if (!colorEntity.getName().equals(name)) {
               colorRepository.findByName(name).ifPresent(existingColor -> {
                   throw new ColorAlreadyExistException("Duplicating color name: " + name);
               });
               colorEntity.setName(name);
               colorRepository.save(colorEntity);
           }
       } else {
           throw new CustomIllegalArgumentException("Illegal name: " + name);
       }
    }

    @Override
    public void delete(Integer id) {
        ColorEntity colorEntity = colorRepository.findById(id).orElseThrow(() ->
            new ColorNotFoundException("Color not found with id: " + id)
        );
        colorRepository.delete(colorEntity);
    }
}
