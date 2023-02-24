package com.igrallery.jun.domain.repository;

import com.igrallery.jun.domain.entity.img.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
