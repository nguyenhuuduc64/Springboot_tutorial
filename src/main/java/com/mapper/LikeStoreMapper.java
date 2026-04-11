package com.mapper;

import com.dto.response.LikeStoreResponse;
import com.entity.LikeStore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeStoreMapper {
    LikeStoreResponse toLikeStoreResponse(LikeStore likeStore);
}
