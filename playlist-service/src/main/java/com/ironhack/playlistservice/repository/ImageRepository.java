package com.ironhack.playlistservice.repository;

import com.ironhack.playlistservice.dao.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {}