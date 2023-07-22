package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.TagEntity;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.exception.TagAlreadyExistException;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.TagRepository;
import alticshaw.com.coszastore.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService implements TagServiceImp {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> getAllTags() {
        List<TagEntity> tagEntityList = tagRepository.findAll();
        return tagEntityList.stream()
                .map(data -> new TagResponse().mapTagEntityToTagResponse(data))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(String tagName) {
        if (!(tagName == null || tagName.trim().isEmpty())) {
            Optional<TagEntity> optionalTag = tagRepository.findByName(tagName);
            if (optionalTag.isPresent()) {
                throw  new TagAlreadyExistException("Tag with that name already existed!");
            }
            TagEntity tagEntity = new TagEntity();
            tagEntity.setName(tagName);

            tagRepository.save(tagEntity);
            return true;
        } else {
            throw new CustomIllegalArgumentException("Tag name can not null or empty");
        }


    }
}
