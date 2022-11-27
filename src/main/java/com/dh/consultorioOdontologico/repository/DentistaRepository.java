package com.dh.consultorioOdontologico.repository;

import com.dh.consultorioOdontologico.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    Dentista findByMatricula(int matricula);
}
