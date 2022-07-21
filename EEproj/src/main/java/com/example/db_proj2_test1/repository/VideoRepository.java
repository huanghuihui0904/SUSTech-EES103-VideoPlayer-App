package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video,Integer> {

    @Query(value = "select link from id_link where id=?",nativeQuery = true)
    String getLink(int id);


}
