package alticshaw.com.coszastore.mapper;

import org.modelmapper.ModelMapper;

public class ModelUtilMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static <T> T map(Object source, Class<T> targetType){
        return modelMapper.map(source,targetType);
    }
}
