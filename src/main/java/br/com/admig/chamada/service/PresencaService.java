package br.com.admig.chamada.service;

import br.com.admig.chamada.dto.RegistrarPresencaRequest;
import br.com.admig.chamada.model.Aluno;
import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.model.Presenca;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.repository.AlunoRepository;
import br.com.admig.chamada.repository.LicaoRepository;
import br.com.admig.chamada.repository.PresencaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final AlunoRepository alunoRepository;
    private final LicaoRepository licaoRepository;

    /**
     * Registra (ou atualiza) a presença/falta de vários alunos para uma
     * mesma lição em uma única chamada. Idempotente: se já existir um
     * registro para o par aluno+lição, ele é atualizado em vez de duplicado.
     */
    @Transactional
    public List<Presenca> registrarChamada(RegistrarPresencaRequest request, Usuario registradoPor) {
        Licao licao = licaoRepository.findById(request.getLicaoId())
                .orElseThrow(() -> new RuntimeException("Lição não encontrada"));

        return request.getItens().stream().map(item -> {
            Aluno aluno = alunoRepository.findById(item.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + item.getAlunoId()));

            Presenca presenca = presencaRepository.findByAlunoIdAndLicaoId(aluno.getId(), licao.getId())
                    .orElse(Presenca.builder().aluno(aluno).licao(licao).build());

            presenca.setPresente(item.getPresente());
            presenca.setObservacao(item.getObservacao());
            presenca.setRegistradoPor(registradoPor);

            return presencaRepository.save(presenca);
        }).toList();
    }

    public List<Presenca> listarPorLicaoEClasse(Long licaoId, Long classeId) {
        return presencaRepository.findByLicaoIdAndAluno_Classe_Id(licaoId, classeId);
    }

    public List<Presenca> listarPorAluno(Long alunoId) {
        return presencaRepository.findByAlunoId(alunoId);
    }
}
