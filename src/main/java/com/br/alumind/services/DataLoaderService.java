package com.br.alumind.services;

import com.br.alumind.models.FeedbackModel;
import com.br.alumind.repositories.FeedbackRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;


import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
//serviço para carregar os dados de arquivos pdf e ou csv
public class DataLoaderService {
    @Autowired
    private FeedbackRepository feedbackRepository;


    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);

    /*
    *  O método load() usa a classe
    *  PagePdfDocumentReader para ler um arquivo PDF
    *  e carregá-lo no Vector DB
    * */

    public List<Document> getContentFromPdf(Resource resource) {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        return pdfReader.get();
    }

    public List<Document> getContentFromCsv(Resource resource){
        try (Reader reader = Files.newBufferedReader(Paths.get(resource.getURI()))
             ; CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .builder()
                .setHeader().setSkipHeaderRecord(true)
                .setTrim(true)
                .setIgnoreEmptyLines(true)
                .setIgnoreHeaderCase(true)
                .build())) {
            List<Document> documentsToAdd = new ArrayList<>();
            System.out.println();
            System.out.println(csvParser);
            for (CSVRecord csvRecord : csvParser) {
                FeedbackModel feedbackModel = FeedbackModel.builder()
                        .id(Integer.parseInt(csvRecord.get("DOC_ID")))
                        .feedback(csvRecord.get("TEXT"))
                        .sentimento(csvRecord.get("SENTIMENT"))
                        .justificativa("")
                        //.justificativa(csvRecord.get("JUSTIFY"))
                        .build();
                feedbackRepository.save(feedbackModel);
                documentsToAdd.add(feedbackModel.toDocument(feedbackModel));
            }
            return documentsToAdd;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
