package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.response.TagResponse;

import java.util.List;

public interface TagServiceImp {
    List<TagResponse> getAllResponseTags();
    boolean add(String tagName);
    boolean delete(String id);

    boolean edit(String id, String name);
}
