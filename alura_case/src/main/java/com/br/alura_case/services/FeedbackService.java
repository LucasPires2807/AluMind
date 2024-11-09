package com.br.alura_case.services;

import org.springframework.stereotype.Service;

@Service
public class FeedbackService  {

    private final FeedbackRepository repository;
    private final FeedbackDTO dto;
    public List<FeedebackDTO> findAll() {
        List<Feedeback> Feedebacks = repository.findAll();
        return Feedebacks.stream().map(Feedeback -> {
            return dto.buildFeedebackToResponseFeedeback(Feedeback);
        }).toList();
    }
    public FeedebackDTO findById(UUID id){
        Optional<Feedeback> Feedeback = repository.findById(id);
        FeedebackDTO responseFeedeback = null;

        if(Feedeback.isPresent()){
            responseFeedeback = dto.buildFeedebackToResponseFeedeback(Feedeback.get());
        }
        return responseFeedeback;
    }
    public FeedebackDTO Save(FeedebackDTO.RequestFeedeback requestFeedeback){
        FeedebackDTO responseFeedeback = null;
        Optional<Conta> conta  = contaRepository.findById(requestFeedeback.conta_id());
        if(conta.isPresent()){
            Feedeback Feedeback = new Feedeback().builder()
                    .descricao(requestFeedeback.descricao())
                    .valor(requestFeedeback.valor())
                    .valor_pagar(requestFeedeback.valor_pagar())
                    .tipo_juros(requestFeedeback.tipo_juros())
                    .juros(requestFeedeback.juros())
                    .data_inicio(requestFeedeback.data_inicio())
                    .data_final(requestFeedeback.data_final())
                    .conta(conta.get())
                    .build();

            Feedeback createFeedeback = repository.save(Feedeback);
            responseFeedeback = dto.buildFeedebackToResponseFeedeback(createFeedeback);
        }
        return responseFeedeback;
    }

    public boolean delete(UUID id){
        boolean isDelet = false;
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
            isDelet = true;
        }
        return isDelet;
    }

    public List<FeedebackDTO> findByUser(UUID id) {
        List<Feedeback> list = repository.findAll();
        List<FeedebackDTO> responseFeedeback = new ArrayList<>();
        if(!list.isEmpty()){
            list.forEach(Feedeback -> {
                if(id.equals(Feedeback.getConta().getUser().getId())){
                    responseFeedeback.add(dto.buildFeedebackToResponseFeedeback(Feedeback));
                }
            });
        }
        return responseFeedeback;
    }

}
