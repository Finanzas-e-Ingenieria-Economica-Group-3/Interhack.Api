package interhack.api.shared.seed;

import interhack.api.banking.models.entities.Bank;
import interhack.api.banking.models.entities.Rate;
import interhack.api.banking.models.enums.EPeriod;
import interhack.api.banking.models.enums.ERateType;
import interhack.api.banking.repositories.IBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankSeed implements CommandLineRunner {

    @Autowired
    private IBankRepository bankRepository;

    private void seedBankData() {
        var bankData = bankRepository.findAll();

        if (!bankData.isEmpty()) {
            return;
        }

        List<Bank> banks = List.of(
            new Bank(
                "Banco de la Nación",
                "https://toppng.com/uploads/thumbnail/banco-de-la-nacion-vector-logo-11573937106clg6nv6xjx.png",
                "20100030595",
                new Rate(
                    0.048,
                    ERateType.NOMINAL,
                    EPeriod.ANUAL
                )
            ),
            new Bank(
                "Banco de Crédito del Perú",
                "https://joseolaya.com/wp-content/uploads/2022/01/bcp-logo.png",
                "20100047218",
                new Rate(
                    0.051,
                    ERateType.EFECTIVA,
                    EPeriod.ANUAL
                )
            ),
            new Bank(
                "Interbank",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Interbank_logo.svg/1280px-Interbank_logo.svg.png",
                "20100053455",
                new Rate(
                    0.025,
                    ERateType.NOMINAL,
                    EPeriod.SEMESTRAL
                )
            ),
            new Bank(
                "Scotiabank",
                "https://cdn.worldvectorlogo.com/logos/scotiabank-5.svg",
                "20100043140",
                new Rate(
                    0.026,
                    ERateType.EFECTIVA,
                    EPeriod.SEMESTRAL
                )
            ),
            new Bank(
                "BBVA",
                "https://i.pinimg.com/originals/5a/55/9d/5a559d02bc8996d233ba05d8fb2d56e3.png",
                "20100130204",
                new Rate(
                    0.0125,
                    ERateType.NOMINAL,
                    EPeriod.TRIMESTRAL
                )
            ),
            new Bank(
                "Banco Pichincha",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Banco_Pichincha_logo.svg/1024px-Banco_Pichincha_logo.svg.png",
                "20100105862",
                new Rate(
                    0.0042,
                    ERateType.EFECTIVA,
                    EPeriod.MENSUAL
                )
            )
        );

        bankRepository.saveAll(banks);
    }

    @Override
    public void run(String... args) {
        seedBankData();
    }
}
