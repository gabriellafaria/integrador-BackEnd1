package com.dh.consultorioOdontologico.repository;

import com.dh.consultorioOdontologico.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long>{
    Optional<Consulta> findByChave(String chave);
    List<Consulta> findAllByIdDentista(Long idDentista);
    List<Consulta> findAllByIdPaciente(Long idPaciente);
}
