package br.com.bugnacio.techos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 

import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.model.TipoEquipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    boolean existsByNumeroSerie(String numeroSerie);

    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);

    List<Equipamento> findByClienteId(Long clienteId);

    List<Equipamento> findByTipo(TipoEquipamento tipo);

    // <--- ADICIONADO (Método que busca por modelo, nº de série, nome do cliente ou tipo)
    @Query("SELECT e FROM Equipamento e WHERE " +
           "LOWER(e.modelo) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
           "LOWER(e.numeroSerie) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
           "LOWER(e.cliente.nome) LIKE LOWER(CONCAT('%', :busca, '%')) OR " +
           "LOWER(CAST(e.tipo AS string)) LIKE LOWER(CONCAT('%', :busca, '%'))")
    List<Equipamento> buscarPorTermo(@Param("busca") String busca);

}