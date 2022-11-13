package com.dh.consultorioOdontologico;

import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

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
	}


}
