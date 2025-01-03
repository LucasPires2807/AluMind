package com.br.alumind.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;

@Service
public class DataLoaderService {
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    @Value("classpath:/feedback_negativo_exemplo.pdf")
    private Resource pdfResource;

    @Autowired
    private VectorStore vectorStore;
    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);


    /*
    *  O método load() usa a classe
    *  PagePdfDocumentReader para ler um arquivo PDF
    *  e carregá-lo no Vector DB
    * */

    public void load() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(this.pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        var tokenTextSplitter = new TokenTextSplitter();
        this.vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
        //log.info("Documentos Recuperados para [%s]:".formatted(pdfReader.get()));

    }
}
