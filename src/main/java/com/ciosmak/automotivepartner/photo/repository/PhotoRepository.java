package com.ciosmak.automotivepartner.photo.repository;

import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.photo.support.PhotoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>
{
    List<Photo> findByShiftIdAndType(Long shiftId, PhotoType type);
}
