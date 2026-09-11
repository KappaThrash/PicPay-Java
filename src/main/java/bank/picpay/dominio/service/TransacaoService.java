package bank.picpay.dominio.service;

import bank.picpay.api.auth.auth.AuthorizeApi;
import bank.picpay.api.exceptions.custom_exceptions.BusinessException;
import bank.picpay.api.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.dominio.models.transacao.TransacaoDTO;
import bank.picpay.dominio.models.transacao.TransacaoEntity;
import bank.picpay.dominio.notify.NotificationProducer;
import bank.picpay.infra.repository.CarteiraRepository;
import bank.picpay.infra.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CarteiraRepository carteiraRepository;
    private final AuthorizeApi authorizeApi;
    private final NotificationProducer notificationProducer;

    public TransacaoService(TransacaoRepository transacaoRepository, CarteiraRepository carteiraRepository, AuthorizeApi authorizeApi, NotificationProducer notificationProducer) {
        this.transacaoRepository = transacaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.authorizeApi = authorizeApi;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public ResponseEntity<TransacaoEntity> actTransacao(TransacaoDTO dto){

        var PayerCarteira = carteiraRepository.findById(dto.getPayer())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payer não encontrada"));

        var PayeeCarteira = carteiraRepository.findById(dto.getPayee())
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira do Payee não encontrada"));

        var PayerAccount = PayerCarteira.getUser_id();
        var PayeeAccount = PayeeCarteira.getUser_id();

        BigDecimal TransactionValue = dto.getAmount();

        if(PayerAccount.isLOJISTA()){
            throw new BusinessException("Usuarios do tipo LOJISTA não podem efetuar transferencias");
        }

        if(PayerCarteira.getBalance().compareTo(TransactionValue) < 0){
            throw new BusinessException("Saldo insuficiente");
        }

        if(!authorizeApi.getAuth()){
           throw new BusinessException("Não autorizado");
        }

        PayerCarteira.debit(TransactionValue);
        PayeeCarteira.credit(TransactionValue);


        var SavingTransacaoEntity = new TransacaoEntity();
        SavingTransacaoEntity.mapDTOToEntity(dto, PayerCarteira, PayeeCarteira);
        transacaoRepository.save(SavingTransacaoEntity);

        notificationProducer.postTransactionNotification(PayerAccount, PayeeAccount, TransactionValue, SavingTransacaoEntity.getCreated_at());

        return ResponseEntity.status(HttpStatus.CREATED).body(SavingTransacaoEntity);
    }

}
