package com.healsync.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.Topics;
import com.healsync.notification.messages.SystemMessages;
import com.healsync.notification.service.KafkaProducerService;
import com.healsync.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void shouldSendNotificationSuccessfully() throws Exception {
        NotificationRequest request = new NotificationRequest();
        request.setRecipient("test@example.com");
        request.setSubject("Test Subject");
        request.setBody("This is a test notification.");
        request.setTopic(Topics.NOTIFICATION_TOPIC);

        // Convert NotificationRequest object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Act
        mockMvc.perform(post(ApiNames.NotificationControllerHeader+ApiNames.NotificationControllerSend)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(SystemMessages.NotificationSentSuccessfully));

        verify(kafkaProducerService, times(1)).sendEvent(request);
    }

    @Test
    void shouldReturnBadRequestWhenTopicIsNullOrEmptyInvalid() throws Exception {
        // Act
        mockMvc.perform(post(ApiNames.NotificationControllerHeader+ApiNames.NotificationControllerSend)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))  // Sending an empty body to simulate an invalid request
                .andExpect(status().isBadRequest());  // Assert that the response status is 400 Bad Request
    }
}
