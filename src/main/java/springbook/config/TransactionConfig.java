package springbook.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Import(TransactionManagerConfig.class)
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor transactionInterceptor() {
        Map<String, TransactionAttribute> txMethods = new HashMap<>();
        txMethods.put("*", newTransactionAttribute(true, TransactionDefinition.PROPAGATION_REQUIRED));
        txMethods.put("get*", newTransactionAttribute(true, TransactionDefinition.PROPAGATION_REQUIRED));
        txMethods.put("upgrade*", newTransactionAttribute(false, TransactionDefinition.PROPAGATION_REQUIRES_NEW));

        NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
        txAttributeSource.setNameMap(txMethods);

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionAttributeSource(txAttributeSource);
        transactionInterceptor.setTransactionManager(transactionManager);
        return transactionInterceptor;
    }

    private TransactionAttribute newTransactionAttribute(final boolean readOnly, final int transactionDefinition) {
        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
        txAttribute.setReadOnly(readOnly);
        txAttribute.setPropagationBehavior(transactionDefinition);
        return txAttribute;
    }
}
