package bank.picpay.service;

import bank.picpay.Api.auth.auth.AuthorizeApi;
import bank.picpay.Api.Exceptions.custom_exceptions.BusinessException;
import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Entidades.TransacaoEntity;
import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Transacao.Servicos.TransacaoService;
import bank.picpay.Dominio.Notify.NotificationProducer;
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
    UsuarioEntity payerAccount;
    CarteiraEntity payerCarteira;
    UsuarioEntity payeeAccount;
    CarteiraEntity payeeCarteira;
    TransacaoEntity transacaoEntity;

    @BeforeEach
    void setup(){

        payerAccount = new UsuarioEntity(UUID.randomUUID(), "a", TipoUsuario.USUARIO, "84.132.415/0001-09",
                "daniel@gmail.com","abc");
        payerCarteira = new CarteiraEntity(UUID.randomUUID(), payerAccount, new BigDecimal(1000));

        payeeAccount = new UsuarioEntity(UUID.randomUUID(), "ab", TipoUsuario.LOJISTA, "850.987.415-80",
                "danielq@gmail.com","abc");
        payeeCarteira = new CarteiraEntity(UUID.randomUUID(), payeeAccount, new BigDecimal(1000));

        dto = new TransacaoDTO(new BigDecimal(10), payerCarteira.getId(), payeeCarteira.getId());

        transacaoEntity = new TransacaoEntity(UUID.randomUUID(), dto.getAmount(), payerCarteira, payeeCarteira, Instant.now());
    }


    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerAccountIsTipoLojista() {

        payerAccount.setTipo(TipoUsuario.LOJISTA);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(payerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(payeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }

    @Test
    void actTransacaoShouldThrowBusinessExceptionLojistaBecausePayerBalanceIsNotEnough() {

        payerCarteira.setBalance(BigDecimal.ONE);

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(payerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(payeeCarteira));

        assertThrows(BusinessException.class, () -> transacaoService.actTransacao(dto));
    }

    @Test
    void actTransacaoSuccess() {

        when(carteiraRepository.findById(dto.getPayer()))
                .thenReturn(Optional.of(payerCarteira));

        when(carteiraRepository.findById(dto.getPayee()))
                .thenReturn(Optional.of(payeeCarteira));

        when(authorizeApi.getAuth()).thenReturn(true);

        transacaoService.actTransacao(dto);
    }
}