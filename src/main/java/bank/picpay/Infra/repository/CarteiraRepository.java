package bank.picpay.Infra.repository;

import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarteiraRepository extends JpaRepository<CarteiraEntity, UUID> {
//    CarteiraEntity findByUser_id(UUID id);
}
