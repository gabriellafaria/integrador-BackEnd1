package com.dh.consultorioOdontologico.repository;

import com.dh.consultorioOdontologico.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
