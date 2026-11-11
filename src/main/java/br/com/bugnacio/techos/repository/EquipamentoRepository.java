package br.com.bugnacio.techos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.model.TipoEquipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{


boolean existsByNumeroSerie(String numeroSerie);

    
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

    
    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);

   
    List<Equipamento> findByClienteId(Long clienteId);

   
    List<Equipamento> findByTipo(TipoEquipamento tipo);


}
