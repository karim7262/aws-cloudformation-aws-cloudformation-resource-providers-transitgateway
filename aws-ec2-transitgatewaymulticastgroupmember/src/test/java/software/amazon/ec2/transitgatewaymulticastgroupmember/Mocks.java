package software.amazon.ec2.transitgatewaymulticastgroupmember;

import org.apache.commons.lang3.SerializationUtils;
import software.amazon.awssdk.services.ec2.model.*;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mocks {
    public String transitGatewayMulticastDomainId;
    public String groupIpAddress;
    public Instant currentTime;
    public Integer counter;

    public Map<String, String> defaults;

    public Mocks() {
        this.transitGatewayMulticastDomainId = "tgw-mcast-domain-02bb79002EXAMPLE";
        this.currentTime = Instant.now();
        this.counter = 0;
        this.defaults = Stream.of(new String[][] {
                { "transitGatewayMulticastDomainId", "tgw-mcast-domain-02bb79002EXAMPLE" },
                { "transitGatewayAttachmentId", "tgw-att-02bb79002EXAMPLE" },
                { "groupIpAddress", "224.0.0.3" },
                { "subnetId", "subnet-e4f648c5" },
                { "resourceId", "vpc-75f43808" },
                { "resourceType", "vpc" },
                { "networkInterfaceId", "eni-0406f0e5cb9a840ea" },
                { "groupMember", "true" },
                { "groupSource", "true" },
                { "memberType", "static" },
                { "sourceType", "static" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    public Map<String, String> modelMap() {
        return this.modelMap(Collections.emptyMap());
    }

    public Map<String, String> modelMap(Map<String, String> newMap) {
        HashMap<String, String> defaultsHashMap = new HashMap<>(this.defaults);
        Map<String, String> defaultsCopy = SerializationUtils.clone(defaultsHashMap);
        defaultsCopy.putAll(newMap);
        return defaultsCopy;
    }
    public  ResourceHandlerRequest<ResourceModel> request(ResourceModel model) {
        return ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();
    }

    public  ResourceModel model() {
        return this.model(Collections.emptyMap());
    }

    public  ResourceModel model(Map<String, String> newMap) {
        Map<String, String> values = this.modelMap(newMap);
        return ResourceModel.builder()
            .transitGatewayMulticastDomainId(values.get("transitGatewayMulticastDomainId"))
            .transitGatewayAttachmentId(values.get("transitGatewayAttachmentId"))
            .groupIpAddress(values.get("groupIpAddress"))
            .groupMember(values.get("groupMember").equals("true"))
            .groupSource(values.get("groupSource").equals("true"))
            .networkInterfaceId(values.get("networkInterfaceId"))
            .memberType(values.get("memberType"))
            .subnetId(values.get("subnetId"))
            .resourceId(values.get("resourceId"))
            .resourceType(values.get("resourceType"))
            .sourceType(values.get("sourceType"))
            .build();
    }



    public TransitGatewayMulticastGroup sdkModel() {
        return this.sdkModel(Collections.emptyMap());
    }

    public TransitGatewayMulticastGroup sdkModel(Map<String, String> newMap) {
        Map<String, String> values = this.modelMap(newMap);

        return TransitGatewayMulticastGroup.builder()
            .transitGatewayAttachmentId(values.get("transitGatewayAttachmentId"))
            .groupIpAddress(values.get("groupIpAddress"))
            .groupMember(values.get("groupMember").equals("true"))
            .groupSource(values.get("groupSource").equals("true"))
            .networkInterfaceId(values.get("networkInterfaceId"))
            .memberType(values.get("memberType"))
            .resourceId(values.get("resourceId"))
            .subnetId(values.get("subnetId"))
            .resourceType(values.get("resourceType"))
            .sourceType(values.get("sourceType"))
            .build();
    }


    public SearchTransitGatewayMulticastGroupsResponse readResponse() {
        return this.readResponse(Collections.emptyMap());

    }

    public SearchTransitGatewayMulticastGroupsResponse readResponse(Map<String, String> newMap) {
        return SearchTransitGatewayMulticastGroupsResponse.builder()
            .multicastGroups(
               this.sdkModel(newMap)
            )
        .build();
    }

    public SearchTransitGatewayMulticastGroupsResponse emptyReadResponse() {
        java.util.List<TransitGatewayMulticastGroup> emptyMulticastGroups = new ArrayList<>();
        return SearchTransitGatewayMulticastGroupsResponse.builder()
            .multicastGroups(emptyMulticastGroups)
        .build();
    }

    public SearchTransitGatewayMulticastGroupsResponse listResponse() {
        return this.listResponse(Collections.emptyMap());

    }

    public SearchTransitGatewayMulticastGroupsResponse listResponse(Map<String, String> newMap) {
        return SearchTransitGatewayMulticastGroupsResponse.builder()
            .multicastGroups(
               this.sdkModel(newMap)
            )
        .build();
    }


    public DeregisterTransitGatewayMulticastGroupMembersResponse deleteResponse() {
        return this.deleteResponse(Collections.emptyMap());
    }

    public DeregisterTransitGatewayMulticastGroupMembersResponse deleteResponse(Map<String, String> newMap) {
        Map<String, String> values = this.modelMap(newMap);
        return DeregisterTransitGatewayMulticastGroupMembersResponse.builder()
            .deregisteredMulticastGroupMembers(
                TransitGatewayMulticastDeregisteredGroupMembers.builder()
                    .transitGatewayMulticastDomainId(values.get("transitGatewayMulticastDomainId"))
                    .groupIpAddress(values.get("transitGatewayMulticastDomainId"))
                    .deregisteredNetworkInterfaceIds(values.get("transitGatewayMulticastDomainId"))
                    .build()
            )
            .build();
    }


    public RegisterTransitGatewayMulticastGroupMembersResponse createResponse() {
        return this.createResponse(Collections.emptyMap());

    }
    public RegisterTransitGatewayMulticastGroupMembersResponse createResponse(Map<String, String> newMap) {
        Map<String, String> values = this.modelMap(newMap);

        return RegisterTransitGatewayMulticastGroupMembersResponse.builder()
            .registeredMulticastGroupMembers(
                TransitGatewayMulticastRegisteredGroupMembers.builder()
                    .transitGatewayMulticastDomainId(values.get("transitGatewayMulticastDomainId"))
                    .groupIpAddress(values.get("transitGatewayMulticastDomainId"))
                    .registeredNetworkInterfaceIds(values.get("transitGatewayMulticastDomainId"))
                .build()
            )
        .build();
    }



}
