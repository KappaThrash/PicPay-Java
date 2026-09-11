package bank.picpay.service;

import bank.picpay.Api.auth.auth.AuthorizeApi;
import bank.picpay.Api.exceptions.custom_exceptions.BusinessException;
import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Entidades.TransacaoEntity;
import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Transacao.Servicos.TransacaoService;
import bank.picpay.Dominio.notify.NotificationProducer;
import bank.picpay.Infra.repository.CarteiraRepository;
import bank.picpay.Infra.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    AuthorizeApi authorizeApi;

    @Mock
    NotificationProducer notificationProducer;

    @Mock
    TransacaoRepository transacaoRepository;

    @Mock
    CarteiraRepository carteiraRepository;

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoDTO dto;
    UsuarioEntity PayerAccount;
    CarteiraEntity PayerCarteira;
    UsuarioEntity PayeeAccount;
    CarteiraEntity PayeeCarteira;
    TransacaoEntity transacaoEntity;

    @BeforeEach
    void setup(){

        PayerAccount = new UsuarioEntity(UUID.randomUUID(), "a", TipoUsuario.USUARIO, "84.132.415/0001-09",
                "daniel@gmail.com","abc");
        PayerCarteira = new CarteiraEntity(UUID.randomUUID(),PayerAccount, new BigDecimal(1000));

        PayeeAccount = new UsuarioEntity(UUID.randomUUID(), "ab", TipoUsuario.LOJISTA, "850.987.415-80",
                "danielq@gmail.com","abc");
        PayeeCarteira = new CarteiraEntity(UUID.randomUUID(),PayeeAccount, new BigDecimal(1000));

        dto = new TransacaoDTO(new BigDecimal(10),PayerCarteira.getId(),PayeeCarteira.getId());

        transacaoEntity = new TransacaoEntity(UUID.randomUUID(),dto.getAmount(), PayerCarteira, PayeeCarteira, Instant.now());
    }


    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerAccountIsTipoLojista() {

        PayerAccount.setTipo(TipoUsuario.LOJISTA);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(PayerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(PayeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }

    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerBalanceIsNotEnough() {

        PayerCarteira.setBalance(BigDecimal.ONE);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(PayerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(PayeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }

    @Test
    void actTransacaoSuccess() {

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(PayerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(PayeeCarteira));

        when(authorizeApi.getAuth()).thenReturn(true);

        transacaoService.actTransacao(dto);
    }
}