package com.br.fiap.nexora.model;




import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.enums.TipoInvestimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




import java.time.LocalDate;




import static org.junit.jupiter.api.Assertions.*;




class InvestimentoTest {

    private Cliente cliente;
    private InvestimentoDTO dto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setCpf("98765432100");
        cliente.setNome("Lety Investidora");

        dto = new InvestimentoDTO(
                null,
                TipoInvestimento.TESOURO_SELIC,
                5000.0f,
                LocalDate.of(2024, 1, 10),
                5500.0f,
                cliente.getCpf()
        );
    }

    @Test
    void deveCriarInvestimentoAPartirDoDTO() {
        Investimento investimento = new Investimento(dto, cliente);

        assertEquals(TipoInvestimento.TESOURO_SELIC, investimento.getTipo());
        assertEquals(5000.0f, investimento.getValorAplicado());
        assertEquals(LocalDate.of(2024, 1, 10), investimento.getDataAplicacao());
        assertEquals(5500.0f, investimento.getRendimentoAtual());
        assertEquals(cliente, investimento.getCliente());
    }

    @Test
    void deveAtualizarInvestimentoComNovoDTO() {
        Investimento investimento = new Investimento(dto, cliente);

        InvestimentoDTO novoDto = new InvestimentoDTO(
                investimento.getId(),
                TipoInvestimento.CDB,
                8000.0f,
                LocalDate.of(2024, 5, 15),
                8800.0f,
                cliente.getCpf()
        );

        investimento.atualizar(novoDto, cliente);

        assertEquals(TipoInvestimento.CDB, investimento.getTipo());
        assertEquals(8000.0f, investimento.getValorAplicado());
        assertEquals(LocalDate.of(2024, 5, 15), investimento.getDataAplicacao());
        assertEquals(8800.0f, investimento.getRendimentoAtual());
        assertEquals(cliente, investimento.getCliente());
    }

    @Test
    void equalsEHashCodeDevemUsarId() {
        Investimento i1 = new Investimento(dto, cliente);
        Investimento i2 = new Investimento(dto, cliente);

        i1.id = 1L;
        i2.id = 1L;

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoIdForDiferente() {
        Investimento i1 = new Investimento(dto, cliente);
        Investimento i2 = new Investimento(dto, cliente);

        i1.id = 1L;
        i2.id = 2L;

        assertNotEquals(i1, i2);
    }




}