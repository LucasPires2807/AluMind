package com.br.alumind.runner;

import com.br.alumind.repositories.VectorDatabaseRepository;
import com.br.alumind.services.DataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = {"database.init"}, havingValue = "true")
public class DataLoadRunner implements ApplicationRunner {
    private final VectorDatabaseRepository documentRepository;
    private final DataLoaderService service;

    @Value("classpath:sample_csv.csv")
    private Resource resourceCsv;

    @Value("classpath:sample_pdf.pdf")
    private Resource resourcePdf;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Adding documents to vector store from CSV");

        List<Document> documentsCSV = service.getContentFromCsv(resourceCsv);
        documentsCSV.forEach(doc -> log.info("Document: {}", doc));
        //documentRepository.addDocuments(documentsCSV);
        log.info("done!");

        log.info("Adding documents to vector store from PDF");
        List<Document> documentsPDF = service.getContentFromPdf(resourcePdf);
        documentsPDF.forEach(doc -> log.debug("Document: {}", doc));
        //documentRepository.addDocuments(documentsPDF);
        log.info("done!");

        log.info("Adding documents to vector store from List");
        List<Document> documents = List.of(
                new Document("Java is a high-level, object-oriented programming language known for its platform independence."),
                new Document("It is widely used for developing enterprise applications, Android apps, and big data processing systems."),
                new Document("Java's strong typing, automatic memory management, and extensive libraries contribute to its popularity.", Map.of("reason", "popularity"))
        );

        // Add the documents to PGVector
        //documentRepository.addDocuments(documents);
        log.info("done!");
    }
}