package com.br.alumind;

import com.br.alumind.controllers.FeedbackController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebMvcTest(FeedbackController.class)
class AlumindApplicationTests {
}
