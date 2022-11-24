package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class EnderecoServiceTest {
    EnderecoService enderecoService = new EnderecoService();

    @Test
    public void cadastro() throws SQLException{
        Endereco endereco = new Endereco("Rua três", 13, "Belo Horizonte", "MG");
        enderecoService.cadastrar(endereco);

        Assertions.assertTrue(endereco.getId() > 0);
    }

    @Test
    public void busca() throws SQLException {
        List<Endereco> enderecos = enderecoService.buscarTodos();
        Assertions.assertEquals(28, enderecos.size());
    }

    @Test
    public void testeCompleto() throws SQLException {
        Endereco endereco = new Endereco("Rua dos estudos", 13, "Contagem", "MG");
        //enderecoService.cadastrar(endereco);

        endereco.setRua("Rua I");
        endereco.setNumero(325);
        endereco.setCidade("Belo Horizonte");
        endereco.setSiglaEstado("MG");

        enderecoService.modificar(endereco);

    }

}