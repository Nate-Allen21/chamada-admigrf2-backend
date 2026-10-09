package br.com.admig.chamada.service;

import br.com.admig.chamada.dto.CalendarioResponse;
import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.model.Trimestre;
import br.com.admig.chamada.repository.LicaoRepository;
import br.com.admig.chamada.repository.TrimestreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

/**
 * A revista estudada segue o padrão de 13 lições por trimestre, uma por
 * domingo na Escola Dominical. Este serviço cuida da criação do trimestre e
 * da geração automática das 13 lições (13 domingos consecutivos) e também
 * calcula em qual lição a igreja está "agora" com base na data atual.
 */
@Service
@RequiredArgsConstructor
public class TrimestreService {

    private static final int TOTAL_LICOES = 13;

    private final TrimestreRepository trimestreRepository;
    private final LicaoRepository licaoRepository;

    public List<Trimestre> listarTodos() {
        return trimestreRepository.findAll();
    }

    public Trimestre buscarPorId(Long id) {
        return trimestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trimestre não encontrado"));
    }

    /**
     * Cria um trimestre e gera automaticamente as 13 lições, uma a cada
     * domingo, a partir da dataInicio informada (que é ajustada para o
     * domingo seguinte/mesmo, caso não caia num domingo).
     */
    public Trimestre criarComLicoes(Trimestre trimestre) {
        LocalDate primeiroDomingo = ajustarParaDomingo(trimestre.getDataInicio());
        trimestre.setDataInicio(primeiroDomingo);
        trimestre.setDataFim(primeiroDomingo.plusWeeks(TOTAL_LICOES - 1));

        Trimestre salvo = trimestreRepository.save(trimestre);

        for (int i = 0; i < TOTAL_LICOES; i++) {
            Licao licao = Licao.builder()
                    .numero(i + 1)
                    .data(primeiroDomingo.plusWeeks(i))
                    .titulo("Lição " + (i + 1))
                    .trimestre(salvo)
                    .build();
            licaoRepository.save(licao);
        }
        return salvo;
    }

    private LocalDate ajustarParaDomingo(LocalDate data) {
        if (data.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return data;
        }
        return data.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    }

    /**
     * Retorna o trimestre ativo "hoje" e a lição correspondente, para
     * alimentar o calendário exibido no painel.
     */
    public CalendarioResponse calendarioAtual() {
        LocalDate hoje = LocalDate.now();

        Optional<Trimestre> trimestreOpt = trimestreRepository
                .findByDataInicioLessThanEqualAndDataFimGreaterThanEqual(hoje, hoje);

        if (trimestreOpt.isEmpty()) {
            return new CalendarioResponse(null, List.of(), null, null);
        }

        Trimestre trimestre = trimestreOpt.get();
        List<Licao> licoes = licaoRepository.findByTrimestreIdOrderByNumeroAsc(trimestre.getId());

        Optional<Licao> licaoAtual = licaoRepository.findFirstByDataLessThanEqualOrderByDataDesc(hoje)
                .filter(l -> l.getTrimestre().getId().equals(trimestre.getId()));

        return new CalendarioResponse(
                trimestre,
                licoes,
                licaoAtual.orElse(null),
                licaoAtual.map(Licao::getNumero).orElse(null)
        );
    }

    public void excluir(Long id) {
        trimestreRepository.deleteById(id);
    }
}
