package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.model.Endereco;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoServiceTest {
    EnderecoService enderecoService = new EnderecoService();

    @Test
    public void testeCompleto() throws SQLException {
        Endereco endereco = new Endereco("Rua dos estudos", 13, "Contagem", "MG");
        //enderecoService.cadastrar(endereco);

        endereco.setRua("Rua I");
        endereco.setNumero(325);
        endereco.setCidade("Belo Horizonte");
        endereco.setSiglaEstado("MG");

        enderecoService.modificar(endereco);

        enderecoService.excluir(endereco);
    }

}