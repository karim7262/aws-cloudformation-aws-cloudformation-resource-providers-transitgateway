package software.amazon.ec2.transitgatewaymulticastgroupsource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.SearchTransitGatewayMulticastGroupsRequest;
import software.amazon.cloudformation.proxy.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListHandlerTest extends AbstractTestBase {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private ProxyClient<Ec2Client> proxyClient;

    @Mock
    Ec2Client sdkClient;

    private ListHandler handler;

    @BeforeEach
    public void setup() {
        proxy = new AmazonWebServicesClientProxy(logger, MOCK_CREDENTIALS, () -> Duration.ofSeconds(600).toMillis());
        handler = new ListHandler();
        sdkClient = mock(Ec2Client.class);
        proxyClient = MOCK_PROXY(proxy, sdkClient);
    }

    @AfterEach
    public void tear_down() {
        verify(sdkClient, atLeastOnce()).searchTransitGatewayMulticastGroups(any(SearchTransitGatewayMulticastGroupsRequest.class));
        verifyNoMoreInteractions(sdkClient);
    }

    @Test
    public void handleRequest_SimpleSuccess() {
        ResourceModel model = MOCKS.model();
        final ResourceModel expectedResult =  ResourceModel.builder()
            .transitGatewayMulticastDomainId(model.getTransitGatewayMulticastDomainId())
            .networkInterfaceId(model.getNetworkInterfaceId())
            .groupIpAddress(model.getGroupIpAddress())
            .groupMember(model.getGroupMember())
            .groupSource(model.getGroupSource())
        .build();

        final List<ResourceModel> expectedResults = new ArrayList<>();
        expectedResults.add(expectedResult);

        when(proxyClient.client().searchTransitGatewayMulticastGroups(any(SearchTransitGatewayMulticastGroupsRequest.class))).thenReturn(MOCKS.listResponse());

        final ProgressEvent<ResourceModel, CallbackContext> response = handler.handleRequest(proxy, MOCKS.request(MOCKS.model()), new CallbackContext(), proxyClient, logger);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModels()).isEqualTo(expectedResults);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    public void handleRequest_Error() {
        AwsErrorDetails errorDetails = AwsErrorDetails.builder().errorMessage("Something went wrong").errorCode("Invalid Request").build();
        AwsServiceException exception = AwsServiceException.builder().awsErrorDetails(errorDetails).build();
        when(proxyClient.client().searchTransitGatewayMulticastGroups(any(SearchTransitGatewayMulticastGroupsRequest.class))).thenThrow(exception);
        ResourceModel model = MOCKS.model();

        final ProgressEvent<ResourceModel, CallbackContext> response = handler.handleRequest(proxy, MOCKS.request(model), new CallbackContext(), proxyClient, logger);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.FAILED);
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage().contains("Something went wrong")).isTrue();
        assertThat(response.getErrorCode()).isEqualTo(HandlerErrorCode.InvalidRequest);
    }

}
