package com.ciosmak.automotivepartner.photo.response;

import com.ciosmak.automotivepartner.photo.support.PhotoType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhotoResponse
{
    private final Long id;
    private final String url;
    private final PhotoType type;
}
