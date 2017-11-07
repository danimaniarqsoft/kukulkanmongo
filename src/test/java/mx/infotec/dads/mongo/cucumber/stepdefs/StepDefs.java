package mx.infotec.dads.mongo.cucumber.stepdefs;

import mx.infotec.dads.mongo.KukulkanmongoApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = KukulkanmongoApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
