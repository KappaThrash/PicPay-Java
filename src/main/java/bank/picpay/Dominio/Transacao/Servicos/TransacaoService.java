package bank.picpay.Dominio.Transacao.Servicos;

import bank.picpay.Api.auth.auth.AuthorizeApi;
import bank.picpay.Api.Exceptions.custom_exceptions.BusinessException;
import bank.picpay.Api.Exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Entidades.TransacaoEntity;
import bank.picpay.Dominio.Notify.NotificationProducer;
import bank.picpay.Infra.repository.CarteiraRepository;
import bank.picpay.Infra.repository.TransacaoRepository;
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
