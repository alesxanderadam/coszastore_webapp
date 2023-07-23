package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.TagEntity;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.exception.TagAlreadyExistException;
import alticshaw.com.coszastore.exception.TagNotFoundException;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.TagRepository;
import alticshaw.com.coszastore.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService implements TagServiceImp {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> getAllResponseTags() {
        List<TagEntity> tagEntityList = tagRepository.findAll();
        return tagEntityList.stream()
                .map(data -> new TagResponse().mapTagEntityToTagResponse(data))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(String tagName) {
        if (!(tagName == null || tagName.trim().isEmpty())) {
            tagRepository.findByName(tagName).orElseThrow(() ->
                    new TagAlreadyExistException("Tag with that name already existed!")
            );

            TagEntity tagEntity = new TagEntity();
            tagEntity.setName(tagName);

            tagRepository.save(tagEntity);
            return true;
        } else {
            throw new CustomIllegalArgumentException("Tag name can not null or empty");
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            int tagId = Integer.parseInt(id);
            TagEntity tag = tagRepository.findById(tagId).orElseThrow(() ->
                    new TagNotFoundException("Tag not found with id: " + tagId));
            tagRepository.delete(tag);
            return true;
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal tag id: " + id);
        }
    }

    @Override
    public boolean edit(String id, String name) {
        try {
            int tagId = Integer.parseInt(id);
            if (!(name == null || name.trim().isEmpty())) {
                TagEntity tag = tagRepository.findById(tagId).orElseThrow(() ->
                        new TagNotFoundException("Tag not found with id: " + tagId));
                if (!tag.getName().equals(name)) {
                    tagRepository.findByName(name).ifPresent(existingTag -> {
                        throw new TagAlreadyExistException("Tag with that name already exists: " + name);
                    });
                    tag.setName(name);
                    tagRepository.save(tag);
                }
                return true;
            } else {
                throw new CustomIllegalArgumentException("Illegal tag parameters");
            }
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal tag parameters");
        }
    }
}
