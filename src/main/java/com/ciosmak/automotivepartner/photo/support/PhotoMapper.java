package com.ciosmak.automotivepartner.photo.support;

import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.photo.response.PhotoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PhotoMapper
{
    public PhotoResponse toPhotoResponse(Photo photo)
    {
        return new PhotoResponse(photo.getId(), photo.getUrl(), photo.getType());
    }
}
