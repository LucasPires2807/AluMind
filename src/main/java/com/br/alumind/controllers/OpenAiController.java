package com.br.alumind.controllers;

import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.services.FeedbackService;
import com.br.alumind.services.OpenAiService;
import com.br.alumind.utils.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedeback")
public class OpenAiController {

}
