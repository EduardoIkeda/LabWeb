package com.uneb.labweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uneb.labweb.model.PostoSaude;

@Repository
public interface PostoSaudeRepository extends JpaRepository<PostoSaude, Long> {
    
}
