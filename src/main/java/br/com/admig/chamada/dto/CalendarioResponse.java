package br.com.admig.chamada.dto;

import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.model.Trimestre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalendarioResponse {
    private Trimestre trimestre;
    private List<Licao> licoes;
    private Licao licaoAtual;
    private Integer numeroLicaoAtual;
}
