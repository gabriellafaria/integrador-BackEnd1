package com.dh.consultorioOdontologico.repository;

import com.dh.consultorioOdontologico.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByRg(String rg);
}
