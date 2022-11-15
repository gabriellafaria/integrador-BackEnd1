package com.dh.consultorioOdontologico;

import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.DentistaService;
import com.dh.consultorioOdontologico.service.EnderecoService;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.time.LocalDate;

@SpringBootApplication
public class ConsultorioOdontologicoApplication {

	public static void main(String[] args) throws SQLException {

		SpringApplication.run(ConsultorioOdontologicoApplication.class, args);

		Dentista dentista1 = new Dentista("Felipe", "Stefani", 6);
		Dentista dentista2 = new Dentista("Gabriela", "Faria", 3);
		Dentista dentista3 = new Dentista("Mariana", "Orsi", 7);
		Dentista dentista4 = new Dentista("Maria", "Bonifacio", 8);
		DentistaService dentistaService = new DentistaService();
		dentistaService.cadastrar(dentista1);
		dentistaService.cadastrar(dentista2);
		dentistaService.cadastrar(dentista3);
		dentistaService.cadastrar(dentista4);

		dentistaService.excluir(dentista1);

		Endereco endereco = new Endereco("Rua um", 12, "Belo horizonte", "MG");
		EnderecoService enderecoService = new EnderecoService();
		enderecoService.cadastrar(endereco);


		Paciente paciente = new Paciente("Gabriella", "Faria", "12312341", LocalDate.now(), endereco);
		PacienteService pacienteService = new PacienteService();
		pacienteService.cadastrar(paciente);
		paciente.setNome("Felipe");
		pacienteService.modificar(paciente);

		pacienteService.excluir(paciente);
	}


}
