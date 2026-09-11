package bank.picpay.Infra.repository;

import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
}
