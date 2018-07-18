package io.m4ks.payment;

import com.google.common.collect.ImmutableList;
import io.m4ks.payment.controllers.PaymentsController;
import io.m4ks.payment.controllers.PersonRepository;
import io.m4ks.payment.model.PaymentResource;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsController.class)
public class PaymentApiLinksTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository personRepository;
    private UUID randomUUID;

    @Before
    public void before() {
        randomUUID = UUID.randomUUID();

        PaymentResource paymentResource = new PaymentResource();
        paymentResource.setId(randomUUID);

        given(personRepository.findById(any())).willReturn(Optional.of(paymentResource));
        given(personRepository.findAll()).willReturn(ImmutableList.of(paymentResource));
        given(personRepository.save(any())).willReturn(paymentResource);
    }

	@Test
	public void getSingleHasLinkToSelf() throws Exception {

        mvc.perform(get("/v1/payment/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("links.self", endsWith(randomUUID.toString())));
    }

    @Test
	public void getListHasLinkToSelf() throws Exception {

        mvc.perform(get("/v1/payment/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("links.self", endsWith("/v1/payment/")));
    }

    @Test
	public void postHasLinkToNewlyCreated() throws Exception {

        String json = IOUtils.toString(getClass().getResourceAsStream("/payment-1.json"), Charset.forName("UTF-8"));

        mvc.perform(post("/v1/payment/").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("links.get", endsWith(randomUUID.toString())));
    }

    @Test
	public void putHasNoLinks() throws Exception {

        String json = IOUtils.toString(getClass().getResourceAsStream("/payment-1.json"), Charset.forName("UTF-8"));

        mvc.perform(put("/v1/payment/{id}", randomUUID.toString()).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("links").isEmpty()); // no links
    }

    @Test
	public void deleteHasNoLinks() throws Exception {

        String json = IOUtils.toString(getClass().getResourceAsStream("/payment-1.json"), Charset.forName("UTF-8"));

        mvc.perform(delete("/v1/payment/{id}", randomUUID.toString()).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("links").isEmpty()); // no links
    }

}
