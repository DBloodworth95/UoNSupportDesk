package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.MessageSubmitRequestAccepted;
import command.TicketMessagesRequestAccepted;
import conversation.Message;
import conversation.MessageList;
import javaobject.MessageSubmitRequestDenied;
import javaobject.ObjectCommand;
import javaobject.SubmitMessageRequest;
import protobuf.ProtoMessageBuffer;
import repository.MessageRepository;
import repository.UserTicketRepository;
import utils.ObjectDeserializer;
import utils.ObjectSerializer;

public class MessageService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public int getParticipant(JsonNode ticketDetails) {
        int participantId = 0;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            participantId = UserTicketRepository.getParticipantOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            participantId = UserTicketRepository.getParticipantOfTechnicalTicket(ticketId);
        }

        return participantId;
    }

    public int getAuthor(JsonNode ticketDetails) {
        int authorId = 0;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            authorId = UserTicketRepository.getAuthorOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            authorId = UserTicketRepository.getAuthorOfTechnicalTicket(ticketId);
        }

        return authorId;
    }

    public int getSendTo(JsonNode ticketDetails) {
        int sendTo;
        int messageAuthor = ticketDetails.get("authorId").asInt();
        int ticketParticipant = getParticipant(ticketDetails);

        if (messageAuthor == ticketParticipant) {
            sendTo = getAuthor(ticketDetails);
        } else {
            sendTo = ticketParticipant;
        }

        return sendTo;
    }

    public String submitMessage(JsonNode ticketDetails) {
        Message message;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();
        String body = ticketDetails.get("messageBody").asText();
        String timestamp = ticketDetails.get("timestamp").asText();
        int authorId = ticketDetails.get("authorId").asInt();

        message = MessageRepository.submit(ticketId, ticketType, body, timestamp, authorId);

        if (message == null) return generateFailedResponse();

        String response = generateSuccessMessageResponse(message);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String getTicketMessages(JsonNode ticketDetails) {
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        MessageList messageList = MessageRepository.findAll(ticketId, ticketType);
        if (messageList == null) return generateFailedResponse();

        String response = generateSuccessResponse(messageList);
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateSuccessMessageResponse(Message message) {
        MessageSubmitRequestAccepted messageSubmitRequestAccepted = new MessageSubmitRequestAccepted(message.getTicketId(), message.getTicketType(),
                message.getMessage(), message.getTimestamp(), message.getAuthorId());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(messageSubmitRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateSuccessResponse(MessageList messageList) {
        TicketMessagesRequestAccepted ticketMessagesRequestAccepted = new TicketMessagesRequestAccepted(messageList.getTicketId(), messageList.getTicketType(),
                messageList.getMessages());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(ticketMessagesRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }

    public ProtoMessageBuffer.ProtoMessage submitMessageFromProto(ProtoMessageBuffer.ProtoMessage msg) {
        Message message;
        int ticketId = msg.getSubmitInstantMessageRequest().getTicketId();
        String ticketType = msg.getSubmitInstantMessageRequest().getTicketType();
        String body = msg.getSubmitInstantMessageRequest().getBody();
        String timestamp = msg.getSubmitInstantMessageRequest().getTimestamp();
        int authorId = msg.getSubmitInstantMessageRequest().getAuthorId();

        message = MessageRepository.submit(ticketId, ticketType, body, timestamp, authorId);

        if (message == null) return generateFailedResponseProto();

        ProtoMessageBuffer.ProtoMessage response = generateSuccessMessageResponseProto(message);
        if (!response.isInitialized()) return generateFailedResponseProto();

        return response;
    }

    private ProtoMessageBuffer.ProtoMessage generateFailedResponseProto() {
        ProtoMessageBuffer.ProtoMessage.Builder failedResponse = ProtoMessageBuffer.ProtoMessage.newBuilder();
        failedResponse.setCommand("failedinstantmessage");

        return failedResponse.build();
    }

    private ProtoMessageBuffer.ProtoMessage generateSuccessMessageResponseProto(Message message) {
        if (message != null) {
            ProtoMessageBuffer.ProtoMessage.Builder successMessageResponse = ProtoMessageBuffer.ProtoMessage.newBuilder()
                    .setCommand("incomingmessage")
                    .setMessageSubmitRequestAccepted(ProtoMessageBuffer.MessageSubmitRequestAccepted.newBuilder()
                            .setTicketId(message.getTicketId())
                            .setAuthorId(message.getAuthorId())
                            .setTicketType(message.getTicketType())
                            .setBody(message.getMessage())
                            .build());

            return successMessageResponse.build();
        } else return null;
    }

    public byte[] submitMessageFromJavaObject(byte[] messageRequest) {
        SubmitMessageRequest submitMessageRequest;
        Message message;

        submitMessageRequest = (SubmitMessageRequest) ObjectDeserializer.deserialize(messageRequest);

        if (submitMessageRequest != null) {
            byte[] successResponse;

            int ticketId = submitMessageRequest.getTicketId();
            String ticketType = submitMessageRequest.getTicketType();
            String body = submitMessageRequest.getMessageBody();
            String timestamp = submitMessageRequest.getTimestamp();
            int authorId = submitMessageRequest.getAuthorId();
            message = MessageRepository.submit(ticketId, ticketType, body, timestamp, authorId);

            successResponse = ObjectSerializer.serialize(generateSuccessMessageResponseForJavaObject(message));

            return successResponse;
        } else return generateFailedResponseObject();
    }

    private byte[] generateFailedResponseObject() {
        return ObjectSerializer.serialize(new MessageSubmitRequestDenied());
    }

    private ObjectCommand generateSuccessMessageResponseForJavaObject(Message message) {
        return new javaobject.MessageSubmitRequestAccepted(message.getTicketId(), message.getTicketType(), message.getMessage(),
                message.getTimestamp(), message.getAuthorId());
    }
}
