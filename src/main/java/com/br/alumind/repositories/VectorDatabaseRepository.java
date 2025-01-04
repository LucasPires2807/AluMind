package com.br.alumind.repositories;


import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;
import java.util.List;

//repositóey para conexão com o pgVector
@Component
public class VectorDatabaseRepository {

    private final VectorStore vectorStore;

    public VectorDatabaseRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }
    public void addDocuments(List<Document> docsToAdd) {
        vectorStore.add(docsToAdd);
    }
    public List<Document> similaritySearchWithTopK(String prompt, int topK) {
        SearchRequest searchRequest = SearchRequest.query(prompt).withTopK(topK);
        return vectorStore.similaritySearch(searchRequest);
    }
}
