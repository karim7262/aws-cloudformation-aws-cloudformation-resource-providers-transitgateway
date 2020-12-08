package software.amazon.ec2.transitgatewaymulticastgroupsource;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.cloudformation.proxy.*;
import software.amazon.ec2.transitgatewaymulticastgroupsource.workflow.read.Read;

public class ReadHandler extends BaseHandlerStd {

    protected ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final ProxyClient<Ec2Client> proxyClient,
        final Logger logger) {
            return ProgressEvent.progress(request.getDesiredResourceState(), callbackContext)
                    .then(new Read(proxy, request, callbackContext, proxyClient, logger)::run);
    }
}
